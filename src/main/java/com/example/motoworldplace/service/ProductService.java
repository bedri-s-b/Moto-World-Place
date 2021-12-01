package com.example.motoworldplace.service;

import com.example.motoworldplace.model.binding.ProductBindingModel;
import com.example.motoworldplace.model.binding.ProductUpdateBindingModel;
import com.example.motoworldplace.model.service.ProductServiceModel;
import com.example.motoworldplace.model.view.ProductsViewModel;

import java.security.Principal;
import java.util.List;

public interface ProductService {
    List<ProductsViewModel> findAllProductsViewModel();

    void addProduct(ProductBindingModel productBindingModel);

    ProductsViewModel findProductById(Long id, Principal principal);

    ProductServiceModel findProductServiceModelById(Long id);

    void editProduct(ProductUpdateBindingModel productUpdateBindingModel, String username);

    boolean isOwnerOfProduct(String userName, Long id);

    void deleteProduct(Long id);
}
