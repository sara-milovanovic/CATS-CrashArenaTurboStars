package com.example.catsproject;

import com.example.catsproject.db.Components;

public class Component {
    int power, health, energy;
    int id;

    public Component(int power, int health, int energy) {
        this.power = power;
        this.health = health;
        this.energy = energy;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Components makeFromId(int id){
        switch (id){
            case 1:{
                return new Components(10,10,6,id);
            }
            case 2:{
                return new Components(10,10,6,id);
            }
            case 3:{
                return new Components(10,10,6,id);
            }case 4:{
                return new Components(10,10,6,id);
            }case 5:{
                return new Components(10,10,6,id);
            }
            case 6:{
                return new Components(10,10,6,id);
            }


        }
        return null;
    }

}
