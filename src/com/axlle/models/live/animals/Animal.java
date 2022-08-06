package com.axlle.models.live.animals;

import com.axlle.models.island.Island;
import com.axlle.models.live.Live;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Live {
    protected Integer moveToX = null;
    protected Integer moveToY = null;
    private int speed;
    private double saturation;
    private double maxSaturation;

    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public int getSpeed() {
        return speed;
    }

    public Animal setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public void addSaturation(double saturation) {
        this.saturation += saturation;
    }

    public double getMaxSaturation() {
        return maxSaturation;
    }

    protected void setMaxSaturation(double saturation) {
        this.saturation = saturation / 2;
        this.maxSaturation = saturation;
    }

    public Integer getMoveToX() {
        return moveToX;
    }

    public void setMoveToX(Integer moveToX) {
        this.moveToX = moveToX;
    }

    public Integer getMoveToY() {
        return moveToY;
    }

    public void setMoveToY(Integer moveToY) {
        this.moveToY = moveToY;
    }

    public void eat(Live target) {
        String key = target.getClass().getSimpleName();
        boolean isSaturation = this.getSaturation() >= this.getMaxSaturation();
        if (this.ration.containsKey(key) && target.isLive() && !isSaturation) {
            int rand = ThreadLocalRandom.current().nextInt(0, 100);
            int chance = this.ration.get(key);
            if (rand <= chance) {
                this.addSaturation(target.getWeight());
                target.die();
            }
        }
    }

    public void revision() {
        if (this.getSaturation() <= 1) {
            this.die();
        } else {
            this.setSaturation(1);
        }
    }

    public void chooseDirection() {

    }

    public void move(int x, int y) {
        if (this.getSpeed() > 0 && this.isLive()) {
            int newX = x;
            int newY = y;
            this.chooseDirection();
            int step = ThreadLocalRandom.current().nextInt(-this.getSpeed(), this.getSpeed() + 1);
            if (step > 0) {
                if (ThreadLocalRandom.current().nextInt(0, 2) == 1) {
                    newX += step;
                    if (newX < 0) {
                        newX = 0;
                    }
                    if (newX > Island.X - 1) {
                        newX = Island.X - 1;
                    }
                } else {
                    newY += step;
                    if (newY < 0) {
                        newY = 0;
                    }
                    if (newY > Island.Y - 1) {
                        newY = Island.Y - 1;
                    }
                }
            }
            if (newX == x && newY == y) {
                this.setMoveToX(null);
                this.setMoveToY(null);
            } else {
                this.setMoveToX(newX);
                this.setMoveToY(newY);
            }
        }
    }
}