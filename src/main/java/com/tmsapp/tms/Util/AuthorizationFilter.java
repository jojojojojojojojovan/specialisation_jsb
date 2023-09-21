package com.tmsapp.tms.Util; 

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Repository.AccountRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final String requiredGroup;
    
    public AuthorizationFilter(String requiredGroup, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.requiredGroup = requiredGroup;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String permittedGroup = permittedGroup(request);
        if (checkgroup(user, permittedGroup)){
          filterChain.doFilter(request, response);
          return;
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }

    private boolean isApiPathMatching(HttpServletRequest request) {
        String requestPath = new UrlPathHelper().getPathWithinApplication(request);
        return antPathMatcher.match("/api/**", requestPath);
    }
    
    private String permittedGroup(HttpServletRequest request) {
        String requestPath = new UrlPathHelper().getPathWithinApplication(request);
        String group = "";
        if (antPathMatcher.match("/api/**", requestPath)){
            group = "admin";
        }
        return group;
    }
    
    private boolean checkgroup(String username, String groupName){
        AccountRepository accountRepository = new AccountRepository(new HibernateUtil());
        Account account = accountRepository.getAccountByUsername(username);
        if(account != null){
            List<Accgroup> userGroups = accountRepository.getGroupsByUsername(username);
            for (Accgroup accgroup : userGroups) {
                if(groupName.equals(accgroup.getGroupName())){
                    return true;
                }
            }
        }
        return false;
    }
}
