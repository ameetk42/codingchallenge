package net.contal.demo.modal;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class CustomerAccount {
    @Id
    @GeneratedValue
    private long id;
    @OneToMany
    private List<BankTransaction> transactions;

    @Column (name = "firstName")
    private String firstName;
    @Column (name = "lastName")
    private String lastName;
    @Column (name = "accountNumber")
    private int accountNumber;
    @Column (name = "accountBalance")
    private double accountBalance;
    
    //getter and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public List<BankTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BankTransaction> transactions) {
        this.transactions = transactions;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "CustomerAccount [id=" + id + ", transactions=" + transactions + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", accountNumber=" + accountNumber + ", accountBalance=" + accountBalance
				+ "]";
	}
    
    
	
    
    
}
