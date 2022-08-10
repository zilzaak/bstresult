<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"  %>
<!DOCTYPE html>
<html>
<title>all result</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/static/theme/bootstrap431.css" /> " rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css
" rel="stylesheet">
<script src="<c:url value="/static/theme/jquery340.js" />" > </script> 
<script src="<c:url value="/static/theme/popper114.js" />" > </script>
<script src="<c:url value="/static/theme/angular1.8.2.js" />" > </script>
<script src="<c:url value="/static/theme/bootstrap431.js" />" > </script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
<script type="text/javascript" src="https://html2canvas.hertzen.com/dist/html2canvas.js"></script>
<script type="text/javascript">

var module=angular.module("arapp",[]);

module.controller("ar",function($scope,$http){
	
	$scope.posts=[];
$scope.allpost=function(){
	
	$http({ 
		method:"GET" , 
		url:"${pageContext.request.contextPath}/post/allpost", 
	  //	data:angular.toJson($scope.p),
	    headers:{"Content-Type":"application/json"}	
		
	        }).then(function(response){
	        			
	     	$scope.posts=response.data;
	        	
	        	})	
	
}
	

	
});




	
</script>

<style>
h2,h3,h4{
word-spacing:10px;

}
table th,td{
wrap-word:break-word;
padding:8px;
}
</style>

</head>
<body ng-controller="ar" ng-app="arapp" >

<div align="center">
<button ng-click="allpost()"> see all post </button>
</div>
 <br/>
 
 <table align="center" border="1">
 
 <tr>
 <th>post id</th>
 <th>title</th>
 <th>post review</th>
 </tr>
 
 <tr ng-repeat="x in posts" >
 <td>{{x.id}}</td>
 <td>{{x.title}}</td>
 <td ng-repeat="c in x.comments">
 {{c.review}}
 </td>
 </tr>
 
 </table>



</body>
</html>