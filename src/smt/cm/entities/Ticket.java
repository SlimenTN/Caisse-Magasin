package smt.cm.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="ticket")
public class Ticket implements Serializable{
	
	@Id 
	@GeneratedValue
    @Column(name = "id")
	private int id;
	
	private String code;
	
	private Date dateTicket;
	
	private double totTicket;
	
	private double totRemise;
	
	private double paiedMoney;
	
	private double restMoney;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="ticket", fetch=FetchType.EAGER)
	private Set<TicketLine> ticketLines = new HashSet<TicketLine>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="ticket", fetch=FetchType.LAZY)
	private Set<TicketHasModes> modes = new HashSet<TicketHasModes>();
	
	@ManyToOne
	private CaisseSession caisseSession;

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

	public Date getDateTicket() {
		return dateTicket;
	}

	public void setDateTicket(Date dateTicket) {
		this.dateTicket = dateTicket;
	}

	public double getTotTicket() {
		return totTicket;
	}

	public void setTotTicket(double totTicket) {
		this.totTicket = totTicket;
	}
	
	
	public double getTotRemise() {
		return totRemise;
	}

	public void setTotRemise(double totRemise) {
		this.totRemise = totRemise;
	}

	public double getPaiedMoney() {
		return paiedMoney;
	}

	public void setPaiedMoney(double paiedMoney) {
		this.paiedMoney = paiedMoney;
	}

	public double getRestMoney() {
		return restMoney;
	}

	public void setRestMoney(double restMoney) {
		this.restMoney = restMoney;
	}
	

	public Set<TicketLine> getTicketLines() {
		return ticketLines;
	}

	public void setTicketLines(Set<TicketLine> ticketLines) {
		this.ticketLines = ticketLines;
	}

	public CaisseSession getCaisseSession() {
		return caisseSession;
	}

	public void setCaisseSession(CaisseSession caisseSession) {
		this.caisseSession = caisseSession;
	}
	
	

	public Set<TicketHasModes> getModes() {
		return modes;
	}

	public void setModes(Set<TicketHasModes> modes) {
		this.modes = modes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Ticket other = (Ticket) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticket [code=" + code + ", dateTicket=" + dateTicket + "]";
	}



}	
