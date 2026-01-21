package com.mydreams.service;

import com.mydreams.dto.LoginRequest;
import com.mydreams.dto.LoginResponse;
import com.mydreams.entity.Usuario;
import com.mydreams.exception.UnauthorizedException;
import com.mydreams.repository.UsuarioRepository;
import com.mydreams.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthService(UsuarioRepository usuarioRepository, 
                      PasswordEncoder passwordEncoder, 
                      JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Usuario o contraseña incorrectos"));
        
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new UnauthorizedException("Usuario o contraseña incorrectos");
        }
        
        String token = jwtUtil.generateToken(usuario.getUsername());
        return new LoginResponse(token, "Bearer");
    }
}
