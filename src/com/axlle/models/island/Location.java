package com.axlle.models.island;

import com.axlle.Main;
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
    HashMap<String, Live> lives = new HashMap<>();
    HashMap<String, Live> newLives = new HashMap<>();
    HashMap<String, Live> newborn = new HashMap<>();
    HashMap<String, Integer> currentLives = new HashMap<>();
    HashMap<String, Integer> maxLives = new HashMap<>() {{
        put(Bear.class.getName(), 5);
        put(Boa.class.getName(), 30);
        put(Fox.class.getName(), 30);
        put(Wolf.class.getName(), 30);
        put(Boar.class.getName(), 50);
        put(Deer.class.getName(), 20);
        put(Duck.class.getName(), 200);
        put(Goat.class.getName(), 140);
        put(Eagle.class.getName(), 20);
        put(Horse.class.getName(), 20);
        put(Mouse.class.getName(), 500);
        put(Sheep.class.getName(), 140);
        put(Plant.class.getName(), 200);
        put(Rabbit.class.getName(), 150);
        put(Buffalo.class.getName(), 10);
        put(Caterpillar.class.getName(), 1000);
    }};
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
        for (Map.Entry<String, Integer> entry : maxLives.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = 0; i < value; i++) {
                try {
                    Class<?> clazz = Class.forName(key);
                    Constructor<?> constructor = clazz.getConstructor();
                    var newInstance = (Live) constructor.newInstance();
                    lives.put(newInstance.getUuid(), newInstance);
                    this.putCurrentLives(newInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void life() {
        if (newLives.size() > 0) {
            lives.putAll(newLives);
            newLives.clear();
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

    public synchronized void clear() {
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
                    if (this.maxLives.containsKey(name) && temp > this.maxLives.get(name)) {
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
            Location location = Island.locations[x][y];
            location.newLives.put(animal.getUuid(), animal);
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
