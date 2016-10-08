package com.example.administrator.skill.towerSkill;

import com.example.administrator.jewel.Jewel;

import java.util.ArrayList;

/**
 * 攻击类型的技能
 */
public abstract class AttackSkill extends JewelSkill{
    private int jewelSkillNumber;//技能编号

    public int getSkillNumber() {
        return jewelSkillNumber;
    }
    public abstract void handlerSkill(ArrayList monsters, int index, Jewel jewel);
    public void setSkillNumber(int jewelSkillNumber) {
        this.jewelSkillNumber = jewelSkillNumber;
    }
}
