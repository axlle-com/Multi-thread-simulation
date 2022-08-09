package com.axlle.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Settings {
    public HashMap<String, Integer> maxLives;
    private static volatile Settings self;

    private Settings(){
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("com/axlle/init.yml")).getFile());

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            self = mapper.readValue(file, Settings.class);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static Settings inst() {
        if (Settings.self == null) {
            synchronized (Settings.class) {
                new Settings();
            }
        }
        return Settings.self;
    }
}
