package com.axlle.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Settings {
    private static volatile Settings self;
    private HashMap<String, Integer> maxLives;
    private int x;
    private int y;

    private Settings() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("com/axlle/config/init.yml")).getFile());

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            Settings.self = mapper.readValue(file, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public String toString() {
        return "Settings{" +
                "maxLives=" + maxLives +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public HashMap<String, Integer> getMaxLives() {
        return maxLives;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
