package com.axlle.models.live.plants;

import com.axlle.config.Settings;
import com.axlle.models.live.Live;

import java.util.HashMap;
import java.util.Map;

public class Plant extends Live {
    public Plant() {
        this.setUuid();
        HashMap<String, Double> set = Settings.getLivesSetting(this.getClass().getName());
        if (set != null) {
            for (Map.Entry<String, Double> current : set.entrySet()) {
                Double value = current.getValue();
                String key = current.getKey();
                if (key.equals("weight")) {
                    this.setWeight(value);
                }
            }
        }
    }
}
