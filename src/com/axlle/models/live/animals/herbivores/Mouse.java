package com.axlle.models.live.animals.herbivores;

public class Mouse extends Herbivore {
    public Mouse() {
        super();
        this.ration.put("Caterpillar", 90);
        this.setWeight(0.05);
        this.setMaxSaturation(0.01);
        this.setSpeed(1);
    }
}
