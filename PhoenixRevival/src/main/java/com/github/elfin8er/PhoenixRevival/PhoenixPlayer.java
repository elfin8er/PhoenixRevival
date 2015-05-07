package com.github.elfin8er.PhoenixRevival;

import java.util.List;

public class PhoenixPlayer {

	private double money;
	private List<String> mail;
	
	PhoenixPlayer(){
		this.money = SETTINGS.StartingMoney;
	}
	
	//money
	public void setMoney(double amount){
		this.money = amount;
	}
	
	public void changeMoney(double amount){
		this.money += amount;
	}
	
	public double getMoney(){
		return this.money;
	}
	
	//mail
	public void addMail(String message){
		mail.add(message);
	}
	
	public List<String> getmail(){
		return mail;
	}
	
}
