package com.example.motoworldplace.config;

import com.example.motoworldplace.service.GroupService;
import com.example.motoworldplace.service.MessageService;
import com.example.motoworldplace.service.UserService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class MotoWorldMethodSecurityExpressionHandler
        extends DefaultMethodSecurityExpressionHandler {

    private final GroupService groupService;
    private final UserService userService;
    private final MessageService messageService;

    public MotoWorldMethodSecurityExpressionHandler(GroupService groupService, UserService userService, MessageService messageService) {
        this.groupService = groupService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        OwnerSecurityExpressionRoot root = new OwnerSecurityExpressionRoot(authentication);

        root.setMessageService(messageService);
        root.setUserService(userService);
        root.setGroupService(groupService);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());

        return root;
    }
}
