package com.example.administrator.jewel.specialJewel;

import com.example.administrator.jewel.Jewel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/21.
 */
public class SpecialJewelController {
    public SpecialJewel construct(ArrayList<Jewel> materials,int builder_type,int x,int y,ArrayList jewels) {
        SpecialJewelBuilder stb;
        SpecialJewel sTower;
        stb = new MalachiteBuilder();
        switch (builder_type){
            case 1:stb = new MalachiteBuilder();break;
        }
        stb.buildMaterial_jewel(materials);
        stb.buildDamage();
        stb.buildSpeed();
        stb.buildName();
        stb.buildID();
        stb.buildSkill();
        stb.buildLevel();
        stb.buildRange();
        stb.buildLastAttackTime(0);
        stb.buildExtraDamage();
        stb.buildExtraSpeed();
        stb.buildIsAttack();
        stb.bulidX(x);
        stb.buildBuffs();
        stb.bulidY(y);
        stb.buildAttack_priority();
        sTower = stb.createSpecialTower();
        return sTower;
    }
}
