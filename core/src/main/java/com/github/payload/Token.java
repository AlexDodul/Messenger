package com.github.payload;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.Date;

public class Token implements Serializable {

    private String login;

    private String nickname;

    private Date expireIn;

    private Date createdAt;

    private PublicKey publicKey;

    public Token() {

    }

    public Token(String login, String nickname, Date expireIn, Date createdAt, PublicKey publicKey) {
        this.login = login;
        this.nickname = nickname;
        this.expireIn = expireIn;
        this.createdAt = createdAt;
        this.publicKey = publicKey;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Date expireIn) {
        this.expireIn = expireIn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

}