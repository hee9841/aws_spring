package com.jojoludu.book.springboot.web;

import com.jojoludu.book.springboot.config.auth.LoginUser;
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
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        /**
         * 2.
         * 세션에 저장된 값이 있을 때만 모델에 등록
         *
         * */
        if (user != null) {
            model.addAttribute("uName", user.getName());
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
