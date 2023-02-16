<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>hompage</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/static/theme/bootstrap431.css" /> " rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="<c:url value="/static/theme/jquery340.js" />" > </script> 
<script src="<c:url value="/static/theme/angular1.8.2.js" />" > </script>
<script src="<c:url value="/static/theme/bootstrap431.js" />" > </script>

<script>
var module=angular.module("sellapp",[]);
module.controller("sellcontrol",function($scope,$http){
$scope.sch=["2010-11","2011-12","2012-13",'2013-14','2014-15','2015-16','2016-17','2017-18','2018-19','2019-20','2020-21',
	'2021-22','2022-23','2023-24','2024-25','2025-26','2O26-27','2027-28','2028-29','2029-30','2030-31','2031-32',
	'2032-33','2033-34','2034-35',"any"];
	
$scope.khan1=['FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH','SEVENTH','EIGHTH','any'];
$scope.khan2=["66 - Computer Technology","64 - Civil Technology","67 - Electrical Technology","any"];

$scope.p={"credit":null,"dept":"","semester":"","session":"","subcode":"",
		"subname":"","fullmark":null,"tcv":null,"pcv":null,"tfv":null,"pfv":null};

$scope.examtype2=["--","regular","referred","irregular"];
$scope.p2={"examtype":"","year":null,"dept":"any","semester":"any","session":"any","subcode":null};

$scope.p3={"dept":"","semester":"","rollno":""};
$scope.strecord=[];
var r1={"credit":null,"dept":"","semester":"","session":"","subcode":null,"studentname":"","rollno":"","regno":"","subname":"",
		"duration":"","year":null,"pub":"","issue":"","examtype":"","tcv":null,"pcv":null,"tfv":null,"pfv":null};
		
var r2={"credit":null,"dept":"","semester":"","session":"","subcode":null,"studentname":"","rollno":"","regno":"","subname":"",
		"duration":"","year":null,"pub":"","issue":"","examtype":"","tcv":null,"pcv":null,"tfv":null,"pfv":null};
var r3={"credit":null,"dept":"","semester":"","session":"","subcode":null,"studentname":"","rollno":"","regno":"","subname":"",
		"duration":"","year":null,"pub":"","issue":"","examtype":"","tcv":null,"pcv":null,"tfv":null,"pfv":null};
		
$scope.strecord.push(r1);
$scope.strecord.push(r2);
$scope.strecord.push(r3);

$scope.stfilter={"studentname":"","rollno":""};
$scope.clearstfilter=function(){
	
	$scope.stfilter.studentname="";
	$scope.stfilter.rollno="";
	
}


$scope.addst=function(i){
	var r={"credit":null,"dept":"","semester":"","session":"","subcode":null,"studentname":"","rollno":"","regno":"","subname":"",
		"duration":"","year":null,"pub":"","issue":"","examtype":"","tcv":null,"pcv":null,"tfv":null,"pfv":null};
	$scope.strecord.splice(i, 0, r);
}

$scope.examtype=["--","regular","referred","irregular"];

$scope.removest=function(i){
	if($scope.strecord.length>1){
		$scope.strecord.splice(i,1);	
	}
	
	
}


$scope.setmarkcredit=function(){
	
$scope.p.fullmark=$scope.p.tcv+$scope.p.pcv+$scope.p.tfv+$scope.p.pfv;	
$scope.p.credit=$scope.p.fullmark/50;	
	
}



$scope.existedrecord=function(){
	
	if($scope.p.session!="" && $scope.p.dept!=""){
		
			$http({ 
			method:"POST" , 
			url:"${pageContext.request.contextPath}/existedrecord", 
		  	data:angular.toJson($scope.p),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
		        	if(response.data.length>0){
		        	
		        		$scope.strecord=response.data;
		        	}	
		        	
		        	if(response.data.length<1){
		        		$scope.strecord=[];
		        		$scope.strecord.push(r1,r2,r3);
		        	}
		        	
		          	})		
		
	}
	
	$scope.getsublist($scope.p);
	
	    }
	    
$scope.subjects=[]; $scope.subjectsn=[];

$scope.getsublist=function(p){
	$scope.mb=[p.dept,p.semester];
	
	if(p.dept=="" || p.semester=="" || p.dept=="any" || p.semester=="any"){
		alert("please select department and  semester ");
	}
	
	if(p.dept!="" && p.semester!=""){
		$scope.subjects=[]; $scope.subjectsn=[];
		
		$http({ 
			method:"POST" , 
			url:"${pageContext.request.contextPath}/uniquesub", 
		  	data:angular.toJson($scope.mb),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
	   	
	  $scope.subj=response.data;
	  angular.forEach($scope.subj,function(v,k){
		  
		 $scope.subjects.push(v.subcode); 
		 $scope.subjectsn.push(v.subname);
	  })
	  
		        	})	
		
	}
	
	
}


$scope.setname=function(){

	angular.forEach($scope.subj,function(v,k){
	
	if(v.subname==$scope.p.subname ){
			
		$scope.p.subname=v.subname; 
		$scope.p.subcode=v.subcode ; 
		$scope.p.tcv=v.tcv; 
		$scope.p.tfv=v.tfv;
		$scope.p.pcv=v.pcv;
		$scope.p.pfv=v.pfv;
		$scope.p.fullmark=v.tcv+v.tfv+v.pcv+v.pfv;
		$scope.p.credit=$scope.p.fullmark/50;
		}
	
	})
	
 
}

$scope.setcode=function(){

	angular.forEach($scope.subj,function(v,k){
	
	if(v.subcode==$scope.p.subcode){
			
		$scope.p.subname=v.subname; 
		$scope.p.subcode=v.subcode ; 
		$scope.p.tcv=v.tcv; 
		$scope.p.tfv=v.tfv;
		$scope.p.pcv=v.pcv;
		$scope.p.pfv=v.pfv;
		$scope.p.fullmark=v.tcv+v.tfv+v.pcv+v.pfv;
		$scope.p.credit=$scope.p.fullmark/50;
		}
	
	})
	
 
}


$scope.substrecord=function(){

	var x="no";
	angular.forEach($scope.strecord,function(v,k){
		v.credit=$scope.p.credit;
		v.dept=$scope.p.dept;
		v.semester=$scope.p.semester;
		v.session=$scope.p.session;
		v.subcode=$scope.p.subcode;
		
		v.tcv=$scope.p.tcv; 
		v.tfv=$scope.p.tfv;
		v.pcv=$scope.p.pcv; 
		v.pfv=$scope.p.pfv;

		v.subname=$scope.p.subname;
		v.fullmark=$scope.p.fullmark;
	v.year=$scope.p.year; v.duration=$scope.p.duration;
	v.pub=$scope.p.pub;  v.issue=$scope.p.issue; v.examtype=$scope.p.examtype;
	
if(v.studentname=="" || v.dept=="" || v.semester=="" || v.subcode==null || v.rollno=="" || v.regno=="" || v.credit==null ||
	v.year==null || v.examtype=="" || v.examtype=="--" || v.session=="" || v.fullmark<=0 || v.fullmark==null || 
	v.subname=="" || v.rollno<=0 || v.regno<=0 || v.subcode<=0 || v.pcv==null || v.pfv==null || v.tcv==null || v.tfv==null){
	x="yes";
}
	});
	
	
	if(x=="no"){
		
		document.getElementById("mbd").style.display="none";
		
		$http({ 
			method:"POST" , 
			url:"${pageContext.request.contextPath}/substrecord", 
		  	data:angular.toJson($scope.strecord),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
		        	document.getElementById("mbd").style.display="block";    	
	alert(response.data[0].studentname);
	  
		        	})	
	}
	if(x=="yes"){	
		
		alert("empty field exist, fill the empty field");
	} 
		
}

$scope.clearstrecord=function(){
	$scope.p.subcode=null;
$scope.p.subname="";
$scope.p.fullmark=null;
$scope.p.credit=null; 	
$scope.p.pcv=null; 	$scope.p.tcv=null; $scope.p.tfv=null; $scope.p.pfv=null; 	
}


$scope.filtstudent=function(){
if($scope.p2.examtype!="" || $scope.p2.examtype!="--" || $scope.p2.year!=null){
		
		$http({ 
			method:"POST" , 
			url:"${pageContext.request.contextPath}/filtstudent", 
			data:angular.toJson($scope.p2),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
		      
		        	$scope.fdept=response.data;
		        	
		        	})
	}
	
	if($scope.p2.examtype=="" || $scope.p2.examtype=="--" || $scope.p2.year==null){		
		alert("select exam type and exam year for example 'regular' 2021 ");
	}
	
}	



$scope.cleardept=function(){
	
	$http({ 
		method:"POST" , 
		url:"${pageContext.request.contextPath}/cleardept", 
		data:angular.toJson($scope.p2),
	    headers:{"Content-Type":"application/json"}	
		
	        }).then(function(response){
	      
	     alert("successfully deleted department data")
  
	        	})		
	
	
}


$scope.chad={
	"email":null,
	"password":"",
	"code":null
		
};



$scope.getcode=function(){
	
	var cf="no";
	
	if($scope.chad.email==null ||  $scope.chad.email.indexOf("@gmail.com")==-1){
		cf="yes";
	}
	
	
	if(cf=="no"){
		$http({ 
			method:"POST" , 
			url:"${pageContext.request.contextPath}/getcode", 
			data:angular.toJson($scope.chad),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
		      
		        if(response.data.code!="ok"){
		        	alert(response.data.code);
		        }	
		        	
		   	        	})
		   	        	
		   	       	   	}
	
	if(cf=="yes"){	
		
	alert('wrong email id');	
		
	}
	
	
}



$scope.subcod=function(){
	
	$http({ 
		method:"POST" , 
		url:"${pageContext.request.contextPath}/subcode", 
		data:angular.toJson($scope.chad),
	    headers:{"Content-Type":"application/json"}	
		
	        }).then(function(response){
	      
	     alert(response.data.code);
  
	        	})		
		
}





$scope.clearres=function(){
	
	$http({ 
		method:"POST" , 
		url:"${pageContext.request.contextPath}/clearres", 
		data:angular.toJson($scope.p2),
	    headers:{"Content-Type":"application/json"}	
		
	        }).then(function(response){
	      
	        alert("successfully deleted result data");
  
	        	})		
	
	
}


$scope.getgpa=function(x){
	
	if(x.studentname=="" || x.rollno=="" || x.regno==""){
		
		alert("null value , can not update");
	}

	
if(x.studentname!="" && x.rollno!="" && x.regno!=""){
	$http({ 
		method:"POST" , 
		url:"${pageContext.request.contextPath}/makegpa", 
	  	data:angular.toJson(x),
	    headers:{"Content-Type":"application/json"}	
		
	        }).then(function(response){
	        	

	        	 alert(response.data.studentname);
	        	 
	    		$http({ 
	    			method:"POST" , 
	    			url:"${pageContext.request.contextPath}/filtstudent", 
	    			data:angular.toJson($scope.p2),
	    		    headers:{"Content-Type":"application/json"}	
	    			
	    		        }).then(function(response){
	    		      
	    		        	$scope.fdept=response.data;
	    		        	
	    		        	})
	   
	     
	  })		
		
	}	

	      }


$scope.submark=function(){
	var dept=$scope.fdept[0].dept;
	
	document.getElementById("mbd").style.display="none";
	
	$http({ 
		method:"POST" , 
		url:"${pageContext.request.contextPath}/submark", 
		data:angular.toJson($scope.fdept),
	    headers:{"Content-Type":"application/json"}	
		
	        }).then(function(response){
	      document.getElementById("mbd").style.display="block";
	        alert(response.data[0].dept);
	        $scope.fdept=response.data;
	        $scope.fdept[0].dept=dept;
	        	})		
		
}	



$scope.deletemark=function(dept){
	
	var person = window.confirm("want to delete student mark record??");
	
	if(person){
		
$http({ 
	method:"DELETE" , 
	url:"${pageContext.request.contextPath}/deldepartment", 
	data:angular.toJson(dept),
    headers:{"Content-Type":"application/json"}	
	
        }).then(function(response){
    		$http({ 
    			method:"POST" , 
    			url:"${pageContext.request.contextPath}/filtstudent", 
    			data:angular.toJson($scope.p2),
    		    headers:{"Content-Type":"application/json"}	
    			
    		        }).then(function(response){
    		      
    		        	$scope.fdept=response.data;
    		        	 
    		        	})
     })
        
		}
		
}


$scope.marktime=function(){

	if($scope.p2.dept!="any" || $scope.p2.session!="any" || $scope.p2.semester!="any"){
	angular.forEach($scope.fdept,function(v,k){

		if(k!=0){
		v.fullmark=$scope.fdept[0].fullmark; 
		v.year=$scope.fdept[0].year;
		v.duration=$scope.fdept[0].duration;
		v.issue=$scope.fdept[0].issue;
		v.pub=$scope.fdept[0].pub;	
			
		}

			
		})

		$http({ 
			method:"PUT" , 
			url:"${pageContext.request.contextPath}/updatede", 
			data:angular.toJson($scope.fdept[0]),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
		      

		        	})	
		        	
	}      	
		      
		        	if($scope.p2.dept=="any" || $scope.p2.session=="any" || $scope.p2.semester=="any"){
		        		
		        		alert("can not update or edit because you have not selected session , deparment and semester . please select");
		        	}
		        				        	
		        			        	
	
}

 
		
	
$scope.marktime2=function(){

	if($scope.p2.dept!="any" && $scope.p2.session!="any" && $scope.p2.subcode!=null){
		angular.forEach($scope.fdept,function(v,k){

			if(k!=0){
			v.fullmark=$scope.fdept[0].fullmark; 
			v.subname=$scope.fdept[0].subname;
			v.credit=$scope.fdept[0].credit;
			
			v.tcv=$scope.fdept[0].tcv; 
			v.tfv=$scope.fdept[0].tfv;
			
			v.pcv=$scope.fdept[0].pcv;
			v.pfv=$scope.fdept[0].pfv;
			
			}

				
			})

			$http({ 
				method:"PUT" , 
				url:"${pageContext.request.contextPath}/updatede2", 
				data:angular.toJson($scope.fdept),
			    headers:{"Content-Type":"application/json"}	
				
			        }).then(function(response){
			      

			        	})		
		
	}
		
	if($scope.p2.dept=="any" || $scope.p2.session=="any" || $scope.p2.subcode==null){
		
		alert("can not update full mark or subject name , first select session , deparment and subcode");
	}
		
		
	}	




$scope.admin={"email":"","password":"","code":""};
$scope.confirm="";

$scope.epl="no";
$scope.epm="no";
$scope.en="no";

$scope.reg=function(){
	
	$scope.epl="no";
	$scope.epm="no";
	$scope.en="no";	
var  epl1=""; var epm1=""; var epm2="";

if($scope.admin.password.length!=6){
	$scope.epl="yes";
	epl1="password length must be 6";
}

if($scope.admin.password!=$scope.confirm){
	$scope.epm="yes"; epm1="confirm pasword not match";
}

if($scope.admin.password==""){
	$scope.epm="yes";
	epm2="blank user or password field or invalid mail";
}


if($scope.epl=="no" && $scope.epm=="no" && $scope.en=="no"){

	$http({ 
		method:"POST" , 
		url:"${pageContext.request.contextPath}/change", 
	  	data:angular.toJson($scope.admin),
	    headers:{"Content-Type":"application/json"}	
		
	        }).then(function(response){
	        	
alert(response.data.code);
  
	        	})	
		
}

if($scope.epl=="yes" || $scope.epm=="yes" || $scope.en=="yes"){

	alert(epm1+epm2+epl1);	
		
	}
	
}


$scope.serials2={
    	"session":"","department":"","semester":"","orsubs":[]	  
      };
      
 $scope.orsub=[];     
      var s1={"subcode":""};
      var s2={"subcode":""};
      var s3={"subcode":""};
      $scope.orsub.push(s1,s2,s3);	
      
	$scope.addsub=function(i){
	      var s={"subcode":""};
	      $scope.orsub.splice(i,0,s);		
		
	}        	
	 
	$scope.removesub=function(i){
		
		 $scope.orsub.splice(i,1);
	} 
	
	$scope.setserial=function(){
		
		$scope.serials2.orsubs=$scope.orsub;
		
		$http({ 
			method:"POST" , 
			url:"${pageContext.request.contextPath}/setserial", 
			data:angular.toJson($scope.serials2),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
		      
				alert("successfully set subject serial");
		        })		
		
	}	        	
	        	
})
</script>

<script>
function showdiv(i){
	
	for(var x=1;x<4;x++){
		
		if(x==i){
			
			document.getElementById(i).style.display="block";
		}
		if(x!=i){
			
			document.getElementById(x).style.display="none";
		}	
		
	}
	
               }
      
function autosubject(){
	document.getElementById("ainput").style.display="inline";

	document.getElementById("minput").style.display="none";
}
    
function manualsubject(){
	document.getElementById("minput").style.display="inline";

	document.getElementById("ainput").style.display="none";
}

</script>


<style>
body{
box-sizing:border-box;
background-image:url("/static/theme/sea.jpg");
background-size:1400px 500px;
}
.contain1{
display:grid;
grid-template-columns: 30% 30% 10%;
column-gap:20px;
padding-bottom:30px;
}
span:hover{
color:red;
background-color:skyblue;
}
table td:hover{
background-color:silver; color:green;
}
input:hover{
background-color:maroon; color:white;
}
table{
overflow-x:scroll;
}
table th{
wrap-word:break-word;
background-color:black;
color:white;
padding:8px;
}
table td{
wrap-word:break-word;
background-color:white;
color:black;
text-align:center;
}
a:hover{
background-color:steelblue;
}
.dropdown-menu a:hover{
background-color:steelblue;
}
</style>

</head>
<body  ng-controller="sellcontrol"  ng-app="sellapp" id="mbd">

<%
if(session.getAttribute("user")==null && session.getAttribute("password")==null){
	response.sendRedirect("${pageContext.request.contextPath}");
	}
	  %>

<nav class="navbar navbar-expand-lg" style="margin-right:7%;margin-left:8%;margin-top:2%;border-radius:8px;background-color:darkslategrey;">
  <a class="navbar-brand" href="#" style="margin-left:5%;color:maroon;background-color:orange;padding:8px;">Controller office, BASATI</a>
   <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon" style="color:white;"><b>click</b></span>
  </button> 

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" style="margin-left:5%;color:white;" role="button" 
        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
         admin panel
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#" onclick="showdiv('1');">add student record</a>
          <a class="dropdown-item" href="#" onclick="showdiv('2');">insert student mark</a>
          </div>
       </li>
       

   
      <li class="nav-item dropdown">
 
    <b data-toggle="modal" data-target="#slm"  style="color:white;">set serial</b>
        

                  
       </li> 
   
   
   
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" style="margin-left:5%;color:white;" role="button" 
        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
           result
            </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
       <!--   <a class="dropdown-item" href="${pageContext.request.contextPath}/adminmark"> </a>result -->
  <form method="get" target="_blank" action="/adminmark" style="margin-left:15%;">
<button type="submit">result</button>
</form>
          
          
          <a class="dropdown-item" href="#" onclick="window.open('${pageContext.request.contextPath}/allresult')" >all result</a>
           <a class="dropdown-item" href="#" onclick="window.open('${pageContext.request.contextPath}/allresult2')" >Tabulation Sheet</a>
            <a class="dropdown-item" href="#" onclick="window.open('${pageContext.request.contextPath}/subjective')" >subjective mark Sheet</a>
          </div>
       </li> 
       
             <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" style="margin-left:5%;color:white;" role="button" 
        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
         setting
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        
          <a class="dropdown-item" data-toggle="modal" data-target="#myModal">change password</a>
          <a class="dropdown-item" data-toggle="modal" data-target="#myemail">change email</a>
          </div>
          
          
       </li> 
       
       
        <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" style="margin-left:5%;color:white;" role="button" 
        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          log out
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        
    <a class="dropdown-item" href="${pageContext.request.contextPath}/logout" >log out</a>
          </div> 
       </li> 
       
       </ul>
      </div>
</nav>




<div  style="margin-left:8%;background-color:#66CDAA;width:85%;display:none;font-size:0.80em;" id="1">
<br/>
<table border="1" align="center" >
<tr>
<th style="background-color:blue;">session*</th>
<th style="background-color:blue;">Department*</th>
<th style="background-color:blue;">Semester*</th>
<th style="background-color:blue;">examtype*</th>
<th style="background-color:blue;">exam year*</th>
</tr>
<tr>
<td><select  ng-model="p.session" ng-options="c for c in sch" ng-change="existedrecord()"></select></td>
<td><select  ng-model="p.dept" ng-options="c for c in khan2"  ng-change="existedrecord()"></select></td>
<td><select  ng-model="p.semester" ng-options="d for d in khan1" ng-change="getsublist(p);"></select></td>
<td><select  ng-model="p.examtype" ng-options="c for c in examtype"></select></td>
<td><input type="number" style="width:100px;"  ng-model="p.year"/></td>
</tr>
</table>
<br/>
<span style="margin-left:30%;"> 
<button onclick="autosubject();">auto subject input</button>
<button onclick="manualsubject();">manual subject input</button>
</span> <br/> <br/>
<table border="1"  id="ainput" style="margin-left:20%;">
<tr>
<th style="background-color:blue;">subject code*</th>
<th style="background-color:blue;">sub name*</th>
<th style="background-color:blue;">TC*</th>
<th style="background-color:blue;">TF*</th>
<th style="background-color:blue;">PC*</th>
<th style="background-color:blue;">PF*</th>
<th style="background-color:blue;">total mark*</th>
<th style="background-color:blue;">credit*</th>

</tr>
<tr>
<td>
<div ng-if="subj.length<2">
<input type="text"  ng-model="p.subcode" />
<br/>

</div>
<div ng-if="subj.length>1">
<select ng-options="c  for c in subjects"  ng-model="p.subcode" ng-change="setcode()" ></select>
</div>

</td>

<td>
<div ng-if="subj.length<2">
<input type="text"  ng-model="p.subname" />
<br/>

</div>
<div ng-if="subj.length>1">
<select ng-options="c  for c in subjectsn"  ng-model="p.subname" ng-change="setname()" ></select>
</div>



</td>

<td><input type="number"  style="width:70px;"  ng-model="p.tcv" ng-keyup="setmarkcredit()" /></td>
<td><input type="number"  style="width:70px;"  ng-model="p.tfv" ng-keyup="setmarkcredit()" /></td>
<td><input type="number"  style="width:70px;"  ng-model="p.pcv" ng-keyup="setmarkcredit()"/></td>
<td><input type="number"  style="width:70px;"  ng-model="p.pfv" ng-keyup="setmarkcredit()"/></td>   


<td><input type="number"  style="width:70px;"  ng-model="p.fullmark"/></td>
<td><input type="number"  style="width:70px;"  ng-model="p.credit"/>   
  </td>
</tr>
</table>

<table border="1"  id="minput" style="display:none;margin-left:20%;">
<tr>
<th style="background-color:blue;">subject code*</th>
<th style="background-color:blue;">sub name*</th>
<th style="background-color:blue;">TC*</th>
<th style="background-color:blue;">TF*</th>
<th style="background-color:blue;">PC*</th>
<th style="background-color:blue;">PF*</th>
<th style="background-color:blue;">total mark*</th>
<th style="background-color:blue;">credit*</th>

</tr>
<tr>
<td>
<input type="text"  ng-model="p.subcode" /></td>
<td>
<input type="text"  ng-model="p.subname" />
</td>
<td><input type="number"  style="width:70px;"  ng-model="p.tcv" ng-keyup="setmarkcredit()" /></td>
<td><input type="number"  style="width:70px;"  ng-model="p.tfv" ng-keyup="setmarkcredit()" /></td>
<td><input type="number"  style="width:70px;"  ng-model="p.pcv" ng-keyup="setmarkcredit()"/></td>
<td><input type="number"  style="width:70px;"  ng-model="p.pfv" ng-keyup="setmarkcredit()"/></td>   
<td><input type="number"  style="width:70px;"  ng-model="p.fullmark"/></td>
<td><input type="number"  style="width:70px;"  ng-model="p.credit"/>   
</td>
</tr>
</table>
<br/>
<div class="row" style="text-align:center;"><button class="btn btn-primary btn-sm"  style="margin-left:30%;" ng-click="clearstrecord()">clear subject</button></div>
<br/>
  <h5 style="color:white;text-align:center;">exam duration, year and others</h5> 
<table border="1" align="center" >
<tr>
<th>exam duration</th>
<th>result publication date</th>
<th>result issue date</th>
</tr>
<tr>
<td><input type="text"   ng-model="p.duration"/></td>
<td><input type="text"   ng-model="p.pub"/></td>
<td><input type="text"  ng-model="p.issue"/></td>
</tr>
</table>
<br/><br/>



  <h5 style="color:white;text-align:center;">enter student's record related to the subject code</h5> 
<table border="1" align="center">
<tr>
<th>record no</th>
<th>name*</th>
<th>roll no*</th>
<th>reg no*</th>
<th>delete</th>
<th>add</th>
</tr>

<tr ng-repeat="x in strecord">
<td>{{$index+1}}</td>
<td><input  ng-model="x.studentname"/></td>
<td><input   ng-model="x.rollno" style="width:110px;" /></td>
<td><input   ng-model="x.regno" style="width:110px;" /></td>
<td><button class="btn btn-dark btn-sm" ng-click="removest($index)">del</button></td>
<td><button class="btn btn-success btn-sm"  ng-click="addst($index)">add</button></td>
</tr>

</table>
<br/>

<div class="row">
<button class="btn btn-success btn-sm" style="margin-left:30%;" ng-click="substrecord()">submit</button>
</div>

<br/>




<br/>

</div>


<div  style="margin-left:8%;background-color:#DCDCDC;width:85%;display:none;font-size:0.80em;" id="2">
<h4 style="text-align:center;color:black;padding:5px;">select 1. session , 2. dept , 3.semester,  4. subject code to insert mark</h4>
<table border="1" align="center" >
<tr>
<th>exam type</th>
<th>exam year</th>
<th>session</th>
<th>Department</th>
<th>Semester</th>
<th>subject code</th>
</tr>
<tr>
<td><select  ng-model="p2.examtype" ng-options="c for c in examtype2" ng-change="filtstudent()"></select></td>
<td><input type="number"   ng-model="p2.year" /></td>
<td><select  ng-model="p2.session" ng-options="c for c in sch" ng-change="filtstudent()"></select></td>
<td><select  ng-model="p2.dept" ng-options="c for c in khan2" ng-change="filtstudent()"></select></td>
<td><select  ng-model="p2.semester" ng-options="d for d in khan1" ng-change="filtstudent()"></select></td>
<td><input type="number"  ng-model="p2.subcode" ng-keyup="filtstudent()"  ng-change="filtstudent()" /></td>

</tr>
<tr>
</tr>
</table>
<br/>
<div align="center">
subject name<input type="text" ng-model="fdept[0].subname" ng-change="marktime2()"  class="form-control" style="width:40%;" placeholder="subject name"  />
<br/>
subject credit:-<input type="number" ng-model="fdept[0].credit" ng-change="marktime2()" /> <br/>

TC:-<input type="number" ng-model="fdept[0].tcv" ng-change="marktime2()" /> TF:-<input type="number" ng-model="fdept[0].tfv" ng-change="marktime2()" />
<br/>

PC:-<input type="number" ng-model="fdept[0].pcv" ng-change="marktime2()" /> PF:-<input type="number" ng-model="fdept[0].pfv" ng-change="marktime2()" />
<br/>

</div>
<br/><br/>

<h4 style="text-align:center;color:black;padding:5px;">insert marks</h4>




<table border="1" align="center" >
<tr ng-if="fdept!=null">
<th>full mark</th>
<th>exam year</th>
<th>duration</th>
<th>result publication date</th>
<th>result issue date</th>
</tr>

<tr ng-if="fdept!=null">>
<td>
<input type="number" ng-model="fdept[0].fullmark" ng-change="marktime2();" />
</td>
<td>
<input type="number" ng-model="fdept[0].year" ng-change="marktime();"/>
</td>
<td>
<input type="text" ng-model="fdept[0].duration" ng-change="marktime();"/>
</td>
<td>
<input type="text" ng-model="fdept[0].pub" ng-change="marktime();"/>
</td>
<td>
<input type="text" ng-model="fdept[0].issue" ng-change="marktime();"/>
</td>

</tr>

</table>

<br/>



<div ng-if="fdept!=null" style="background-color:darkseagreen;text-align:center;padding:15px;" >
<button ng-click="clearstfilter()" class="btn btn-primary btn-sm" style="margin-left:50px;">clear</button>
<input type="text"  ng-model="stfilter.studentname" placeholder="search student name" />  
<input type="text" style="margin-left:50px;" ng-model="stfilter.rollno" placeholder="search roll no" />
</div>

<br/> 
<table border="1" align="center" >
<tr ng-if="fdept!=null">
<th>record no</th>
<th>Dept & Sub</th>
<th>student info</th>
<th>Set MarkK</th>
<th>grade</th>
<th>task</th>
<th>delete</th>
</tr>

<tr ng-repeat="x in fdept | filter:stfilter">
<td>{{$index+1}}</td>

<td>

<b>department::</b><br/>
{{x.dept}}
<br/>
<b>subject::</b><br/>
{{x.subname}}
<br/>
<b>subcode::</b><br/>
{{x.subcode}}
<br/>

</td>


<td style="background-color:gray;">
<b>student name</b> <br/>
<input  ng-model="x.studentname"/>
<br/>
<b>roll no</b> <br/>
<input  ng-model="x.rollno"  /> <br/>
<b>reg no</b> <br/>
<input  ng-model="x.regno"  /> 
</td>


<td> 
<div style="background-color:black;color:white;">
<b>TC:{{x.tcv}} , TF:{{x.tfv}}</b><br/>
<b>PC:{{x.pcv}} , PF:{{x.pfv}}</b><br/> </div>

<b>TC::</b>
<input  style="width:50%"  type="number"  ng-model="x.tc" ng-if="x.tcv>0" /> 
<br/>
<b>TF::</b>
<input style="width:50%"  type="number"  ng-model="x.tf" ng-if="x.tfv>0"  /> 
<br/>
<b>PC::</b>
<input style="width:50%"  type="number"  ng-model="x.pc" ng-if="x.pcv>0" /> 
<br/>
<b>PF::</b>
<input style="width:50%"  type="number"   ng-model="x.pf" ng-if="x.pfv>0" />  
</td>

<td>
<b>grade:</b>{{x.grade}}
<br/>
<b>gpa:</b>
{{x.gradepoint}}
<br/>
<b>obtain mark</b><br/>
{{x.total}}
</td>
<td>
<button ng-click="getgpa(x)">update</button>
</td>
<td><button ng-click="deletemark(x);" class="btn btn-danger btn-sm">delete</button></td>
</tr>
</table>
<br/>
<button align="center" ng-click="submark();" style="margin-left:50%;" class="btn btn-success brn-md">submit all</button>

<br/>
<br/>
</div>


 <!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">password change</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body" style="text-align:center;">
      <b>use 6 character for password</b> <br/>
        <form>
       <input type="text" ng-model="admin.email" placeholder="enter old password"/> <br/> <br/>
       <input type="text" ng-model="admin.password" placeholder="new password" /> <br/> <br/>
       <input type="text" ng-model="confirm" placeholder="confirm password" /> <br/> <br/>
       </form>
       <button ng-click="reg();">submit</button>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>


 <!-- The Modal -->
<div class="modal" id="myemail">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">change email</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body" style="text-align:center;">
      
      <script type="text/javascript">
      function showsb(){
    	  alert("okkkk");
          document.getElementById("cd").style.display="block";  
    	       }

      </script>

     
      
      <b>enter new email</b> <br/>
      <input type="text" ng-model="chad.email" placeholder="enter email email"/> <br/> <br/>
      <button   ng-click="getcode();" onclick="showsb();">submit</button>
       
      <div id="cd" style="display:none;">
      <b>enetr code sent to your new email</b> <br/>
      <input type="text" ng-model="chad.code"/> <br/>
      <br/>
      <button  ng-click="subcod();">submit</button>
      </div> 
         </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>


<div id="slm" class="modal fade" role="dialog">
  <div class="modal-dialog">
 <!-- Modal content-->
      <div class="modal-content">
      <div class="modal-header" style="background-color:gray;">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
      <b>session:</b><select ng-model="serials2.session"  ng-options="x for x in sch"></select> <br/><br/>
         <b>department:</b><select ng-model="serials2.department" ng-options="x for x in khan2"></select> <br/><br/>
            <b>semester:</b><select ng-model="serials2.semester" ng-options="x for x in khan1"></select> <br/><br/>
<table border="1" align="center" >
<tr>
<th>SL NO</th>
<th>subject code</th>
<th>add/remove</th>
</tr>

<tr ng-repeat="sl in orsub">
<td>{{$index+1}}</td>
<td><input type="text" ng-model="sl.subcode" /></td>
<td>

<button ng-click="addsub($index)">(+)</button> 
<button ng-click="removesub($index)">(-)</button>
</td>
</tr>
</table>

<br/>
<div align="center" >
<button class="btn btn-sm" ng-click="setserial()"  style="background-color:white;"><b>save serial</b></button>
</div>
<br/>
 </div>
<div class="modal-footer">
  <button type="button" class="btn btn-default" data-dismiss="modal" id="reclose">Close</button>
</div>
</div>
</div>
</div>
</body>
</html>
