package com.axlle.models.live;

import com.axlle.config.Settings;
import com.axlle.models.live.plants.Plant;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Live {
    public Map<String, Integer> ration = new HashMap<>();
    private double weight;
    private String uuid;
    private Boolean live = true;
    private Boolean isReproduce = false;

    public Live() {
        this.setUuid();
        HashMap<String, Double> set = Settings.getLivesSetting(this.getClass().getName());
        if (set != null) {
            for (Map.Entry<String, Double> current : set.entrySet()) {
                Double value = current.getValue();
                String key = current.getKey();
                if (key.equals("weight")) {
                    this.setWeight(value);
                }
                if (key.equals("maxSaturation")) {
                    this.setWeight(value);
                }
            }
        }

    }

    public Boolean getReproduce() {
        return isReproduce;
    }

    public void setReproduce(Boolean reproduce) {
        isReproduce = reproduce;
    }

    public double getWeight() {
        return weight;
    }

    public Live setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Boolean isLive() {
        return live;
    }

    public void die() {
        this.live = false;
    }

    public void reproduce(Live target, HashMap<String, Live> newborn) {
        if (target.isLive() && this != target) {
            String name = this.getClass().getName();
            String nameTarget = target.getClass().getName();
            if (this instanceof Plant && target instanceof Plant) {
                Plant plant = new Plant();
                newborn.put(plant.getUuid(), plant);
            } else if (name.equals(nameTarget) && !this.getReproduce() && !target.getReproduce()) {
                int rand = ThreadLocalRandom.current().nextInt(0, 10);
                if (rand == 0) {
                    try {
                        Constructor<? extends Live> constructor = this.getClass().getConstructor();
                        var newInstance = constructor.newInstance();
                        this.setReproduce(true);
                        target.setReproduce(true);
                        newborn.put(newInstance.getUuid(), newInstance);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
