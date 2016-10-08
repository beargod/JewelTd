package com.example.administrator.jewelshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片资源类
 */
public class ImageResourse {
    private Context context;
    private Map images = new HashMap();
    private Map monsterImages = new HashMap();
    private int default_pic = R.mipmap.ic_launcher;
    public ImageResourse(Context context){
        this.context=context;
        monsterImages.put(1,initBitMap(R.drawable.boss));
        images.put(-1,initBitMap(R.drawable.stone));
        images.put(0,initBitMap(R.drawable.backgroud));
        images.put(1,initBitMap(R.drawable.red));
        images.put(2,initBitMap(R.drawable.yellow));
        images.put(3,initBitMap(R.drawable.blue));
        images.put(4,initBitMap(R.drawable.purple));
        images.put(5,initBitMap(R.drawable.green));
        images.put(6,initBitMap(R.drawable.egg));
        images.put(7,initBitMap(R.drawable.diamond));
        images.put(8,initBitMap(R.drawable.q));
        images.put(9,initBitMap(R.drawable.malachite));
    }
    //通过图片id获取bitmap
    //id-----0,背景图片
    public Bitmap getImageById(int id){
        if(images.containsKey(id))
            return (Bitmap) images.get(id);
        else
            return initBitMap(default_pic);
    }
    public Bitmap getMonsterById(int id){
        if(images.containsKey(id))
            return (Bitmap) monsterImages.get(id);
        else
            return initBitMap(default_pic);
    }
    //通过资源id初始化bitmap
    private Bitmap initBitMap(int resid){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        options.inSampleSize=2;
        InputStream is = context.getResources().openRawResource(resid);
        return BitmapFactory.decodeStream(is,null,options);
    }
}
