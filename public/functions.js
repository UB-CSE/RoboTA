
const socket = io.connect("http://localhost:8082", {transports: ['websocket']});

socket.on('messages', function (event) {
    document.getElementById("my_div").innerHTML = event;
});
