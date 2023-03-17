package com.nhnacademy.edu.service;

import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.ResidentNotFoundException;
import com.nhnacademy.edu.repository.resident.ResidentRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ResidentRepository residentRepository;

    public CustomUserDetailsService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Resident resident = residentRepository.findByLoginId(username)
                .orElseThrow(ResidentNotFoundException::new);


        return new User(
                resident.getLoginId(),
                resident.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))
        );
    }
}
