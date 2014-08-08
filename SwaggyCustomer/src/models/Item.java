package models;

public class Item {
	Delivery delivery;
	Attributes attributes;
	String eta;

	public Delivery getDelivery() {
		return delivery;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public String getEta() {
		if (eta == null)
			return "0";
		else
			return eta;
	}
}
