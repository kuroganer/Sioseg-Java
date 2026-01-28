package com.sioseg.controllers;

import com.sioseg.models.OrdemServico;
import com.sioseg.models.Usuario;
import com.sioseg.services.OrdemServicoService;
import com.sioseg.services.AvaliacaoTecnicaService;
import com.sioseg.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    
    @Autowired
    private OrdemServicoService ordemServicoService;
    
    @Autowired
    private AvaliacaoTecnicaService avaliacaoTecnicaService;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("userName", "Admin");
        
        try {
            // Buscar estatísticas reais como no PHP
            java.util.Map<String, Object> stats = new java.util.HashMap<>();
            
            // Contar OS por status (igual ao PHP)
            long abertas = ordemServicoService.countByStatus("aberta");
            long concluidas = ordemServicoService.countByStatus("concluida");
            long emAndamento = ordemServicoService.countByStatus("em andamento");
            
            stats.put("abertas_hoje", abertas);
            stats.put("abertas_total", abertas); // Para o gráfico
            stats.put("total_concluidas", concluidas);
            stats.put("em_andamento", emAndamento);
            stats.put("avaliacoes_pendentes", 0); // Implementar depois
            stats.put("conclusoes_pendentes", 0); // Implementar depois
            
            model.addAttribute("stats", stats);
            
            // Buscar OS para Kanban com relações (limitado a 3 como no PHP)
            List<OrdemServico> osAFazer = ordemServicoService.findByStatusWithRelations("aberta")
                .stream().limit(3).collect(Collectors.toList());
            List<OrdemServico> osEmAndamento = ordemServicoService.findByStatusWithRelations("em andamento")
                .stream().limit(3).collect(Collectors.toList());
            List<OrdemServico> osConcluidas = ordemServicoService.findByStatusWithRelations("concluida")
                .stream().limit(3).collect(Collectors.toList());
            
            model.addAttribute("osAFazer", osAFazer);
            model.addAttribute("osEmAndamento", osEmAndamento);
            model.addAttribute("osConcluidas", osConcluidas);
            
            // Tentar usar dados reais de avaliações primeiro
            try {
                java.util.Map<String, Double> avaliacoesData = avaliacaoTecnicaService.getMediaAvaliacoesUltimosDias(7);
                String[] chartLabels = avaliacoesData.keySet().toArray(new String[0]);
                double[] chartDataDouble = avaliacoesData.values().stream().mapToDouble(Double::doubleValue).toArray();
                
                model.addAttribute("chartLabels", chartLabels);
                model.addAttribute("chartData", chartDataDouble);
                
                // Top técnicos por avaliação real
                java.util.List<java.util.Map<String, Object>> topTecnicos = avaliacaoTecnicaService.getTopTecnicosByAvaliacao(3);
                model.addAttribute("topTecnicos", topTecnicos);
            } catch (Exception e2) {
                // Fallback para dados simulados baseados em OS
                java.util.Map<String, Long> osData = ordemServicoService.getOSConcluidasUltimosDias(7);
                String[] chartLabels = osData.keySet().toArray(new String[0]);
                double[] chartDataDouble = osData.values().stream().mapToDouble(Long::doubleValue).toArray();
                
                model.addAttribute("chartLabels", chartLabels);
                model.addAttribute("chartData", chartDataDouble);
                
                java.util.List<java.util.Map<String, Object>> topTecnicos = ordemServicoService.getTopTecnicosByOS(3);
                model.addAttribute("topTecnicos", topTecnicos);
            }
            
        } catch (Exception e) {
            System.err.println("Erro no dashboard: " + e.getMessage());
            e.printStackTrace();
            
            // Dados padrão em caso de erro
            java.util.Map<String, Object> stats = new java.util.HashMap<>();
            stats.put("abertas_hoje", 0);
            stats.put("abertas_total", 0);
            stats.put("total_concluidas", 0);
            stats.put("em_andamento", 0);
            stats.put("avaliacoes_pendentes", 0);
            stats.put("conclusoes_pendentes", 0);
            model.addAttribute("stats", stats);
            
            model.addAttribute("osAFazer", java.util.Collections.emptyList());
            model.addAttribute("osEmAndamento", java.util.Collections.emptyList());
            model.addAttribute("osConcluidas", java.util.Collections.emptyList());
            model.addAttribute("topTecnicos", java.util.Collections.emptyList());
            
            // Dados padrão para gráficos
            model.addAttribute("chartLabels", new String[]{"D-6", "D-5", "D-4", "D-3", "D-2", "Ontem", "Hoje"});
            model.addAttribute("chartData", new double[]{0, 0, 0, 0, 0, 0, 0});
        }
        
        return "admin/dashboard/index";
    }

    @GetMapping("/admin/dashboard/simple")
    public String dashboardSimple() {
        return "admin/dashboard/simple";
    }

    @GetMapping("/admin/os")
    public String listarOS(Model model) {
        model.addAttribute("ordens", java.util.Collections.emptyList());
        return "admin/os/list";
    }



    @GetMapping("/admin/os/calendario")
    public String calendarioOS(Model model) {
        return "admin/os/calendario";
    }

    @GetMapping("/admin/relatorios")
    public String relatorios(Model model) {
        return "admin/relatorios/index";
    }

    @GetMapping("/admin/relatorios/resumo-geral")
    public String resumoGeral(Model model) {
        return "admin/relatorios/resumo-geral";
    }

    @GetMapping("/admin/relatorios/performance-tecnicos")
    public String performanceTecnicos(Model model) {
        return "admin/relatorios/performance-tecnicos";
    }

    @GetMapping("/admin/clientes")
    public String clientes(Model model) {
        model.addAttribute("clientes", java.util.Collections.emptyList());
        return "admin/clientes/list";
    }

    @GetMapping("/admin/produtos")
    public String produtos(Model model) {
        model.addAttribute("produtos", java.util.Collections.emptyList());
        return "admin/produtos/list";
    }

    @GetMapping("/admin/pedidos")
    public String pedidos(Model model) {
        model.addAttribute("pedidos", java.util.Collections.emptyList());
        return "admin/pedidos/list";
    }

    @GetMapping("/admin/tecnicos")
    public String tecnicos(Model model) {
        model.addAttribute("tecnicos", java.util.Collections.emptyList());
        return "admin/tecnicos/list";
    }

    @GetMapping("/admin/users")
    public String usuarios(Model model) {
        List<Usuario> users = usuarioService.findAll();
        model.addAttribute("users", users);
        return "admin/users/index";
    }
    
    @GetMapping("/admin/users/search")
    public String pesquisarUsuarios(@RequestParam(required = false) String nome, Model model) {
        model.addAttribute("searchTerm", nome);
        
        if (nome != null && !nome.trim().isEmpty()) {
            List<Usuario> usuarios = usuarioService.findByNomeContaining(nome.trim());
            model.addAttribute("usuarios", usuarios);
        } else {
            model.addAttribute("usuarios", java.util.Collections.emptyList());
        }
        
        return "admin/users/search";
    }
    
    @PostMapping("/admin/users/changeStatus")
    public String alterarStatusUsuario(@RequestParam Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.alterarStatus(id, status);
            redirectAttributes.addFlashAttribute("sucesso", "Status do usuário alterado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao alterar status: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    // Formulários de cadastro
    
    @GetMapping("/admin/produtos/register")
    public String novoProduto(Model model) {
        java.util.Map<String, Object> produto = new java.util.HashMap<>();
        produto.put("idProd", null);
        model.addAttribute("produto", produto);
        return "admin/produtos/form";
    }
    
    @GetMapping("/admin/tecnicos/register")
    public String novoTecnico(Model model) {
        java.util.Map<String, Object> tecnico = new java.util.HashMap<>();
        tecnico.put("idTec", null);
        model.addAttribute("tecnico", tecnico);
        return "admin/tecnicos/form";
    }
    
    @GetMapping("/admin/usuarios/register")
    public String novoUsuario(Model model) {
        java.util.Map<String, Object> usuario = new java.util.HashMap<>();
        usuario.put("idUsu", null);
        model.addAttribute("usuario", usuario);
        return "admin/usuarios/form";
    }
    
    // Páginas de pesquisa
    @GetMapping("/admin/usuarios/search")
    public String pesquisarUsuarios(Model model) {
        return "admin/usuarios/search";
    }
    
    @GetMapping("/admin/tecnicos/search")
    public String pesquisarTecnicos(Model model) {
        return "admin/tecnicos/search";
    }
    
    @GetMapping("/admin/clientes/search")
    public String pesquisarClientes(Model model) {
        return "admin/clientes/search";
    }
    
    @GetMapping("/admin/produtos/search")
    public String pesquisarProdutos(Model model) {
        return "admin/produtos/search";
    }
    
    @GetMapping("/admin/os/search")
    public String pesquisarOS(Model model) {
        return "admin/os/search";
    }
    
    @GetMapping("/admin/relatorios/buscar-os")
    public String buscarOS(Model model) {
        return "admin/relatorios/buscar-os";
    }
    
    @GetMapping("/admin/relatorios/produtos")
    public String relatorioProdutos(Model model) {
        return "admin/relatorios/produtos";
    }
    
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}