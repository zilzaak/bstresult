package basati.model;

import java.util.List;

public class Subserial {
String session;
String department;
String semester;
List<Orsub> orsubs;
public String getSession() {
	return session;
}
public void setSession(String session) {
	this.session = session;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getSemester() {
	return semester;
}
public void setSemester(String semester) {
	this.semester = semester;
}
public List<Orsub> getOrsubs() {
	return orsubs;
}
public void setOrsubs(List<Orsub> orsubs) {
	this.orsubs = orsubs;
}

}
