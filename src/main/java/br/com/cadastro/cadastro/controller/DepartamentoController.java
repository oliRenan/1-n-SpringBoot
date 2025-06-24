package br.com.cadastro.cadastro.controller;

import java.util.List;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cadastro.cadastro.model.Departamento;
import br.com.cadastro.cadastro.repository.DepartamentoRepository;
import br.com.cadastro.cadastro.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoRepository departamentoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @GetMapping
    public String listar(Model model) {
        List<Departamento> departamentos = departamentoRepository.findAll();
        model.addAttribute("departamentos", departamentos);
        return "departamentos/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "departamentos/form";
    }

 
    @PostMapping
    public String salvar(@ModelAttribute Departamento departamento) {
        departamentoRepository.save(departamento);
        return "redirect:/departamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("departamento", departamento);
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "departamentos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        departamentoRepository.deleteById(id);
        return "redirect:/departamentos";
    }
}