package br.com.cadastro.cadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cadastro.cadastro.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}