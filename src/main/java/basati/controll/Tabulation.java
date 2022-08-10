package basati.controll;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import basati.model.Department;
import basati.model.Helperr;
import basati.model.Orsub;
import basati.model.Resultst;
import basati.model.Subserial;
import basati.model.Tabulationhelper;
import basati.repo.Departmentrepo;

@Controller
public class Tabulation {

	@Autowired
	private Departmentrepo drr;

	public List<Department> dlst2=new ArrayList<Department>();
	public boolean checkdp(String roll) {
		int count=0;
		for(Department dp : dlst2) {
		if(dp.getRollno().contentEquals(roll))	{
			
			count++;
		}
		}
		if(count==0) {
			return true;
		}
		
		return false;
		
	}
	

public Resultst findresult( Department dp){
	Resultst rs = new Resultst();
List<Department> lst = drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollno(
dp.getExamtype(),dp.getYear(),		
		dp.getSession(),dp.getDept(),dp.getSemester(),dp.getRollno());
float totalcredit=0;float sum=0;float gpa=0; 
DecimalFormat dfrmt = new DecimalFormat("#.00");
boolean p=false;
for(Department d: lst) {
        String gd=d.getGrade();

if(gd==null || gd.contentEquals("F") || d.getGradepoint()<2) {
p=true;
}
sum=sum+d.getGradepoint()*d.getCredit();
    	totalcredit=totalcredit+d.getCredit();
    }
    

  if(p) {
rs.setGpa("0.00");rs.setRoll(dp.getRollno());
rs.setSemester(dp.getSemester());
rs.setDept(dp.getDept());
rs.setSession(dp.getSession());
rs.setRegno(dp.getRegno());    rs.setName(dp.getStudentname());
rs.setSms("F");
}
      
if(!p) {
gpa=sum/totalcredit; 
String gpas=dfrmt.format(gpa);
rs.setSession(lst.get(0).getSession());
rs.setRegno(lst.get(0).getRegno());	
rs.setGpa(gpas);rs.setRoll(dp.getRollno());
rs.setSemester(dp.getSemester());
rs.setSms(getletter(Float.valueOf(gpas)));
rs.setDept(dp.getDept()); rs.setName(dp.getStudentname());
}
  return rs;
		
	}	

@PostMapping("/tabresult")
	public ResponseEntity<List<Helperr>> getallresult(@RequestBody Tabulationhelper th,HttpSession session) {
	List<Subserial> subs=(List<Subserial>) session.getAttribute("codelist");
	List<Orsub> orlist=new ArrayList<Orsub>();
	Department df=th.getDp();
for(Subserial s : subs) {
if(s.getDepartment().contentEquals(df.getDept()) && s.getSession().contentEquals(df.getSession()) && s.getSemester().contentEquals(df.getSemester())) {
	orlist=s.getOrsubs();		
	}
	}

	th.setOrsub(orlist);
		Department dp=th.getDp(); //assigning the dp value for search or filter student 
		
	    List<Department> dl2=new ArrayList<Department>();
		dlst2=dl2;
		List<Helperr> helplst = new ArrayList<Helperr>();
		List<Department> dlst=new ArrayList<Department>();
		
	
		if(!dp.getExamtype().contentEquals("regular")){
			dlst=drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterOrderByRollnoAsc(dp.getExamtype(),dp.getYear(),
				dp.getSession(),dp.getDept(),dp.getSemester());// get all the data of a particulat dept sem and year
	
		}
		
		if(dp.getExamtype().contentEquals("regular")){
			dlst=drr.findByExamtypeAndSessionAndDeptAndSemesterOrderByRollnoAsc(dp.getExamtype(),
				dp.getSession(),dp.getDept(),dp.getSemester());// get all the data of a particulat dept sem and year
	
		}		
		
	
		for(Department d : dlst) {
			
			if(checkdp(d.getRollno())) {
				dlst2.add(d);
				
			}
		}
		
		
		
for(Department d : dlst2) {
			Helperr h = new Helperr();
h.setRst(findresult(d)); // contain gpa , reg no roll , session , dept , semester , name , grade , letter grade,
h.setDps(drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollno(
d.getExamtype(),d.getYear(),		
		d.getSession(),d.getDept(),d.getSemester(),d.getRollno())); // return subject list and subjective grade or result		
		// now arrange the dps or sublitectve result list into subjective order according to technical board
List<Department> tempdps=new ArrayList<Department>(); // this empty list  is taken to add the dps according to subcode order or sirial ,

for(Orsub suborder : th.getOrsub()) {
	
for(Department dt : h.getDps()){
	
if(dt.getSubcode().contentEquals(suborder.getSubcode())) {
	tempdps.add(dt);
}
	
}
	
}
h.setDps(tempdps);
helplst.add(h);
			
		}
		
return new ResponseEntity<List<Helperr>>(helplst,HttpStatus.OK)  ;
		
	}	
		

	
	public String getletter(float mark) {
		
	
	if(mark>=4) {
		
	return "A+";

	}
	
	if(3.75<=mark && mark<4) {
		
	return "A";
	}
	
	if(3.50<=mark && mark<3.75) {
		return "A-";

	}
	if(3.25<=mark && mark<3.50) {
		return "B+";


	}
	
	if(3.00<=mark && mark<3.25) {
return "B";
	}
	
	if(2.75<=mark && mark<3.00) {
	return "B-";

	}
	if(2.50<=mark && mark<2.75) {
		
	
		return "C+";
	}
	if(2.25<=mark && mark<2.50) {
return "C";
	}
	
	if(2.00<=mark && mark<2.25) {
		

		return "D";
	}
	
	if(mark<2.00) {
		
		return "F";
	}
	
	return "und";

	}
	

}
