<jsp:include page="shared/header.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="container mt-4">
	<div class="d-flex justify-content-between align-items-center">
		<h2>List of students</h2>
		<a href="/student-form" title="Student form" class="btn btn-success">
			New student 
		</a>

	</div>
	<hr>

	<table class="table table-hover">
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th width="40%"></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="2">
					<input 	value="${query}"
							onkeypress="javascript: if(event.keyCode == 13) search();" 
							placeholder="Search by name..." 
							class="form-control" 
							name="searchInput" 
							id="searchInput" />
				</td>
				<td>
					<button class="btn btn-success" onclick="search()">Search</button>
				</td>
			</tr>
			<c:forEach items="${list}" var="student">
				<tr>
					<td>${ student.getFirstName() }</td>
					<td>${ student.getLastName() }</td>
					<td>
						<a 	type="button" class="btn btn-link"
							href="/classes-of-student/${student.getStudentID()}">
							Show classes
						</a>
						<a 	type="button" class="btn btn-outline-primary"
							href="/student-form/${student.getStudentID()}">
							Update
						</a>
						<a 	type="button" onclick="areYouSure('${student.getStudentID()}')"
							class="btn btn-outline-danger" href="#">
							Delete
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</section>
<jsp:include page="shared/footer.jsp"></jsp:include>
<script>
function search() {
	var value = $('#searchInput').val();
	window.location.href = "/students-list?query="+value;
}
function areYouSure(studentID) {	
		Swal.fire({
		  title: 'Are you sure?',
		  text: "You won't be able to revert this!",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
		  if (result.isConfirmed) {

		    // /student-delete/${student.getStudentID() }
		    window.location.href = "/student-delete/"+ studentID;
		  }
		});
	}
</script>