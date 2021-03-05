<jsp:include page="shared/header.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="container mt-4">
	<div class="d-flex justify-content-between align-items-center">
		<h2>Class name: ${ classGroup.getTitle() }</h2>
		<a href="/add-students-to-class/${classGroup.getClassID()}" title="Class form" class="btn btn-success">
			Add student 
		</a>
	</div>
	<hr>
	<p>
		<strong>Description: </strong> <br>
		${ classGroup.getDescription() }
	</p>
	
	<c:if test="${classGroup.getStudents().size() == 0}">
	 	<p class="text-center pt-4" style="font-size:24px;"><i>Without students</i></p>
	</c:if>
	<c:if test="${classGroup.getStudents().size() > 0}">	 	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Student Full Name</th>
					<th width="40%"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${classGroup.getStudents()}" var="student">
					<tr>
						<td>${ student.getFirstName() } ${student.getLastName() }</td>
						<td>
							<a 	type="button" onclick="areYouSure('${student.getStudentID()}')"
								class="btn btn-outline-danger" 
								href="#">
								Delete
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</section>

<jsp:include page="shared/footer.jsp"></jsp:include>

<script>
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
		    Swal.fire(
		      'Deleted!',
		      'Your file has been deleted.',
		      'success'
		    );
		    window.location.href = "/delete-student-of-class/" + studentID + "/${classGroup.getClassID() }";
		    ///delete-student-of-class/${student.getStudentID()}/${classGroup.getClassID() }
		  }
		});
	}
</script>