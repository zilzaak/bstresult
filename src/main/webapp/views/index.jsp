<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>get result</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/static/theme/bootstrap431.css" /> " rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="<c:url value="/static/theme/jquery340.js" />" > </script> 
<script src="<c:url value="/static/theme/angular1.8.2.js" />" > </script>
<script src="<c:url value="/static/theme/bootstrap431.js" />" > </script>

<script>
var module=angular.module("sellapp",[]);
module.controller("sellcontrol",function($scope,$http){
	
	$scope.sch=['2010-11','2011-12','2012-13','2013-14','2014-15','2015-16','2016-17','2017-18','2018-19','2019-20','2020-21',
		'2021-22','2022-23','2023-24','2024-25','2025-26','2026-27','2027-28','2028-29','2029-30','2030-31','2031-32','2032-33','2033-34'];
	
$scope.khan1=['FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH','SEVENTH','EIGHTH'];
$scope.examtype=["--","regular","referred","irregular"];
$scope.khan2=['66 - Computer Technology','64 - Civil Technology','67 - Electrical Technology',"any"];

$scope.p={"dept":"","semester":"","session":"","subcode":null,"subname":"","fullmark":null};
$scope.p2={"dept":"","semester":"","session":"","subcode":""};
$scope.p3={"examtype":"","year":null, "session":"","dept":"","semester":"","rollno":""};

$scope.dps=null;
$scope.gotresult=null;

$scope.chtype=function(){
	
	if($scope.p3.examtype=="irregular" || $scope.p3.examtype=="referred"){
		document.getElementById("year").style.display="block";
		
	}
	if($scope.p3.examtype=="regular" || $scope.p3.examtype=="--"){
		document.getElementById("year").style.display="none";	
		
	}	
	
}

$scope.findresult=function(){
	
					
	if($scope.p3.examtype!="" || $scope.p3.examtype!="--"){
		
		
	if($scope.p3.examtype=="regular"){
		$http({ 
			method:"POST" , 
			url:"${pageContext.request.contextPath}/findresult", 
			data:angular.toJson($scope.p3),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
		      
		     $scope.gotresult=response.data.rst;
		     $scope.dps=response.data.dps;
	  
		        	})		
		
	}	
	
	if($scope.p3.examtype!="regular"){
		
	if($scope.p3.year==null){
		alert("select exam year ");
	}	
		
	if($scope.p3.year!=null){
		
		$http({ 
			method:"POST" , 
			url:"${pageContext.request.contextPath}/findresult", 
			data:angular.toJson($scope.p3),
		    headers:{"Content-Type":"application/json"}	
			
		        }).then(function(response){
		      
		     $scope.gotresult=response.data.rst;
		     $scope.dps=response.data.dps;
	  
		        	})		
		

	}		
				        	
	}			
		
		
	}	
				
      }
	        	
	        	
})

</script>

<style>
body{
box-sizing:border-box;
background-image:url("/static/theme/sea.jpg");
background-size:1400px 700px;
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
display:block;
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
<body  ng-controller="sellcontrol"  ng-app="sellapp">

<div  style="margin-left:8%;background-color:#66CDAA;width:85%;font-size:0.80em;" id="3">
<h2 style="text-align:center;color:green;background-color:#66CDAA;padding:5px;">get result</h2>

<table border="1" align="center" >
<tr>
<th>examtype</th>
<th>Session</th>
<th>Department</th>
<th>Semester</th>
<th>rollno</th>
</tr>
<tr>
<td><select  ng-model="p3.examtype" ng-options="c for c in examtype" ng-change="chtype()"></select></td>
<td><select  ng-model="p3.session" ng-options="c for c in sch"></select></td>
<td><select  ng-model="p3.dept" ng-options="c for c in khan2"></select></td>
<td><select  ng-model="p3.semester" ng-options="d for d in khan1"></select></td>
<td><input type="text"  ng-model="p3.rollno" /></td>
</tr>
</table> 
<br/>
<div id="year" style="display:none;" align="center"> exam year: <input type="number"  ng-model="p3.year" /></div> <br/>


<button style="margin-left:50%;" class="btn btn-dark btn-sm" ng-click="findresult()">submit</button>
<br/>
<br/>


<div style="background-color:#66CDAA;padding:10px;" align="center" ng-if="gotresult!=null">
<h4 style="color:green;">note::{{gotresult.sms}}</h4>

<ul style="list-style-type:none;">
<li style="color:red;">Result of:-- {{dps[0].studentname}}</li>
<li>reg no::{{gotresult.regno}}</li>
<li style="color:red;font-size:1.5em;">Result::{{gotresult.gpa}} (out of 4)</li>
</ul>
</div>

<div style="background-color:skyblue;color:darkslategrey;padding:10px;" align="center">

<table border="1" align="center" >
<tr ng-if="dps.length>0">
<th>subject</th>
<th>full mark</th>
<th>marks obtained</th>
<th>letter grade</th>
<th> grade point</th>
</tr>

<tr ng-repeat="x in dps">
<td>({{x.subcode}}) {{x.subname}}</td>
<td>{{x.fullmark}}</td>
<td>hidden</td>
<td>{{x.grade}}</td>
<td>{{x.gradepoint}}</td>
</tr>

</table> 
</div>
</div>
</body>
</html>
