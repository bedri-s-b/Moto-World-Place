package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entiy.MessageEntity;
import com.example.motoworldplace.model.entiy.UserEntity;
import com.example.motoworldplace.model.entiy.enums.CityEnum;
import com.example.motoworldplace.model.entiy.enums.GroupEnum;
import com.example.motoworldplace.model.entiy.enums.RoleEnum;
import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.repository.UserRepository;
import com.example.motoworldplace.service.CityService;
import com.example.motoworldplace.service.MessageService;
import com.example.motoworldplace.service.PictureService;
import com.example.motoworldplace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.example.motoworldplace.constans.ConstantsUrl.DEFAULT_MESSAGE;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final CityService cityService;
    private final PictureService pictureService;
    private final MessageService messageService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, GroupRepository groupRepository, CityService cityService, PictureService pictureService, MessageService messageService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
        this.cityService = cityService;
        this.pictureService = pictureService;
        this.messageService = messageService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean existUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        UserEntity user = modelMapper.map(userServiceModel, UserEntity.class);
        user.setGroup(Set.of(groupRepository.findByName(GroupEnum.FREE_RAIDER)));
        user
                .setCity(cityService.findByName(userServiceModel.getCity()))
                .setPictureUrl(pictureService.getDefaultPic())
                .setRole(RoleEnum.USER)
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setPictureUrl(pictureService.getDefaultPic());
        UserEntity currentUser = userRepository.save(user);
        //message
        UserEntity admin = userRepository.findByUsername("Admin").orElse(null);
        MessageEntity fromAdmin = new MessageEntity().setMessage(DEFAULT_MESSAGE)
                .setFromUser(admin)
                .setToUser(currentUser)
                .setReadMessage(false);
        messageService.saveMessage(fromAdmin);
    }

    @Override
    public void initAdmin() {
        if (userRepository.count() != 0) {
            return;
        }
        UserEntity admin = new UserEntity()
                .setAge(30)
                .setCity(cityService.findByName(CityEnum.СОФИЯ))
                .setGroup(Set.of(groupRepository.findByName(GroupEnum.FREE_RAIDER)))
                .setEmail("admin@abv.bg")
                .setFullName("Admin Adminov")
                .setPictureUrl(pictureService.getDefaultPic())
                .setRole(RoleEnum.ADMIN)
                .setUsername("Admin")
                .setPassword(passwordEncoder.encode("admin"));
        userRepository.save(admin);
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class)).orElse(null);
    }

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
        return userRepository.findById(id).map(user -> modelMapper.map(user,UserServiceModel.class));
    }


}
