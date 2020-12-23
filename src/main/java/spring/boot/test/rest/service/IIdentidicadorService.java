package spring.boot.test.rest.service;

import java.util.List;

import spring.boot.test.rest.entity.Identificador;



public interface IIdentidicadorService {
	
	public List<Identificador> findAll();
	public Identificador findById(Long id);	
	public Identificador save(Identificador producto);	
	public void delete(Long id); 

}
