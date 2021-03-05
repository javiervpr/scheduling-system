<jsp:include page="shared/header.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="container mt-4">
	<div class="d-flex justify-content-between align-items-center">
		<h2>Class name: ${ classGroup.getTitle() }</h2>
	</div>
	<hr>
	<p>
		<strong>Description: </strong> <br>
		${ classGroup.getDescription() }
	</p>
	
	<c:if test="${students.size() == 0}">
	 	<p class="text-center pt-4" style="font-size:24px;">
	 		<i>No students available</i>
	 	</p>
	</c:if>
	<c:if test="${students.size() > 0}">	 	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Student Full Name</th>
					<th width="40%"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${students}" var="student">
					<tr>
						<td>${ student.getFirstName() } ${student.getLastName() }</td>
						<td>
							<a 	type="button" onclick="onTapBtnSelect(event)"
								id="${student.getStudentID() }"
								class="btn btn-outline-success" href="#">
								Select
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<button class="btn btn-success btn-block col-md-2" onclick="save()" id="btnSave">Save</button>
	</c:if>
</section>

<jsp:include page="shared/footer.jsp"></jsp:include>
<script>
	var studentList = [];
	function onTapBtnSelect(event) {
		var target = event.target;
		if (target.innerText === 'Select') {
			selectStudent(target);
		} else {
			unselect(target);
		}
		console.log(studentList);
	}
	function unselect(target) {
		$(target).removeClass("btn-success");
		$(target).addClass("btn-outline-success");
		$(target).html("Select");
		var index = studentList.indexOf(target.id);
		studentList.splice(index, 1);
	}
	function selectStudent(target) {
		$(target).removeClass("btn-outline-success");
		$(target).addClass("btn-success");
		$(target).html("Selected");
		studentList.push(target.id);
	}
	function save() {
		if (studentList.length === 0) {
			Toast.fire({
				  icon: 'error',
				  title: 'You must choose a student'
			});
			return;
		}
		$('#btnSave').on('click',function() {
    		$(this).prop("disabled",true);
		});
		$.ajax({
		  type: "POST",
		  contentType: "application/json",
		  dataType: 'json',
		  url: "/add-students-to-class/${classGroup.getClassID()}",
		  data: JSON.stringify(studentList),
		  cache: false,
		  error:function(data) {
		  	$('#btnSave').on('click',function() {
   				$(this).prop("disabled",false);
			});
		  },
		  success: function(data){
		  console.log(data);
		     if (data !== null) {
		     	//window.location.href = "/students-of-class/${classGroup.getClassID()}";
		     	Toast.fire({
				  icon: 'success',
				  title: 'Student successfully added'
				});
				setTimeout(function(){
					window.location.href = "/students-of-class/${classGroup.getClassID()}";	 
				}, 1000);
				
				$('#btnSave').on('click',function() {
    				$(this).prop("disabled",false);
				});
		     }
		  }
		});
	}
	const Toast = Swal.mixin({
	  toast: true,
	  position: 'top-end',
	  showConfirmButton: false,
	  timer: 3000,
	  timerProgressBar: false,
	  didOpen: (toast) => {
	    toast.addEventListener('mouseenter', Swal.stopTimer)
	    toast.addEventListener('mouseleave', Swal.resumeTimer)
	  }
	})
	
	
	
</script>