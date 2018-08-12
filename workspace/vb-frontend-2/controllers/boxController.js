const Box = require('../models/Box').Box;
const Request = require("request");

module.exports = {
    getAll: (req, res) => {
        Request.get({
            "headers": {
                "content-type": "application/json",
                "Authorization": "Basic YWRtaW46MTIz"
            },
            "url": "http://localhost:8080/vb/api/box"
        }, (error, response, body) => {
            let r = JSON.parse(body);

            res.render('box', { boxList: r });
        });
    },
    getPlay: (req, res) => {
        const playId = req.params.playId;
        Request.get({
            "headers": {
                "content-type": "application/json",
                "Authorization": "Basic YWRtaW46MTIz"
            },
            "url": "http://localhost:8080/vb/api/play/" + playId
        }, (error, response, body) => {
            let r = JSON.parse(body);
            console.info(r);
            res.render('play', { play: r, body: body });
        });
    }
};