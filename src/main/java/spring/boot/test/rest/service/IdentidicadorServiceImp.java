package spring.boot.test.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.boot.test.rest.entity.Identificador;
import spring.boot.test.rest.dao.IIdentificadorDao;

@Service
public class IdentidicadorServiceImp implements IIdentidicadorService{
	
	@Autowired
	private IIdentificadorDao iIdentificadorDao;

	@Override
	public List<Identificador> findAll() {
		// TODO Auto-generated method stub
		return (List<Identificador>)iIdentificadorDao.findAll();
	}

	@Override
	public Identificador findById(Long id) {
		// TODO Auto-generated method stub
		return iIdentificadorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Identificador save(Identificador identificador) {
		// TODO Auto-generated method stub
		return iIdentificadorDao.save(identificador);
	}

	@Override
	@Transactional
	public void delete(Long id) {

		iIdentificadorDao.deleteById(id);
		
	}

}
