<%-- 
    Document   : chat2
    Created on : 04-Feb-2019, 21:56:45
    Author     : Giorgio
--%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            var listid = '${listID}';
            var userid = '${userID}';
            var url = '${url}';
            var ws = new WebSocket(url + listid + "/" + userid);

            ws.onopen = function (evt) {

            };

            ws.onmessage = function (evt) {
                var json = JSON.parse(evt.data);
                addText(json.authorName + " " + json.text + " " + json.sendTime + "\n");
            };

            ws.onclose = function (evt) {
                console.log("close");
            };

            ws.onerror = function (evt) {
                console.log("big error");
            };

            function writeMessage(txt) {
                ws.send(txt);
            }

            window.addEventListener('beforeunload', function (e) {
                ws.close()
            });
        </script>
        <style>
            textarea {
                overflow-y: scroll;
                height: 300px;
                width: 500px
            }
        </style>
    </head>
    <body>

        <input name="text" id="textMessage" type="text" placeholder="write message ..."/>
        <button onclick="writeMessage(document.getElementById('textMessage').value)">Click me</button>
        <textarea id="chatbox"></textarea>
        
        <script>
        function addText(event) {
            document.getElementById("chatbox").value += event;
        }
        </script>
    </body>
</html>
