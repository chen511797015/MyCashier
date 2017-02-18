package com.pax.e820.model;

import java.io.Serializable;

public class ModelOrdered implements Serializable{
	
	private String name;
	private int number;
	private double price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public ModelOrdered(String name, int number, double price) {
		super();
		this.name = name;
		this.number = number;
		this.price = price;
	}
	
	public ModelOrdered() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
