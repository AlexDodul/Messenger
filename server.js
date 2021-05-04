'use strict';

const fs = require('fs');
const http = require('http');
const WebSocket = require('ws');
const axios = require("axios");
const index = fs.readFileSync('chat/chat.html', 'utf8');
const css = fs.readFileSync('chat/chat.css', 'utf8');
const js = fs.readFileSync('chat/chat.js', 'utf8');
const reg = fs.readFileSync('chat/web/toregister.html', 'utf8');
const auth = fs.readFileSync('chat/web/tologin.html', 'utf8');
const cssWeb = fs.readFileSync('chat/web/style.css', 'utf8');

const checkToken = function (token){
    return true;
}

const doGet = function (req, res) {
    let url = req.url;
    if(url.indexOf('?') !== -1) {
        url = url.substr(0, url.indexOf('?'));
    }
    switch (url) {
        case '/chat':
            console.log('Chat get');
            var body = ''
            req.on('data', function(data) {
                body += data
            });
            req.on('end', function() {
                if(checkToken(body)) {
                    res.writeHead(200);
                    res.end(index);
                }
            });
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
    console.log('post');
    var body = ''
    req.on('data', function(data) {
        body += data
    });
    req.on('end', function() {
        let url = req.url;
        switch (url) {
            case  '/reg' :
                axios.post(
                    'http://localhost:8080/users/reg',
                    body,
                    {headers: {"Content-Type": "text/plain"}}
                )
                    .then(function (response) {
                        if(response.status === 200){
                            res.status = 200;
                            res.end();
                        } else {
                            res.status = 200;
                            res.end();
                        }
                    })
                    .catch(function (error) {
                    });
                break;
            case  '/auth' :
                console.log('auth post');
                axios.post(
                    'http://localhost:8080/users/auth',
                    body,
                    {headers: {"Content-Type": "text/plain"}}
                )
                    .then(function (response) {
                        if(response.status === 200){
                            res.writeHead(200);
                            console.log(response.data);
                            res.end(response.data);
                        } else {
                            res = response;
                            res.end();
                        }
                    })
                    .catch(function (error) {
                    });
                break;
            default:
                res.writeHead(404);
                res.end();
        }
    });
}

const server = http.createServer((req, res) => {

    console.log(req.method + ' ' + req.url);
    switch (req.method) {
        case  'GET' :
            doGet(req, res);
            break;
        case 'POST' :
            doPost(req,res);
            break;
        default: res.writeHead(404);
            res.end();
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

