package com.example.administrator.skill.towerSkill;

import java.io.Serializable;

/**
 * 增益状态
 */
public class Buff implements Serializable{
    private String name;
    private int id;
    private int level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
