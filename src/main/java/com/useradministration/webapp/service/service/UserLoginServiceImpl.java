package com.useradministration.webapp.service.service;

import com.useradministration.webapp.dao.entity.UserAccount;
import com.useradministration.webapp.dao.entity.enumeration.UserStatus;
import com.useradministration.webapp.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAccount currentUser = userRepository.findByUsername(s);
        if (currentUser.getStatus().equals(UserStatus.INACTIVE)) {
            throw new UsernameNotFoundException("User with username: " + currentUser.getUsername()
                    + " has inactive status!!");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(currentUser.getRole().name()));

        return User
                .builder()
                .username(currentUser.getUsername())
                .password(currentUser.getPassword())
                .authorities(authorities)
                .build();
    }
}