package com.jojoludu.book.springboot.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 1. @AfterEach
 *      - jUnit 단위 테스트가 끝날 때마다 수행되는  메소드 지정
 *      - 보통 배포 전 전체테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용
 *      - 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어
 *      다음 테스트 실행 시 테스트 실패할 수 있음
 * 2. postsRepository.save
 *      - posts 테이블에 insert/update 쿼리 실행
 *      - id값이 있으면 update, 없으면 insert
 * */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
            .title(title)
            .content(content)
            .author("user1@gmai.com")
            .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    void BaseTimeEntity_등록() throws Exception {
        //given
//        LocalDateTime now = LocalDateTime.of(2024, 5, 28, 21, 14, 0);
        LocalDateTime now = LocalDateTime.now();
        Thread.sleep(1000);
        postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("author")
            .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>> createDate=" + posts.getCreateDate() + ", modifiedDate="
            + posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
