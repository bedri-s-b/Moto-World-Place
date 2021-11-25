$('#loadMessages').onload(() =>{
    readMessages()
});

function  readMessages(){
    $("#mainLi").empty()

    fetch("http://localhost:8000/users/messages/rest/all")
        .then(response => response.json())
        .then(json => json.forEach(message =>{
            let dive =


            $("#mainLi").append(dive);
        }));
}