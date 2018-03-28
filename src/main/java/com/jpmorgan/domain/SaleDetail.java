package com.jpmorgan.domain;

/**
 * @author Thomas Francis
 *
 * POJO Class to map Sale details.
 */
public class SaleDetail {

	private String name;

	private double price;

	private int quantity;

	private double totalCost;
	
	public SaleDetail() {
		super();
	}

	public SaleDetail(String name, double price, int quantity, double totalCost) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.totalCost = totalCost;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

}
