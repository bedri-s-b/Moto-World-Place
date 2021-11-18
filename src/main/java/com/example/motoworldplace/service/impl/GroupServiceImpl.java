package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.binding.GroupAddBindingModel;
import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.MessageEntity;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.GroupEnum;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.model.service.GroupServiceModel;
import com.example.motoworldplace.model.view.GroupViewModel;
import com.example.motoworldplace.model.view.UserViewModel;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.repository.MessageRepository;
import com.example.motoworldplace.repository.UserRepository;
import com.example.motoworldplace.service.GroupService;
import com.example.motoworldplace.service.PictureService;
import com.example.motoworldplace.service.UserService;
import com.example.motoworldplace.service.cluodinary.CloudinaryImg;
import com.example.motoworldplace.service.cluodinary.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.motoworldplace.constans.ConstantsUrl.DEFAULT_MESSAGE_GROUP;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final UserService userService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MotoWorldUserServiceImpl motoWorldUserService;


    public GroupServiceImpl(GroupRepository groupRepository, UserService userService, PictureService pictureService, ModelMapper modelMapper, CloudinaryService cloudinaryService, UserRepository userRepository, MessageRepository messageRepository, MotoWorldUserServiceImpl motoWorldUserService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.motoWorldUserService = motoWorldUserService;
    }

    @Override
    public void initGroup() {
        if (groupRepository.count() != 0) {
            return;
        }
        GroupEntity group = new GroupEntity().setName(GroupEnum.FREE_RAIDER.name())
                .setAdmin(userService.findByUserName("Admin")).setPicture(pictureService.getDefaultGroupPic());
        groupRepository.save(group);
        userService.addUser(userService.findByUserName("Admin"), group);
    }

    @Override
    public List<GroupViewModel> findAllGroup(Principal principal) {
        return
                groupRepository.findAll().stream()
                        .map(groupEntity -> map(groupEntity,principal.getName()))
                        .collect(Collectors.toList());
    }

    @Override
    public GroupServiceModel addGroup(GroupAddBindingModel groupAddBindingModel, String username) throws IOException {
        UserEntity currentUser = userService.findByUserName(username);
        currentUser.setGroupEnum(RoleEnum.GROUP_ADMIN);
        GroupEntity group = modelMapper.map(groupAddBindingModel, GroupEntity.class);
        group.setAdmin(currentUser);
        group.getMembers().add(currentUser);
        CloudinaryImg upload = cloudinaryService.upload(groupAddBindingModel.getPicture());
        PictureEntity picture = new PictureEntity().setPublicId(upload.getPublicId()).setUrl(upload.getUrl());
        pictureService.savePicture(picture);
        group.setPicture(picture);
        GroupEntity save = groupRepository.save(group);
        currentUser.getGroup().add(save);
        userRepository.save(currentUser);

        UserDetails principal = motoWorldUserService.loadUserByUsername(currentUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                currentUser.getPassword(),
                principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return modelMapper.map(save, GroupServiceModel.class);
    }

    @Override
    public boolean findByName(String name) {
        return groupRepository.existsByName(name);
    }

    @Override
    public GroupViewModel findGroupViewModelById(Long id, UserViewModel userViewModel) {
        GroupEntity group = groupRepository.findById(id).get();
        return new GroupViewModel()
                .setName(group.getName())
                .setAdmin(group.getAdmin().getUsername())
                .setPicture(group.getPicture().getUrl())
                .setCreated(group.getCreated())
//                .setCanJoin(isMember(userViewModel.getUsername(),id))
                .setMembers(group.getMembers().size())
                .setId(group.getId())
                .setMembersName(group.getMembers().stream().map(UserEntity::getUsername).collect(Collectors.toSet()));
    }

    @Override
    public void joinMemberToGroup(Long idGroup, String currentUser, String otherNameUser) {
        GroupEntity groupEntity = groupRepository.findById(idGroup).get();

        UserEntity currentU = userService.findByUserName(currentUser);
        UserEntity otherU = userService.findByUserName(otherNameUser);
        // save group
        currentU.getGroup().add(groupEntity);
        userRepository.saveAndFlush(currentU);

        MessageEntity message = new MessageEntity().setFromUser(currentU).setToUser(otherU)
                .setMessage(String.format(DEFAULT_MESSAGE_GROUP, groupEntity.getName())).setReadMessage(false);
        messageRepository.save(message);

    }

    @Override
    public List<GroupViewModel> findAllGroupWhichAdmin(String username) {
        return groupRepository.findByAdmin_Username(username).stream()
                .map(u -> map(u,username)).collect(Collectors.toList());
    }


    private GroupViewModel map(GroupEntity groupEntity,String username) {
        GroupViewModel groupViewModel = new GroupViewModel();
        groupViewModel.setName(groupEntity.getName());
        groupViewModel.setCanJoin(isMember(username,groupEntity.getId()));
        groupViewModel.setMembers(groupEntity.getMembers().size());
        groupViewModel.setAdmin(groupEntity.getAdmin().getUsername());
        groupViewModel.setPicture(groupEntity.getPicture().getUrl());
        groupViewModel.setCreated(groupEntity.getCreated());
        groupViewModel.setId(groupEntity.getId());
        return groupViewModel;
    }

    public boolean isMember(String username, Long id) {

        Optional<GroupEntity> group = groupRepository.findById(id);

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isEmpty() || group.isEmpty()) {
            return false;
        } else {
            return group.get().getMembers().stream().noneMatch(m -> m.getUsername().equals(username));
        }
    }
}
