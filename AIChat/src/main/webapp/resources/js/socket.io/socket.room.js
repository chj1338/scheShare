var fs = require('fs');

var server = require('http').createServer();

server.listen(8000, function(){
	console.log('Server Running at http://localhost:8000');
});

server.on('request', function(req, res){ 
	/*test*/console.log('request Call');
	
	fs.readFile( 'catting.html', function(error, data){
		res.writeHead( 200, { 'Content-Type' : 'text/html' } );
		res.end( data );
	});
});

var io = require('socket.io').listen(server);

io.sockets.on( 'connection', function(socket){
	socket.on( 'join', function(data){
		/*test*/console.log(data) // data : 사용자가 입력한 방이름
		socket.join(data); //사용자가 입력한 방에 socket을 참여시킨다.
		socket.room = data; //'room' 속성에 사용자가 입력한 방이름을 저장한다.
	});
	
	socket.on( 'message', function(data){
		//'room' 속성값에 해당하는 방에 참여중인 Client에 메세지를 보낸다.
		console.log( 'id : %s, msg : %s, date : %s', data.id, data.message, data.date );
		io.sockets.in( socket.room ).emit('message', data); //public 통신 : io.sockets.emit(...);
	});
});