
package br.com.sistemas.chamados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChamadosViewController {

    @GetMapping("/painel/chamados")
    public String listarChamados() {
        return "views/chamados";
    }

    @GetMapping("/painel/clientes")
    public String listarClientes() {
        return "views/clientes";
    }

    @GetMapping("/painel/tecnicos")
    public String listarTecnicos() {
        return "views/tecnicos";
    }
}
