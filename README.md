# 트러블 슈팅

1. JPA Repository를 사용할때 메서드 이름의 중요성
### 상황 
- 품목 상세페이지에 댓글 기능을 추가하고 댓글이 많을 경우를 대비하여 페이지네이션 기능을 적용하기로 하였다
- URL을 "/detail/1?comment=1" 파라미터로 받는 형식으로 사용하기로 하였다 
- @RequestParm을 사용하여 파라미터로 받는데 값을 다 넘어오지만 JPA Query 오류가 발생하였다. 

### 원인
- 결과 부터 말하자면 JPA의 메서드명이 정확하지 않아서 거기에 대한 Query 메서드문을 만들지 못하는 오류였습니다.
- JPA는 메서드 이름으로 쿼리를 자동으로 생성한다.
- 그렇기 때문에 "findby~~" 이런식으로 메서드명을 하지않으면 오류가 발생할 수 있다.

```
ItemRepository
Page<Item> findPageBy(Pageable page);

CommentRepository
 Page<Comment> findPageBy(Long parentId, Pageable page);
```
- 이렇게 하면 CommentRepository에서는 오류가 난다 이유는 
- parentId를 통해 찾기때문에 메서드명에 findByparentId를 적어야 자동으로 적합한 쿼리를 생성해준다.

```
ItemRepository
Page<Item> findByPage(Pageable page);

CommentRepository
 Page<Comment> findByParentId(Long parentId, Pageable page);
```
- 그렇기 때문에 "findBy~~~" 이런형식으로 메서드를 구현해야 오류가 없어진다.
- 그리고 findBy를 사용할 때 뒤에 조건이 붙는다면 그 조건을 메서드에 넣어서 구현해야한다.

2. JWT 오류 발생
### 상황
- jwt 필터를 만들고 쿠기와 유효기간을 확인하고 auth에 정보를 넣었는데 다른것 다 되는데 auth,authority에 정보가 들어가지 않는다
- auth 가 null로 오류 발생중
```
java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getPrincipal()" because "auth" is null
	at com.hw.shopping.controller.MemberController.myPageJWT(MemberController.java:104) ~[classes/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:569) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:255) ~[spring-web-6.1.14.jar:6.1.14]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:188) ~[spring-web-6.1.14.jar:6.1.14]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.1.14.jar:6.1.14]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:926) ~[spring-webmvc-6.1.14.jar:6.1.14]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:831) ~[spring-webmvc-6.1.14.jar:6.1.14]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.1.14.jar:6.1.14]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1089) ~[spring-webmvc-6.1.14.jar:6.1.14]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979) ~[spring-webmvc-6.1.14.jar:6.1.14]
```

### 원인
```
UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            customUser, null
        );
```
- authToken에 authorities를 같이 넘겨주지 않았기 때문이다.
- UsernamePasswordAuthenticationToken 생성 시 authorities(사용자의 권한 정보)가 없는 경우
- Spring Security가 인증된 사용자가 아니라고 간주하기 때문이다.
- 그렇기 때문에 null 값으로 넘어가는 상황이 발생하는 것이다.

### 해결
```
UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            customUser, null, authorities
        );
```

## UsernamePasswordAuthenticationToken 구성
1. Principal: 사용자의 고유 식별 정보 (예: 사용자 객체). 
2. Credentials: 인증 정보 (예: 비밀번호, 토큰). 
3. Authorities: 사용자의 권한 정보 (GrantedAuthority의 리스트).