let login_form = document.getElementById('login-validation');

login_form.addEventListener('submit', async (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (login_form.checkValidity() === true) {
        document.getElementById("submit-button").style.display = "none";
        document.getElementById("spinner-button").style.display = "block";
        let response = await fetch('api/students/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                email: document.getElementById('email').value,
            })
        });
        try{
            let result = await response.json();
            // console.log(result);
            //console.log(response);
       // if(result['status'] === 200){
         //   let data = response.json();
            document.getElementById("submit-button").style.display = "block";
            document.getElementById("spinner-button").style.display = "none";

            sessionStorage.setItem('id', result["student_id"]);
            //console.log(data);
            var st_id=sessionStorage.getItem('id');
           // console.log(st_id);
            //System.Out.println()
            location.href = "dashboard.html";

        }
        catch(err){
            document.getElementById("submit-button").style.display = "block";
            document.getElementById("spinner-button").style.display = "none";

            document.getElementById("login-alert").style.display = "block";
        }
    }
});