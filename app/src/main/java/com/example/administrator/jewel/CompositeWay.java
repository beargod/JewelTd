package com.example.administrator.jewel;

import com.example.administrator.jewel.basicJewel.BasicJewel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/28.
 */
public class CompositeWay {
    private String[] materials;
    private int result_id;
    public CompositeWay(String[] material_stone, int result_id) {
        this.materials = material_stone;
        this.result_id = result_id;
    }
    /*判断该合成方法中是否含有指定塔*/

    public String[] getMaterials() {
        return materials;
    }

    public int getResult_id() {
        return result_id;
    }

    public int[] compositeNeed(ArrayList jewels) {
        int[] cn = new int[4];
        cn[0] = 0;//0.不能融合1.能融合
        for (int j = 0; j < materials.length / 2; j++) {
            for (int i = 0; i < jewels.size(); i++) {
                Jewel t = (Jewel) jewels.get(i);
                int id = Integer.parseInt(materials[j * 2]);
                int quality = Integer.parseInt(materials[j * 2 + 1]);
                if (t.getId() == id) {
                    if (id <= 8) {
                        BasicJewel it = (BasicJewel) t;
                        if (it.getQuality() == quality) {
                            cn[j+1] = i;
                            cn[0] = 1;
                            break;
                        }
                        else {
                            cn[0]=0;
                        }
                    }
                    else {
                        cn[j+1] = i;
                        cn[0] = 1;
                    }
                }
                else cn[0]=0;
            }
            if(cn[0]==0){
                break;
            }
        }
        return cn;
    }
    public boolean isContainJewel(Jewel jewel){
        boolean judge = false;
        for(int i = 0;i<materials.length;){
            if(jewel.getId()==toInt(materials[i])){
                i++;
                if(toInt(materials[i])==0){
                    judge=true;
                }
                else{
                    BasicJewel t = (BasicJewel)jewel;
                    if(t.getQuality()==toInt(materials[i])) {
                        judge = true;

                    }
                }
            }
            else
                i++;
            i++;
        }
        return judge;
    }

    private int toInt(String s){
        return Integer.parseInt(s);
    }
}
