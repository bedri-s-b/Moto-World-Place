package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.binding.ProductBindingModel;
import com.example.motoworldplace.model.binding.ProductUpdateBindingModel;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.ProductEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.model.service.ProductServiceModel;
import com.example.motoworldplace.model.view.ProductsViewModel;
import com.example.motoworldplace.repository.PictureRepository;
import com.example.motoworldplace.repository.ProductRepository;
import com.example.motoworldplace.service.PictureService;
import com.example.motoworldplace.service.ProductService;
import com.example.motoworldplace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PictureService pictureService;
    private final PictureRepository pictureRepository;
    private final MotoWorldUserServiceImpl motoWorldUserService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, UserService userService, PictureService pictureService, PictureRepository pictureRepository, MotoWorldUserServiceImpl motoWorldUserService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.pictureService = pictureService;
        this.pictureRepository = pictureRepository;
        this.motoWorldUserService = motoWorldUserService;
    }

    @Transactional
    @Override
    public List<ProductsViewModel> findAllProductsViewModel() {
        List<ProductEntity> all = productRepository.findAll();
        return all.stream().map(this::mapPVM).collect(Collectors.toList());
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

    @Transactional
    @Override
    public ProductsViewModel findProductById(Long id, Principal principal) {
        ProductEntity product = productRepository.findByCurrentId(id);
        if (principal == null){
            return mapPVM(product);
        }
        UserEntity currentUser = userService.findByUserName(principal.getName());
        return mapPVM(product).setOwnerOfProduct(product.getSeller().getUsername().equals(currentUser.getUsername())
                || currentUser.getRole().equals(RoleEnum.ADMIN));
    }

    @Override
    public ProductServiceModel findProductServiceModelById(Long id) {
        ProductEntity product = productRepository.findByCurrentId(id);
        return modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public void editProduct(ProductUpdateBindingModel productUpdateBindingModel, String username) {
        UserEntity user = userService.findByUserName(username);
        ProductEntity product = productRepository.findByCurrentId(productUpdateBindingModel.getId());
        ProductEntity productUpdate = modelMapper.map(productUpdateBindingModel, ProductEntity.class);
        ProductEntity productSave = modelMapper.map(productUpdate, ProductEntity.class);
        productSave.setCreated(LocalDateTime.now());
        productSave.setSeller(user);
        productRepository.save(productSave);
    }

    @Override
    public boolean isOwnerOfProduct(String userName, Long id) {

        UserEntity seller = productRepository.findByCurrentId(id).getSeller();
        if (seller.getRole().equals(RoleEnum.ADMIN)) {
            return true;
        }
        return seller.getUsername().equals(userName);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
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

    private ProductsViewModel mapPVM(ProductEntity productEntity) {
        return new ProductsViewModel()
                .setModel(productEntity.getModel())
                .setBrand(productEntity.getBrand())
                .setDescription(productEntity.getDescription())
                .setKilometers(productEntity.getKilometers())
                .setPrice(productEntity.getPrice())
                .setPhoneNumber(productEntity.getPhoneNumber())
                .setId(productEntity.getId())
                .setPictures(productEntity.getPictures().stream().map(PictureEntity::getUrl).collect(Collectors.toSet()))
                .setType(productEntity.getType())
                .setModel(productEntity.getModel())
                .setPowerHp(productEntity.getPowerHp())
                .setYear(productEntity.getYear())
                .setSeller(productEntity.getSeller().getFullName())
                .setDescription(productEntity.getDescription())
                .setCreated(productEntity.getCreated().format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")));
    }
}
