package smt.cm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="product")
public class Product implements Serializable{
	
	public static final String VENTE_DIRECT = "V";
	public static final String BALANCE = "B";
	
	@Id @GeneratedValue
    @Column(name = "id")
	private int id;
	
	@Column(name = "code_prd")
	private String code;
	
	@Column(name = "barcode")
	private String BarreCode;
	
	@Column(name = "designation_produit")
	private String designation;
	
	@Column(name = "description_produit")
	private String description;
	
	@Column(name = "sale_price")
	private double salePrice;
	
	@Column(name = "photo")
	private String image;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private CategoryProduct categoryProduct;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBarreCode() {
		return BarreCode;
	}
	public void setBarreCode(String barreCode) {
		BarreCode = barreCode;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double price) {
		this.salePrice = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public CategoryProduct getCategoryProduct() {
		return categoryProduct;
	}
	public void setCategoryProduct(CategoryProduct categoryProduct) {
		this.categoryProduct = categoryProduct;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}
	 
	@Override
	public String toString() {
		return this.designation;
	}
}
