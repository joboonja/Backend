function loadXMLDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let skill = JSON.parse(this.responseText);
            document.getElementById("newSkill").innerHTML =
                skill;
            alert(skill);
        }
    }
    const params = {
        skillName: document.querySelector('#skillName').value
    }
    xhttp.open("POST", "http://localhost:8080/skills", true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    xhttp.send(JSON.stringify(params));
}