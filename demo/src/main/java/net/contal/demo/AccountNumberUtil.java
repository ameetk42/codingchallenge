package net.contal.demo;

import java.util.Random;

public abstract class AccountNumberUtil {

	
	private static Random random=new Random();

    /**
     * @return random integer
     */
    public static int generateAccountNumber(){
    	
    	return random.nextInt(123456, 999999);
       
    }

}
