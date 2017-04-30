<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 <form action="DynamicJoinServlet">
 <table>
 	<tbody>

 		<tr>
 			<td><h2 >ColorType : </h2></td>
	 		<td>
				<select name="ColorType" style="float:left;">
				  <option value="">No Choice</option>
				  <option value="red">red</option>
				  <option value="yellow">yellow</option>
				  <option value="green">green</option>
				  <option value="blue">blue</option>
				  <option value="purple">purple</option>
				  <option value="white">white</option>
				  <option value="black">black</option>
				</select>
			</td>
 			<td><h2 style="padding-left: 20px;">price : </h2></td>
	 		<td>
				<select name="Price" style="float:left;">
				  <option value="">No Choice</option>
				  <option value="500">less than 500</option>
				  <option value="1000">less than 1000</option>
				  <option value="1500">less than 1500</option>
				</select>
			</td>			 		
 		</tr>
 		<tr>
 			<td><h2>MaterialType : </h2></td>
	 		<td>
				<select name="MaterialType" style="float:left;">
				  <option value="">No Choice</option>
				  <option value="cotton">cotton</option>
				  <option value="linen">linen</option>
				  <option value="wool">wool</option>
				  <option value="ayon">ayon</option>
				</select>
			</td> 
 			<td><h2 style="padding-left: 20px;">isSell : </h2></td>
	 		<td>
				<select name="IsSell" style="float:left;">
				  <option value="">No Choice</option>
				  <option value="1">true</option>
				  <option value="0">false</option>
				</select>
			</td>					
 		</tr> 		
 	</tbody>
 </table>
  
  <input type="submit">
</form>
</body>
</html>