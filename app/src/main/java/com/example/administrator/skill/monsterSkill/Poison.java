package com.example.administrator.skill.monsterSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;
import com.example.administrator.skill.towerSkill.PoisonSkill;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Poison extends DeBuff{
    private int posionEffect;//毒液效果
    private long lastTime;//上次造成伤害的时间
    public Poison(PoisonSkill abs, Jewel jewel){
        this.id = 3;
        long time = System.currentTimeMillis();
        this.startTime =time;
        lastTime = time;
        this.setJewel(jewel);
        this.remainTime =abs.getRetainTime();
        this.posionEffect = abs.getPoison_damage();
        this.level = abs.getSkillLevel();
    }

    public int getPosionEffect() {
        return posionEffect;
    }

    public void setPosionEffect(int decelerationEffect) {
        this.posionEffect = decelerationEffect;
    }
    public void handlerDebuff(Monster monster) {
        long time = System.currentTimeMillis();
        if((time-lastTime)>500){
            int damage = posionEffect*(1-monster.getMk()/100);
            if(monster.isAlive())
                if (monster.beAttacked(damage))
                    jewel.addExperience(10);
            lastTime = time;
        }
    }
    public void removeDebuff(Monster monster) {

    }
}
