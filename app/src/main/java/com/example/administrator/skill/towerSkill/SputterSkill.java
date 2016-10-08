package com.example.administrator.skill.towerSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14.
 */
public class SputterSkill extends AttackSkill {

    private int range;//范围
    private int sputterPct;//溅射百分比

    public SputterSkill(int skillLevel){
        super.setSkillLevel(skillLevel);
        setSkillType(1);
        if(skillLevel==0){
            this.range = 3;
            this.sputterPct = 35;
        }
        else if(skillLevel==1){
            this.range = 3;
            this.sputterPct = 85;
        }
        super.setSkillNumber(1);
        super.setSkillName("溅射");
    }
    public void handlerSkill(ArrayList monsters,int index, Jewel jewel){
        int damage = jewel.getDamage();
        Monster goalMonster = (Monster) monsters.get(index);//目标怪物
        for(int i=0;i<monsters.size();i++){
            if(i!=index){
                Monster monster =(Monster) monsters.get(i);
                int x = goalMonster.getX()-monster.getX();
                int y = goalMonster.getY()-monster.getY();
                if((x*x+y*y)<range*range*100*100){
                    if(monster.beAttacked(damage*sputterPct/100))
                        jewel.addExperience(10);
                }
            }
        }
    }
    public int getSputterPct() {
        return sputterPct;
    }

    public int getRange() {
        return range;
    }

}
