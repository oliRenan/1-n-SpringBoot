package br.com.cadastro.cadastro.model;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @CPF(message = "CPF inválido")
  @NotBlank(message = "CPF é obrigatório")
  private String cpf;

  @NotBlank(message = "Cargo é obrigatório")
  private String cargo;

  @NotNull(message = "Salário é obrigatório")
  @DecimalMin(value = "0.01", message = "Salário deve ser maior que zero")

  private BigDecimal salario;;

  @ManyToOne
  @JoinColumn(name = "departamento_id")
   @NotNull(message = "Departamento é obrigatório")
  private Departamento departamento;
}
