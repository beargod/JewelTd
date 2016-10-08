package com.example.administrator.skill.towerSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;
import com.example.administrator.skill.monsterSkill.ArmorBreak;
import com.example.administrator.skill.monsterSkill.DeBuff;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/1.
 */
public class ArmorBreakSkill extends AttackSkill {
    private int armorBreak_number;
    private int armorBreak_time;

    public int getArmorBreak_number() {
        return armorBreak_number;
    }

    public int getArmorBreak_time() {
        return armorBreak_time;
    }

    public ArmorBreakSkill(int skillLevel){
        super.setSkillLevel(skillLevel);
        super.setSkillNumber(4);
        setSkillType(1);
        super.setSkillName("破甲");
        armorBreak_time=8;
        switch (skillLevel){
            case 1:armorBreak_number=5;break;
            case 2:armorBreak_number=7;break;
        }
    }
    public void handlerSkill(ArrayList monsters, int index, Jewel jewel) {
        Monster monster =(Monster)monsters.get(index);
        setSkillType(1);
        ArrayList debuffs = monster.getDebuffs();
        synchronized (debuffs) {
            for (int i = 0; i < debuffs.size(); i++) {
                DeBuff deBuff = (DeBuff) debuffs.get(i);
                if (deBuff.getId() == 2 && deBuff.getLevel() == this.getSkillLevel()) {
                    monster.removeDebuff(i);
                    deBuff.removeDebuff(monster);
                    break;
                }
            }
            monster.addDebuff(new ArmorBreak(this,jewel));
        }
    }
}
