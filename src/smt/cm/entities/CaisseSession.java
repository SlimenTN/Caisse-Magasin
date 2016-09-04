package smt.cm.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class CaisseSession implements Serializable{
	@Id 
	@GeneratedValue
    @Column(name = "id", columnDefinition="serial")
	private int id;
	
	private Date openDate;
	
	private Date closeDate;
	
	private double startFond;
	
	private double closeFond;
	
	private double ecart;
	
	@ManyToOne
	private Employee employee;
	
	@ManyToOne
	private Caisse caisse;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public double getStartFond() {
		return startFond;
	}
	public void setStartFond(double startFond) {
		this.startFond = startFond;
	}
	public double getCloseFond() {
		return closeFond;
	}
	public void setCloseFond(double closeFond) {
		this.closeFond = closeFond;
	}
	public double getEcart() {
		return ecart;
	}
	public void setEcart(double ecart) {
		this.ecart = ecart;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Caisse getCaisse() {
		return caisse;
	}
	public void setCaisse(Caisse caisse) {
		this.caisse = caisse;
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
		CaisseSession other = (CaisseSession) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
