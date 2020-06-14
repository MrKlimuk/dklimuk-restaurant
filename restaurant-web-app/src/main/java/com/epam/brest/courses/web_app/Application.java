package com.epam.brest.courses.web_app;

import com.epam.brest.courses.service_rest.ItemServiceRest;
import com.epam.brest.courses.service_rest.OrderServiceRest;
import com.epam.brest.courses.service_rest.PositionServiceRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.epam.brest.courses*"})
public class Application implements WebMvcConfigurer {

    @Value("${protocol}")
    private String protocol;

    @Value("${point.port}")
    private String port;

    @Value("${point.host}")
    private String host;

    @Value("${point.orders}")
    private String pointOrders;

    @Value("${point.positions}")
    private String pointPositions;

    @Value("${point.items}")
    private String pointItems;


    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ItemServiceRest itemServiceRest(){
        ItemServiceRest itemService = new ItemServiceRest(protocol + host + port + pointItems, restTemplate());
        return itemService;
    }

    @Bean
    public OrderServiceRest orderServiceRest(){
        OrderServiceRest orderService = new OrderServiceRest(protocol + host + port + pointOrders, restTemplate());
        return orderService;
    }

    @Bean
    public PositionServiceRest positionServiceRest(){
        PositionServiceRest positionService = new PositionServiceRest(protocol + host + port + pointPositions, restTemplate());
        return positionService;
    }


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConveter = new MappingJackson2HttpMessageConverter();
        jsonMessageConveter.setObjectMapper(objectMapper);
        messageConverters.add(jsonMessageConveter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

}
