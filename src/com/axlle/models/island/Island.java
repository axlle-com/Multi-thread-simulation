package com.axlle.models.island;

import com.axlle.Main;
import com.axlle.config.Settings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Island implements Runnable {
    public static final int X = Settings.inst().getX();
    public static final int Y = Settings.inst().getY();
    public static Location[][] locations = new Location[X][Y];
    ExecutorService executor = Executors.newFixedThreadPool(Island.X * Island.Y);

    public void initialize() {
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                Location location = new Location(i, j);
                locations[i][j] = location;
            }
        }
    }

    public void run() {
        for (Location[] location : locations) {
            for (Location value : location) {
                executor.execute(value);
            }
        }
        System.out.println("Основной поток: " + (System.currentTimeMillis() - Main.time));
    }

    public void print() {
        for (Location[] location : locations) {
            for (Location value : location) {
                System.out.println(value);
            }
        }
    }
}

