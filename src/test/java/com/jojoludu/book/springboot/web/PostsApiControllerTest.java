package com.jojoludu.book.springboot.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.jojoludu.book.springboot.domain.posts.Posts;
import com.jojoludu.book.springboot.domain.posts.PostsRepository;
import com.jojoludu.book.springboot.web.dto.PostsSaveRequestDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @WebMvcTest를 사용하지 않음 : WebMvcTest는 JPA기능이 작동하지 않아서
 * -> JPA기능까지 한번에 테스트 할때는 @SpringBootTest와 TestRestTemplate를 사용
 * <p>
 * WebEnvironment.RANDOM_PORT로 랜덤 포트 실행, insert쿼리가 실행됨
 **/

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    void tearDown() {
        postsRepository.deleteAll();
    }


    @Test
    @DisplayName("posts_등록된다")
    void save() {
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
            .title(title)
            .content(content)
            .author("author")
            .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto,
            Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }
}
