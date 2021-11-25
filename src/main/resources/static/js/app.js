$('#products').onclick(() =>{
    reloadProducts()
});

function  reloadProducts(){

    fetch("http://localhost:8000/products/all/rest")
        .then(response => response.json())
        .then(json => json.forEach(product =>{
            let result = '<p>' + product.id +'</p>';

            $("#parent-div-products").append(result);
        }))
}