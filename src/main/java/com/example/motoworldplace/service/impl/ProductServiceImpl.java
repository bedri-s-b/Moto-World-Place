package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.binding.ProductBindingModel;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.ProductEntity;
import com.example.motoworldplace.model.view.ProductsViewModel;
import com.example.motoworldplace.repository.PictureRepository;
import com.example.motoworldplace.repository.ProductRepository;
import com.example.motoworldplace.service.PictureService;
import com.example.motoworldplace.service.ProductService;
import com.example.motoworldplace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PictureService pictureService;
    private final PictureRepository pictureRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, UserService userService, PictureService pictureService, PictureRepository pictureRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.pictureService = pictureService;
        this.pictureRepository = pictureRepository;
    }

    @Transactional
    @Override
    public List<ProductsViewModel> findAllProductsViewModel() {
        List<ProductEntity> all = productRepository.findAll();
        return all.stream().map(productEntity -> modelMapper.map(productEntity, ProductsViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void addProduct(ProductBindingModel productBindingModel) {
        ProductEntity product = map(productBindingModel);
        productRepository.save(product);
        productBindingModel
                .getPictures()
                .forEach(p -> {
                    try {
                        String newPicture = pictureService.createNewPicture(p, product.getSeller().getId());
                        pictureRepository.save(pictureService.findByPublicId(newPicture).setProduct(product));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private ProductEntity map(ProductBindingModel pbm) {

        return new ProductEntity()
                .setSeller(userService.findByUserName(pbm.getSeller()))
                .setBrand(pbm.getBrand())
                .setDescription(pbm.getDescription())
                .setKilometers(pbm.getKilometers())
                .setModel(pbm.getModel())
                .setPowerHp(pbm.getPowerHp())
                .setPhoneNumber(pbm.getPhoneNumber())
                .setType(pbm.getType())
                .setPrice(pbm.getPrice())
                .setYear(pbm.getYear());
    }
}
