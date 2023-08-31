package com.tmsapp.tms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Repository.AccountRepository;
import com.tmsapp.tms.Service.AccountService;

import java.util.Map;
import java.util.HashMap;

@SpringBootApplication
public class TmsApplication {

	public static void main(String[] args) {

		SpringApplication.run(TmsApplication.class, args);
	}

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountService accountService;

	@EventListener(ApplicationReadyEvent.class)
	public void createInitialAccount() {
		Account adminUser = accountRepository.getAccountByUsername("admin");
		if(adminUser != null) {
			return;
		}
		Map<String, Object> account = new HashMap<>();
		Map<String, Object> groups = new HashMap<>();
		groups.put("groupName", "admin");
		account.put("username", "admin");
		account.put("password", "p@ssw0rd");
		account.put("status", 1);
		List<Object> groupsList = new ArrayList<Object>();
		groupsList.add(groups);
		account.put("groups", groupsList);
		Map<String, Object> input = new HashMap<>();
		input.put("account", account);
		accountService.createAccount(input);

		//create pl account
		Map<String, Object> accountPL = new HashMap<>();
		Map<String, Object> groupsPL = new HashMap<>();
		groupsPL.put("groupName", "project leader");
		accountPL.put("username", "pl");
		accountPL.put("password", "p@ssw0rd");
		accountPL.put("status", 1);
		List<Object> groupsListpl = new ArrayList<Object>();
		groupsListpl.add(groupsPL);
		accountPL.put("groups", groupsListpl);
		Map<String, Object> inputpl = new HashMap<>();
		inputpl.put("account", accountPL);
		accountService.createAccount(inputpl);
	}

}
