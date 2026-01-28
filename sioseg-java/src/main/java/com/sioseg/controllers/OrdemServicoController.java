package com.sioseg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/os")
public class OrdemServicoController {

    @GetMapping("/register")
    public String novaOS(Model model) {
        return "admin/os/form";
    }

    @PostMapping("/salvar")
    public String salvarOS(@RequestParam Map<String, String> params, 
                          RedirectAttributes redirectAttributes) {
        try {
            // Log dos dados recebidos
            System.out.println("=== DADOS OS RECEBIDOS ===");
            params.forEach((key, value) -> System.out.println(key + ": " + value));
            
            // Validações básicas
            String idCliFk = params.get("idCliFk");
            String idTecFk = params.get("idTecFk");
            String idUsuFk = params.get("idUsuFk");
            String tipoServico = params.get("tipoServico");
            String status = params.get("status");
            String dataAgendamento = params.get("dataAgendamento");
            String dataEncerramento = params.get("dataEncerramento");
            String servicoPrestado = params.get("servicoPrestado");
            
            if (idCliFk == null || idCliFk.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Cliente é obrigatório");
                return "redirect:/admin/os/register";
            }
            
            if (idTecFk == null || idTecFk.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Técnico é obrigatório");
                return "redirect:/admin/os/register";
            }
            
            if (idUsuFk == null || idUsuFk.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Usuário responsável é obrigatório");
                return "redirect:/admin/os/register";
            }
            
            if (tipoServico == null || tipoServico.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Tipo de serviço é obrigatório");
                return "redirect:/admin/os/register";
            }
            
            if (status == null || status.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Status é obrigatório");
                return "redirect:/admin/os/register";
            }
            
            if (dataAgendamento == null || dataAgendamento.isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Data de agendamento é obrigatória");
                return "redirect:/admin/os/register";
            }
            
            // Validar IDs numéricos
            try {
                Integer.parseInt(idCliFk);
                Integer.parseInt(idTecFk);
                Integer.parseInt(idUsuFk);
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("erro", "IDs inválidos");
                return "redirect:/admin/os/register";
            }
            
            // Processar materiais/produtos
            System.out.println("=== PROCESSANDO MATERIAIS ===");
            params.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("produtos["))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
            
            // Simular salvamento no banco
            System.out.println("OS seria salva com sucesso!");
            System.out.println("Campos principais:");
            System.out.println("- Cliente ID: " + idCliFk);
            System.out.println("- Técnico ID: " + idTecFk);
            System.out.println("- Usuário ID: " + idUsuFk);
            System.out.println("- Tipo: " + tipoServico);
            System.out.println("- Status: " + status);
            System.out.println("- Data Agendamento: " + dataAgendamento);
            System.out.println("- Data Encerramento: " + (dataEncerramento != null ? dataEncerramento : "Não informada"));
            System.out.println("- Serviço: " + (servicoPrestado != null ? servicoPrestado : "Não informado"));
            
            redirectAttributes.addFlashAttribute("sucesso", "Ordem de Serviço cadastrada com sucesso!");
            return "redirect:/admin/os";
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar OS: " + e.getMessage());
            return "redirect:/admin/os/register";
        }
    }
}