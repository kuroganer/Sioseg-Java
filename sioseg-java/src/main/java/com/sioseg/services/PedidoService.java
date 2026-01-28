package com.sioseg.services;

import com.sioseg.models.Pedido;
import com.sioseg.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        if (pedido.getDataPedido() == null) {
            pedido.setDataPedido(LocalDateTime.now());
        }
        if (pedido.getStatus() == null) {
            pedido.setStatus("pendente");
        }
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Page<Pedido> searchByStatus(String status, Pageable pageable) {
        return pedidoRepository.findByStatusContaining(status, pageable);
    }

    public Page<Pedido> searchByProdutoNome(String nome, Pageable pageable) {
        return pedidoRepository.findByProdutoNomeContaining(nome, pageable);
    }

    public Pedido updateStatus(Long id, String status) {
        Optional<Pedido> pedidoOpt = findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setStatus(status);
            if ("entregue".equals(status) && pedido.getDataEntrega() == null) {
                pedido.setDataEntrega(LocalDateTime.now());
            }
            return save(pedido);
        }
        return null;
    }
}