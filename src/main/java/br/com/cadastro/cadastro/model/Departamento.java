package br.com.cadastro.cadastro.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Departamento {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDepartamento;

    private String nome;
    private String descricao;

    @OneToOne(optional = true)
    @JoinColumn(name = "supervisor_id", nullable = true)
    private Funcionario supervisor;

    @OneToMany(mappedBy = "departamento")
    private List<Funcionario> funcionarios; 
}
