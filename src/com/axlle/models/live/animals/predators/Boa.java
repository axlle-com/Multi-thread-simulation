package com.axlle.models.live.animals.predators;

/*** удав ***/
public class Boa extends Predator {
    public Boa() {
        this.setUuid();
        this.setWeight(15);
        this.setMaxSaturation(3);
        this.setSpeed(1);
        this.ration.put("Fox", 15);
        this.ration.put("Rabbit", 20);
        this.ration.put("Mouse", 40);
        this.ration.put("Duck", 10);
    }
}
