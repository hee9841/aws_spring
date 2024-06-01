package com.jojoludu.book.springboot.config.auth;

import com.jojoludu.book.springboot.config.auth.dto.SessionUser;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * HandlerMethodArgumentResolver를 구현
 * HandlerMethodArgumentResolver
 * - 조건에 맞는 메소드가 있으면 HandlerMethodArgumentResolver의 구현제가
 * 지정한 겂으로 해당 메소드의 파라미터로 넘길 수 있음
 */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    /**
     * 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
     * 여기서는 @LoginUser어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class인경우 true를 반환
     * **/
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    /**
     * 파라미터의 전달할 객체를 생성
     * 여거서는 세션에서 객체를 가져옴
     * **/
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");
    }
}
