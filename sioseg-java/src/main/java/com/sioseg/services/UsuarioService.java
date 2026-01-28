package com.sioseg.services;

import com.sioseg.models.Usuario;
import com.sioseg.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> findAll() {
        return repo.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return repo.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return repo.save(usuario);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return repo.findByEmailUsu(email);
    }

    public Page<Usuario> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Usuario saveWithPassword(Usuario usuario, String senha) {
        if (usuario.getDataCadastroUsu() == null) {
            usuario.setDataCadastroUsu(LocalDateTime.now());
        }
        if (usuario.getStatus() == null) {
            usuario.setStatus("ativo");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setSenhaHashUsu(encoder.encode(senha));
        return repo.save(usuario);
    }

    public Page<Usuario> searchByNome(String nome, Pageable pageable) {
        return repo.findByNomeUsuContainingIgnoreCase(nome, pageable);
    }

    public Page<Usuario> searchByEmail(String email, Pageable pageable) {
        return repo.findByEmailUsuContainingIgnoreCase(email, pageable);
    }

    public Page<Usuario> searchByCpf(String cpf, Pageable pageable) {
        return repo.findByCpfUsuContaining(cpf, pageable);
    }

    public Usuario updateStatus(Long id, String status) {
        Optional<Usuario> usuarioOpt = findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setStatus(status);
            return save(usuario);
        }
        return null;
    }
    
    public List<Usuario> findByNomeContaining(String nome) {
        return repo.findByNomeUsuContainingIgnoreCase(nome);
    }
    
    public void alterarStatus(Long id, String status) {
        Usuario usuario = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setStatus(status);
        repo.save(usuario);
    }
}
