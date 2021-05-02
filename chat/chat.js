// const CHAR_RETURN = 13;
//
//
// const socket = new WebSocket('ws://127.0.0.1:8000/chat');
// const chat = document.getElementById('chat');
// const msg = document.getElementById('message');
// msg.focus();
//
// const writeLine = text => {
//     const line = document.createElement('div');
//     line.innerHTML = `<p>${text}</p>`;
//     chat.appendChild(line);
// };
//
// socket.onopen = () => {
//     writeLine('connected');
// };
//
// socket.onclose = () => {
//     writeLine('closed');
// };
//
// socket.onmessage = event => {
//     writeLine(event.data);
// };
//
// msg.addEventListener('keydown', event => {
//     if (event.keyCode === CHAR_RETURN) {
//         const s = msg.value;
//         msg.value = '';
//         writeLine(s);
//         socket.send(s);
//     }
// });
const CHAR_RETURN = 13;

const socket = new WebSocket('ws://127.0.0.1:8000/chat');
const chat = document.getElementById('chat');
const msg = document.getElementById('message');
const btn = document.getElementById('btn');


msg.focus();

const writeLine = text => {
    const line = document.createElement('div');
    line.innerHTML = `<p>${text}</p>`;
    chat.appendChild(line);
};

socket.onopen = () => {
    writeLine('connected');
    let payload2 = {
        login: 'Tura',
        firstName: 'Vasia',
        lastName: 'Pupcin',
        expireIn: new Date(),
        createdAt: new Date(),
    }
    let envelope = {
        topic: 'auth',
        payload: JSON.stringify(payload2)
    };
    socket.send(JSON.stringify(envelope));
};

socket.onclose = () => {
    writeLine('closed');
};

btn.onclick = () => {
    const s = msg.value;
    msg.value = '';
    let payload2 = {
        nickname: 'nameMI',
        time: new Date(),
        text: s
    }

    let envelope = {
        topic: 'messages',
        payload: JSON.stringify(payload2)

    };
    socket.send(JSON.stringify(envelope));
}

msg.addEventListener('keydown', event => {
    if (event.keyCode === CHAR_RETURN) {
        const s = msg.value;
        msg.value = '';
        let payload2 = {
            nickname: 'nameMI',
            time: new Date(),
            text: s
        }

        let envelope = {
            topic: 'messages',
            payload: JSON.stringify(payload2)

        };
        socket.send(JSON.stringify(envelope));
    }
});

socket.onmessage = function (event) {
    let message = JSON.parse(event.data);
    if (message){
        writeLine(message);
    }

};
