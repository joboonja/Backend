function loadXMLDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let skill = JSON.parse(this.responseText);
            let response = "<ul>";
            for(let i = 0 ; i < skill.length ; i++)
            {
                response += "<li>" + skill[i].points + " , " +  skill[i].name + "</li>";
            }
            response += "</ul>"
            document.getElementById("newSkill").innerHTML =
                response;
        }
        else if(this.readyState === 4 && this.status !== 200)
        {
            let response = JSON.parse(this.responseText);
            alert(response.message);
        }
    };

    const params ="skillName=" + document.querySelector('#skillName').value;


    xhttp.open("POST", "http://localhost:8080/skills", true);
    xhttp.setRequestHeader("Content-type",  "application/x-www-form-urlencoded");
    xhttp.send(params);
}