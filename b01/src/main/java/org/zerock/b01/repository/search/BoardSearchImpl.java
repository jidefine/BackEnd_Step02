package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;
import org.zerock.b01.domain.QReply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{
    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        QBoard board = QBoard.board;
        //
        JPQLQuery<Board> query = from(board);

        //
        query.where(board.title.contains("1"));

        this.getQuerydsl().applyPagination(pageable, query);
        //
        List<Board> list = query.fetch();
        //
        long count = query.fetchCount();
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if((types != null && types.length > 0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type: types){
                    switch (type){
                        case "t":
                            booleanBuilder.or(board.title.contains(keyword));
                            break;
                        case "c":
                            booleanBuilder.or(board.title.contains(keyword));
                            break;
                        case "w":
                            booleanBuilder.or(board.writer.contains(keyword));
                            break;
                    }
                }
                query.where(booleanBuilder);
            }
            // AND bno > 0L
            query.where(board.bno.gt(0L));
        // ORDER BY bno DESC limit 1, 10;
            this.getQuerydsl().applyPagination(pageable, query);
            List<Board> list = query.fetch();
            long count = query.fetchCount();

            /* list : 실제 row 리스트들
           pageable : 페이지 처리 필요 정보
           count : 전체 row 갯수

           페이징 처리하려면 이것들이 모두 필요하므로 묶어서 넘기려고
           Page<E>를 상속받은 PageImpl<E> 클래스를 만들어 놓은 것이다.
            * */

            return new PageImpl<>(list, pageable,count);

    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        // Querydsl -> JPQL로 변환
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        // 왼쪽 테이블인 board에 기준을 맞춰서 board는 다 출력줘
        // board의 row(행)을 댓글이 존재하지 않아도 모두 출력해줘
        // FROM board LEFT OUTER JOIN reply ON reply.board_bno = board.bno;

        // FROM board;
        JPQLQuery<Board>query = from(board);

        // LEFT OUTER JOIN reply ON reply.board_bno = board.bno
        query.leftJoin(reply).on(reply.board.eq(board));

        //GROUB BY board의 컬럼들
        query.groupBy(board);

        // OR가 AND보다 연산자 우선순위가 낮기 때문에 OR조건들은 ()괄호로 묶어준다.
        if((types != null && types.length > 0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.title.contains(keyword));
                    case "w":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                }
            }

             /*
            WHERE (
            board.title LIKE '%:keyword%'
            OR
            board.content LIKE '%:keyword%'
            OR
            board.writer LIKE '%:keyword%'
            )
            * */

            query.where(booleanBuilder);
        }
        // AND board.bno > 0
        query.where(board.bno.gt(0L));

        // SELECT bno, title, writer, regDate, COUNT(reply) replyCount
        JPQLQuery<BoardListReplyCountDTO>dtoQuery = query.select(Projections
                .bean(BoardListReplyCountDTO.class,
                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        reply.count().as("replyCount")
                ));

        // paging 처리를 dtoQuery에 적용해줌(페이징 처리 sql문을 추가해줌)
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        // 쿼리 실행
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();

        // 현재 결과 전체 갯수
        long count = dtoQuery.fetchCount();

        // thymeleaf html에 전달하기 위해서 묶어서 리턴
        return new PageImpl<>(dtoList, pageable, count);
    }

}
