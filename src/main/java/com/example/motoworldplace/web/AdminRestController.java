package com.example.motoworldplace.web;

import com.example.motoworldplace.model.view.EventViewModel;
import com.example.motoworldplace.model.view.ProductsViewModel;
import com.example.motoworldplace.model.view.UserViewModel;
import com.example.motoworldplace.service.EventService;
import com.example.motoworldplace.service.ProductService;
import com.example.motoworldplace.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final ProductService productService;
    private final EventService eventService;
    private final UserService userService;

    public AdminRestController(ProductService productService, EventService eventService, UserService userService) {
        this.productService = productService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/all/products")
    public ResponseEntity<List<ProductsViewModel>> getAllProducts(){
        List<ProductsViewModel> products = productService.findAllProductsViewModel();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductsViewModel> getProduct(@PathVariable("id") Long id, Principal principal){
        ProductsViewModel product = productService.findProductById(id, principal);
        if (product == null) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.
                    ok(product);
        }

    }

    @GetMapping("/all/events")
    public ResponseEntity<List<EventViewModel>> getAllEvents(){
        List<EventViewModel> events = eventService.findAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/all/users")
    public ResponseEntity<List<UserViewModel>> getAllUser(){
        List<UserViewModel> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}
