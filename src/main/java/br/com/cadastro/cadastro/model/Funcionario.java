package br.com.cadastro.cadastro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
  @Id  
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long idFuncionario;

  private String nome;
  private Integer cpf;
  private String cargo;
  private Integer salario;

  @ManyToOne
  @JoinColumn(name = "departamento_id")
  private Departamento departamento;
}
