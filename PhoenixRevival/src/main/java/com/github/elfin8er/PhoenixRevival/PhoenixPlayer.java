package com.github.elfin8er.PhoenixRevival;

public class PhoenixPlayer {

	private Double money;
	
	PhoenixPlayer(){
		this.money = SETTINGS.StartingMoney;
	}
	
	public void setMoney(Double amount){
		this.money = amount;
	}
	
	public Double getMoney(){
		return this.money;
	}
	
}
