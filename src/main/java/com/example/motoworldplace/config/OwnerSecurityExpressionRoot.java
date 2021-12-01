package com.example.motoworldplace.config;

import com.example.motoworldplace.service.*;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class OwnerSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {


    private GroupService groupService;
    private UserService userService;
    private MessageService messageService;
    private ProductService productService;
    private EventService eventService;
    private Object filterObject;
    private Object returnObject;

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */

    public OwnerSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isMember(Long id) {
        String userName = currentUsername();
        if (isAdmin()){
            return true;
        }
        if (userName != null) {
            return groupService.isMember(userName, id);
        }
        return false;
    }

    public boolean isOwner(Long id){
        String userName = currentUsername();
        if (isAdmin()){
            return true;
        }
        if (userName != null) {
            return userService.isOwner(userName, id);
        }
        return false;
    }

    public boolean isCreatorEvent(Long id){
        String userName = currentUsername();
        if (isAdmin()){
            return true;
        }
        if (userName != null){
            return eventService.isCreatorEvent(userName,id);
        }
        return false;
    }

    public boolean isOwnerOnMessages(Long id){
        String userName = currentUsername();
        if (isAdmin()){
            return true;
        }
        if (userName != null) {
            return messageService.isOwnerOnMessages(userName, id);
        }
        return false;
    }

    public boolean isOwnerOfProduct(Long id){
        String userName = currentUsername();
        if (isAdmin()){
            return true;
        }
        if (userName!= null){
            return productService.isOwnerOfProduct(userName,id);
        }
        return false;
    }





    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public String currentUsername() {
        Authentication auth = getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails){
            return  ((UserDetails) auth.getPrincipal()).getUsername();
        }
        return null;
    }

    public boolean isAdmin(){
        Authentication auth = getAuthentication();
        return hasAnyRole("ROLE_ADMIN");
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    public OwnerSecurityExpressionRoot setEventService(EventService eventService) {
        this.eventService = eventService;
        return this;
    }
}
