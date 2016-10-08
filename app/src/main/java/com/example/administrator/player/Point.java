package com.example.administrator.player;


public class Point {
	private Point parent;
	private int f;//f=g+h
	private int g;
	private int h;
	private int x;
	private int y;
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getF(){
		return f;
	}
	public int getG(){
		return g;
	}
	public Point getParent(){
		return parent;
	}
	public void setG(int g){
		this.g=g;
	}
	public void setH(int h){
		this.h=h;
	}
	public Point(int x,int y){
		this.x = x;
		this.y = y;
	}
	public void setParent(Point parent){
		this.parent = parent;
	}
	public void calcF(){
		f=g+h;
	}
}
