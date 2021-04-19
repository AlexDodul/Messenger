package com.github.service;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.repository.user.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService implements IUserService{

    private final IUserRepository userRepository;

    public List<User> cache;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.cache = new ArrayList<>();
    }

    @Override
    public User findAuth(UserAuthDto userAuthDto) {
        User result = this.cache.stream()
                .filter(user ->
                        user.getLogin().equals(userAuthDto.getLogin())
                        && user.getPassword().equals(userAuthDto.getPassword())
                ).findFirst().orElse(null);
        if(Objects.isNull(result)){
            result = this.userRepository.findAuth(userAuthDto);
            this.cache.add(result);
        }
        return result;
    }

    @Override
    public User insert(UserRegDto userRegDto) {
        User result = this.userRepository.insert(userRegDto);
        this.cache.add(result);
        return result;
    }

}
