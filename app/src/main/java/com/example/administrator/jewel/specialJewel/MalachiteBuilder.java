package com.example.administrator.jewel.specialJewel;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.skill.towerSkill.SplitShotSkill;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/6/13.
 */
public class MalachiteBuilder extends SpecialJewelBuilder {
    public MalachiteBuilder() {
    }
    public void buildRange(){
        super.specialJewel.setRange(500);
    }
    public void buildMaterial_jewel(ArrayList<Jewel> materials) {
        super.specialJewel.setSynthesisWay(materials);
    }
    public void buildName() {
        super.specialJewel.setName("孔雀石");
    }
    public void buildDamage() {
        super.specialJewel.setDamage(20);

    }
    public void buildSpeed() {
        int speed = 100;
        super.specialJewel.setSpeed(speed);
    }
    public void buildSkill() {
        ArrayList skills = new ArrayList();
        skills.add(new SplitShotSkill(3));
        super.specialJewel.setSkills(skills);
    }
    public void buildID() {
        super.specialJewel.setId(9);
    }
}
