package com.tmsapp.tms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Entity.JwtInvalidation;
import com.tmsapp.tms.Repository.AccountRepository;
import com.tmsapp.tms.Repository.JwtRepository;

@SpringBootApplication
public class TmsApplication {

	public static void main(String[] args) {

		SpringApplication.run(TmsApplication.class, args);
	}

	// @Autowired
	// private AccountRepository accountRepository;

	// @EventListener(ApplicationReadyEvent.class)
	// public void createInitialAccount() {
	// 	List<Accgroup> gList = new ArrayList<Accgroup>();
	// 	gList.add(new Accgroup("testing2"));
	// 	Account a1 = new Account("user2", "p@ssw0rd2", 1, "email2@tms.com", gList);
	// 	accountRepository.createAccount(a1);
	// }

}
