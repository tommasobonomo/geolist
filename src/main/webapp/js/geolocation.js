let latestLat = 0;
let latestLng = 0;

const geoSuccess = position => {
    $("#geoerror").hide();
    
    // aggiungo precisione per evitare chiamate in più
    const eps_precision = 0.0005;
    
    if (
            Math.abs(position.coords.latitude - latestLat) > eps_precision ||
            Math.abs(position.coords.longitude - latestLng) > eps_precision
        ) {
        latestLat = position.coords.latitude;
        latestLng = position.coords.longitude;
        $.ajax({
            url: "/Geolocation",
            data: {
                latitude: position.coords.latitude,
                longitude: position.coords.longitude
            },
            success: result => {
                console.log(result);
            }
        })
    }
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