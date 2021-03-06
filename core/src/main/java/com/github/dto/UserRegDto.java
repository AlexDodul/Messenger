package com.github.dto;

import com.github.entity.User;

import java.util.Objects;

public class UserRegDto {

    private String firstName;

    private String lastName;

    private String nickname;

    private String login;

    private String password;

    private String passwordConfirm;

    private String email;

    private String phone;

    public UserRegDto() {
    }

    public UserRegDto(String firstName, String lastName, String nickname, String login, String password, String passwordConfirm, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.login = login;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.phone = phone;
    }

    public UserRegDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.nickname = user.getNickname();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.passwordConfirm = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegDto that = (UserRegDto) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName) && nickname.equals(that.nickname) && login.equals(that.login) && password.equals(that.password) && passwordConfirm.equals(that.passwordConfirm) && email.equals(that.email) && phone.equals(that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nickname, login, password, passwordConfirm, email, phone);
    }
}
