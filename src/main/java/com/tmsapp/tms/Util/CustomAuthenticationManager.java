package com.tmsapp.tms.Util;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Repository.AccountRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager{
    
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Account account = accountRepository.getAccountByUsername(authentication.getName().toString());

        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), account.getPassword())){
            throw new BadCredentialsException("Wrong pass");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), account.getPassword());
    }
    
}
