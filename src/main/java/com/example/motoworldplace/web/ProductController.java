package com.example.motoworldplace.web;

import com.example.motoworldplace.model.binding.ProductBindingModel;
import com.example.motoworldplace.model.binding.ProductUpdateBindingModel;
import com.example.motoworldplace.model.service.ProductServiceModel;
import com.example.motoworldplace.model.view.ProductsViewModel;
import com.example.motoworldplace.service.ProductService;
import com.example.motoworldplace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ProductController(ProductService productService, ModelMapper modelMapper, UserService userService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping("/all")
    public String allProducts(Model model) {
        List<ProductsViewModel> products = productService.findAllProductsViewModel();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String addProducts() {
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid ProductBindingModel productBindingModel, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productBindingModel", productBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.productBindingModel", bindingResult);
            return "redirect:/products/add";
        }
        String username = userService.findUserViewModelByUsername(principal.getName()).getUsername();
        productBindingModel.setSeller(username);
        productBindingModel.loadPictures();

        productService.addProduct(productBindingModel);


        return "redirect:/products/all";
    }

    @GetMapping("/{id}/details")
    public String detailsProduct(@PathVariable("id") Long id, Model model,Principal principal) {
        ProductsViewModel product = productService.findProductById(id,principal);
        model.addAttribute("product", product);
        return "product";
    }

    @PreAuthorize("isOwnerOfProduct(#id)")
    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable("id") Long id,Model model) {
        ProductServiceModel productServiceModel = productService.findProductServiceModelById(id);
        ProductUpdateBindingModel productUpdateBindingModel = modelMapper.map(productServiceModel,ProductUpdateBindingModel.class).setId(id);
        model.addAttribute("productUpdateBindingModel",productUpdateBindingModel);
        return "edit-product";
    }


    @PreAuthorize("isOwnerOfProduct(#id)")
    @PatchMapping("/{id}/edit")
    public String editProduct(@PathVariable("id") Long id,
                              @Valid ProductUpdateBindingModel productUpdateBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal User user){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productUpdateBindingModel", productUpdateBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.productUpdateBindingModel", bindingResult);

            return "redirect:/products/" + id + "/edit/errors";
        }
        productService.editProduct(productUpdateBindingModel,user.getUsername());
        return "redirect:/products/" + id  +"/details";
    }

    @GetMapping("/{id}/edit/errors")
    public String editProfileError(@PathVariable Long id,Model model) {
        return "edit-product";
    }


    @DeleteMapping("{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/products/all";
    }



    @ModelAttribute
    public ProductBindingModel productBindingModel() {
        return new ProductBindingModel();
    }


}
