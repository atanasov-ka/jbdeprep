var createError = require('http-errors');
var express = require('express');
var expressSession = require('express-session');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require("body-parser");
var indexRouter = require('./routes/index');

var app = express();
app.use(expressSession(
    {
        secret: "IamL3g10n!",
        name: "VERBBOX_SESSION",
        //store: sessionStore, // connect-mongo session store
        proxy: true,
        resave: true,
        saveUninitialized: true
    }
));
app.use(bodyParser.urlencoded({
    extended: true
}));


// view engine setup
app.set('views', path.join(__dirname, 'views'));
var hbs = require('hbs');
hbs.registerHelper('dateFormat', require('handlebars-dateformat'));
app.set('view engine', 'hbs');

app.set("env", "development");

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

app.use('/images',express.static(path.join(__dirname, 'public/images')));
app.use('/js',express.static(path.join(__dirname, 'public/js')));
app.use('/css',express.static(path.join(__dirname, 'public/css')));
app.use('/', indexRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
