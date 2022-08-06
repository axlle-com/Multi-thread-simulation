package com.axlle.models.live.animals.herbivores;

public class Duck extends Herbivore {
    public Duck() {
        super();
        this.ration.put("Caterpillar", 90);
        this.setWeight(1);
        this.setMaxSaturation(0.15);
        this.setSpeed(4);
    }

}
