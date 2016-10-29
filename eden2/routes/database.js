var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var PythonShell = require('python-shell');

var pyoptions = {
    mode: 'text',
    pythonPath: '/usr/bin/python',
    scriptPath: '/home/force/CodeForGood'
}

var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "cooper",
    database: "CooperCares"
});

con.connect(function(err){
  if(err){
    console.log('Error connecting to Db');
    return;
  }
  console.log('Connection established');
});

/* Handle requests to database */
router.get('/', function(req, res, next) {
    res.send('hello world!');
});

router.get('/GET/Users', function(req, res, next) {
    con.query('SELECT * FROM Users',function(err,rows){
      if(err) throw err;

      console.log('Data received from Db:\n');
      res.send(rows);
    });
});

router.get('/GET/Children', function(req, res, next) {
    con.query('SELECT * FROM Children',function(err,rows){
      if(err) throw err;

      console.log('Data received from Db:\n');
      res.send(rows);
    });
});

router.post('/POST/Recording', function(req, res, next) {
    var pyshell = new PythonShell('autismtrain.py', pyoptions);
    pyshell.send('test2.wav');
    pyshell.on('message', function (message) {
        console.log("MESSAGE: " + message); 
    });

    pyshell.end(function (err) {
        if (err) throw err;
        console.log('done with pyshell');
    });
    res.send('hello world');
});

module.exports = router;
