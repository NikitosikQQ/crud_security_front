package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.SecurityConfig.passwordEncoder;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final VKService vkService;

    @Autowired
    public UserService(UserRepository userRepository, VKService vkService) {
        this.userRepository = userRepository;
        this.vkService = vkService;
    }

    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Transactional
    public HttpStatus saveUser(User user) throws JsonProcessingException {

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User checkUsername = userRepository.findUserByName(user.getName());
        if (checkUsername != null) {
            return HttpStatus.BAD_REQUEST;
        }
        int countOfVideo = vkService.getCountOfVideosByOwnerId(user.getVkOwnerId());
        user.setCountOfVideo(countOfVideo);
        userRepository.save(user);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus updateUser(User user) throws JsonProcessingException {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User checkUser = userRepository.findUserById(user.getId());
        if (checkUser != null) {
            int countOfVideo = vkService.getCountOfVideosByOwnerId(user.getVkOwnerId());
            user.setCountOfVideo(countOfVideo);
            userRepository.save(user);
            return HttpStatus.OK;

        }
        return HttpStatus.BAD_REQUEST;
    }

    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Transactional
    public HttpStatus deleteUserById(Long id) {
        User checkUser = userRepository.findUserById(id);
        if (checkUser == null) {
            return HttpStatus.BAD_REQUEST;
        }
        userRepository.deleteById(id);
        return HttpStatus.OK;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:(");
        }
        return user;
    }


}
