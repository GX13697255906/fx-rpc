package com.fx.client.interceptor;


import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    private static final AntPathMatcher pathMatch = new AntPathMatcher();

    private Set<String> ignoreUrl = Sets.newHashSet();

    public RequestInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }
}
