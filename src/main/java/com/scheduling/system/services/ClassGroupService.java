package com.scheduling.system.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduling.system.dao.IClassGroupDao;
import com.scheduling.system.dao.IStudentDao;
import com.scheduling.system.models.ClassGroup;
import com.scheduling.system.models.Student;

@Service
public class ClassGroupService implements IClassGroupService {

	@Autowired
	private IClassGroupDao classDao;
	@Autowired
	private IStudentDao studentDao;

	@Override
	public List<ClassGroup> findAll() {
		return (List<ClassGroup>) classDao.findAll();
	}

	@Override
	public void save(ClassGroup classGroup) {
		ClassGroup classGroupTemp = classDao.findById(classGroup.getClassID()).orElse(null);
		if (classGroupTemp == null) {
			classDao.save(classGroup);
		} else {
			classGroup.setStudents(classGroupTemp.getStudents());
			classDao.save(classGroup);
		}

	}

	@Override
	public ClassGroup findOne(UUID id) {
		return classDao.findById(id).orElse(null);
	}

	@Override
	public void delete(UUID id) {
		classDao.deleteById(id);
	}

	@Override
	public List<Student> findAllStudents(UUID classID) {
		List<Object[]> studentsOfClass = classDao.findAvailableStudents(classID);
		List<Student> availableStudentsForClass = new ArrayList<Student>();

		for (Object[] data : studentsOfClass) {
			availableStudentsForClass.add(new Student(UUID.fromString(String.valueOf(data[0])), String.valueOf(data[1]),
					String.valueOf(data[2])));
		}
		return availableStudentsForClass;
	}

	@Override
	public void addStudents(UUID classGroupID, List<String> studentIDs) throws Exception {
		ClassGroup classGroup = classDao.findById(classGroupID).orElse(null);
		if (classGroup == null) {
			throw new Exception("Class not found");
		}
		for (String studentID : studentIDs) {
			Student studentTemp = studentDao.findById(UUID.fromString(studentID)).orElse(null);
			if (studentTemp == null)
				continue;

			classGroup.addStudent(studentTemp);
		}
		classDao.save(classGroup);
	}

	@Override
	public void deleteStudentOfClass(UUID classGroupID, UUID studentID) throws Exception {
		ClassGroup classGroup = classDao.findById(classGroupID).orElse(null);
		if (classGroup == null)
			throw new Exception("Class not found");
		Student student = studentDao.findById(studentID).orElse(null);
		if (student == null)
			throw new Exception("Student not found");
		classGroup.deleteStudent(student);
		classDao.save(classGroup);
	}

	@Override
	public List<ClassGroup> findByQuery(String query) {
		return (List<ClassGroup>) classDao.findByQuery(query);
	}

}
