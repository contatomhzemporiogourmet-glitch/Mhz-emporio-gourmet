package com.mhz.api.service;

import com.mhz.api.dto.LoginRequest;
import com.mhz.api.dto.LoginResponse;
import com.mhz.api.dto.RegistroRequest;
import com.mhz.api.dto.UsuarioDTO;
import com.mhz.api.entity.Cliente;
import com.mhz.api.entity.Usuario;
import com.mhz.api.repository.ClienteRepository;
import com.mhz.api.repository.UsuarioRepository;
import com.mhz.api.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UsuarioRepository usuarioRepository,
                      ClienteRepository clienteRepository,
                      PasswordEncoder passwordEncoder,
                      AuthenticationManager authenticationManager,
                      JwtTokenProvider jwtTokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        String token = jwtTokenProvider.generateToken(authentication);
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new LoginResponse(token, "Bearer", convertToDTO(usuario));
    }

    public void registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setNome(request.getNome());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setTipoUsuario("cliente");
        
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_CLIENT");
        usuario.setRoles(roles);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuarioSalvo);
        cliente.setTelefone(request.getTelefone());
        cliente.setCpf(request.getCpf());
        clienteRepository.save(cliente);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getNome(),
            usuario.getTipoUsuario(),
            usuario.getDataCriacao(),
            usuario.getAtivo(),
            usuario.getRoles()
        );
    }
}