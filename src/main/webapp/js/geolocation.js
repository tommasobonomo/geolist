let latestLat = 0;
let latestLng = 0;
let latestLogin = "false";
let loginHappened = true;

const geoSuccess = position => {
    $("#geoerror").hide();
    $("#globe").css("color","green");
    // aggiungo precisione per evitare chiamate in più
    const eps_precision = 0.00001;
    
    // Guardo se l'utente ha fatto login o no
    if ($("#logged").text() === latestLogin) {
        loginHappened = false
    } else {
        loginHappened = true
    }
    latestLogin = $("#logged").text();
    
    if (
            Math.abs(position.coords.latitude - latestLat) > eps_precision ||
            Math.abs(position.coords.longitude - latestLng) > eps_precision ||
            loginHappened
        ) {
        latestLat = position.coords.latitude;
        latestLng = position.coords.longitude;
        
        // Prendo le gli id delle categorie in testo e faccio il parsing in un 
        // array di id
        let parsed_categories = $("#listcategories").text().split(',').slice(0,-1);
        
        // Metto tutto in un Set in modo da evitare doppioni e lo riconverto a 
        // un array
        let set_categories = new Set(parsed_categories);
        let categories = Array.from(set_categories);
       
        $.ajax({
            type:"POST",
            url: "/Geolocation",
            dataType: 'json',
            data: {
                latitude: latestLat,
                longitude: latestLng,
                'categories': JSON.stringify(categories)
            },
            success: result => {
                displayResult(result);
            }
        })
    }
}

const geoError = () => {
    $("#geoerror").show();
    $("#globe").css("color","red");
}

const geoOptions = {
  enableHighAccuracy: true, 
  maximumAge        : 30000, 
  timeout           : 27000
};
 
const removebr = vicinity => {
    let index = vicinity.search("<br/>");
    return vicinity.substring(0,index) + vicinity.substring(index+5);
}

const displayResult = result => {
    let j = 0;
    let no_res = true;
    $(".georesults").empty();
    for (category in result) {
        $(`.catTitle${j}`).empty();
        let shops = result[category];
        if (shops !== undefined && shops !== null && shops.length > 0) {
            no_res = false;
            shops = shops.slice(0,5);
            $(".georesults").append(
                        `<div class="catTitle${j}"> <div class="display-4 font-15 py-4">${shops[0].catName}</div> </div>`
            );
            for (let i = 0; i < shops.length; i++) {
                let shop = shops[i];
                let vicinity = removebr(shop.vicinity);
                $(`.catTitle${j}`).append(`<p>${shop.title}, ${vicinity}<br/>Location: ${shop.location}, Distance: ${shop.distance}, <a href="https://www.google.com/maps/dir/?api=1&destination=${shop.location}" target="_blank">Directions</a></p>`);
            }
        }
        j++;
    }
    if (no_res) {
        $("#georesults").append('<div class="display-4 font-15 py-4 text-center">No shop found for your lists!</div>')
    }
}

$(window).load(() => {
        if ("geolocation" in navigator) {
            navigator.geolocation.watchPosition(geoSuccess, geoError, geoOptions)
        } else {
            geoError();
        }
});