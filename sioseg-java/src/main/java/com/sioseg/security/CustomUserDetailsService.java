package com.sioseg.security;

import com.sioseg.models.Usuario;
import com.sioseg.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("DEBUG: Tentando carregar usuário: " + username);
        Usuario usuario = usuarioRepository.findByEmailUsu(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        System.out.println("DEBUG: Usuário encontrado: " + usuario.getEmailUsu());
        System.out.println("DEBUG: Hash da senha: " + usuario.getSenhaHashUsu());
        return new UserPrincipal(usuario);
    }
}
