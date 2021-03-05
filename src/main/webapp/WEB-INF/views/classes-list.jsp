<jsp:include page="shared/header.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="container mt-4">
	<div class="d-flex justify-content-between align-items-center">
		<h2>List of classes</h2>
		<a href="/class-form" title="Class form" class="btn btn-success">
			New class 
		</a>

	</div>
	<hr>

	<table class="table table-hover">
		<thead>
			<tr>
				<th>Code</th>
				<th>Title</th>
				<th width="40%"></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="2">
					<input 	value="${query}"
							onkeypress="javascript: if(event.keyCode == 13) search();" 
							placeholder="Search by title or code..."
							class="form-control" 
							name="searchInput" 
							id="searchInput" />
				</td>
				<td>
					<button class="btn btn-success" onclick="search()">Search</button>
				</td>
			</tr>
			<c:forEach items="${list}" var="class">
				<tr>
					<td>${ class.getCode() }</td>
					<td>${ class.getTitle() }</td>
					<td>
						<a 	type="button" class="btn btn-link"
							href="/students-of-class/${class.getClassID()}">
							Show students
						</a>
						<a 	type="button" class="btn btn-outline-primary"
							href="/class-form/${class.getClassID()}">
							Update
						</a>
						<a 	type="button" onclick="areYouSure('${class.getClassID()}')"
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
	window.location.href = "/?query="+value;
}
function areYouSure(classID) {	
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
		    window.location.href = "/class-delete/"+ classID;
		  }
		});
	}
</script>