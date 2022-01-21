package com.example.task4.security;

import com.example.task4.entity.Password;
import com.example.task4.entity.User;
import com.example.task4.repository.PasswordRepository;
import com.example.task4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email:" + email));
        Password password = passwordRepository.findByUser_Email(email).get();
        return UserDetailsImpl.fromUserEntityToUserDetails(user, password);
    }
}