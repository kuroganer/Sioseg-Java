package com.sioseg.security;

import com.sioseg.models.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private final Usuario usuario;

    public UserPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String perfil = usuario.getPerfil();
        if (perfil == null) perfil = "USER";
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + perfil.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return usuario.getSenhaHashUsu();
    }

    @Override
    public String getUsername() {
        return usuario.getEmailUsu();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return "ativo".equalsIgnoreCase(usuario.getStatus()); }
}
