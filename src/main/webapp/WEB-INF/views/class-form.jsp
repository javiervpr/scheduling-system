<jsp:include page="shared/header.jsp"></jsp:include>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<section class="container mt-4">
	<h2>${ title }</h2>
	<form:form method="post" modelAttribute="classGroup" class="col-md-6">
		<hr>
		<form:hidden path="classID" />
		<div class="form-group">
			<form:label path="code">Code</form:label>
			<form:input path="code" type="text" class="form-control"
				placeholder="Ex: 1321" required="required" />
			<form:errors path="code" cssClass="text-warning" />
		</div>

		<div class="form-group">
			<form:label path="title">Title</form:label>
			<form:input path="title" type="text" class="form-control"
				placeholder="Ex: WEB I" required="required" />
			<form:errors path="title" cssClass="text-warning" />
		</div>
		<div class="form-group">
			<form:label path="description">Description</form:label>
			<form:textarea path="description" required="required"
				class="form-control" />
			<form:errors path="title" cssClass="text-warning" />
		</div>
		<button class="btn btn-success btn-block" type="submit">Save</button>
		
	</form:form>
</section>
<jsp:include page="shared/footer.jsp"></jsp:include>

<script>
</script>