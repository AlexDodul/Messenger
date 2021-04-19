package com.github.payload;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.Date;
import java.util.Objects;

public class Token implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private Date expireIn;

    private Date createdAt;

    private PublicKey publicKey;

    public Token() {

    }

    public Token(String id, String firstName, String lastName, Date expireIn, Date createdAt, PublicKey publicKey) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expireIn = expireIn;
        this.createdAt = createdAt;
        this.publicKey = publicKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(id, token.id) && Objects.equals(firstName, token.firstName) && Objects.equals(lastName, token.lastName) && Objects.equals(expireIn, token.expireIn) && Objects.equals(createdAt, token.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, expireIn, createdAt);
    }
}