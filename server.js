'use strict';

const fs = require('fs');
const http = require('http');
const WebSocket = require('ws');
const index = fs.readFileSync('chat/chat.html', 'utf8');
const css = fs.readFileSync('chat/chat.css', 'utf8');
const js = fs.readFileSync('chat/chat.js', 'utf8');
const reg = fs.readFileSync('chat/web/toregister.html', 'utf8');
const auth = fs.readFileSync('chat/web/tologin.html', 'utf8');
const cssWeb = fs.readFileSync('chat/web/style.css', 'utf8');

const doGet = function (req, res) {
    switch (req.url) {
        case '/chat':
            res.writeHead(200);
            res.end(index);
            break;
        case '/chat/style':
            res.writeHead(200);
            res.end(css);
            break;
        case '/chat/script':
            res.writeHead(200);
            res.end(js);
            break;
        case '/reg' :
            res.writeHead(200);
            res.end(reg);
            break;
        case '/reg/style':
            res.writeHead(200);
            res.end(cssWeb);
            break
        case '/auth' :
            res.writeHead(200);
            res.end(auth);
            break;
        case '/auth/style':
            res.writeHead(200);
            res.end(cssWeb);
            break
        default:
            res.writeHead(404);
            res.end();
    }
}

const doPost = function (req, res) {
    switch (req.url) {
        case  '/reg' :
            let body = JSON.parse(req.body);
            axios.post('http://localhost:8080/users/reg', {
                firstName: body.firstName,
                lastName: body.lastName,
                login: body.login,
                password: body.password,
                passwordConfirm: body.passwordConfirm,
                email: body.email,
                phone: body.phone
            })
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
            break;
        case  '/auth' :
            break;
        default:
            res.writeHead(404);
            res.end();
    }
}

const server = http.createServer((req, res) => {
    switch (req.method) {
        case  'GET' :
            doGet(req, res);
            break;
    }
});

server.listen(8000, () => {
    console.log('Listen port 8000');
});

const ws = new WebSocket.Server({server});

ws.on('connection', (connection, req) => {
    const ip = req.socket.remoteAddress;
    console.log(`Connected ${ip}`);
    connection.on('message', message => {
        console.log('Received: ' + message);
        for (const client of ws.clients) {
            if (client.readyState !== WebSocket.OPEN) continue;
            // if (client === connection) continue;
            //console.log('before send')
            client.send(message);
        }
    });
    connection.on('close', () => {
        console.log(`Disconnected ${ip}`);
    });
});

