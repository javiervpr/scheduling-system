package com.scheduling.system.services;

import java.util.List;
import java.util.UUID;

import com.scheduling.system.models.ClassGroup;
import com.scheduling.system.models.Student;

public interface IClassGroupService {
	public List<ClassGroup> findAll();
	public void save(ClassGroup classGroup);
	public ClassGroup findOne(UUID id);
	public void delete(UUID id);
	public List<Student> findAllStudents(UUID classID);
	public void addStudents(UUID classGroupID ,List<String> studentIDs) throws Exception;
	public void deleteStudentOfClass(UUID classGroupID ,UUID studentID) throws Exception;
	public List<ClassGroup> findByQuery(String query);
}
