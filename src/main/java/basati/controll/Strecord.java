package basati.controll;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import basati.model.Department;
import basati.model.Helperr;
import basati.model.Resultst;
import basati.model.Serialmake;
import basati.repo.Departmentrepo;
import basati.repo.Serialmakerepo;


@Controller
public class Strecord {
@Autowired
private Departmentrepo drr;
@Autowired
private Serialmakerepo srr;
	
List<Department> uinlist=new ArrayList<Department>();
public boolean checkunique(Department d) {
	int count=0;
	for(Department dp : uinlist) {
		if(d.getRollno().contentEquals(dp.getRollno()) || d.getRegno().contentEquals(dp.getRegno())) {
			count++;
		}
	}
	
	if(count<1) {
		uinlist.add(d);
		return false;
	}
	
	return true;
}
	
	@PostMapping("/substrecord")
	public ResponseEntity<List<Department>> strecordadd(@RequestBody List<Department> record){
		uinlist=new ArrayList<Department>();
		String dupin="";
		for(Department dp : record) {

		if(checkunique(dp)) {
			dupin=dupin+" duplicate input of roll or reg no for roll:"+dp.getRollno()+", ";
		}
		}	
		
		if(!dupin.contentEquals("")) {
			record.get(0).setStudentname(dupin);	
			return new  ResponseEntity<List<Department>>(record,HttpStatus.OK);
		}
		
		if(drr.count()<1) {
                drr.saveAll(record);
				record.get(0).setStudentname("successfully added unique record");	
				return new  ResponseEntity<List<Department>>(record,HttpStatus.OK);
					
		}
		
	
List<Department> lst =drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndSubcodeOrderByRollnoAsc(
		record.get(0).getExamtype(),record.get(0).getYear(),record.get(0).getSession(),record.get(0).getDept(),
	record.get(0).getSemester(),record.get(0).getSubcode());
		
		if(lst.isEmpty()) {
			drr.saveAll(record);
			String pm="successfully added unique record";
			record.get(0).setStudentname(pm);
	return new  ResponseEntity<List<Department>>(record,HttpStatus.OK);		
			
		}
		
		

		for(Department d : record) {
			
			if(!lst.get(0).getSubname().contentEquals(d.getSubname())) {
							String x="subject name "+d.getSubname()+" do not match for subject code "+d.getSubcode()+" change it to "+lst.get(0).getSubname()+",";
				record.get(0).setStudentname(x);
				return new  ResponseEntity<List<Department>>(record,HttpStatus.OK);
						}
			if(lst.get(0).getFullmark()!=d.getFullmark()) {
				String y="full mark should be "+lst.get(0).getFullmark()+"for subject code "+d.getSubcode()+" edit please,";
				record.get(0).setStudentname(y);
				return new  ResponseEntity<List<Department>>(record,HttpStatus.OK);
			}	
			
		if(lst.get(0).getCredit()!=d.getCredit()) {
			String z="credit should be "+lst.get(0).getCredit()+" for subject code "+d.getSubcode()+" edit please,";
				record.get(0).setStudentname(z);
				return new  ResponseEntity<List<Department>>(record,HttpStatus.OK);
			}
					
		}
		
		
			String sms="";	
		
		
		for(Department d : record) {		
if(drr.existsByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollnoAndSubcode(d.getExamtype(),d.getYear(),
d.getSession(),d.getDept(),d.getSemester(),d.getRollno(),d.getSubcode())
||	drr.existsByExamtypeAndYearAndSessionAndDeptAndSemesterAndRegnoAndSubcode(d.getExamtype(),d.getYear(),
				d.getSession(),d.getDept(),d.getSemester(),d.getRegno(),d.getSubcode())) {
			
			sms=sms+",name:"+d.getStudentname()+"roll: "+d.getRollno()+", reg no:"+d.getRegno()+",subject code: "+d.getSubcode()+",";
			
		}

			
		}
		
		if(!sms.contentEquals("")){
			sms="sorry!! can not insert these students record because these record already added "+sms+",";
			record.get(0).setStudentname(sms);
		}
		if(sms.contentEquals("")){
			drr.saveAll(record);
			sms="successfully added unique record";
			record.get(0).setStudentname(sms);
		}
		
		return new  ResponseEntity<List<Department>>(record,HttpStatus.OK);
		
	}
	

	
	@PostMapping("/filtstudent")
	public ResponseEntity<List<Department>> filtstudent(@RequestBody Department d){
		String subcode=d.getSubcode();
		List<Department> lst=new ArrayList<Department>();
	if(!d.getSession().contentEquals("any") &&  d.getDept().contentEquals("any") && d.getSemester().contentEquals("any") && subcode==null  ) {
		lst=drr.findByExamtypeAndSessionOrderByRollnoAsc(d.getExamtype(),d.getSession());
		
		}
	
	if(d.getSession().contentEquals("any") &&  !d.getDept().contentEquals("any") &&  !d.getSemester().contentEquals("any") &&  subcode==null ) {
		lst=drr.findByExamtypeAndDeptAndSemesterOrderByRollnoAsc(d.getExamtype(),d.getDept(),d.getSemester());
		
		}
	
	if(!d.getSession().contentEquals("any") &&  !d.getDept().contentEquals("any")  &&  d.getSemester().contentEquals("any") &&  subcode==null ) {
		lst=drr.findByExamtypeAndSessionAndDeptOrderByRollnoAsc(d.getExamtype(),d.getSession(),d.getDept());
		
	}
	
	
	if(!d.getSession().contentEquals("any") &&  !d.getDept().contentEquals("any")  &&  !d.getSemester().contentEquals("any") &&  subcode==null) {
		lst=drr.findByExamtypeAndSessionAndDeptAndSemesterOrderByRollnoAsc(d.getExamtype(),d.getSession(),d.getDept(),d.getSemester());
		
	}
	
	if(!d.getSession().contentEquals("any") &&  !d.getDept().contentEquals("any")  &&  !d.getSemester().contentEquals("any") &&  subcode!=null  ) {
		lst=drr.findByExamtypeAndSessionAndDeptAndSemesterAndSubcodeOrderByRollnoAsc(d.getExamtype(),d.getSession(),d.getDept(),d.getSemester(),d.getSubcode());
		
	}
	
	if(d.getSession().contentEquals("any") &&  d.getDept().contentEquals("any")  &&  d.getSemester().contentEquals("any") &&  subcode!=null  ) {
		lst=drr.findByExamtypeAndSubcodeOrderByRollnoAsc(d.getExamtype(),d.getSubcode());	
		
	}
	
	if(d.getSession().contentEquals("any") &&  !d.getDept().contentEquals("any")  &&  d.getSemester().contentEquals("any") &&  subcode!=null  ) {
		lst=drr.findByExamtypeAndDeptAndSubcodeOrderByRollnoAsc(d.getExamtype(),d.getDept(),d.getSubcode());	
		
	}
		
	if(d.getSession().contentEquals("any") &&  !d.getDept().contentEquals("any")  &&  d.getSemester().contentEquals("any") &&  subcode==null ) {
		lst=drr.findByExamtypeAndDeptOrderByRollnoAsc(d.getExamtype(),d.getDept());
		
	}
	if(d.getSession().contentEquals("any") &&  d.getDept().contentEquals("any")  &&  !d.getSemester().contentEquals("any") &&  subcode==null ) {
		lst=drr.findByExamtypeAndSemesterOrderByRollnoAsc(d.getExamtype(),d.getSemester());
		
	}
	if(!d.getSession().contentEquals("any") &&  !d.getDept().contentEquals("any")  &&  d.getSemester().contentEquals("any") &&  subcode!=null ) {
		lst=drr.findByExamtypeAndSessionAndDeptAndSubcodeOrderByRollnoAsc(d.getExamtype(),d.getSession(),d.getDept(),d.getSubcode());
		
	}
	
	if(d.getSession().contentEquals("any") &&  !d.getDept().contentEquals("any")  &&  !d.getSemester().contentEquals("any") &&  subcode!=null ) {
		lst=drr.findByExamtypeAndDeptAndSemesterAndSubcodeOrderByRollnoAsc(d.getExamtype(),d.getDept(),d.getSemester(),d.getSubcode());
		
	}
				return new  ResponseEntity<List<Department>>(lst,HttpStatus.OK);
		
	}	
	
	
	@PostMapping("/makegpa")
	public ResponseEntity<Department> makegpa(@RequestBody Department dp) throws ParseException{
		
		String sms="";
		Department chng=null;
	  chng=drr.findById(dp.getDid()).get();
	  
	  
	  if(!dp.getStudentname().contentEquals(chng.getStudentname())) {
List<Department> lst=drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollnoAndRegno(chng.getExamtype(),chng.getYear(),
		chng.getSession(),chng.getDept(),chng.getSemester(),chng.getRollno(),chng.getRegno());

		for(Department dk : lst) {
			dk.setStudentname(dp.getStudentname());
		drr.save(dk);
			System.out.println("updating student name for name:"+dk.getStudentname());
		}
		
		  }
	  
	  	  
	  if(!dp.getRollno().contentEquals(chng.getRollno())) {
		  	if(!drr.existsByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollno(
	chng.getExamtype(),chng.getYear(),dp.getSession(),dp.getDept(),dp.getSemester(),dp.getRollno())) {
		List<Department> lst=drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollnoAndRegno(chng.getExamtype(),chng.getYear(),
	chng.getSession(),chng.getDept(),chng.getSemester(),chng.getRollno(),chng.getRegno());
		
		for(Department dk : lst) {
		dk.setRollno(dp.getRollno());
		   drr.save(dk);
			System.out.println("updating the roll no for name:"+dk.getStudentname());
		}
		}
		  	
		else {
	Department fr=drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollno(dp.getExamtype(),dp.getYear(),
	dp.getSession(),dp.getDept(),dp.getSemester(),dp.getRollno()).get(0);
			sms=sms+" this roll no belongs to "+fr.getStudentname()+"edit or delete that otherwise can not update, ";
			System.out.println("sorry duplicate roll no found");
			dp.setStudentname(sms);
		return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		
		  
	  }
	 
	  chng=drr.findById(dp.getDid()).get();
	  
	  if(!dp.getRegno().contentEquals(chng.getRegno())) {
		  
	if(!drr.existsByExamtypeAndYearAndSessionAndDeptAndSemesterAndRegno(dp.getExamtype(),dp.getYear(),
			dp.getSession(),dp.getDept(),dp.getSemester(),dp.getRegno())) {
		List<Department> lst=drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollnoAndRegno(chng.getExamtype(),chng.getYear(),
				chng.getSession(),chng.getDept(),chng.getSemester(),chng.getRollno(),chng.getRegno());
	
		for(Department dk : lst) {
			dk.setRegno(dp.getRegno());
			drr.save(dk);
			System.out.println("updating the reg no for name:"+dk.getStudentname());
				}	
	}
		  
	else {
		
		Department fr=drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRegno(dp.getExamtype(),dp.getYear(),
				dp.getSession(),dp.getDept(),chng.getSemester(),dp.getRegno()).get(0);
		sms=sms+" this reg no belongs to "+fr.getStudentname()+" edit or delete that otherwise can not update, ";	
		dp.setStudentname(sms);
	return new  ResponseEntity<Department>(dp,HttpStatus.OK);
	}
		  }
	  
	  
		float tcvp=(float) (0.40*dp.getTcv());float pcvp=(float) (0.40*dp.getPcv());
		float tfvp=(float) (0.40*dp.getTfv());float pfvp=(float) (0.40*dp.getPfv());

if(dp.getTc()<tcvp || dp.getTf()<tfvp || dp.getPc()<pcvp || dp.getPf()<pfvp){
	    dp.setGrade("F");
		dp.setGradepoint((float) 0.00);
		drr.save(dp);
		dp.setStudentname("updated mark successfully");
		return new  ResponseEntity<Department>(dp,HttpStatus.OK);
	
}
	  
		float obtain=dp.getPc()+dp.getPf()+dp.getTc()+dp.getTf();
		dp.setTotal(obtain);
	float mark=(float) ((obtain*100.00)/dp.getFullmark());
			
			if(dp.getFullmark()>0) 	{
		if(mark>=80) {
			dp.setGrade("A+");
			dp.setGradepoint((float) 4.00);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		
		if(75<=mark && mark<80) {
			dp.setGrade("A");
			dp.setGradepoint((float) 3.75);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		
		if(70<=mark && mark<75) {
			dp.setGrade("A-");
			dp.setGradepoint((float) 3.50);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		if(65<=mark && mark<70) {
			dp.setGrade("B+");
			dp.setGradepoint((float) 3.25);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		if(60<=mark && mark<65) {
			dp.setGrade("B");
			dp.setGradepoint((float) 3.00);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		if(55<=mark && mark<60) {
			dp.setGrade("B-");
			dp.setGradepoint((float) 2.75);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		if(50<=mark && mark<55) {
			dp.setGrade("C+");
			dp.setGradepoint((float) 2.50);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		if(45<=mark && mark<50) {
			dp.setGrade("C");
			dp.setGradepoint((float) 2.25);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		
		if(40<=mark && mark<45) {
			dp.setGrade("D");
			dp.setGradepoint((float) 2.00);
			dp.setStudentname("successfully updated");
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		
		if(mark<40) {
			dp.setGrade("F");
			dp.setGradepoint((float) 0.0);
			drr.save(dp);
			System.out.println("the obtained mark in total is"+obtain+"  mark is in percent is "+mark+" FULL MARK IS "+dp.getFullmark());
			dp.setStudentname("successfully updated");
			return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		}
		
	} 
			
			dp.setStudentname("successfully updated");
		return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		
	}	
		
	public List<Department> lst2 = new ArrayList<Department>();

	@PostMapping("/submark")
	public ResponseEntity<List<Department>> filtstudent(@RequestBody List<Department> lst) throws ParseException{
		
	lst2=lst;
String sms="";
		boolean x=false;
		
		for(Department dn : lst){
		Department chng=null;
	chng=drr.findById(dn.getDid()).get();
	if(!chng.getRollno().contentEquals(dn.getRollno()) || !chng.getRegno().contentEquals(dn.getRegno()) || !chng.getStudentname().contentEquals(dn.getStudentname())) {
	sms=sms+" Sorry ,press update button for Name:"+dn.getStudentname()+", roll no: "+dn.getRollno()+",subject code:"+dn.getSubcode()+"as you have edited this record,";
x=true;
	}
	}
		
	if(x) {
		lst.get(0).setDept(sms);
		return new  ResponseEntity<List<Department>>(lst,HttpStatus.OK);
	}
		
		for(Department d : lst) {
if(checkdub(d)) {
	sms=sms+"record no::"+(lst.indexOf(d)+1)+",";
	x=true;
}
			
		}
		
		
			
		if(!x) {
			for(Department d : lst) {
				  Department chng=null;
				 chng=drr.findById(d.getDid()).get();
				d=gpasubmitall(d);
				drr.save(d);

		}
			sms="successfully added record";
			lst.get(0).setDept(sms);
			return new  ResponseEntity<List<Department>>(lst,HttpStatus.OK);
			
		}
		
sms=sms+" these record has duplicate roll no for same student for same dept,semester and subject, make correction please";
		lst.get(0).setDept(sms);
		return new  ResponseEntity<List<Department>>(lst,HttpStatus.OK);
		
		}
	
	
	public boolean checkdub(Department p) {
		boolean x=false;
		int count=0;
		for(Department b : lst2) {
if(b.getSession().contentEquals(p.getSession()) && b.getDept().contentEquals(p.getDept()) && b.getSemester().contentEquals(p.getSemester()) 
		&& b.getSubcode().contentEquals(p.getSubcode()) && b.getRollno().contentEquals(p.getRollno())) {
	count++;
}
		}	
		
		if(count>1) {
			x=true;
		}
		return x;
	}
	

	public Department gpasubmitall(Department dp) throws ParseException{
		
		float tcvp=(float) (0.40*dp.getTcv());float pcvp=(float) (0.40*dp.getPcv());
		float tfvp=(float) (0.40*dp.getTfv());float pfvp=(float) (0.40*dp.getPfv());

if(dp.getTc()<tcvp || dp.getTf()<tfvp || dp.getPc()<pcvp || dp.getPf()<pfvp){
	    dp.setGrade("F");
		dp.setGradepoint((float) 0.00);
		return dp;
	
}
		

		float obtain=dp.getPc()+dp.getPf()+dp.getTc()+dp.getTf();
		dp.setTotal(obtain);
		
	
			float mark=(float) ((obtain*100.00)/dp.getFullmark());
			
			if(dp.getFullmark()>0) 	{
		if(mark>=80) {
			dp.setGrade("A+");
			dp.setGradepoint((float) 4.00);
			return dp;
		}
		
		if(75<=mark && mark<80) {
			dp.setGrade("A");
			dp.setGradepoint((float) 3.75);
			return dp;
		}
		
		if(70<=mark && mark<75) {
			dp.setGrade("A-");
			dp.setGradepoint((float) 3.50);
			return dp;
		}
		if(65<=mark && mark<70) {
			dp.setGrade("B+");
			dp.setGradepoint((float) 3.25);
			return dp;
		}
		if(60<=mark && mark<65) {
			dp.setGrade("B");
			dp.setGradepoint((float) 3.00);
			return dp;
		}
		if(55<=mark && mark<60) {
			dp.setGrade("B-");
			dp.setGradepoint((float) 2.75);
			return dp;
		}
		if(50<=mark && mark<55) {
			dp.setGrade("C+");
			dp.setGradepoint((float) 2.50);
			return dp;
		}
		if(45<=mark && mark<50) {
			dp.setGrade("C");
			dp.setGradepoint((float) 2.25);
			return dp;
		}
		
		if(40<=mark && mark<45) {
			dp.setGrade("D");
			dp.setGradepoint((float) 2.00);
			return dp;
		}
		
		if(mark<40) {
			dp.setGrade("F");
			dp.setGradepoint((float) 0.00);
			return dp;
		}
		
	}
		return dp;
		
	}		
	
	
	@PostMapping("/findresult")
	public ResponseEntity<Helperr> findresult(@RequestBody Department dp,HttpSession session){
		List<Department> lst=new ArrayList<Department>();
		
if(dp.getExamtype().contentEquals("regular")) {
	 lst = drr.findByExamtypeAndSessionAndDeptAndSemesterAndRollno(dp.getExamtype(),
			 dp.getSession(),dp.getDept(),dp.getSemester(),dp.getRollno());
}

if(!dp.getExamtype().contentEquals("regular")) {
	 lst = drr.findByExamtypeAndYearAndSessionAndDeptAndSemesterAndRollno(dp.getExamtype(),dp.getYear(),
			 dp.getSession(),dp.getDept(),dp.getSemester(),dp.getRollno());
}

 

float totalpoint=0;float totalcredit=0;
float gpa=0; 
DecimalFormat dfrmt = new DecimalFormat("#.00");
boolean p=false;
Resultst rs = new Resultst();

  for(Department d: lst) {
	  String sd=d.getGrade();
   if(sd==null) {
	   rs.setName(d.getStudentname());
			rs.setRoll(d.getRollno());
		    rs.setRegno(d.getRegno());	rs.setSession(d.getSession());
		    rs.setSerial("not found");rs.setSms("student mark not inserted, insert to get result");	    
		    Helperr hp=new Helperr(rs,lst);
		    session.setAttribute("helper", hp);
		    return new  ResponseEntity<Helperr>(hp,HttpStatus.OK);
    }
	
}
  
  
  
if(!lst.isEmpty()) {
    for(Department d: lst) {
        
if(d.getGrade().contentEquals("F")) {
	p=true;
}
    totalpoint=totalpoint+d.getGradepoint()*d.getCredit();
    	totalcredit=totalcredit+d.getCredit();
    }
    
    
    
if(p) {
rs.setGpa("0.00");rs.setRoll(dp.getRollno());
rs.setName(dp.getStudentname());
rs.setSemester(dp.getSemester());
rs.setDept(dp.getDept());
rs.setSession(dp.getSession());
rs.setRegno(dp.getRegno());
rs.setSms("failed");
}
      
if(!p){
gpa=totalpoint/totalcredit; 
String gpas=dfrmt.format(gpa);
String serial="12345678";
if(srr.existsByDeptAndRollnoAndSemester(dp.getDept(),dp.getRollno(),dp.getSemester())) {
Serialmake ms =srr.findByDeptAndRollnoAndSemester(dp.getDept(),dp.getRollno(),dp.getSemester()).get(0);
rs.setSerial(ms.getSerial());
rs.setSession(lst.get(0).getSession());
rs.setRegno(lst.get(0).getRegno());	
rs.setGpa(gpas);rs.setRoll(dp.getRollno());rs.setName(dp.getStudentname());
rs.setSemester(dp.getSemester());rs.setSms("successfully found result");
rs.setDept(dp.getDept());
}

if(!srr.existsByDeptAndRollnoAndSemester(dp.getDept(),dp.getRollno(),dp.getSemester())) {
    do {
        serial=makeserial();
      }while(srr.existsBySerialAndDeptAndRollnoAndSemester(serial,dp.getDept(),dp.getRollno(),dp.getSemester()));	
    Serialmake ms = new Serialmake();
    ms.setDept(dp.getDept());ms.setRollno(dp.getRollno());ms.setSerial(serial); 
    ms.setRegno(dp.getRegno());ms.setSemester(dp.getSemester());
    srr.save(ms);
    rs.setSerial(serial);
    rs.setSession(lst.get(0).getSession());
    rs.setRegno(lst.get(0).getRegno());	rs.setName(dp.getStudentname());
    rs.setGpa(dfrmt.format(gpa));rs.setRoll(dp.getRollno());
    rs.setSemester(dp.getSemester());rs.setSms("successfully found result");
    rs.setDept(dp.getDept());         
    
}

}
}

if(lst.isEmpty()) {
	rs.setRoll("student not found");
    rs.setRegno("student not found");	
    rs.setSerial("not found");
    rs.setSms("student not exist of this roll");
}


Helperr hp=new Helperr(rs,lst);
session.setAttribute("helper", hp);
session.setAttribute("dp",dp);
return new  ResponseEntity<Helperr>(hp,HttpStatus.OK);
		
	}		
	
	
public String makeserial() {
		String chars="0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(8);
				for (int i = 0; i < 8; i++)
					sb.append(chars.charAt(rnd.nextInt(chars.length())));
				return sb.toString();
	}
	
	

	@PostMapping("/clearres")
	public ResponseEntity<Department> cd1(@RequestBody Department dp ){
 return new  ResponseEntity<Department>(dp,HttpStatus.OK);
         	}	
	
	@PostMapping("/cleardept")
	public ResponseEntity<Department> cds(@RequestBody Department dp ){
		drr.deleteAll();
       return new  ResponseEntity<Department>(dp,HttpStatus.OK);
		
	}	
		
	
	
	@DeleteMapping("/deldepartment")
	
	public ResponseEntity<Department>  delmark(@RequestBody Department dp) {
		Department d=drr.findById(dp.getDid()).get();
		drr.delete(d);
		dp.setDept("successfull");
		return new ResponseEntity<Department>(dp,HttpStatus.OK);
	
	}	
	
	@RequestMapping("/changesemester/{s}/{s2}")
	public void  changesem(@PathVariable String s,@PathVariable String s2) {
	List<Department> cdl=drr.findBySemester(s);
	for(Department d : cdl) {
d.setSemester(s2);
drr.save(d);
	}
	
	}	
	
	
	@PostMapping("/uniquesub")
	
	public ResponseEntity<List<Department>>unique(@RequestBody String[] dp) {
	
List<Department> udlst=drr.findByDeptAndSemester(dp[0],dp[1]);
List<Department> udlst2=new ArrayList<Department>();
if(!udlst.isEmpty()) {
	for(Department d : udlst) {
		int x=0;
		for(Department k:udlst2) {
			if(d.getSubcode().contentEquals(k.getSubcode())) {
				x=x+1;
			}
		}
		
		if(x<1) {
			udlst2.add(d);
		}
		
	}
}
else {
	udlst2.add(new Department());
}

		return new ResponseEntity<List<Department>>(udlst2,HttpStatus.OK);
	
	}		
	
	
}
