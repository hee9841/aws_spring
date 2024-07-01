//package com.jojoludu.book.springboot.web;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jojoludu.book.springboot.domain.posts.Posts;
//import com.jojoludu.book.springboot.domain.posts.PostsRepository;
//import com.jojoludu.book.springboot.web.dto.PostsSaveRequestDto;
//import com.jojoludu.book.springboot.web.dto.PostsUpdateRequestDto;
//import java.util.List;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
///**
// * @WebMvcTest를 사용하지 않음 : WebMvcTest는 JPA기능이 작동하지 않아서
// * -> JPA기능까지 한번에 테스트 할때는 @SpringBootTest와 TestRestTemplate를 사용
// * <p>
// * WebEnvironment.RANDOM_PORT로 랜덤 포트 실행, insert쿼리가 실행됨
// **/
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//class PostsApiControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//
//    @Autowired
//    private PostsRepository postsRepository;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mvc;
//
//    @AfterEach
//    void tearDown() {
//        postsRepository.deleteAll();
//    }
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//            .webAppContextSetup(context)
//            .apply(springSecurity())
//            .build();
//    }
//
//
//    @Test
//    @DisplayName("posts_등록된다")
//    @WithMockUser(roles = "USER")
//    void save() throws Exception {
//        //given
//        String title = "제목";
//        String content = "내용";
//
//        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
//            .title(title)
//            .content(content)
//            .author("author")
//            .build();
//
//        String url = "http://localhost:" + port + "/api/v1/posts";
//
//        //when
//        /*
//         * 생성된 MockMvc를 통해 API를 테스트
//         * 본문(Body)영역은 문자열로 표현하기 위해 ObjectMapper를 등록해서 문자열을 JSON으로 변환
//         * */
//        mvc.perform(post(url)
//                .contentType(
//                    MediaType.APPLICATION_JSON)   //MediaType.APPLICATION_JSON_UTF8 Deprecated 됨
//                .content(new ObjectMapper().writeValueAsString(requestDto)))
//            .andExpect(status().isOk());
//
//        //then
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(title);
//        assertThat(all.get(0).getContent()).isEqualTo(content);
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    void Posts_수정하다() throws Exception {
//        //given
//        Posts savedPosts = postsRepository.save(Posts.builder()
//            .title("title")
//            .content("content")
//            .author("author")
//            .build());
//
//        Long updateId = savedPosts.getId();
//        String expectedTitle = "title2";
//        String expectedContent = "content2";
//
//        PostsUpdateRequestDto requestsDto = PostsUpdateRequestDto.builder()
//            .title(expectedTitle)
//            .content(expectedContent)
//            .build();
//
//        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
//
//        //when
//        mvc.perform(put(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(requestsDto)))
//            .andExpect(status().isOk());
//
//        //then
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
//
//    }
//}
