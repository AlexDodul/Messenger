package com.github.service;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.repository.user.IUserRepository;

public class UserService implements IUserService{

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findAuth(UserAuthDto userAuthDto) {
        return this.userRepository.findAuth(userAuthDto);
    }

    @Override
    public User findReg(UserRegDto userRegDto) {
        return this.userRepository.findReg(userRegDto);
    }

    @Override
    public void insert(UserRegDto userRegDto) {
        this.userRepository.insert(userRegDto);
    }

    @Override
    public void update(User user) {
        this.userRepository.update(user);
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(user);
    }
    /*private UserService(){

    }
    private static UserService userService;

    public static UserService getUserService() {
        return userService;
    }*/
}
