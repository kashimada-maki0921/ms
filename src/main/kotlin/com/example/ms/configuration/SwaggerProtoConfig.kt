package com.example.ms.configuration

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.core.util.VersionUtil
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.*
import com.google.protobuf.Descriptors
import com.google.protobuf.Message
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Jackson module that works together with com.hubspot.jackson.datatype.protobuf.ProtobufModule.
 * When added to the springfox ObjectMapper, it allows protobuf messages to show up in swagger ui.
 * SpringFoxでProtocolBuffersのモデル定義を参照するためのライブラリ
 * Kotlinに変換後部分修正
 * https://github.com/innogames/springfox-protobuf
 *
 * Copyright (c) 2018 InnoGames GmbH
 */
class SwaggerProtoConfig : Module() {

    private val cache = ConcurrentHashMap<Class<*>, Map<String, Descriptors.FieldDescriptor>>()

    override fun getModuleName(): String {
        return "SwaggerProtoConfig"
    }

    override fun version(): Version {
        return VersionUtil.packageVersionFor(javaClass)
    }

    override fun setupModule(context: SetupContext) {

        context.setClassIntrospector(ProtobufClassIntrospector())

        context.insertAnnotationIntrospector(object : NopAnnotationIntrospector() {

            override fun findAutoDetectVisibility(ac: AnnotatedClass?, checker: VisibilityChecker<*>): VisibilityChecker<*> {
                return if (Message::class.java.isAssignableFrom(ac!!.rawType)) {
                    checker.withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                } else super.findAutoDetectVisibility(ac, checker)
            }
        })

    }

    internal inner class ProtobufClassIntrospector : BasicClassIntrospector() {

        override fun forDeserialization(cfg: DeserializationConfig, type: JavaType, r: MixInResolver): BasicBeanDescription {
            val desc = super.forDeserialization(cfg, type, r)

            return if (Message::class.java.isAssignableFrom(type.rawClass)) {
                protobufBeanDescription(cfg, type, r, desc)
            } else desc

        }

        override fun forSerialization(cfg: SerializationConfig, type: JavaType, r: MixInResolver): BasicBeanDescription {
            val desc = super.forSerialization(cfg, type, r)

            return if (Message::class.java.isAssignableFrom(type.rawClass)) {
                protobufBeanDescription(cfg, type, r, desc)
            } else desc

        }

        private fun protobufBeanDescription(cfg: MapperConfig<*>, type: JavaType, r: MixInResolver, baseDesc: BasicBeanDescription): BasicBeanDescription {

            val types = cache.getOrPut(type.rawClass) { getDescriptorForType(type.rawClass) }

            val ac = AnnotatedClassResolver.resolve(cfg, type, r)

            val props = ArrayList<BeanPropertyDefinition>()

            for (p in baseDesc.findProperties()) {
                val name = p.name

                if (types.containsKey(name)) {
                    props.add(p.withSimpleName(name))
                    continue
                }

                // special handler for Lists
                if (name.endsWith("List") && types.containsKey(substr(name, 4))) {
                    props.add(p.withSimpleName(substr(name, 4)))
                }

            }

            return object : BasicBeanDescription(cfg, type, ac, props) {
            }
        }

        private fun getDescriptorForType(type: Class<*>): Map<String, Descriptors.FieldDescriptor> {
            return try {
                val invoke = type.getMethod("getDescriptor").invoke(null) as Descriptors.Descriptor
                val descriptorsForType = HashMap<String, Descriptors.FieldDescriptor>()
                invoke.fields.stream().forEach { fieldDescriptor ->
                    descriptorsForType[fieldDescriptor.name] = fieldDescriptor
                    descriptorsForType[fieldDescriptor.jsonName] = fieldDescriptor
                }
                descriptorsForType
            } catch (e: Exception) {
                println("Error getting ProtocolBuffers descriptor for swagger.")
                HashMap()
            }
        }

        private fun substr(name: String, cnt: Int): String {
            return name.substring(0, name.length - cnt)
        }
    }
}