package com.github.utils;


import com.github.payload.Envelope;
import com.github.payload.Topic;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class JsonHelperTest {
    private static final String envelopStr = "{}";

    private static final Envelope env = new Envelope(Topic.auth,"ololo");

    @Test
    public void toJson() {
        Optional<String> result = JsonHelper.toJson(env);
        String act = result.orElseThrow();
        System.out.println(act);
    }

    @Test
    public void fromJson() {
        Optional<Envelope> result = JsonHelper.fromJson(envelopStr,Envelope.class);
        Envelope act = result.orElseThrow();
        System.out.println(act);
    }
}