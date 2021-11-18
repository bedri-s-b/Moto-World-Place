package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MotoWorldUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public MotoWorldUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with name " + username + " not found"));
        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity){
        GrantedAuthority authority =  new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name());
        GrantedAuthority authority1 =  new SimpleGrantedAuthority("ROLE_" + userEntity.getGroupEnum().name());
        List<GrantedAuthority> authorities = List.of(authority,authority1);
        return new User(userEntity.getUsername(),userEntity.getPassword(),authorities);
    }
}
