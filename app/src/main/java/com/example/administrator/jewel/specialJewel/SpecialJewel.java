package com.example.administrator.jewel.specialJewel;

import com.example.administrator.jewel.Jewel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/13.
 */
public class SpecialJewel extends Jewel implements Serializable{
    private ArrayList<Jewel> material_jewel = new ArrayList<Jewel>();
    public  ArrayList<Jewel> getSynthesisWay() {
        return material_jewel;
    }
    public void setSynthesisWay(ArrayList<Jewel> synthesisWay) {
        this.material_jewel = synthesisWay;
    }
}