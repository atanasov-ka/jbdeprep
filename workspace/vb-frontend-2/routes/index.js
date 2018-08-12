var express = require('express');
var router = express.Router();
const boxController = require('../controllers/boxController');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/box', boxController.getAll);
router.get('/play/:playId', boxController.getPlay);

module.exports = router;
