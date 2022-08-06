package com.axlle.models.live.animals.herbivores;

/*** кабан ***/
public class Boar extends Herbivore {
    public Boar() {
        super();
        this.ration.put("Mouse", 50);
        this.ration.put("Caterpillar", 90);
        this.setWeight(400);
        this.setMaxSaturation(50);
        this.setSpeed(2);
    }
}
