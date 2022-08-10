package com.axlle.config;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class Settings {
    private static volatile Settings self;
    private HashMap<String, Integer> maxLives;
    private HashMap<String, HashMap<String, Double>> livesSetting;
    private HashMap<String, HashMap<String, Integer>> livesRation;
    private int x = 1;
    private int y = 1;

    private Settings() {
        try {
            URL resource = Settings.class.getClassLoader().getResource("com/axlle/config/init.yml");
            ObjectReader objectReader = new YAMLMapper().readerForUpdating(this);
            if (Objects.nonNull(resource)) {
                objectReader.readValue(resource.openStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Settings inst() {
        if (Settings.self == null) {
            synchronized (Settings.class) {
                Settings.self = new Settings();
            }
        }
        return Settings.self;
    }

    public static HashMap<String, Integer> getMaxLives() {
        return Settings.inst().maxLives;
    }

    public Settings setMaxLives(HashMap<String, Integer> maxLives) {
        this.maxLives = maxLives;
        return this;
    }

    public static int getX() {
        return Settings.inst().x;
    }

    public Settings setX(int x) {
        this.x = x;
        return this;
    }

    public static int getY() {
        return Settings.inst().y;
    }

    public Settings setY(int y) {
        this.y = y;
        return this;
    }

    public static HashMap<String, Double> getLivesSetting(String live) {
        Settings settings = Settings.inst();
        if (settings.livesSetting.containsKey(live)) {
            return settings.livesSetting.get(live);
        }
        return null;
    }

    public static HashMap<String, Integer> getLivesRation(String live) {
        Settings settings = Settings.inst();
        if (settings.livesRation.containsKey(live)) {
            return settings.livesRation.get(live);
        }
        return null;
    }

    public Settings setLivesSetting(HashMap<String, HashMap<String, Double>> livesSetting) {
        this.livesSetting = livesSetting;
        return this;
    }

    public Settings setLivesRation(HashMap<String, HashMap<String, Integer>> livesRation) {
        this.livesRation = livesRation;
        return this;
    }
}
