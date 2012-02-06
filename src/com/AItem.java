package com;

public abstract class AItem implements IItem {
	
	private int id;
	private double unitPrice;
	private int quantity;
	
	public AItem(String[] datas) {
		id = Integer.parseInt(datas[0]);
		unitPrice = Double.parseDouble(datas[1]);
		quantity = Integer.parseInt(datas[2]);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public double getUnitPrice() {
		return unitPrice;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public void setQuantity(int i){
		quantity = i;
	}
}
