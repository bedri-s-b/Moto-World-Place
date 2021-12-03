$('#loadAllProducts').click(() =>{
    readAllProducts()
});

$('#loadAllEvent').click(() =>{
    readAllEvents()
})

$('#loadAllUser').click(() =>{
    readAllUsers()
})

function readAllUsers(){
    $("#user-container").empty()

    fetch("http://localhost:8000/admin/all/users")
        .then(response => response.json())
        .then(json => json.forEach(user =>{
            let result =
                '<tr>' +
                '<td>' + user.id + '</td>' +
                '<td>' + user.username + '</td>' +
                '<td>' + user.age + '</td>' +
                '<td>' + user.city + '</td>' +
                '<td>' + user.role + '</td>' +
                '</tr>'
            $("#user-container").append(result);
        }));
}

function readAllEvents(){
    $("#events-container").empty()

    fetch("http://localhost:8000/admin/all/events")
        .then(response => response.json())
        .then(json => json.forEach(events =>{
            let result =
                '<tr>' +
                '<td>' + events.id + '</td>' +
                '<td>' + events.title + '</td>' +
                '<td>' + events.description + '</td>' +
                '<td>' + events.started + '</td>' +
                '<td>' + events.creator + '</td>' +
                '</td>' +
                '</tr>'
            $("#events-container").append(result);
        }));
}

function  readAllProducts(){
    $("#products-container").empty()

    fetch("http://localhost:8000/admin/all/products")
        .then(response => response.json())
        .then(json => json.forEach(products =>{
            let result =
                '<tr>' +
                '<td>' + products.id + '</td>' +
                '<td>' + products.brand + '</td>' +
                '<td>' + products.type + '</td>' +
                '<td>' + products.powerHp + '</td>' +
                '<td>' + products.kilometers + '</td>' +
                '<td>' + products.price + '</td>' +
                '<td>' + products.year + '</td>' +
                '<td>' + products.phoneNumber + '</td>' +
                '<td>' + products.seller + '</td>' +
                '<td>' + products.description + '</td>' +
                '<td>' + products.created + '</td>' +
                '<td>' +
                '</td>' +
                '</tr>'
            $("#products-container").append(result);
        }));
}

