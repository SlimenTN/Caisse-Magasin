package smt.cm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Caisse implements Serializable{
	@Id 
	@GeneratedValue
    @Column(name = "id")
	private int id;
	
	private String ipAdress;
	
	private Integer stat;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	public int getStat() {
		return stat;
	}
	public void setStat(int stat) {
		this.stat = stat;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((ipAdress == null) ? 0 : ipAdress.hashCode());
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
		Caisse other = (Caisse) obj;
		if (id != other.id)
			return false;
		if (ipAdress == null) {
			if (other.ipAdress != null)
				return false;
		} else if (!ipAdress.equals(other.ipAdress))
			return false;
		return true;
	}
	
	
}
