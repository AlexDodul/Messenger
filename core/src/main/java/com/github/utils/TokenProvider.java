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
import java.util.Base64;

public class TokenProvider {

    public static String encode(Token token, PrivateKey privateKey){

        String login = token.getLogin();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            login = Base64.getEncoder().encodeToString(cipher.doFinal(login.getBytes()));
            Token buffer = new Token(login, token.getNickname(), token.getCreatedAt(), token.getExpireIn(), token.getPublicKey());
            return JsonHelper.toJson(buffer).orElseThrow(TokenProviderException::new);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            throw new TokenProviderException();
        }
    }

    public static Token decode(String str){
        Token buffer = JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
        String login = buffer.getLogin();
        String nickname = buffer.getNickname();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, buffer.getPublicKey());
            buffer.setLogin(new String(cipher.doFinal(Base64.getDecoder().decode(login))));
            buffer.setNickname(nickname);
            return JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
            throw new TokenProviderException(e.getMessage());
        }
    }

}
