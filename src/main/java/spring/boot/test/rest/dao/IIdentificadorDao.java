package spring.boot.test.rest.dao;

import org.springframework.data.repository.CrudRepository;

import spring.boot.test.rest.entity.Identificador;


public interface IIdentificadorDao extends CrudRepository<Identificador, Long>{

}
