/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var ws;

function connect(url, userCookie) {
    ws = new WebSocket(url + userCookie);

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

function writeMessage(txt, itemid, listid) {
    ws.send(txt);
    console.log(txt[0]);
    if (txt[0] === '-')
        decrementValue('counter' + itemid + ' ' + listid);
    else if (txt[0] === '+')
        incrementValue('counter' + itemid + ' ' + listid);

}

window.addEventListener('beforeunload', function (e) {
    ws.close();
});

function incrementValue(id)
{
    var value = parseInt(document.getElementById(id).innerHTML, 10);
    value = isNaN(value) ? 0 : value;
    value++;
    document.getElementById(id).innerHTML = value;
}

function decrementValue(id)
{
    var value = parseInt(document.getElementById(id).innerHTML, 10);
    value = isNaN(value) ? 0 : value;
    if (value > 1)
        value--;
    document.getElementById(id).innerHTML = value;
}