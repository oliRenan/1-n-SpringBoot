package br.com.cadastro.cadastro.controller;

import java.util.List;

import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // Import RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; // Import ResponseBody

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

    // This is your existing POST for regular form submissions (redirects)
    @PostMapping
    public String salvar(@ModelAttribute Departamento departamento) {
        // When saving from the main form (e.g., /departamentos/novo -> POST /departamentos)
        // the 'supervisor' might be selected from a dropdown of existing employees.
        // If the supervisor field is not provided or is null, it will save as null due to optional = true.
        departamentoRepository.save(departamento);
        return "redirect:/departamentos";
    }

    // --- NEW ENDPOINT FOR AJAX MODAL SUBMISSION ---
    // This method will handle the AJAX request from the "new department" modal
    @PostMapping("/api") // A new, distinct path for the API endpoint
    @ResponseBody // This annotation tells Spring to return the object as JSON/XML directly in the response body
    public ResponseEntity<Departamento> createDepartamentoFromModal(@RequestBody Departamento departamento) {
        // The @RequestBody annotation will map the JSON sent from the modal's fetch request
        // directly to a Departamento object.

        // Important: If your modal form sends a simple 'String supervisorName' instead of a Funcionario ID
        // for the supervisor, this 'departamento' object's 'supervisor' field will be null (as it expects Funcionario).
        // This is perfectly fine because we made the supervisor field 'optional = true' in your entity.
        // If you intended to link to an *existing* Funcionario from the modal, you'd need
        // to modify the modal's input to send an ID and then fetch that Funcionario here.
        // For now, it correctly handles the case where supervisor is not provided or invalid, saving it as null.

        // Ensure that the 'funcionarios' list is not populated from the modal input
        departamento.setFuncionarios(null);

        Departamento savedDepartamento = departamentoRepository.save(departamento);
        return new ResponseEntity<>(savedDepartamento, HttpStatus.CREATED);
    }
    // ----------------------------------------------


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