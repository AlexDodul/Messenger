'use strict';

const fs = require('fs');
const http = require('http');
const WebSocket = require('ws');
const index = fs.readFileSync('chat/chat.html', 'utf8');
const css = fs.readFileSync('chat/chat.css', 'utf8');
const js = fs.readFileSync('chat/chat.js', 'utf8');

const server = http.createServer((req, res) => {
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
        default: res.writeHead(404);
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
            console.log('before send')
            client.send(message);
        }
    });
    connection.on('close', () => {
        console.log(`Disconnected ${ip}`);
    });
});

