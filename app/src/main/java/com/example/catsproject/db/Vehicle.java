package com.example.catsproject.db;

import com.example.catsproject.Component;

import java.util.ArrayList;

public class Vehicle {
    ArrayList<Component> components=new ArrayList<>();
    int power, energy, health;
    int player;

    public Vehicle(int player){
        this.player=player;

    }

    void addAll(Component... c){
        for(int i=0;i<c.length;i++){
            components.add(c[i]);
        }
        updateAtributes(c);
    }

    void add(Component c){
        components.add(c);
        updateAtributes(c);
    }

    void updateAtributes(Component... c){
        for(int i=0;i<c.length;i++){
            power+=c[i].getPower();
            energy+=c[i].getEnergy();
            health+=c[i].getHealth();
        }
    }
}
