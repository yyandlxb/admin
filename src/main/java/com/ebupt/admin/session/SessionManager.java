package com.ebupt.admin.session;

import com.ebupt.admin.pojo.WebUser;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.stream.Stream;

/**
 * 处理session的拦截器
 */
@Component
public class SessionManager extends HandlerInterceptorAdapter implements HandlerMethodArgumentResolver {


    /**
     *拦截器执行的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        boolean needAuthenticated = AnnotationUtils.findAnnotation(method.getBeanType(),Authenticated.class) != null;
        if (!needAuthenticated){
            needAuthenticated = method.hasMethodAnnotation(Authenticated.class);

            if (!needAuthenticated){
                needAuthenticated = Stream.of(method.getMethodParameters()).anyMatch(
                        p -> p.hasParameterAnnotation(Authenticated.class)
                );
            }
        }
        if (!needAuthenticated){
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        Object value = session.getAttribute(Authenticated.class.getName());
        if (value == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        request.setAttribute(Authenticated.class.getName(),value);
        return  true;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //判断参数上是否有@Authenticated 这个注解
        return methodParameter.hasParameterAnnotation(Authenticated.class);
    }
    /**
     * 如果参数上有@Authenticated 注解走下面方法
     *
     */
    @Override
    public Object resolveArgument(
            MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        //从session里面获取对象
        return nativeWebRequest.getAttribute(Authenticated.class.getName(),RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 将session放到作用域
     */
    public void bind(HttpSession session, Object user){
        WebUser u = (WebUser) user;
        session.setAttribute(Authenticated.class.getName(),user);
    }
}
