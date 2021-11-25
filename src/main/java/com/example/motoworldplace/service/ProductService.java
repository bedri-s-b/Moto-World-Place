package com.example.motoworldplace.service;

import com.example.motoworldplace.model.binding.ProductBindingModel;
import com.example.motoworldplace.model.view.ProductsViewModel;

import java.util.List;

public interface ProductService {
    List<ProductsViewModel> findAllProductsViewModel();

    void addProduct(ProductBindingModel productBindingModel);
}
