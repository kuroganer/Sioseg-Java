package com.sioseg.services;

import com.sioseg.models.Cliente;
import com.sioseg.repositories.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<Cliente> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente salvar(Cliente cliente) {
        if (cliente.getIdCli() == null) {
            cliente.setDataCadastroCli(LocalDateTime.now());
            if (cliente.getSenhaHashCli() != null) {
                cliente.setSenhaHashCli(passwordEncoder.encode(cliente.getSenhaHashCli()));
            }
        }
        return clienteRepository.save(cliente);
    }

    public void alterarStatus(Long id, String status) {
        Cliente cliente = buscarPorId(id);
        cliente.setStatus(status);
        clienteRepository.save(cliente);
    }

    public Page<Cliente> buscarPorNome(String nome, Pageable pageable) {
        return clienteRepository.findByNomeCliContainingIgnoreCaseOrRazaoSocialContainingIgnoreCase(
                nome, nome, pageable);
    }

    public java.util.List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
}