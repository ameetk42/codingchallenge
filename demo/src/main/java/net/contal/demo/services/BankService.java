package net.contal.demo.services;

import net.contal.demo.AccountNumberUtil;
import net.contal.demo.DbUtils;
import net.contal.demo.modal.BankTransaction;
import net.contal.demo.modal.CustomerAccount;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class BankService {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	// database
	private final DbUtils dbUtils;

	@Autowired
	public BankService(DbUtils dbUtils) {
		this.dbUtils = dbUtils;
	}

	/**
	 * @param customerAccount 
	 * @return accountNumber
	 */
	public String createAnAccount(CustomerAccount customerAccount) {
		
		
		Session session = this.dbUtils.openASession();
		int accountNum = AccountNumberUtil.generateAccountNumber(); //generating account number
		customerAccount.setAccountNumber(accountNum);
		session.save(customerAccount); //saving customeraccount
		session.getTransaction().commit();

			return accountNum + "";
	} // createAnAccount()

	/**
	 * @param accountNumber target account number
	 * @param amount        amount to register as transaction
	 * @return boolean , if added as transaction
	 */
	public boolean addTransactions(int accountNumber, Double amount) {

		if (amount != null) {
			// query
			String hql = "from CustomerAccount where accountNumber = :accountNumber";

			CustomerAccount custmr;

			try {
				custmr = this.dbUtils.openASession().createQuery(hql, CustomerAccount.class)
						.setParameter("accountNumber", accountNumber).getSingleResult();
			} catch (NoResultException e) {
				System.err.println("No result found for provided account number");
				return false;
			} catch (Exception e) {
				return false;
			}

			BankTransaction bankTransaction = new BankTransaction();
			bankTransaction.setCustomerAccount(custmr);
			bankTransaction.setTransactionAmount(amount);

			Date dt = new Date();
			bankTransaction.setTransactionDate(dt);

			Session session = this.dbUtils.openASession();

			session.save(bankTransaction); //saving bank transaction

			custmr.setAccountBalance(custmr.getAccountBalance() + bankTransaction.getTransactionAmount());
			session.merge(custmr); //updating customer account

			session.getTransaction().commit();
		
			return true;

		} // if amount null
		System.err.println("Amount provided  is null");
		return false;

	}//addTransactions

	/**
	 * @param accountNumber target account
	 * @return account balance
	 */
	public double getBalance(int accountNumber) {

		/**
		 * TODO find the account by this account Number sum total of transactions belong
		 * to account return sum of amount
		 *
		 */

		// finding account with provided account number

		String hql = "from CustomerAccount where accountNumber = :accountNumber";

		CustomerAccount custmr;

		try {
			custmr = this.dbUtils.openASession().createQuery(hql, CustomerAccount.class)
					.setParameter("accountNumber", accountNumber).getSingleResult();
		} catch (NoResultException e) {
			System.err.println("No result found for provided account number");
			return 0d;
		} catch (Exception e) {
			return 0d;
		}

		String hql2 = "select sum(bT.transactionAmount) from BankTransaction as bT where bT.customerAccount.id = :id";
		Double sum = (Double) this.dbUtils.openASession().createQuery(hql2).setParameter("id", custmr.getId())
				.getSingleResult();

		return sum;
	}//getBalance()

	/**
	 * @param accountNumber accountNumber
	 * @return HashMap [key: date , value: double]
	 */
	public Map<Date, Double> getDateBalance(int accountNumber) {
	
		//finding account with provided account number

		String hql = "from CustomerAccount where accountNumber = :accountNumber";

		CustomerAccount custmr;

		//try catch if there is no result
		try {
			custmr = this.dbUtils.openASession().createQuery(hql, CustomerAccount.class)
					.setParameter("accountNumber", accountNumber).getSingleResult();
		} catch (NoResultException e) {
			System.err.println("No result found for provided account number");
			return null;
		} catch (Exception e) {
			return null;
		}

		
		String hql2 = "select bT.transactionDate, sum(bT.transactionAmount) from BankTransaction as bT where bT.customerAccount.id = :id group by bT.transactionDate";

		//getting result from query
		List<Object[]> resultBT = this.dbUtils.openASession().createQuery(hql2).setParameter("id", custmr.getId())
				.getResultList();

		Map<Date, Double> mapData = new LinkedHashMap<Date, Double>();

		//putting data in map
		for (Object[] row : resultBT) {
			mapData.put((Date) row[0], (Double) row[1]);

		}

		return mapData;
	}// getDateBalance method
	
	


	/**
	 * @param accountNumber accountNumber
	 * @return CustomerAccount information about customer account
	 */
	public CustomerAccount getAccountInformation(int accountNumber) {
		
		CustomerAccount customerAccount;
		if(accountNumber != 0) {
			
			String hql = "from CustomerAccount where accountNumber = :accountNumber";
		
			try {
				customerAccount = this.dbUtils.openASession().createQuery(hql, CustomerAccount.class)
						.setParameter("accountNumber", accountNumber).getSingleResult();
			} catch (NoResultException e) {
				System.err.println("No result found for provided account number");
				return null;
			} catch (Exception e) {
				return null;
			}
			
			return customerAccount;
			
		}//if not 0
		
		System.err.println("No result found for provided account number");
		
		return null;
	}//getAccountInformation

}// BankService class
