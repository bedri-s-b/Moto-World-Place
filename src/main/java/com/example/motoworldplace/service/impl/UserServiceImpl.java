package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.binding.UserProfileUpdateBindingModel;
import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.MessageEntity;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;
import com.example.motoworldplace.model.entity.enums.GroupEnum;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.model.view.UserViewModel;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.repository.UserRepository;
import com.example.motoworldplace.service.CityService;
import com.example.motoworldplace.service.MessageService;
import com.example.motoworldplace.service.PictureService;
import com.example.motoworldplace.service.UserService;
import com.example.motoworldplace.service.cluodinary.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.motoworldplace.constans.ConstantsUrl.DEFAULT_MESSAGE;
import static com.example.motoworldplace.constans.ConstantsUrl.DEFAULT_URL_PIC_GROUP;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final CityService cityService;
    private final PictureService pictureService;
    private final MessageService messageService;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final MotoWorldUserServiceImpl motoWorldUserService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, GroupRepository groupRepository, CityService cityService, PictureService pictureService, MessageService messageService, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService, MotoWorldUserServiceImpl motoWorldUserService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
        this.cityService = cityService;
        this.pictureService = pictureService;
        this.messageService = messageService;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
        this.motoWorldUserService = motoWorldUserService;
    }


    @Override
    public boolean existUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        UserEntity user = modelMapper.map(userServiceModel, UserEntity.class);
        GroupEntity freeRaider = groupRepository.findByName("FREE_RAIDER");
        user.getGroup().add(freeRaider);
        user
                .setCity(cityService.findByName(userServiceModel.getCity()))
                .setPicture(pictureService.getDefaultPic())
                .setRole(RoleEnum.USER)
                .setGroupEnum(RoleEnum.USER)
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setPicture(pictureService.getDefaultPic());
        UserEntity currentUser = userRepository.saveAndFlush(user);
        freeRaider.getMembers().add(currentUser);
        UserEntity admin = userRepository.findByUsername("Admin").orElse(null);
        MessageEntity fromAdmin = new MessageEntity().setMessage(String.format(DEFAULT_MESSAGE,user.getFullName()))
                .setFromUser(admin)
                .setToUser(currentUser)
                .setReadMessage(false);
        messageService.saveMessage(fromAdmin);

        UserDetails principal = motoWorldUserService.loadUserByUsername(currentUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                currentUser.getPassword(),
                principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Override
    public void initAdmin() {
        if (userRepository.count() != 0) {
            return;
        }
        UserEntity admin = new UserEntity()
                .setAge(30)
                .setCity(cityService.findByName(CityEnum.СОФИЯ))
                .setEmail("admin@abv.bg")
                .setFullName("Admin Adminov")
                .setPicture(pictureService.getDefaultPic())
                .setRole(RoleEnum.ADMIN)
                .setGroupEnum(RoleEnum.GROUP_ADMIN)
                .setUsername("Admin")
                .setPassword(passwordEncoder.encode("admin"));
        userRepository.save(admin);
    }

//    @Override
//    public UserServiceModel findByUsernameAndPassword(String username, String password) {
//        return userRepository.findByUsernameAndPassword(username, password)
//                .map(user -> modelMapper.map(user, UserServiceModel.class)).orElse(null);
//    }

    @Override
    public Optional<UserServiceModel> findByUsername(String username) {
        return userRepository.findByUsername(username).map(u -> {
            UserServiceModel userServiceModel = modelMapper.map(u, UserServiceModel.class);
            userServiceModel.setCity(u.getCity().getName());
            return userServiceModel;
        });
    }

    @Override
    public Optional<UserServiceModel> findById(Long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserServiceModel.class));
    }

    @Override
    public void replacePic(Long userId, String publicId) {
        PictureEntity byPublicId = pictureService.findByPublicId(publicId);
        PictureEntity oldPic = userRepository.findById(userId).get().getPicture();
        userRepository.changePicture(userId, byPublicId);
        if (!oldPic.getPublicId().equals("default")) {
            cloudinaryService.delete(oldPic.getPublicId());
            pictureService.delete(oldPic);
        }
    }

    @Override
    public void editProfile(UserProfileUpdateBindingModel userProfileUpdateModel) throws IOException {
        Long id = userProfileUpdateModel.getId();

        if (!userProfileUpdateModel.getPicture().isEmpty()) {
            String publicId = pictureService.createNewPicture(userProfileUpdateModel.getPicture(), id);
            replacePic(id, publicId);
        }

        UserEntity user = userRepository.findById(id).get();

        user.setEmail(userProfileUpdateModel.getEmail()).
                setFullName(userProfileUpdateModel.getFullName()).
                setCity(cityService.findByName(userProfileUpdateModel.getCity()));

        userRepository.save(user);
    }

    @Override
    public UserEntity findByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void addUser(UserEntity user, GroupEntity group) {
        user.setGroup(Set.of(group));
        userRepository.save(user);
    }

    @Override
    public UserViewModel findUserViewModelByUsername(String name) {
        UserEntity userEntity = findByUserName(name);
        UserViewModel userViewModel = modelMapper.map(userEntity, UserViewModel.class);
        userViewModel.getGroups().addAll(userEntity.getGroup().stream().map(GroupEntity::getName).collect(Collectors.toSet()));
        return userViewModel;
    }


}
