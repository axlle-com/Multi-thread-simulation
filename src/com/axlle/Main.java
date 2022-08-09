package com.axlle;

import com.axlle.config.Settings;
import com.axlle.models.island.Island;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static long time;

    public static void main(String[] args) {
        time = System.currentTimeMillis();
        Island island = new Island();
        island.initialize();
        island.print();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(island, 0, 1, TimeUnit.SECONDS);
    }
}
