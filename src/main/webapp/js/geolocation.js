const geoSuccess = position => {
    $("#geoerror").hide();
    $.ajax({
        url: "/Geolocation",
        data: {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
        },
        success: result => {
            console.log("Thank god it worked!");
            console.log(result);
        }
    })
}

const geoError = () => {
    $("#geoerror").show();
}

const geoOptions = {
  enableHighAccuracy: true, 
  maximumAge        : 30000, 
  timeout           : 27000
};
 
$(window).load(() => {
        if ("geolocation" in navigator) {
            navigator.geolocation.watchPosition(geoSuccess, geoError, geoOptions)
        } else {
            geoError();
        }
});