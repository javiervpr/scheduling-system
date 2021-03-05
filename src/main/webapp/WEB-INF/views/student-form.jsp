<jsp:include page="shared/header.jsp"></jsp:include>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<section class="container mt-4">
	<h2>${ title }</h2>
	<form:form method="post" modelAttribute="student" class="col-md-6">
		<hr>
		<form:hidden path="studentID" />
		<div class="form-group">
			<form:label path="firstName">First Name</form:label> 
			<form:input path="firstName" type="text"
				class="form-control"
				placeholder="Ex: Juan..." required="required" />
			<form:errors path="firstName" cssClass="text-warning" />
		</div>
		
		<div class="form-group">
			<form:label path="lastName">Last Name</form:label>
			<form:input path="lastName" type="text"
				class="form-control"  
				placeholder="Ex: Roca..." required="required" />
			<form:errors path="lastName" cssClass="text-warning" />
		</div>
		<button class="btn btn-success btn-block" type="submit">Save</button>
	</form:form>
</section>
<jsp:include page="shared/footer.jsp"></jsp:include>