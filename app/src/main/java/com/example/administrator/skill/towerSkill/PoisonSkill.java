package com.example.administrator.skill.towerSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;
import com.example.administrator.skill.monsterSkill.Poison;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/1.
 */
public class PoisonSkill extends AttackSkill{
    private int poison_damage;
    private int retainTime;
    public int getPoison_damage() {
        return poison_damage;
    }

    public PoisonSkill(int skillLevel){
        super.setSkillLevel(skillLevel);
        super.setSkillNumber(5);
        setSkillType(1);
        super.setSkillName("毒液");
        retainTime = 5;
        switch (skillLevel){
            case 1:poison_damage=1;break;
            case 2:poison_damage=2;break;
            case 3:poison_damage=4;break;
            case 4:poison_damage=8;break;
        }
    }

    public int getRetainTime() {
        return retainTime;
    }

    public void setRetainTime(int retainTime) {
        this.retainTime = retainTime;
    }

    public void setPoison_damage(int poison_damage) {
        this.poison_damage = poison_damage;
    }

    @Override
    public void handlerSkill(ArrayList monsters, int index, Jewel jewel) {
        Monster monster =(Monster)monsters.get(index);
        ArrayList debuffs = monster.getDebuffs();
        synchronized (debuffs) {
            monster.addDebuff(new Poison(this,jewel));
        }
    }
}
