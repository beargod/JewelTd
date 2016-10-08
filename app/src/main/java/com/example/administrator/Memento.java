package com.example.administrator;

import com.example.administrator.player.Chapter;
import com.example.administrator.player.Player;

import java.io.Serializable;

/**
 * 备忘录
 */
public class Memento implements Serializable{
    private Player player;
    private static final long serialVersionUID = 1L;
    private Chapter chapter;
    private int saveTime;
    public Memento(Player player,Chapter chapter){
        this.player = player;
        this.chapter = chapter;
        saveTime = 10;
    }
    public void deleteSaveTime(){
        saveTime--;
    }
    public int getSaveTime(){
        return saveTime;
    }
    public void resetMemento(Player player,Chapter chapter){
        this.player = player;
        this.chapter = chapter;
    }
    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
