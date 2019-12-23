SELECT
  /*%expand "ub" */*
FROM users_body AS ub
WHERE ub.user_id = /* userId */1
