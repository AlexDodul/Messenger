package com.github.service.user;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserServiceTestMock {

    static final Collection<UserRegDto> userRegCollection = new ArrayList<>();

    static final Collection<UserRegDto> updatedUserRegCollection = new ArrayList<>();

    static final Collection<UserAuthDto> userAuthCollection = new ArrayList<>();

    static final UserRegDto firstUserReg = new UserRegDto(
            "Vasya",
            "Pupkin",
            "VasyaKrut",
            "VasyaKrut",
            "12345",
            "12345",
            "vasya.krut@gmail.com",
            "+380440000001"
    );

    static final UserRegDto updatedFirstUserReg = new UserRegDto(
            "Vasya",
            "Pupkin",
            "VasyaNeKrut",
            "VasyaKrut",
            "54321",
            "54321",
            "vasya.krut@gmail.com",
            "+380440000001"
    );

    static final UserRegDto secondUserReg = new UserRegDto(
            "Petya",
            "Razboynik",
            "Petro",
            "VasyaKrut",
            "12345",
            "12345",
            "petro.razboynik@gmail.com",
            "+380440000002"
    );

    static final UserRegDto updatedSecondUserReg = new UserRegDto(
            "Petro",
            "Dobryi",
            "Petro",
            "VasyaKrut",
            "12345",
            "12345",
            "petro.dobryi@gmail.com",
            "+380440000003"
    );

    static final UserRegDto nullUserReg = new UserRegDto(
            null, null, null, null, null, null, null, null
    );

    static final UserAuthDto firstUserAuth = new UserAuthDto(
            "VasyaKrut", "12345"
    );

    static final UserAuthDto secondUserAuth = new UserAuthDto(
            "Petro", "12345"
    );

    static final UserAuthDto notExistingAuth = new UserAuthDto(
            "Bartolomeo", "777"
    );

    static final User notExistingUser = new User(
            -100L,
            "AAAA",
            "BBBB",
            "CCCC",
            "asd",
            "qwerty",
            "pass",
            "word"
    );

    static {

        userRegCollection.add(firstUserReg);
        userRegCollection.add(secondUserReg);

        userAuthCollection.add(firstUserAuth);
        userAuthCollection.add(secondUserAuth);

        updatedUserRegCollection.add(updatedFirstUserReg);
        updatedUserRegCollection.add(updatedSecondUserReg);
    }

}
