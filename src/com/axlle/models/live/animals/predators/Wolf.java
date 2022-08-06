package com.axlle.models.live.animals.predators;

public class Wolf extends Predator {
    public Wolf() {
        this.setUuid();
        this.setWeight(50);
        this.setMaxSaturation(8);
        this.setSpeed(3);
        this.ration.put("Horse", 10);
        this.ration.put("Deer", 15);
        this.ration.put("Rabbit", 60);
        this.ration.put("Mouse", 80);
        this.ration.put("Goat", 60);
        this.ration.put("Sheep", 70);
        this.ration.put("Boar", 15);
        this.ration.put("Buffalo", 10);
        this.ration.put("Duck", 40);
    }
}
