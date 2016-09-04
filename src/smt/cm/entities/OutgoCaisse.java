package smt.cm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class OutgoCaisse implements Serializable{
	@Id 
	@GeneratedValue
    @Column(name = "id")
	private int id;
	
	private double money;
	
	@ManyToOne
	private CaisseSession caisseSession;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public CaisseSession getCaisseSession() {
		return caisseSession;
	}
	public void setCaisseSession(CaisseSession caisseSession) {
		this.caisseSession = caisseSession;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(money);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		OutgoCaisse other = (OutgoCaisse) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(money) != Double
				.doubleToLongBits(other.money))
			return false;
		return true;
	}
	
	
}
