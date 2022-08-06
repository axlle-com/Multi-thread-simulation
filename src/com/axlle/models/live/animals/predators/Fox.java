package com.axlle.models.live.animals.predators;

public class Fox extends Predator {
    public Fox() {
        this.setUuid();
        this.setWeight(8);
        this.setMaxSaturation(2);
        this.setSpeed(2);
        this.ration.put("Rabbit", 70);
        this.ration.put("Mouse", 90);
        this.ration.put("Duck", 60);
        this.ration.put("Caterpillar", 40);
    }
}
