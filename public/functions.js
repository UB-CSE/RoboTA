
const socket = io.connect();

socket.on('messages', function (event) {
    document.getElementById("my_div").innerHTML = event;
});
