const Box = require('../models/Box').Box;
const Request = require("request");

module.exports = {
    getAll: (req, res) => {
        Request.get({
            "headers": {
                "content-type": "application/json",
                "Authorization": req.session.authrorizationHeader
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
                "Authorization": req.session.authrorizationHeader
            },
            "url": "http://localhost:8080/vb/api/play/" + playId
        }, (error, response, body) => {
            let r = JSON.parse(body);
            let indexList = [];
            let concatHelper = function(id, s, t) {
                Array(t).fill().map(()=>{
                    indexList = indexList.concat({
                        "i": id,
                        "s": s
                    })
                });
            };

            let shuffle = function shuffle(array) {
                let currentIndex = array.length, temporaryValue, randomIndex;
                while (0 !== currentIndex) {
                    randomIndex = Math.floor(Math.random() * currentIndex);
                    currentIndex -= 1;

                    temporaryValue = array[currentIndex];
                    array[currentIndex] = array[randomIndex];
                    array[randomIndex] = temporaryValue;
                }

                return array;
            };

            for (let i = 0; i < r.verbPlayList.length; ++i) {
                if (r.verbPlayList[i].progressBack < 34) {
                    concatHelper(i, "f", 4);
                } else if (r.verbPlayList[i].progressBack >= 34 && r.verbPlayList[i].progressBack < 67) {
                    concatHelper(i, "f", 3);
                } else {
                    // assumption >= 67
                    concatHelper(i, "f", 2);
                }
            }

            for (let i = 0; i < r.verbPlayList.length; ++i) {
                if (r.verbPlayList[i].progressFront < 34) {
                    concatHelper(i, "b", 4);
                } else if (r.verbPlayList[i].progressFront >= 34 && r.verbPlayList[i].progressFront < 67) {
                    concatHelper(i, "b", 3);
                } else {
                    // assumption >= 67
                    concatHelper(i, "b", 2);
                }
            }
            let sIndexList = shuffle(indexList);
            console.info(sIndexList);
            res.render('play', { play: r, body: body, playIndexes: JSON.stringify(sIndexList)});
        });
    }
};