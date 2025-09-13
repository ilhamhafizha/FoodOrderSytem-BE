package org.fos.foodordersystem.service.app.impl;


import lombok.RequiredArgsConstructor;
import org.fos.foodordersystem.config.UserLoggedInConfig;
import org.fos.foodordersystem.repository.managementuser.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoggedInServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User : " + username + " tidak ditemukan"));
        return new UserLoggedInConfig(user);
    }
}
