package br.com.cadastro.cadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cadastro.cadastro.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

}