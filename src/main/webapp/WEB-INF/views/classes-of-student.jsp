<jsp:include page="shared/header.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="container mt-4">
	<div class="d-flex justify-content-between align-items-center">
		<h2>Student: ${ student.getFirstName() } ${student.getLastName()} </h2>
	</div>
	<hr>
	<p>
		<strong>Student-ID: </strong> <br>
		${ student.getStudentID() }
	</p>
	
	<c:if test="${student.getClasses().size() == 0}">
	 	<p class="text-center pt-4" style="font-size:24px;"><i>The student doesn't have classes</i></p>
	</c:if>
	<c:if test="${student.getClasses().size() > 0}">	 	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Code</th>
					<th>Title</th>
					<th width="40%"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${student.getClasses()}" var="class">
					<tr>
						<td>${ class.getCode() }</td>
						<td>${ class.getTitle() }</td>
						<td>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</section>

<jsp:include page="shared/footer.jsp"></jsp:include>