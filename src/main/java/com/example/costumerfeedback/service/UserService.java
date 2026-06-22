package com.example.costumerfeedback.service;

import com.example.costumerfeedback.dto.LoginRequest;
import com.example.costumerfeedback.dto.RegisterRequest;
import com.example.costumerfeedback.model.Users;
import com.example.costumerfeedback.repository.UserRepository;
import com.example.costumerfeedback.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public List<Users> getAllUser()
    {
        return repository.findAll();
    }

    public Users getUsersById(Long id)
    {
        return repository.findById(id).orElse(null);
    }

    public void deleteUser(Long id)
    {
        repository.deleteById(id);
    }

    public Users register(RegisterRequest request)
    {
        if (repository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email already registered");

        if (request.getConfirmPassword() != null &&
            !request.getPassword().equals(request.getConfirmPassword()))
            throw new IllegalArgumentException("Passwords do not match");

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setUsername(request.getName());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Users.Role.USER);

        return repository.save(user);
    }

    public String login(LoginRequest request)
    {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        return jwtUtil.generateToken(userDetails);
    }
}
