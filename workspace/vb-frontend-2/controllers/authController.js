const Request = require("request");
let Crypto = require('crypto-js');
// let backendHost = "localhost";//process.env.BACKEND_HOST;
// let backendPort = 8081;//process.env.BACKEND_PORT;
let backendHost = "backend";//process.env.BACKEND_HOST;
let backendPort = 8080;//process.env.BACKEND_PORT;
module.exports = {
    authenticate: (req, res) => {
        let userName = req.body.user.username;
        let plainPassword = req.body.user.password;
        let hashedPassword = Crypto.SHA256(plainPassword).toString();
        console.log("host: " + backendHost);
        console.log("port: " + backendPort);
        let authenticationObj = {
            "userName": userName,
            "hashedPassword": hashedPassword
        };
        Request.post({
            "headers": {
                "content-type": "application/json"
            },
            "url": "http://" + backendHost + ":" + backendPort +"/vb/api/users/authenticate",
            "json": authenticationObj
        }, function(error, response, body) {
            console.error("error: " + error);
            if (response.statusCode !== 200) {
                console.error("Error: " +  response.statusCode + ": " + response.statusMessage);
                req.session.user = null;
                res.render('error', {layout: false,
                    message: response.statusMessage,
                    code: response.statusCode,
                    stacktrace: response.stacktrace});
            } else {
                console.info("OK: " + body);
                req.session.user = {
                    name: userName,
                    pass: hashedPassword
                };

                let plainAuth = req.session.user.name + ":" + req.session.user.pass;
                let encoded = Buffer.from(plainAuth).toString('base64');
                console.log(encoded);
                req.session.authrorizationHeader = "Basic " + encoded;

                res.redirect('/box');
            }
        }.bind({username: userName, hashedPassword: hashedPassword}));
    }
};