package com.github.dto;

import com.github.entity.User;

import java.util.Objects;

public class UserAuthDto {

    private String login;

    private String password;

    public UserAuthDto() {
    }

    public UserAuthDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserAuthDto(User user) {
        this.login = user.getLogin();
        this.password = user.getPassword();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthDto that = (UserAuthDto) o;
        return login.equals(that.login) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
