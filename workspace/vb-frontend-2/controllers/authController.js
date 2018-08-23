const Request = require("request");
let Crypto = require('crypto-js');

module.exports = {
    authenticate: (req, res) => {
        let userName = req.body.user.username;
        let plainPassword = req.body.user.password;
        let hashedPassword = Crypto.SHA256(plainPassword).toString();

        let authenticationObj = {
            "userName": userName,
            "hashedPassword": hashedPassword
        };

        Request.post({
            "headers": {
                "content-type": "application/json"
            },
            "url": "http://localhost:8080/vb/api/users/authenticate",
            "json": authenticationObj
        }, function(error, response, body) {
            if (error) {
                console.error("Error: " +  error);
                req.session.user = null;
                // pass "username or passowrd are incorrect
                res.redirect('/');
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

                res.redirect('/');
            }
        }.bind({username: userName, hashedPassword: hashedPassword}));
    }
};