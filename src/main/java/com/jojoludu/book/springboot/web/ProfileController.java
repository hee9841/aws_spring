package com.jojoludu.book.springboot.web;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles()); // 현재 실행 중인 ActiveProfile을 모두 가져온다
        List<String> realProfiles = Arrays.asList("real", "blue", "green");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        log.info("!!!!!!!!!!!!profiles:{}", profiles);

        return profiles.stream()
            .filter(realProfiles::contains)
            .findAny()
            .orElse(defaultProfile);
    }

}
