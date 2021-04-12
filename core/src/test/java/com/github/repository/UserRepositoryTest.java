package com.github.repository;

import com.github.dto.UserRegDto;
import com.zaxxer.hikari.HikariConfig;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserRepositoryTest {

    @Test
    public void findAll() {
    }

    @Test
    public void findAuth() {
    }

    @Test
    public void findReg() {
    }

    @Test
    public void insert() {
        IUserRepository userRepository = new UserRepository(new HikariConfig("db.properties"));
        userRepository.insert(new UserRegDto(
                "Vasya",
                "Pupkin",
                "pupkin-destroyer",
                "password",
                "password",
                "vasya.krut@gmail.com",
                "+380440000000"
        ));
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}