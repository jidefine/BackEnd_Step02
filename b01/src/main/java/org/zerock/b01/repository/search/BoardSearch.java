package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.Board;

/*QuertDsl을 사용하기 위해서 작성
왜 필요하느냐?
여기에 메서드를 정의하고 2군데를 상속시킨다.
1) 메서드에 QueryDsl 문법으로 구현해야 하는 자식 클래스
2) 우리가 Dao로 사용하는 BoardRepository에 상속시킨다.

이렇게 하면 JPA가 BoardSearch를 인지하고,
이 인터페이스를 기준으로 아래 명명법으로 구현 클래스를 찾는다.
(반드시 BoardSearchImpl = BoardSearch + Impl)
찾은 다음에 BoardRepository에 상속받은 추상메서드를 구현할 대
BoardSearch*/
public interface BoardSearch {
    Page<Board> search1(Pageable pageable);
}
