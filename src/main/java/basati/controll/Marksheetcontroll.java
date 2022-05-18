package basati.controll;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import basati.model.Department;
import basati.model.Helperr;
import basati.model.Markseethelper;
import basati.model.Orsub;

@Controller
public class Marksheetcontroll {
	
	
	@RequestMapping("/download")
	public ModelAndView downloadsheet(HttpSession session) {
		List<Orsub> arangesub=new ArrayList<Orsub>();
        Markseethelper tbh = new Markseethelper();
		Helperr hp=(Helperr) session.getAttribute("helper");
		ModelAndView mv = new ModelAndView("msheet");
		for(Department d : hp.getDps()) {
			 String sd=d.getGrade();
			if(d.getTotal()==0 || d.getGradepoint()==0 || sd==null) {
				
			}
			
			Orsub o=new Orsub();
			o.setSubcode(d.getSubcode());
		arangesub.add(o);
		
		}
	
		tbh.setOrsub(arangesub);
	    mv.addObject("hp",hp);
		mv.addObject("markform",tbh);
		return mv;	
	
	}
	
	
	public List<Department> tempdps=null;
	public List<Department> tempdps2=null;
public void serialsubject(Orsub or,int index){
	
	for(Department dp : tempdps) {
			
			if(dp.getSubcode().contentEquals(or.getSubcode())) {
			
			tempdps2.add(index,dp);
			
			}
		
			}

			}
	
	
@RequestMapping(value="/savemark",method=RequestMethod.POST)
	public ModelAndView arrangesubject(@ModelAttribute("contactForm") Markseethelper contactForm, HttpSession session) {
		Helperr hp=(Helperr) session.getAttribute("helper");
		ModelAndView mv = new ModelAndView("msheet");
		tempdps=new ArrayList<Department>();
		tempdps2=new ArrayList<Department>();
		tempdps= hp.getDps();
		for(Orsub or : contactForm.getOrsub()) {
serialsubject(or,contactForm.getOrsub().indexOf(or));
			
		}
hp.setDps(tempdps2);
		mv.addObject("hp",hp);
		mv.addObject("markform",contactForm);
return mv;	
	
	}	
	
	
	
}
