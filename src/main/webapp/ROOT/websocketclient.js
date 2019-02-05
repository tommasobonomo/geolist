
var ws = new WebSocket("ws://localhost:8084/chat/1/2");

ws.onopen = function (evt) {

};

ws.onmessage = function (evt) {
    console.log(evt.data);
};

ws.onclose = function (evt) {
    console.log("close");
};

ws.onerror = function (evt) {
    console.log("big error");
};

function writeMessage(txt){
    ws.send(txt);
}

window.addEventListener('beforeunload', function (e) {
  ws.close()
});
