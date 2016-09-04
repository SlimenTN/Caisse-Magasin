package smt.cm.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="prd_category")
public class CategoryProduct implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="background")
	private String background;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="categoryProduct", fetch=FetchType.EAGER)
	private Set<Product> relatedProducts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
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
		CategoryProduct other = (CategoryProduct) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.designation;
	}
}
