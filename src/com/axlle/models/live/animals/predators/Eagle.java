package com.axlle.models.live.animals.predators;

public class Eagle extends Predator {
    public Eagle() {
        this.setUuid();
        this.setWeight(6);
        this.setMaxSaturation(1);
        this.setSpeed(1);
        this.ration.put("Fox", 10);
        this.ration.put("Rabbit", 90);
        this.ration.put("Mouse", 90);
        this.ration.put("Duck", 80);
    }
}
