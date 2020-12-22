// let student_form = document.getElementById('student-validation');
// let course_form = document.getElementById('course-validation');
window.onload = fetch_courses;
window.onload=fetch_studentdata();

// student_form.addEventListener('submit', async (e) => {
//   e.preventDefault();
//   e.stopPropagation();
//   if (student_form.checkValidity() === true) {
//     let response = await fetch('api/courses/register_course', {
//       method: 'POST',
//       headers: {
//           'Content-Type': 'application/json;charset=utf-8'
//       },
//       body: JSON.stringify({
//           //first_name: document.getElementById('first_name').value,
//           //last_name: document.getElementById('last_name').value,
//           //email: document.getElementById('email').value,
//           course_id: sessionStorage.getItem('id'),
//           description: document.getElementById('courses').value,
//       })
//     }).then(
//         response => {
//             if(response['status'] === 203){
//                 document.getElementById("login-success").style.display = "none";
//                 document.getElementById("login-alert").style.display = "block";
//
//             }else{
//                 document.getElementById("login-alert").style.display = "none";
//                 document.getElementById("login-success").style.display = "block";
//
//             }
//         }
//     );
//   }else{
//       student_form.classList.add('was-validated');
//   }
// });

// course_form.onsubmit = async (e) => {
//     e.preventDefault();
//     e.stopPropagation();
//     if (course_form.checkValidity() === true) {
//       let form_data = new FormData();
//       form_data.append('name', document.getElementById('name').value);
//       form_data.append('description', document.getElementById('description').value);
//       form_data.append('credits', document.getElementById('credits').value);
//         // $.ajax({
//         //   type: "POST",
//         //   url: "api/courses/register",
//         //   enctype: 'multipart/form-data',
//         //   data: form_data,
//         //   processData: false,
//         //   contentType: false,
//         // }).done(function(response, status) {
//         //   console.log(response, status);
//         // });
//         let response = await fetch('api/courses/register', {
//         method: 'POST',
//         body: form_data
//       });
//       let result = await response;
//       console.log(result);
//     }
//     course_form.classList.add('was-validated');
// };
async  function fetch_studentdata()
{
    let response=await fetch('api/students/get_data',{
        method: 'POST',
        headers: {
            'Content-Type':'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            student_id:sessionStorage.getItem('id'),
        })
    });
    let student=await response.json();
    console.log(student);
    let response1=await fetch('api/students/get_courses',{
        method: 'POST',
        headers: {
            'Content-Type':'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            student_id:sessionStorage.getItem('id'),
        })
    });
    let courses=await response1.json();
    console.log(courses);
    buildTable1(student);

    function buildTable1(student) {
        function f1(id) {
            console.log(id);
        }

        var table = document.getElementById('Student_table')
        console.log(student.student_id);
        console.log(student.first_name);
        console.log(student.last_name);
        console.log(student.credits);


       // for (var i = 0; i <=student.length; i++) {
         //   console.log(student[i].student_id);
            //console.log(student[i].first_name);
            //console.log(student[i].last_name);
            //console.log(student[i].credits);

            var row = `<tr>
							<td>${student.student_id}</td>
							<td>${student.first_name}</td>
							<td>${student.last_name}</td>
							<td>${student.credits}</td>
							</tr>`
            table.innerHTML += row


       // }
    }
    buildTable2(courses);
    function buildTable2(courses) {
        function f2(id) {
            console.log(id);
        }

        var table = document.getElementById('Student_courses')

        for (var i = 0; i < courses.length; i++){
            var row = `<tr>
							<td>${courses[i].course_id}</td>
							<td>${courses[i].name}</td>
							<td>${courses[i].description}</td>
							<td>${courses[i].credits}</td>
							<td>${courses[i].course_code}</td>
                            
					  </tr>`
            table.innerHTML += row


        }

    }


    }
async function fetch_courses(){
    //console.log(sessionStorage.getItem('id'));
    let response = await fetch('api/courses/get', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            student_id:sessionStorage.getItem('id'),
        })
    });
    //let response = await fetch("api/courses/get");
    console.log(response);
    let courses = await response.json(); // read response body and parse as JSON
    //console.log(courses['description']);
    buildTable(courses);

    function buildTable(courses){
        function f1(id)
        {
            console.log(id);
        }
        var table = document.getElementById('myTable')

        for (var i = 0; i < courses.length; i++){
            var row = `<tr>
							<td>${courses[i].course_id}</td>
							<td>${courses[i].name}</td>
							<td>${courses[i].description}</td>
							<td>${courses[i].credits}</td>
							<td>${courses[i].course_code}</td>
                            <td><input type ="checkbox" id="i" name="c" value=${courses[i].course_id}
                              ></td>
					  </tr>`
            table.innerHTML += row


        }

    }
    document.getElementById('select').onclick = async function () {
        var checkboxes = document.getElementsByName('c');
        var c = 0;
        var cid = new Array();
        for (var checkbox of checkboxes) {
            if (checkbox.checked) {
                //document.body.append(checkbox.value + ' ');
                c++;
                //console.log(checkbox.value);
                cid.push(checkbox.value);

            }
        }
        // if(c<4)
        //     alert('Please select 4 courses');
        localStorage.setItem("cid", JSON.stringify(cid));
        var retrievedData = localStorage.getItem("cid");
        //console.log(retrievedData);
        /*var cid2 = JSON.parse(retrievedData);
        for(var i=0;i<cid2.length;i++)
            console.log(cid2[i])*/
        let response1 = await fetch('api/courses/register_course', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                student_id: sessionStorage.getItem('id'),
                cid:localStorage.getItem("cid"),
            })
        });


    }
    // let courses_option = document.getElementById('courses');
    // courses_option.innerHTML = '<option value=""> Choose...</option>';
    //
    // for(let i = 0 ; i<courses.length ; i++){
    //     courses_option.innerHTML += '<option value="'+courses[i]['description']+'">'+courses[i]['description']+'</option>';
    // }
}