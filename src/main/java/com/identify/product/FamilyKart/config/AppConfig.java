package com.identify.product.FamilyKart.config;

import com.identify.product.FamilyKart.catagory.payload.APIResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {



    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public APIResponse getApiResponse(){
        return new APIResponse(" ",false);
    }




}
