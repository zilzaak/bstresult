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
	public ResponseEntity<Orsub> setserial(@RequestBody List<Orsub> cs,HttpSession session) {
	 List<Orsub> codelist=new ArrayList<Orsub>();
	 codelist=cs;
 session.setAttribute("codelist", codelist);
		return new ResponseEntity<Orsub>(codelist.get(0),HttpStatus.OK);
	
	}
	
	
	
	@RequestMapping("/download")
	public ModelAndView downloadsheet(HttpSession session) {
		List<Orsub> arangesub=(List<Orsub>) session.getAttribute("codelist");
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
