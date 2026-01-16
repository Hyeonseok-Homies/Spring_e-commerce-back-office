package com.backoffice.admin.config;

import com.backoffice.admin.dto.SessionAdmin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    // 세션타입 확인
    boolean hasLoginUserAnnotation = parameter.hasParameterAnnotation(Login.class);
    boolean isSessionDtoType = SessionAdmin.class.isAssignableFrom(parameter.getParameterType());

    return hasLoginUserAnnotation && isSessionDtoType;
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory)
      throws Exception {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    HttpSession session = request.getSession(false);
    if (session == null) {
      throw new IllegalStateException("로그인이 필요합니다.");
    }
    Object attribute = session.getAttribute("loginAdmin");
    if (attribute == null) {
      throw new IllegalStateException("로그인이 필요합니다.");
    }
    if (!(attribute instanceof SessionAdmin)) {
      // 안맞는 데이터 제거
      session.invalidate();
      throw new IllegalStateException("데이터가 올바르지 않습니다.");
    }
    SessionAdmin sessionAdmin = (SessionAdmin) attribute;
    return sessionAdmin;
  }
}
