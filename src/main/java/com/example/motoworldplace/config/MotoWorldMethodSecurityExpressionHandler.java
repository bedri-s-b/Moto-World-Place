package com.example.motoworldplace.config;

import com.example.motoworldplace.service.*;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


public class MotoWorldMethodSecurityExpressionHandler
        extends DefaultMethodSecurityExpressionHandler {

    private final GroupService groupService;
    private final UserService userService;
    private final MessageService messageService;
    private final ProductService productService;
    private final EventService eventService;

    public MotoWorldMethodSecurityExpressionHandler(GroupService groupService, UserService userService, MessageService messageService, ProductService productService, EventService eventService) {
        this.groupService = groupService;
        this.userService = userService;
        this.messageService = messageService;
        this.productService = productService;
        this.eventService = eventService;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        OwnerSecurityExpressionRoot root = new OwnerSecurityExpressionRoot(authentication);

        root.setEventService(eventService);
        root.setProductService(productService);
        root.setMessageService(messageService);
        root.setUserService(userService);
        root.setGroupService(groupService);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());

        return root;
    }
}
