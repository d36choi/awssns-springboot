# awssns-springboot

aws sdk 를 활용해 스프링부트 API 서버로 amazon SNS 서비스를 사용할 수 있습니다.  
spring boot API server for Amazon SNS client request

## features
- create topic
- subscribe
- publish message

## MUST DO
```properties
sns.topic.arn=
aws.accessKey=
aws.secretKey=
aws.region=
```
application.properties 의 항목들을 본인의 환경에 맞게 입력해야 합니다.  
you should add appropriate value in application.properties's key-value lines.

## guideline
[[AWS] amazon SNS + Spring Boot 이용한 메시지 서비스 만들기](https://choichumji.tistory.com/123)
