package com.jojoludu.book.springboot.web;

import com.jojoludu.book.springboot.config.auth.dto.SessionUser;
import com.jojoludu.book.springboot.service.posts.PostsService;
import com.jojoludu.book.springboot.web.dto.PostResponseDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        /**
         * 1. CustomOAuth2UserService에서 httpSession.setAttribute("user", new SessionUser(user)) 부분
         *  로그인 성공 시 세션에 SessionUser를 저장하도록 구성
         *  로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수있음
         * **/
        SessionUser user = (SessionUser) httpSession.getAttribute("user");


        /**
         * 2.
         * 세션에 저장된 값이 있을 때만 모델에 등록
         *
         * */
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
