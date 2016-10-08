package com.example.administrator.skill.towerSkill;

/**
 * 暴击
 */
public class CritSkill extends JewelSkill{
    private double critMultiple;
    private int critChance;
    public CritSkill(int skillLevel){
        super.setSkillLevel(skillLevel);
        super.setSkillType(4);
        super.setSkillName("暴击");
        switch (skillLevel){
            case 1:critMultiple=1.5;critChance=20;break;
            case 2:critMultiple=2;critChance=20;break;
        }
    }
    public int getCritDamage(int damage){
        if(critChance>Math.random()*100&&Math.random()*100>=0){
            damage*=critMultiple;
        }
        return damage;
    }
}
