package com.pax.e820.model;

import java.io.Serializable;

import com.pax.e820.utils.OrderMenu.MenuCategory;

public class ModelOrder implements Serializable{
	
	private MenuCategory cate;
	private String name;
	private double price;
	private String intro;
	private int image;
	private int number;
	public MenuCategory getCate() {
		return cate;
	}
	public void setCate(MenuCategory cate) {
		this.cate = cate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public ModelOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ModelOrder [cate=" + cate + ", name=" + name + ", price="
				+ price + ", intro=" + intro + ", image=" + image + ", number="
				+ number + "]";
	}
	public ModelOrder(MenuCategory cate, String name, double price,
			String intro, int image, int number) {
		super();
		this.cate = cate;
		this.name = name;
		this.price = price;
		this.intro = intro;
		this.image = image;
		this.number = number;
	}
	
}
