package sru.edu.group1.workorders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.repository.DepartmentsRepository;

@Service
public class DepartmentsService {

	@Autowired
	private DepartmentsRepository repo;
	
	public List<Departments> listAll() {
		return repo.findAll();
	}
	
	public void save(Departments departments) {
		repo.save(departments);
	}
	
	public Departments get(Long id) {
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}
		
	}	
