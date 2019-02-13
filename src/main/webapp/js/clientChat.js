var ws;

function connect(url, listid, userCookie, myUsername) {
    ws = new WebSocket(url + listid + "/" + userCookie);

    ws.onopen = function (evt) {

    };

    ws.onmessage = function (evt) {
        var json = JSON.parse(evt.data);
        console.log("|" + json.authorName + "|");
        console.log("|" + myUsername + "|");
        if (json.authorName === myUsername) {
            createOutMsg(json.authorName, json.text, json.sendTime);
        } else {
            createInMsg(json.authorName, json.text, json.sendTime);
        }
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

function createInMsg(authorName, text, sentTime) {
    var div = document.createElement("div");
    div.classList.add("incoming_msg");
    div.innerHTML = "<div class=\"received_msg\"> <div class=\"received_withd_msg\"><div class=\"authorName\">" + authorName + ":</div><p>" + text + "</p> <span class=\"time_date\"> " + sentTime + " </span> </div> </div>";

    document.getElementById("main").appendChild(div);
}

function createOutMsg(authorName, text, sentTime) {
    var div = document.createElement("div");
    div.classList.add("outgoing_msg");
    div.innerHTML = "<div class=\"outgoing_msg_img\"> <div class=\"sent_msg\"> <p>" + text + "</p> <span class=\"time_date\"> " + sentTime + " </span> </div> </div>";

    document.getElementById("main").appendChild(div);
}