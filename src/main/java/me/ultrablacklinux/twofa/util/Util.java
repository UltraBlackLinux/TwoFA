package me.ultrablacklinux.twofa.util;

import me.ultrablacklinux.twofa.config.Config;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class Util {
    public static String toBase(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String fromBase(String base) {
        return new String(Base64.getDecoder().decode(base.getBytes()));
    }

    public static String freshPassword() {
        String password = Config.get().registration.defaultPass;

        if (Config.get().registration.random) { //generate a random password
            ArrayList<String> tmppass = new ArrayList<>();
            for (int i = 0; i < Config.get().registration.randomLength; i++) {
                ArrayList<String> chars = new ArrayList<>(Arrays.asList(Config.get().registration.randomChars.split("")));

                //Collections.shuffle(chars);
                //tmppass.add(chars.get(0));
                tmppass.add(chars.get(new SecureRandom().nextInt(chars.size())));
            }
            password = String.join("", tmppass.toArray(new String[tmppass.size()]));
        }
        return password;
    }
}
