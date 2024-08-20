package com.dardanqsot.eval.security;


import com.dardanqsot.eval.model.User;
import com.dardanqsot.eval.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findOneByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(String.format("El usuario no existe", email));
        }

        user.setLastLogin(LocalDateTime.now());
        repo.save(user);
        List<GrantedAuthority> permissions = new ArrayList<>();
        UserDetails ud = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), permissions);
        return ud;
    }
}
