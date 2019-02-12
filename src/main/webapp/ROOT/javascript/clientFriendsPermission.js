/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var ws;

function connect(url, listId , userCookie) {
    ws = new WebSocket(url + userCookie + '/' +listId);

    ws.onopen = function (evt) {
        console.log("open");
    };

    ws.onmessage = function (evt) {
        console.log(JSON.parse(evt.data));
    };

    ws.onclose = function (evt) {
        console.log("close");
    };

    ws.onerror = function (evt) {
        console.log("big error");
    };

}

function writeMessage(txt) {
    ws.send(txt);
}

window.addEventListener('beforeunload', function (e) {
    ws.close();
});

