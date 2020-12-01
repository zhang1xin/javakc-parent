package com.javakc.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // ## 允许的域
        config.addAllowedOrigin("*");
        // ## 允许的请求方式
        config.addAllowedMethod("*");
        // ## 允许的头信息
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        // ## 添加映射路径
        source.registerCorsConfiguration("/**", config);
        // ## 新的跨域资源配置信息
        return new CorsWebFilter(source);
    }
}