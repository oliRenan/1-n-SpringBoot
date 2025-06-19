package br.com.cadastro.cadastro.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cadastro.cadastro.model.Funcionario;
import br.com.cadastro.cadastro.repository.DepartamentoRepository;
import br.com.cadastro.cadastro.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

   private final FuncionarioRepository funcionarioRepository;
    private final DepartamentoRepository departamentoRepository;

    @GetMapping
    public String listar(Model model) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "funcionarios/listar";
    }

    @GetMapping("/novo")
    public String novoFuncionario(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("departamentos", departamentoRepository.findAll());
        return "funcionarios/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
        return "redirect:/funcionarios";
    }

    @GetMapping("/editar/{id}")
    public String editarFuncionario(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        model.addAttribute("funcionario", funcionario);
        model.addAttribute("departamentos", departamentoRepository.findAll());
        return "funcionarios/form";
}


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/funcionarios";
    }
}
