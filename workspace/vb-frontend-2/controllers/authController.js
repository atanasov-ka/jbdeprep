const Request = require("request");

module.exports = {
    authenticate: (req, res) => {
        Request.post({
            "headers": {
                "content-type": "application/json",
                //"Authorization": "Basic YWRtaW46MTIz"
            },
            "url": "http://localhost:8080/vb/api/users/authenticate"
        }, (error, response, body) => {
            let r = JSON.parse(body);
            // set session
            //res.render('box', { boxList: r });
        });
    }
};