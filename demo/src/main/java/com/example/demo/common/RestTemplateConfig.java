package com.example.demo.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//RestTemplate 은 자동으로 빈등록되지 않기 때문에 직접 등록해줘야함
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(){
        // HttpURLConnection을 사용해 request 생성
        // 기본생성자로 초기화시 SimpleClientHttpRequestFactory 사용
        RestTemplate restTemplate = new RestTemplate();

        // apache HttpClient를 사용해 request 생성
        //RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        return restTemplate;
    }
}
