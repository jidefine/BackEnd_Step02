package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/*DB 논리적 설계 단게에서 물리적 설계로 전환되기 전에
* 물리적 Table로 생성되어야 할 논리적 묶음을 Entity라고 한다
*
* 그래서 종종 Entity와 Table을 동일한 개년으로 사용하곤 한다*/

// 이 클래스의 정보를 가지고 자동으로 Board Table를 생성할거야
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity{
    /*@Id는 Pk(Primary*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;
    @Column(length = 2000, nullable = false)
    private String content;
    @Column(length = 50, nullable = false)
    private String writer;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }

    @OneToMany
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>();

}
