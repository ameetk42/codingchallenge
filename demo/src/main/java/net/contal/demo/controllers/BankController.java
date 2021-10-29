package net.contal.demo.controllers;

import net.contal.demo.modal.CustomerAccount;
import net.contal.demo.services.BankService;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks")
public class BankController {
    final Logger logger = LoggerFactory.getLogger(BankController.class);
    final BankService dataService;

    public BankController(BankService dataService) {
        this.dataService = dataService;
    }


    /**
     * @param account {firstName:"" , lastName:"" }
     * @return bank account number
     */
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public long createBankAccount(@RequestBody CustomerAccount account){
        logger.info("{}" ,account.toString());

        return Long.parseLong(dataService.createAnAccount(account));
    }//createBankAccount

    /**
     * @param accountNumber BankAccount number
     * @param amount Amount as Transaction
     */
    @RequestMapping(method = RequestMethod.POST,value = "/transaction")
    public void addTransaction(@RequestParam("accountNumber") int accountNumber, @RequestParam("amount") Double amount){
        logger.info("Bank Account number is :{} , Transaction Amount {}",accountNumber,amount);
        dataService.addTransactions(accountNumber, amount);
    }//addTransaction


    /**
     * @param accountNumber customer  bank account  number
     * @return balance
     */
    @RequestMapping(method = RequestMethod.POST,value = "/balance")
    public Double getBalance(@RequestParam("accountNumber") int accountNumber){
        logger.info("Bank Account number is :{}",accountNumber);
  
        return dataService.getBalance(accountNumber);
        
         
    }//getBalance
    
    
    /**
     * @param accountNumber customer  bank account  number
     * @return balance
     */
    @RequestMapping(method = RequestMethod.POST,value = "/datebalance")
    public Map<Date,Double> getDateBalance(@RequestParam("accountNumber") int accountNumber){
        logger.info("Bank Account number is :{}",accountNumber);
     
        return dataService.getDateBalance(accountNumber);
        
         
    }//getDateBalance
    
    
    
    /**
     * @param accountNumber customer  bank account  number
     * @return CustomerAccount
     */
    @RequestMapping(method = RequestMethod.POST,value = "/accountInfo")
    public CustomerAccount getAccountInformation(@RequestParam("accountNumber") int accountNumber){
        logger.info("Bank Account number is :{}",accountNumber);
        
        return dataService.getAccountInformation(accountNumber);
        
         
    }//getAccountInformation
    

}//BankController
