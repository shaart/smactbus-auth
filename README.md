### Description
Spring Security OAuth server app with JWT.

By default contains users created via `DatabaseInitializer`:
- email: "admin@example.com", password: "admin"
- email: "user@example.com", password: "user"

### JWT
Sample JWT
```text
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTU5MzMwNTgsInVzZXJfbmFtZSI6ImFkbWluQGV4YW1wbGUuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI0YTgzN2EzNC1jM2ZmLTRhMGQtOTBhMi0wNmU5NzhlODdlNGQiLCJjbGllbnRfaWQiOiJzbWFjdGJ1cy1hcGkiLCJzY29wZSI6WyJyZWFkIl19.cjgCqgqscGjSEo8fAtMqiFGZQAPODlDbTUsdooBV36mJsn1IuNihWR6gAHU9fKQT3GOETN0sjMaQZ_FfeIBqFHocjEM3zao7u0kXn2RRTeo6rXArHMHbtyVQA5b36eyPTkCqkB2-lqQN4O0ew-nczHBAD3Zgbpssz4586NiBS4p-AGs86tCgaynpQiDqmYMX_j6By-q0EGgNwlUJyjg7NaosUbn9tABQYipIzd2DDeSKTaFzkEKcrUNoilhnizbd72lgrt-4W6tCH4UDaBLG-eBvBNiZokIlPR9o954LIqldHTgRdQDCuzUnFTOEaBozG7gK3XRxeb1SwD8mdbMJJw
```

Sample JWT Decoded

Header
```json
{
  "alg": "RS256",
  "typ": "JWT"
}
```

Payload
```json
{
  "exp": 1655933058,
  "user_name": "admin@example.com",
  "authorities": [
    "ROLE_ADMIN"
  ],
  "jti": "4a837a34-c3ff-4a0d-90a2-06e978e87e4d",
  "client_id": "smactbus-api",
  "scope": [
    "read"
  ]
}
```

### Requests
Create user
```shell
curl --location --request POST 'http://localhost:9000/api/signin' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=B065803559D1EA89ACB39F4CD04969F7' \
--data-raw '{
    "email": "user@example.com",
    "password": "user"
}'
```

Create admin
```shell
curl --location --request POST 'http://localhost:9000/api/signin/admin' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=B065803559D1EA89ACB39F4CD04969F7' \
--data-raw '{
    "username": "admin@example.com",
    "password": "admin"
}'
```

Login as USER via username & password
```shell
curl --location --request POST 'http://localhost:9000/oauth/token' \
--header 'Authorization: Basic c21hY3RidXMtYXBpOnNtYWN0YnVzLXNlY3JldA==' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=B065803559D1EA89ACB39F4CD04969F7' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=read' \
--data-urlencode 'username=user@example.com' \
--data-urlencode 'password=user'
```

Login as ADMIN via username & password
```shell
curl --location --request POST 'http://localhost:9000/oauth/token' \
--header 'Authorization: Basic c21hY3RidXMtYXBpOnNtYWN0YnVzLXNlY3JldA==' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=B065803559D1EA89ACB39F4CD04969F7' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=read' \
--data-urlencode 'username=admin@example.com' \
--data-urlencode 'password=admin'
```

Check JWT token
```shell
curl --location --request GET 'http://localhost:9000/oauth/check_token?token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTU5MzMwNTgsInVzZXJfbmFtZSI6ImFkbWluQGV4YW1wbGUuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI0YTgzN2EzNC1jM2ZmLTRhMGQtOTBhMi0wNmU5NzhlODdlNGQiLCJjbGllbnRfaWQiOiJzbWFjdGJ1cy1hcGkiLCJzY29wZSI6WyJyZWFkIl19.cjgCqgqscGjSEo8fAtMqiFGZQAPODlDbTUsdooBV36mJsn1IuNihWR6gAHU9fKQT3GOETN0sjMaQZ_FfeIBqFHocjEM3zao7u0kXn2RRTeo6rXArHMHbtyVQA5b36eyPTkCqkB2-lqQN4O0ew-nczHBAD3Zgbpssz4586NiBS4p-AGs86tCgaynpQiDqmYMX_j6By-q0EGgNwlUJyjg7NaosUbn9tABQYipIzd2DDeSKTaFzkEKcrUNoilhnizbd72lgrt-4W6tCH4UDaBLG-eBvBNiZokIlPR9o954LIqldHTgRdQDCuzUnFTOEaBozG7gK3XRxeb1SwD8mdbMJJw' \
--header 'Cookie: JSESSIONID=85213F37161375B6DCC621741EAE07D7'
```

Default endpoint for JWKS
```shell
curl --location --request GET 'http://localhost:9000/oauth/token_key' \
--header 'Cookie: JSESSIONID=85213F37161375B6DCC621741EAE07D7'
```

Custom endpoint for JWKS
```shell
curl --location --request GET 'http://localhost:9000/.well-known/jwks.json' \
--header 'Cookie: JSESSIONID=85213F37161375B6DCC621741EAE07D7'
```

Check if has USER role
```shell
curl --location --request GET 'http://localhost:9000/api/test/user' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluQGV4YW1wbGUuY29tIiwic2NvcGUiOlsicmVhZCJdLCJleHAiOjE2NTU4Mzg1NDIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI4YjY0MGRmMC1iZWNmLTQxNDMtYmMwZC1mYzkxMzkxMjA3NGUiLCJjbGllbnRfaWQiOiJzbWFjdGJ1cy1hcGkifQ.GIjjwOeRAi4u764pytMoPM7Bkf6iVRK3do_aS3SWaUc' \
--header 'Cookie: JSESSIONID=B065803559D1EA89ACB39F4CD04969F7'
```

Check if has ADMIN role
```shell
curl --location --request GET 'http://localhost:9000/api/test/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluQGV4YW1wbGUuY29tIiwic2NvcGUiOlsicmVhZCJdLCJleHAiOjE2NTU4Mzg1NDIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI4YjY0MGRmMC1iZWNmLTQxNDMtYmMwZC1mYzkxMzkxMjA3NGUiLCJjbGllbnRfaWQiOiJzbWFjdGJ1cy1hcGkifQ.GIjjwOeRAi4u764pytMoPM7Bkf6iVRK3do_aS3SWaUc' \
--header 'Cookie: JSESSIONID=B065803559D1EA89ACB39F4CD04969F7'
```
