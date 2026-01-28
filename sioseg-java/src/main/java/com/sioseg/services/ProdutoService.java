package com.sioseg.services;

import com.sioseg.models.Produto;
import com.sioseg.repositories.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Page<Produto> listarTodos(Pageable pageable) {
        return produtoRepository.findAllByOrderByIdProdDesc(pageable);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto salvar(Produto produto) {
        if (produto.getStatus() == null) {
            produto.setStatus("ativo");
        }
        return produtoRepository.save(produto);
    }

    public void alterarStatus(Long id, String status) {
        Produto produto = buscarPorId(id);
        produto.setStatus(status);
        produtoRepository.save(produto);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrModeloContainingIgnoreCase(
                nome, nome, nome);
    }

    public boolean decrementarEstoque(Long idProd, Integer quantidade) {
        Produto produto = buscarPorId(idProd);
        if (produto.getQtde() >= quantidade) {
            produto.setQtde(produto.getQtde() - quantidade);
            produtoRepository.save(produto);
            return true;
        }
        return false;
    }

    public void incrementarEstoque(Long idProd, Integer quantidade) {
        Produto produto = buscarPorId(idProd);
        produto.setQtde(produto.getQtde() + quantidade);
        produtoRepository.save(produto);
    }

    public void atualizarEstoque(Long idProd, Integer novaQuantidade) {
        Produto produto = buscarPorId(idProd);
        produto.setQtde(novaQuantidade);
        produtoRepository.save(produto);
    }

    public List<Produto> findAllActive() {
        return produtoRepository.findByStatus("ativo");
    }
}