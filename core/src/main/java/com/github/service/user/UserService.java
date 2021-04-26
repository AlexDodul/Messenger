package com.github.service.user;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.repository.user.IUserRepository;

import java.util.ArrayList;
import java.util.Collection;
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
    public Collection<User> findAll() {
        return this.userRepository.findAll();
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
    public User findReg(UserRegDto userRegDto) {
        User result = this.cache.stream()
                .filter(user ->
                        user.getLogin().equals(userRegDto.getLogin())
                                && user.getPassword().equals(userRegDto.getPassword())
                ).findFirst().orElse(null);
        if(Objects.isNull(result)){
            result = this.userRepository.findReg(userRegDto);
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

    @Override
    public User update(User user) {
        User updatedUser = this.userRepository.update(user);
        User cacheUser = this.cache.stream()
                .filter(cacheU -> cacheU.getId().equals(user.getId())).findFirst().orElse(null);
        if(cacheUser == null){
            this.cache.add(updatedUser);
        } else {
            this.cache.remove(cacheUser);
            this.cache.add(updatedUser);
        }
        return updatedUser;
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(user);
        this.cache.remove(user);
    }

    @Override
    public void deleteAll() {
        this.userRepository.deleteAll();
        this.cache.clear();
    }

}
