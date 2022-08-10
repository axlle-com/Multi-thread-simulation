package com.axlle.models.island;

import com.axlle.Main;
import com.axlle.config.Settings;
import com.axlle.models.live.Live;
import com.axlle.models.live.animals.Animal;
import com.axlle.models.live.animals.herbivores.*;
import com.axlle.models.live.animals.predators.*;
import com.axlle.models.live.plants.Plant;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Location implements Runnable {
    final HashMap<String, Live> newLives = new HashMap<>();
    HashMap<String, Live> lives = new HashMap<>();
    HashMap<String, Live> newborn = new HashMap<>();
    HashMap<String, Integer> currentLives = new HashMap<>();
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        this.init();
    }

    public int getX() {
        return x;
    }

    public Location setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Location setY(int y) {
        this.y = y;
        return this;
    }

    public void run() {
        this.life();
        this.clear();
        System.out.println(this);
        System.out.println(System.currentTimeMillis() - Main.time);
    }

    public void init() {
        for (Map.Entry<String, Integer> entry : Settings.getMaxLives().entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = 0; i < value; i++) {
                try {
                    Class<?> clazz = Class.forName(key);
                    Constructor<?> constructor = clazz.getConstructor();
                    Live newInstance = (Live) constructor.newInstance();
                    lives.put(newInstance.getUuid(), newInstance);
                    this.putCurrentLives(newInstance);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void life() {
        if (newLives.size() > 0) {
            lives.putAll(newLives);
            synchronized (this.newLives){
                newLives.clear();
            }
        }
        for (Map.Entry<String, Live> current : lives.entrySet()) {
            Live value = current.getValue();
            if (value.isLive()) {
                for (Map.Entry<String, Live> target : lives.entrySet()) {
                    Live targetValue = target.getValue();
                    if (value instanceof Animal animal) {
                        animal.eat(targetValue);
                        animal.reproduce(targetValue, newborn);
                    } else {
                        value.reproduce(targetValue, newborn);
                    }
                }
                if (value instanceof Animal animal) {
                    animal.revision();
                    animal.move(this.getX(), this.getY());
                }
            }
        }
    }

    public void clear() {
        HashMap<String, Integer> count = new HashMap<>();
        lives.putAll(newborn);
        newborn.clear();
        currentLives.clear();
        Iterator<Map.Entry<String, Live>> it = lives.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Live> target = it.next();
            Live live = target.getValue();
            if (!live.isLive()) {
                it.remove();
            } else {
                if (live instanceof Animal animal && this.migration(animal)) {
                    it.remove();
                } else {
                    String name = live.getClass().getName();
                    int temp = 0;
                    if (count.containsKey(name)) {
                        temp = count.get(name);
                    }
                    temp++;
                    if (Settings.getMaxLives().containsKey(name) && temp > Settings.getMaxLives().get(name)) {
                        it.remove();
                    } else {
                        count.put(name, temp);
                        live.setReproduce(false);
                        this.putCurrentLives(live);
                    }
                }
            }
        }
    }

    public boolean migration(Animal animal) {
        Integer x = animal.getMoveToX();
        Integer y = animal.getMoveToY();
        if (x == null || y == null) {
            return false;
        }
        try {
            synchronized(Island.locations[x][y].newLives){
                Location location = Island.locations[x][y];
                location.newLives.put(animal.getUuid(), animal);
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public String toString() {

        return "********************" + "\n"
                + "Location[" + this.x + "][" + this.y + "]: " + "\n"
                + " - Всего: " + lives.size() + "\n"
                + " - Мигранты: " + newLives.size() + "\n"
                + " - Детально: " + currentLives + "\n"
                + "********************";
    }

    private void putCurrentLives(Live target) {
        String name = target.getClass().getSimpleName();
        int temp = 0;
        if (currentLives.containsKey(name)) {
            temp = currentLives.get(name);
        }
        temp++;
        currentLives.put(name, temp);
    }
}
