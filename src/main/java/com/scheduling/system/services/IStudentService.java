package com.scheduling.system.services;
import java.util.List;
import java.util.UUID;

import com.scheduling.system.models.*;

public interface IStudentService {
	
	public List<Student> findAll();
	public void save(Student student);
	public Student findOne(UUID id);
	public void delete(UUID id) throws Exception;
	public List<Student> findByFullName(String query);
}
