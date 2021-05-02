package com.github.utils;

import com.github.exceptions.TokenProviderException;
import com.github.payload.Token;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

public class TokenProvider {

    public static String encode(Token token, PrivateKey privateKey){

       /* String login = token.getLogin();
        String firstName = token.getFirstName();
        String lastName = token.getLastName();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            login = Base64.getEncoder().encodeToString(cipher.doFinal(login.getBytes()));
            firstName = Base64.getEncoder().encodeToString(cipher.doFinal(firstName.getBytes()));
            lastName = Base64.getEncoder().encodeToString(cipher.doFinal(lastName.getBytes()));
            Token buffer = new Token(login, firstName, lastName, token.getCreatedAt(), token.getExpireIn());
            return JsonHelper.toJson(buffer).orElseThrow(TokenProviderException::new);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new TokenProviderException();
        } catch (InvalidKeyException e) {
            throw new TokenProviderException();
        }*/
        return JsonHelper.toJson(token).orElseThrow(TokenProviderException::new);
    }

    public static Token decode(String str){/*
        Token buffer = JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
        String login = buffer.getLogin();
        String firstName = buffer.getFirstName();
        String lastName = buffer.getLastName();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.DECRYPT_MODE, buffer.getPublicKey());
            buffer.setLogin(new String(cipher.doFinal(Base64.getDecoder().decode(login))));
            buffer.setFirstName(new String(cipher.doFinal(Base64.getDecoder().decode(firstName))));
            buffer.setLastName(new String(cipher.doFinal(Base64.getDecoder().decode(lastName))));
            return JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new TokenProviderException();
        }*/
        return JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
    }

}
