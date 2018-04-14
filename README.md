## Webflux-fn Example
함수형 스타일 웹플럭스를 이용한 사용자 생성조회 예제

## 작성한 것들.
- 함수형 webflux 코드
  - router, handler
- spring-data-monog(embedded mongodb 사용)
- hibernate-validator를 이용한 JSR-303() 설정 추가
- test 코드

## entrypoint
- 사용자생성
```http
POST http://localhost:8080/api/user/create
Content-Type: application/json

{
    "userid": "userid",
    "password": "password",
    "name": "부종민"
}
```
- 사용자조회
```
POST http://localhost:8080/api/user/select
Content-Type: application/json

userid
```

## 실행
gradle bootRun

## intellij Ultimate 사용시.
프로젝트 루트내에 있는 api.http 파일을 이용하여 api 테스트를 할 수 있습니다.
