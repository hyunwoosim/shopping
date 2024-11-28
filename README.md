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