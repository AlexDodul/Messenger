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
const usr = document.getElementById('users');
const chat = document.getElementById('chat');
const msg = document.getElementById('message');
const btn = document.getElementById('btn');

msg.focus();

const usersNickName = nickname => {
    const line = document.createElement('li');
    line.innerHTML = `<img src="https://www.meme-arsenal.com/memes/755658588d31fbf527a72b152150e4fa.jpg" alt="">
                <div>
                    <h2>${nickname}</h2>
                    <h3>
                        <span class="status green"></span>
                        online
                    </h3>
                </div>`;
    usr.appendChild(line);
};

const writeLineNickName = nickname => {
    const line = document.createElement('div');
    line.innerHTML = `<p>${nickname}</p>`;
    line.setAttribute("class", "nickName");
    chat.appendChild(line);
};

const writeLineTime = time => {
    time = new Date().toLocaleString('en-US', {timeZone: 'Europe/Kiev'});
    const line = document.createElement('div');
    line.setAttribute("class", "times");
    line.innerHTML = `<p>${time}</p>`;
    chat.appendChild(line);
};

const writeLine = text => {
    const line = document.createElement('div');
    line.setAttribute("class", "messageText");
    line.innerHTML = `<p>${text}</p>`;
    chat.appendChild(line);
};

socket.onopen = () => {
    writeLine('<div class="connect">Connected</div>');

    let payload2 = {
        login: 'Tura',
        firstName: 'Vasia',
        lastName: 'Pupcin',
        expireIn: new Date(),
        createdAt: new Date(),
    }
    usersNickName(payload2.login);
    let envelope = {
        topic: 'auth',
        payload: JSON.stringify(payload2)
    };
    socket.send(JSON.stringify(envelope));
};

socket.onclose = () => {
    writeLine('<div class="close">Closed</div>');
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
    if (message) {
        let payload = JSON.parse(message.payload);
        if (payload && payload.text) {
            writeLineNickName(payload.nickname);
            writeLineTime(payload.time);
            writeLine(payload.text);
        }
    }
};

