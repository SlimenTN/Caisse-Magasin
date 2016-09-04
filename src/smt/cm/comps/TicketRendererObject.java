package smt.cm.comps;

import smt.cm.entities.Product;

public class TicketRendererObject {
	private Product product;
	private Double quantity;
	private Double remise;

	public TicketRendererObject(Product product, double quantity, double remise) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.remise = remise;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public void increaseQuantity() {
		this.quantity ++;
	}

	public Double getRemise() {
		return remise;
	}

	public void setRemise(Double remise) {
		this.remise = remise;
	}

	
	
}
