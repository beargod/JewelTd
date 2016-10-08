package com.example.administrator.jewel;

import java.io.Serializable;

/**
 * 石头类
 */
public class Stone implements Serializable{
    private String name;
    private int x;
    private int y;
    public Stone(int x,int y){
        this.name = "这是一块普通的石头";
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getName() {
        return name;
    }
}
