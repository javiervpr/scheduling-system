package com.scheduling.system.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.scheduling.system.models.Student;

public interface IStudentDao extends CrudRepository<Student, UUID> {
	
	/**
	 * A list of student filtered by the query
	 * @param query
	 * @return A list of student filtered by the query
	 */
	@Query("from Student s where (lower( concat(s.firstName,' ',s.lastName) ) like ('%' || lower(:query) || '%'))")
	public Iterable<Student> findByFullName(String query);
}
