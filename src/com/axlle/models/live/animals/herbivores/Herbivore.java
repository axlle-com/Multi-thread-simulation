package com.axlle.models.live.animals.herbivores;

import com.axlle.models.live.animals.Animal;

public abstract class Herbivore extends Animal {
    public Herbivore() {
        this.setUuid();
        this.ration.put("Plant", 100);
    }

}
