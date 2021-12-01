package com.example.motoworldplace.config;

import com.example.motoworldplace.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {


   @Autowired
   private MotoWorldMethodSecurityExpressionHandler motoWorldMethodSecurityExpressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return motoWorldMethodSecurityExpressionHandler;
    }

    @Bean
    public MotoWorldMethodSecurityExpressionHandler createExpressionHandler(GroupService groupService, UserService userService,
                                                                            MessageService messageService, ProductService productService, EventService eventService){
      return new MotoWorldMethodSecurityExpressionHandler(groupService, userService, messageService, productService, eventService);
    }
}
