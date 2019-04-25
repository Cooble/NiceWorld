package cs.cooble.nice.entity;

import cs.cooble.nice.util.NBTSaveable;
import cs.cooble.nice.util.NBT;

/**
 * Created by Matej on 6.6.2015.
 */
public class MatejStats implements NBTSaveable {
    private int health, max_health;
    private int hunger, max_hunger;
    private int water, max_water;
    private double speed;

    public MatejStats() {
        max_health = 100;
        max_hunger = 1000;
        max_water = 100;
        speed=10;

        health = max_health;
        hunger = max_hunger;
        water = max_water;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getHunger() {
        return hunger;
    }

    public int getWater() {
        return water;
    }


    public void addHealth(int health0) {
        this.health += health0;
        if (health > max_health)
            health = max_health;
        if (health < 0)
            health = 0;
    }

    public void addHunger(int hunger0) {
        this.hunger += hunger0;
        if (hunger > max_hunger)
            hunger = max_hunger;
        if (hunger < 0)
            hunger = 0;
    }

    public void addWater(int water0) {
        this.water += water0;
        if (water > max_water)
            water = max_water;
        if (water < 0)
            water = 0;
    }

    public double getHealthD() {
        return (double) health / (double) max_health + 0.0;
    }

    public double getHungerD() {
        return (double) hunger / (double) max_hunger + 0.0;
    }

    public double getWaterD() {
        return (double) water / (double) max_water + 0.0;
    }

    @Override
    public void writeToNBT(NBT nbt) {
        nbt.setIntenger("health", health);
        nbt.setIntenger("hunger", hunger);
        nbt.setIntenger("water", water);
    }

    @Override
    public void readFromNBT(NBT nbt) {
        health = nbt.getInteger("health");
        hunger = nbt.getInteger("hunger");
        water = nbt.getInteger("water");
    }


}
