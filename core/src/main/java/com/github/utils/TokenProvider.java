package com.github.utils;

import com.github.exceptions.TokenProviderException;
import com.github.payload.Token;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

public class TokenProvider {

    private static PrivateKey privateKey;

    private static PublicKey publicKey;

    static {
        try {
            KeyPairGenerator keyPairGenerator;
            KeyPair keyPair;
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encode(Token token){
        String login = token.getLogin();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            login = Base64.getEncoder().encodeToString(cipher.doFinal(login.getBytes()));
            Token buffer = new Token(login, token.getNickname(), token.getCreatedAt(), token.getExpireIn());
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
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            buffer.setLogin(new String(cipher.doFinal(Base64.getDecoder().decode(login))));
            buffer.setNickname(nickname);
            return JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
            throw new TokenProviderException(e.getMessage());
        }
    }

}
