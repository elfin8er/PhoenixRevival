package com.github.elfin8er.PhoenixRevival;

public class PhoenixPlayer {

	private double money;
	
	PhoenixPlayer(){
		this.money = SETTINGS.StartingMoney;
	}
	
	public void setMoney(double amount){
		this.money = amount;
	}
	
	public void changeMoney(double amount){
		this.money += amount;
	}
	
	public double getMoney(){
		return this.money;
	}
	
}
