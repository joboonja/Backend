function loadXMLDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let projects = JSON.parse(this.responseText);
            let projectsText = "<tr><th>id</th><th>title</th><th>budget</th> <th>description</th><th>skills</th></tr>";
            for (let i = 0; i < projects.length; i++) {
                let skills = Object.keys(projects[i].skills);
                let skill = "";
                for (let j = 0; j < skills; j) {
                    skill += skills[j] + " ";
                }
                projectsText += "<tr>";
                projectsText += "<td>" + projects[i].id.toString() + "</td>";
                projectsText += "<td>" + projects[i].title + "</td>";
                projectsText += "<td>" + projects[i].budget + "</td>";
                projectsText += "<td>" + projects[i].description + "</td>";
                projectsText += "<td>" + skills + "</td>";
                projectsText += "</tr>";
            }
            document.getElementById("projects").innerHTML =
                projectsText;

        }
    };
    xhttp.open("GET", "http://localhost:8080/projects", true);
    xhttp.send();
}