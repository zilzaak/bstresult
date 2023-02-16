package basati.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import basati.model.Department;



public interface Departmentrepo extends JpaRepository<Department,Integer> {

List<Department> findByExamtypeAndSessionOrderByRollnoAsc(String examtype, String session); // filter student
List<Department> findByExamtypeAndDeptAndSemesterOrderByRollnoAsc(String examtype, String dept, String semester);// filter student
List<Department> findByExamtypeAndSessionAndDeptOrderByRollnoAsc(String examtype, String session, String dept);// filter student
List<Department> findByExamtypeAndSessionAndDeptAndSemesterOrderByRollnoAsc(String examtype, String session,
			String dept, String semester);// filter student
List<Department> findByExamtypeAndSessionAndDeptAndSemesterAndSubcodeOrderByRollnoAsc(String examtype,
			String session, String dept, String semester, String subcode);// filter student
List<Department> findByExamtypeAndSubcodeOrderByRollnoAsc(String examtype, String subcode);// filter student
List<Department> findByExamtypeAndDeptAndSubcodeOrderByRollnoAsc(String examtype, String dept, String subcode);// filter student
List<Department> findByExamtypeAndDeptOrderByRollnoAsc(String examtype, String dept);// filter student
List<Department> findByExamtypeAndSemesterOrderByRollnoAsc(String examtype, String semester);// filter student
List<Department> findByExamtypeAndSessionAndDeptAndSubcodeOrderByRollnoAsc(String examtype, String session,
			String dept, String subcode);// filter student
List<Department> findByExamtypeAndDeptAndSemesterAndSubcodeOrderByRollnoAsc(String examtype, String dept,
			String semester, String subcode);// filter student
List<Department> findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollnoAndRegno(String examtype, int year,
			String session, String dept, String semester, String rollno, String regno);
boolean existsByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollno(String examtype, int year, String session,
			String dept, String semester, String rollno);
List<Department> findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollno(String examtype, int year,
			String session, String dept, String semester, String rollno);
boolean existsByExamtypeAndYearAndSessionAndDeptAndSemesterAndRegno(String examtype, int year, String session,
			String dept, String semester, String regno);
List<Department> findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRegno(String examtype, int year,
			String session, String dept, String semester, String regno);
boolean existsByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollnoAndSubcode(String examtype, int year,
		String session, String dept, String semester, String rollno, String subcode);
boolean existsByExamtypeAndYearAndSessionAndDeptAndSemesterAndRegnoAndSubcode(String examtype, int year, String session,
		String dept, String semester, String regno, String subcode);
List<Department> findByExamtypeAndYearAndSessionAndDeptAndSemesterAndSubcodeOrderByRollnoAsc(String examtype, int year,
		String session, String dept, String semester, String subcode);
List<Department> findByExamtypeAndYearAndSessionAndDeptAndSemester(String examtype, int year, String session,
		String dept, String semester);
List<Department> findByExamtypeAndYearAndSessionAndDeptAndSemesterOrderByRollnoAsc(String examtype, int year,
		String session, String dept, String semester);
List<Department> findByExamtypeAndSessionAndDeptAndSemesterAndRollno(String examtype, String session, String dept,
		String semester, String rollno);
public List<Department> findBySessionAndDept(String session ,String dept);
List<Department> findByDeptAndSemester(String dept, String semester);
List<Department> findBySemester(String s);
			
}
