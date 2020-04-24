package com.qa.tapiwa.spring.services;



import com.qa.tapiwa.spring.data.domain.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service

public class AccountNumGenService {

    public Account getRandomAccount(Account account){




        account.setAccountNumber(accountNumberGenerator());
        return account;

    }

    public String accountNumberGenerator(){
        List<Character> chars = new ArrayList<>();
        chars.add('A');
        chars.add('B');
        chars.add('C');


        Random rand = new Random();
        String num = "";

        int y = rand.nextInt(3);

        if(y==0){
            num = chars.get(0).toString();
        }
        else if (y==1){
            num = chars.get(1).toString();
        }
        else{
            num = chars.get(2).toString();
        }

        for (int i = 0; i < 6 + (2 * y); i++) {
            int x = rand.nextInt(9);
            num += x;

        }

        return num;

    }
}