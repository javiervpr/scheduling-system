package com.scheduling.system.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduling.system.dao.IClassGroupDao;
import com.scheduling.system.dao.IStudentDao;
import com.scheduling.system.models.ClassGroup;
import com.scheduling.system.models.Student;


@Service
public class StudentService implements IStudentService{
	
	@Autowired
	private IStudentDao studentDao;
	@Autowired
	private IClassGroupDao classDao;
	
	@Override
	public List<Student> findAll() {
		return (List<Student>) studentDao.findAll();
	}

	@Override
	public void save(Student student) {
		studentDao.save(student);
		
	}

	@Override
	public Student findOne(UUID id) {
		return studentDao.findById(id).orElse(null);
	}

	@Override
	public void delete(UUID id) throws Exception {
		Student student = studentDao.findById(id).orElse(null);
		if (student == null) {
			throw new Exception("Student not found");
		}
		
		List<ClassGroup> classesList = (List<ClassGroup>) classDao.findClassesByStudent(id);
		for (ClassGroup classGroupTemp : classesList) {
			classGroupTemp.deleteStudent(student);
			classDao.save(classGroupTemp);
		}
		studentDao.deleteById(id);
	}

	@Override
	public List<Student> findByFullName(String query) {
		return (List<Student>) studentDao.findByFullName(query);
	}
	

}
