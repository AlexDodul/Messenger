package com.github.utils;

import com.github.exceptions.TokenProviderException;
import com.github.payload.Token;

public class TokenProvider {

    public static String encoding(Token token){
        return JsonHelper.toJson(token).orElseThrow(TokenProviderException::new);
    }

    public static Token decoding(String str){
        return JsonHelper.fromJson(str, Token.class).orElseThrow(TokenProviderException::new);
    }

}
