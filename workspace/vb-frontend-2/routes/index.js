var express = require('express');
var router = express.Router();
const boxController = require('../controllers/boxController');
const authController = require('../controllers/authController');

function isAuthenticated(req, res, next) {
    // do any checks you want to in here

    // CHECK THE USER STORED IN SESSION FOR A CUSTOM VARIABLE
    // you can do this however you want with whatever variables you set up
    console.log("is authenticated: " + req.session.user);
    if (req.session.user && req.session.user !== null) {
        return next();
    } else {
        // IF A USER ISN'T LOGGED IN, THEN REDIRECT THEM SOMEWHERE
        res.redirect('/login');
    }
}

router.post('/auth', authController.authenticate);
router.get('/logout', function (req, res, next) {
    req.session.user = null;
    req.session.authorization = null;
    res.redirect("/");
});
router.get('/login', function (req, res, next) {
    res.render('login', {layout:false, title: 'Verbbox Login'});
});
router.get('/', isAuthenticated, function (req, res, next) {
    res.render('home', {title: 'Verbbox'});
});

router.get('/box', isAuthenticated, boxController.getAll);
router.get('/play/:playId', isAuthenticated, boxController.getPlay);
router.post('/play/:boxId/saveProgress', isAuthenticated, boxController.saveProgress);
module.exports = router;
