### Description
Spring Security OAuth server app with JWT.

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