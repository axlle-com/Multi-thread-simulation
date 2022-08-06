package com.axlle;

import com.axlle.models.island.Island;

public class Main {
    public static void main(String[] args) {

        Island island = new Island();
        island.initialize();
        island.print();
        for (int i = 0; i < 1; i++) {
            island.run();
            island.print();
        }
    }
}
