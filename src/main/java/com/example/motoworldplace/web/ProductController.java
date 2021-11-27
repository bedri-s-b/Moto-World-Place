package com.example.motoworldplace.web;

import com.example.motoworldplace.model.binding.ProductBindingModel;
import com.example.motoworldplace.model.service.ProductServiceModel;
import com.example.motoworldplace.model.view.ProductsViewModel;
import com.example.motoworldplace.service.ProductService;
import com.example.motoworldplace.service.UserService;
import org.modelmapper.ModelMapper;
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
    public String detailsProduct(@PathVariable("id") Long id, Model model) {
        ProductsViewModel product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/product/{id}/edit")
    public String editProduct(@PathVariable("id") Long id) {
        return "";
    }

    @ModelAttribute
    public ProductBindingModel productBindingModel() {
        return new ProductBindingModel();
    }


}
