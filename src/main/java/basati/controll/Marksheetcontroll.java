package basati.controll;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import basati.model.Department;
import basati.model.Helperr;
import basati.model.Markseethelper;
import basati.model.Orsub;
import basati.model.Subserial;

@Controller
public class Marksheetcontroll {
	
	public List<Department> tempdps=null;
	public List<Department> tempdps2=null;
public void serialsubject(Orsub or,int index){
	
	for(Department dp : tempdps) {
		if(dp.getSubcode().contentEquals(or.getSubcode())) {
				tempdps2.add(index,dp);
			
			}
		
			}

			}



@PostMapping("/setserial")
	public ResponseEntity<Subserial> setserial(@RequestBody Subserial cs,HttpSession session) {
	
	if(session.getAttribute("codelist")==null) {
 List<Subserial> codelist=new ArrayList<Subserial>();
	      codelist.add(cs);
session.setAttribute("codelist", codelist);
for(Orsub o : codelist.get(codelist.size()-1).getOrsubs()) {
	System.out.println("the item inside orsub is "+o.getSubcode());
}
	}
	else {
		
List<Subserial> codelist=(List<Subserial>) session.getAttribute("codelist");
int c=0;
for(Subserial s : codelist) {
	if(s.getSemester().contentEquals(cs.getSemester()) && s.getSession().contentEquals(cs.getSession()) && s.getDepartment().contentEquals(cs.getDepartment())) {
		s.setOrsubs(cs.getOrsubs());
		c++;
	}
}

if(c<1) {
	codelist.add(cs);	
}
session.setAttribute("codelist", codelist);

		}
	
return new ResponseEntity<Subserial>(cs,HttpStatus.OK);

	}
	
	
	
	@RequestMapping("/download")
	public ModelAndView downloadsheet(HttpSession session) {
		List<Subserial> subs=(List<Subserial>) session.getAttribute("codelist");
		List<Orsub> arangesub=new ArrayList<Orsub>();
		Department df=(Department) session.getAttribute("dp");
for(Subserial s : subs) {
if(s.getDepartment().contentEquals(df.getDept()) && s.getSession().contentEquals(df.getSession()) && s.getSemester().contentEquals(df.getSemester())) {
		arangesub=s.getOrsubs();		
		}
		}

Helperr hp=(Helperr) session.getAttribute("helper");
		ModelAndView mv = new ModelAndView("msheet");
		for(Department d : hp.getDps()) {
			 String sd=d.getGrade();
			if(d.getTotal()==0 || d.getGradepoint()==0 || sd==null) {
				d.setGrade("F");
			}
		}
	
		tempdps=new ArrayList<Department>();
		tempdps2=new ArrayList<Department>();
		tempdps= hp.getDps();
		
		for(Orsub or : arangesub) {
serialsubject(or,arangesub.indexOf(or));
			
		}
		hp.setDps(tempdps2);
		mv.addObject("hp",hp);
		return mv;	
	
	}
	
	
	
}
