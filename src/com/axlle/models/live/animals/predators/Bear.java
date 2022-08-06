package com.axlle.models.live.animals.predators;

public class Bear extends Predator {
    public Bear() {
        this.setUuid();
        this.setWeight(500);
        this.setMaxSaturation(80);
        this.setSpeed(2);
        this.ration.put("Boa", 80);
        this.ration.put("Horse", 40);
        this.ration.put("Deer", 80);
        this.ration.put("Rabbit", 80);
        this.ration.put("Mouse", 90);
        this.ration.put("Goat", 70);
        this.ration.put("Sheep", 70);
        this.ration.put("Boar", 50);
        this.ration.put("Buffalo", 20);
        this.ration.put("Duck", 10);
    }
}
