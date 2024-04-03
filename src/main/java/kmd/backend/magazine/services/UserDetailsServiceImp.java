package kmd.backend.magazine.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.UserRepo;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepo repository;

    public UserDetailsServiceImp(UserRepo repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}