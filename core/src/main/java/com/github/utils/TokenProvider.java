package com.github.utils;

import com.github.payload.Token;

import java.util.Optional;

public class TokenProvider {

    public static String encode(Token t) {
        Optional<String> str = JsonHelper.toJson(t);
        // TODO: 16.04.2021 encoding
        return str.orElse(null);
    }

    public static Token decoding(String str) {
        return JsonHelper.fromJson(str, Token.class).orElse(null);
    }

}
