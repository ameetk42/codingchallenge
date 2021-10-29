package net.contal.demo.modal;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

//TODO complete this class
@Entity
@Table
public class BankTransaction {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private CustomerAccount customerAccount;
    
    @Column (name = "transactionAmount")
    private double transactionAmount;
    @Column (name = "transactionDate" ,  columnDefinition  = "Date")
    private  Date transactionDate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "BankTransaction [id=" + id + ", customerAccount=" + customerAccount + ", transactionAmount="
				+ transactionAmount + ", transactionDate=" + transactionDate + "]";
	}
    
    
    
}
