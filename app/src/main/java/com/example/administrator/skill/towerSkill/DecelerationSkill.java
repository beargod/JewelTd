package com.example.administrator.skill.towerSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;
import com.example.administrator.skill.monsterSkill.DeBuff;
import com.example.administrator.skill.monsterSkill.Deceleration;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/1.
 */
//减速技能
public class DecelerationSkill extends AttackSkill{
    private int deceleration_number;
    private int deceleration_time;

    public int getDeceleration_number() {
        return deceleration_number;
    }

    public int getDeceleration_time() {
        return deceleration_time;
    }
    public DecelerationSkill(int skillLevel){
        super.setSkillLevel(skillLevel);
        setSkillType(1);
        super.setSkillNumber(3);
        super.setSkillName("减速");
        this.deceleration_time=8;
        switch (skillLevel){
            case 1:deceleration_number=200;break;
            case 2:deceleration_number=400;break;
        }
    }
    public void handlerSkill(ArrayList monsters, int index, Jewel jewel){
        Monster monster =(Monster)monsters.get(index);
        ArrayList debuffs = monster.getDebuffs();
        synchronized (debuffs) {
            for (int i = 0; i < debuffs.size(); i++) {
                DeBuff deBuff = (DeBuff) debuffs.get(i);
                if (deBuff.getId() == 1 && deBuff.getLevel() == this.getSkillLevel()) {
                    monster.removeDebuff(i);
                    deBuff.removeDebuff(monster);
                    break;
                }
            }
            monster.addDebuff(new Deceleration(this,jewel));
        }
    }
}
