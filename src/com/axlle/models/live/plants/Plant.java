package com.axlle.models.live.plants;

import com.axlle.models.live.Live;

public class Plant extends Live {
    public static final int QUANTITY = 200;
    public static final int SPEED = 0;

    public Plant() {
        this.setWeight(1);
        this.setUuid();
    }
}
