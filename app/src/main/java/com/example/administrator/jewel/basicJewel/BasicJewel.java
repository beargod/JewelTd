package com.example.administrator.jewel.basicJewel;

import com.example.administrator.jewel.Jewel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/18.
 */
public class BasicJewel extends Jewel implements Serializable{
    private int rate;//产生概率
    private int quality;//品质

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
