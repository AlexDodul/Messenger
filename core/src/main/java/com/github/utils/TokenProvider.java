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

        String id = token.getId();
        String firstName = token.getFirstName();
        String lastName = token.getLastName();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            id = Base64.getEncoder().encodeToString(cipher.doFinal(id.getBytes()));
            firstName = Base64.getEncoder().encodeToString(cipher.doFinal(firstName.getBytes()));
            lastName = Base64.getEncoder().encodeToString(cipher.doFinal(lastName.getBytes()));
            Token buffer = new Token(id, firstName, lastName, token.getCreatedAt(), token.getExpireIn(), token.getPublicKey());
            return JsonHelper.toJson(buffer).orElseThrow(TokenProviderException::new);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new TokenProviderException();
        } catch (InvalidKeyException e) {
            throw new TokenProviderException();
        }
    }

    public static Token decode(String str){
        Token buffer = JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
        String id = buffer.getId();
        String firstName = buffer.getFirstName();
        String lastName = buffer.getLastName();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, buffer.getPublicKey());
            buffer.setId(new String(cipher.doFinal(Base64.getDecoder().decode(id))));
            buffer.setFirstName(new String(cipher.doFinal(Base64.getDecoder().decode(firstName))));
            buffer.setLastName(new String(cipher.doFinal(Base64.getDecoder().decode(lastName))));
            return JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new TokenProviderException();
        } catch (InvalidKeyException e) {
            throw new TokenProviderException();
        }
    }

}
