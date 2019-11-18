package com.example.ms.configuration

import com.google.common.collect.Lists
import com.hubspot.jackson.datatype.protobuf.ProtobufModule
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.*
import springfox.documentation.schema.configuration.ObjectMapperConfigured
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

/**
 * RESTControllerで作成したAPI群をSpringFoxを用いて
 * Swagger-UIを自動生成するための設定コントローラー
 */
@Configuration
@EnableSwagger2
class SwaggerConfig : ApplicationListener<ObjectMapperConfigured> {

    /**
     * Swagger-UIにProtocolBuffers形式を対応させるための処理
     */
    override fun onApplicationEvent(event: ObjectMapperConfigured) {
        // com.hubspot.jackson:jackson-datatype-protobufを使用
        event.objectMapper.registerModule(ProtobufModule())
        event.objectMapper.registerModule(SwaggerProtoConfig())
    }

    /**
     * Swagger-UIのドキュメントを作成する
     */
    @Bean
    fun document(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .produces(HashSet(listOf("application/json", "application/x-protobuf")))
            .globalResponseMessage(RequestMethod.GET, getMethodResponses())
            .securitySchemes(listOf(securityScheme()))
            .securityContexts(listOf(securityContext()))

    private fun securityScheme(): ApiKey {
        return ApiKey("JWT", HttpHeaders.AUTHORIZATION, "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Lists.newArrayList(
                SecurityReference("JWT", authorizationScopes))
    }

    /**
     * Swagger-UIに表示するAPI全体に関わる情報を記述する
     * Swagger-UIのTOPに表示される情報
     */
    private fun apiInfo(): ApiInfo = ApiInfoBuilder()
            .title("ユーザーサービス")
            .description("""
                # 概要
                ユーザーサービスのAPI仕様です。

                # 共通仕様
                ## Request Headers
                - `Content-Type` - POSTやPUT,DELETEは**application/json**の形式で送信する。

                ## Response Headers
                - `Content-Type` - ステータスコード200で返却される全てのデータは**application/x-protobuf**の形式で返却する。
                - `X-Protobuf-Schema` - XXX.protoの形式でXXXには使用された**protoファイル**を指定する。ex) MetaTitle.proto
                - `X-Protobuf-Message` - YYY.ZZZの形式でYYYには使用されたprotoファイルの**package名**、ZZZには**message名**を指定する。ex) title.Title
                """.trimIndent()
            )
            .version("1.0.0")
            .build()

    /**
     * GETメソッド共通のレスポンスを定義
     */
    private fun getMethodResponses(): List<ResponseMessage> = listOf(
            createResponseMessage(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.reasonPhrase),
            createResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase),
            createResponseMessage(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.reasonPhrase),
            createResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.reasonPhrase),
            createResponseMessage(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.reasonPhrase),
            createResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    )

    /**
     * レスポンスの構造体を作成
     */
    private fun createResponseMessage(code: Int, message: String): ResponseMessage = ResponseMessageBuilder()
            .code(code)
            .message(message)
            .build()
}

