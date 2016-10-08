package com.example.administrator;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class ExitApplication {
    private List activityList = new LinkedList();
    private static ExitApplication instance;
    public static ExitApplication getInstance(){
        if(null==instance){
            instance=new ExitApplication();
        }
        return instance;
    }
    public void add(Activity activity){
        activityList.add(activity);
    }
    public void remove(Activity activity){
        activityList.remove(activity);
    }
    public void exit(){
        for(int i=0;i<activityList.size();i++){
            Activity activity = (Activity) activityList.get(i);
            activity.finish();
        }
        System.exit(0);
    }
}
