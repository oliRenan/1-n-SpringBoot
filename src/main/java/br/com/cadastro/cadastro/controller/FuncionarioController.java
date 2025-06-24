package br.com.cadastro.cadastro.controller;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cadastro.cadastro.model.Departamento;
import br.com.cadastro.cadastro.model.Funcionario;
import br.com.cadastro.cadastro.repository.DepartamentoRepository;
import br.com.cadastro.cadastro.repository.FuncionarioRepository;
import jakarta.validation.Valid;
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
    public String novoFuncionario(Model model, @RequestParam(name = "novoDep", required = false) Long novoDepId) {
        Funcionario funcionario = new Funcionario();

        if (novoDepId != null) {
            departamentoRepository.findById(novoDepId).ifPresent(funcionario::setDepartamento);
        }

    model.addAttribute("funcionario", funcionario);
    model.addAttribute("departamentos", departamentoRepository.findAll());
    model.addAttribute("novoDepartamento", new Departamento());
    return "funcionarios/form";
}


    @PostMapping
    public String salvar(@Valid @ModelAttribute("funcionario") Funcionario funcionario,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("departamentos", departamentoRepository.findAll());
            model.addAttribute("novoDepartamento", new Departamento());
            return "funcionarios/form"; // volta para o form com os erros
        }


        funcionarioRepository.save(funcionario);
        return "redirect:/funcionarios";
    }

    @GetMapping("/editar/{id}")
    public String editarFuncionario(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        if (funcionario.getDepartamento() == null) {
            funcionario.setDepartamento(new Departamento());
        }

        model.addAttribute("funcionario", funcionario);
        model.addAttribute("departamentos", departamentoRepository.findAll());
        model.addAttribute("novoDepartamento", new Departamento());
        return "funcionarios/form";
    }



    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/funcionarios";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(BigDecimal.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                String normalized = text.replace(".", "").replace(",", ".");
                setValue(new BigDecimal(normalized));
            }
        });
    }

}
