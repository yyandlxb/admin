package com.ebupt.admin;

import com.ebupt.admin.config.LoginInterceptor;
import com.ebupt.admin.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication(scanBasePackages = {"com.ebupt"})
//@EnableTransactionManagement(proxyTargetClass = true)
//@EnableAutoConfiguration(exclude = {
//		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//})
public class AdminApplication  implements WebMvcConfigurer {

	@Autowired
	private LoginInterceptor interceptors;

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptors).addPathPatterns("/**").
				excludePathPatterns("/login","/error","/static/**","/logout");
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").
				addResourceLocations("classpath:/static/");

	}





	@Bean
	public IdWorker idWorkker() {
		return new IdWorker(1, 1);
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}




//	@Override
//	public void addInterceptors(InterceptorRegistry registry){
//		InterceptorRegistration ir=registry.addInterceptor(new LoginInterceptor());
//		ir.addPathPatterns("/**");
//		ir.excludePathPatterns("/login","/static/**");
//	}




//	private HandlerInterceptor[] interceptors;
//
//	private HandlerMethodArgumentResolver[] argumentResolvers;
//
//	@Autowired
//	public void setInterceptors(HandlerInterceptor[] interceptors) {
//		this.interceptors = interceptors;
//	}
//
//	@Autowired
//	public void setArgumentResolvers(HandlerMethodArgumentResolver[] argumentResolvers) {
//		this.argumentResolvers = argumentResolvers;
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		Stream.of(interceptors).forEach(registry::addInterceptor);
//	}
//
//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//		argumentResolvers.addAll(Arrays.asList(this.argumentResolvers));
//	}
}
