var express = require('express');
var router = express.Router();
const boxController = require('../controllers/boxController');
const authController = require('../controllers/authController');

function isAuthenticated(req, res, next) {
    // do any checks you want to in here

    // CHECK THE USER STORED IN SESSION FOR A CUSTOM VARIABLE
    // you can do this however you want with whatever variables you set up
    if (req.session.authenticated)
        return next();

    // IF A USER ISN'T LOGGED IN, THEN REDIRECT THEM SOMEWHERE
    res.redirect('/');
}

router.post('/auth', authController.authenticate);
/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Verbbox Login' });
});

router.get('/box', isAuthenticated, boxController.getAll);
router.get('/play/:playId', isAuthenticated, boxController.getPlay);

module.exports = router;
