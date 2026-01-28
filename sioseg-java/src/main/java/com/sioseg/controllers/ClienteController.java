package com.sioseg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteController {

    @GetMapping("/register")
    public String novoCliente(Model model) {
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("idCli", null);
        cliente.put("tipoPessoa", "");
        model.addAttribute("cliente", cliente);
        return "admin/clientes/form";
    }

    @PostMapping("/salvar")
    public String salvarCliente(@RequestParam Map<String, String> params, 
                               RedirectAttributes redirectAttributes) {
        try {
            // Log dos dados recebidos
            System.out.println("=== DADOS CLIENTE RECEBIDOS ===");
            params.forEach((key, value) -> System.out.println(key + ": " + value));
            
            // Validações básicas
            String tipoPessoa = params.get("tipoPessoa");
            String email = params.get("emailCli");
            String telefone = params.get("tel1Cli");
            String senha = params.get("senha");
            String confirmarSenha = params.get("confirmarSenha");
            
            if (tipoPessoa == null || tipoPessoa.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Tipo de pessoa é obrigatório");
                return "redirect:/admin/clientes/register";
            }
            
            if (email == null || email.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Email é obrigatório");
                return "redirect:/admin/clientes/register";
            }
            
            if (telefone == null || telefone.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Telefone é obrigatório");
                return "redirect:/admin/clientes/register";
            }
            
            if (senha == null || senha.length() < 6) {
                redirectAttributes.addFlashAttribute("erro", "Senha deve ter pelo menos 6 caracteres");
                return "redirect:/admin/clientes/register";
            }
            
            if (!senha.equals(confirmarSenha)) {
                redirectAttributes.addFlashAttribute("erro", "Senhas não coincidem");
                return "redirect:/admin/clientes/register";
            }
            
            // Validações por tipo de pessoa
            if ("fisica".equals(tipoPessoa)) {
                String nome = params.get("nomeCli");
                String cpf = params.get("cpfCli");
                if (nome == null || nome.isEmpty()) {
                    redirectAttributes.addFlashAttribute("erro", "Nome é obrigatório para pessoa física");
                    return "redirect:/admin/clientes/register";
                }
                if (cpf == null || cpf.isEmpty()) {
                    redirectAttributes.addFlashAttribute("erro", "CPF é obrigatório para pessoa física");
                    return "redirect:/admin/clientes/register";
                }
            } else if ("juridica".equals(tipoPessoa)) {
                String razaoSocial = params.get("razaoSocial");
                String cnpj = params.get("cnpj");
                if (razaoSocial == null || razaoSocial.isEmpty()) {
                    redirectAttributes.addFlashAttribute("erro", "Razão Social é obrigatória para pessoa jurídica");
                    return "redirect:/admin/clientes/register";
                }
                if (cnpj == null || cnpj.isEmpty()) {
                    redirectAttributes.addFlashAttribute("erro", "CNPJ é obrigatório para pessoa jurídica");
                    return "redirect:/admin/clientes/register";
                }
            }
            
            // Validações de endereço
            String logradouro = params.get("logradouro");
            String numero = params.get("numEnd");
            String bairro = params.get("bairro");
            String cidade = params.get("cidade");
            String uf = params.get("uf");
            
            if (logradouro == null || logradouro.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Logradouro é obrigatório");
                return "redirect:/admin/clientes/register";
            }
            if (numero == null || numero.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Número é obrigatório");
                return "redirect:/admin/clientes/register";
            }
            if (bairro == null || bairro.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Bairro é obrigatório");
                return "redirect:/admin/clientes/register";
            }
            if (cidade == null || cidade.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Cidade é obrigatória");
                return "redirect:/admin/clientes/register";
            }
            if (uf == null || uf.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "UF é obrigatório");
                return "redirect:/admin/clientes/register";
            }
            
            // Simular salvamento no banco
            System.out.println("Cliente seria salvo com sucesso!");
            
            redirectAttributes.addFlashAttribute("sucesso", "Cliente cadastrado com sucesso!");
            return "redirect:/admin/clientes";
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar cliente: " + e.getMessage());
            return "redirect:/admin/clientes/register";
        }
    }
}