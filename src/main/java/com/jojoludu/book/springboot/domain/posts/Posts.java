package com.jojoludu.book.springboot.domain.posts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 실제 DB 클래스와 매칭될 클래스
 * Entity 클래스라고도 함
 * JPA를 사용하면서 DB데이터에 작업할 경우 실제 쿼리를 날리기 보다는 이 Entity 클래스 수정을 통해 작업
 *
 * =========================================================================================
 * 1. @Entity
 *      - 테이블과 링크될 클래스임을 나타냄
 *      - 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭
 *              -ex) SalesManager.java -> sales_manager table
 * 2. @GeneratedValue
 *      - PK 생성 규칙 나타냄
 *      - 부트 2.0에서는 GenreationType.IDENTYT 옵션을 추가해야 auto_incremnet가 됨
 * 3.  @Column
 *      - 테이블의 칼럼을 나타내며, 굳이 선언하지 않더라더 해당 클래스의 필드는 모두 칼럼이 됨
 *      - 문자열의 경우 VARCHAR(255)가 기본, 사이즐을 늘리고 싶거나(ex.title), 타입을 TEXT로변경(ex.content)
 *      할 경우 등에 사용
 * */

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
