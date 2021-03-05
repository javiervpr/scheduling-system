package com.scheduling.system.dao;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.scheduling.system.models.ClassGroup;

public interface IClassGroupDao extends CrudRepository<ClassGroup, UUID> {
	/**
	 * Return the available students for a class
	 * @param list of students enrolled in the class
	 * @return a list of available students for a class
	 */
	@Query(value = "select cast(ST.studentid as varchar)  ,ST.first_name, ST.last_name from students as ST where not exists ( select S.* from rel_classes_students R   join students S on R.fk_student = S.studentid where R.fk_class = (?1) and ST.studentid = S.studentid )", nativeQuery = true)
	public List<Object[]> findAvailableStudents(UUID classID);
	

	/**
	 * Return the classes of a student
	 * @param studentID
	 * @return Return the classes of a student
	 */
	@Query("select c from ClassGroup c join c.students b where b.studentID = (?1)")
	public Iterable<ClassGroup> findClassesByStudent(UUID studentID);
	
	/**
	 * A list of student filtered by the query
	 * @param query
	 * @return A list of student filtered by the query
	 */
	@Query("from ClassGroup s where (lower( s.code ) like ('%' || lower(:query) || '%')) or (lower( s.title ) like ('%' || lower(:query) || '%'))")
	public Iterable<ClassGroup> findByQuery(String query);
}
