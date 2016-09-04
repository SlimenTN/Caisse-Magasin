package smt.cm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="ticket_has_modes")
public class TicketHasModes implements Serializable{
		
	@Id 
	@GeneratedValue
    @Column(name = "id", columnDefinition="serial")
	private int id;
	
	@ManyToOne
	private Ticket ticket;
	
	@ManyToOne
	private ModeRegulation modeRegulation;
	
	private double money;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public ModeRegulation getModeRegulation() {
		return modeRegulation;
	}

	public void setModeRegulation(ModeRegulation modeRegulation) {
		this.modeRegulation = modeRegulation;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((modeRegulation == null) ? 0 : modeRegulation.hashCode());
		long temp;
		temp = Double.doubleToLongBits(money);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
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
		TicketHasModes other = (TicketHasModes) obj;
		if (modeRegulation == null) {
			if (other.modeRegulation != null)
				return false;
		} else if (!modeRegulation.equals(other.modeRegulation))
			return false;
		if (Double.doubleToLongBits(money) != Double
				.doubleToLongBits(other.money))
			return false;
		if (ticket == null) {
			if (other.ticket != null)
				return false;
		} else if (!ticket.equals(other.ticket))
			return false;
		return true;
	}
	
	
}
