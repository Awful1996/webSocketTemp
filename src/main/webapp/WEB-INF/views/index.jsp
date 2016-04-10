<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Chat</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="../resource/js/jquery-1.10.2.min.js"></script>

    <!-- Le styles -->
    <link href="../resource/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
            overflow-y: hidden;
        }

        .form-signin {
            max-width: 300px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-signin .form-signin-heading,.form-signin .checkbox {
            margin-bottom: 10px;
        }

        .form-signin input[type="text"],.form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }

        #chatroom {
            font-size: 16px;
            height: 40px;
            line-height: 40px;
            width: 300px;
        }

        .received {
            width: 160px;
            font-size: 10px;
        }

        .image {
            width: 110px;
        }

        .layer {
            overflow-y: scroll; /* Добавляем полосы прокрутки */
            height: 310px; /* Высота блока */
            padding: 5px; /* Поля вокруг текста */
        }
    </style>
    <link href="../resource/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="./resource/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="./resource/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="./resource/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="./resource/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed"
          href="./resource/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="./resource/ico/favicon.png">
    <script>
        var wsocket;
        var serviceLocation = "ws://localhost:8087/chat/";
        var $nickName;
        var $message;
        var $chatWindow;
        var room = '';

        function onMessageReceived(evt) {
            var msg = JSON.parse(evt.data); // native API
            for (var i in msg) {
                var $messageLine = $('<tr><td class="received">' + msg[i].received
                        + '</td><td class="image"><img width="100" height="80" src="' + "data:image/png;base64, "+ msg[i].image + '"/>'
                        + '</td><td class="user label label-info">' + msg[i].sender
                        + '</td><td class="message badge">' + msg[i].message

                        + '</td></tr>');
                $chatWindow.append($messageLine);
            }
            document.getElementById('chatmessages').scrollTop = 9999;

        }
        function sendMessage() {
            var msg = '{"message":"' + $message.val() + '", "sender":"'
                    + $nickName.val() + '", "received":""}';
            wsocket.send(msg);
            $message.val('').focus();
        }

        function connectToChatserver() {
            room = $('#chatroom option:selected').val();
            wsocket = new WebSocket(serviceLocation + room);
            wsocket.onmessage = onMessageReceived;
        }

        function leaveRoom() {
            wsocket.close();
            $chatWindow.empty();
            $('.chat-wrapper').hide();
            $('.chat-signin').show();
            $nickName.focus();
        }

        $(document).ready(function() {
            $nickName = $('#nickname');
            $message = $('#message');
            $chatWindow = $('#response');
            $('.chat-wrapper').hide();
            $nickName.focus();

            $('#enterRoom').click(function(evt) {
                evt.preventDefault();
                connectToChatserver();
                $('.chat-wrapper h2').text('Chat # '+$nickName.val() + "@" + room);
                $('.chat-signin').hide();
                $('.chat-wrapper').show();
                $message.focus();
            });
            $('#do-chat').submit(function(evt) {
                evt.preventDefault();
                sendMessage()
            });

            $('#leave-room').click(function(){
                leaveRoom();
            });
        });
    </script>
</head>

<body>

<div class="container chat-signin">
    <form class="form-signin">
        <h2 class="form-signin-heading">Chat sign in</h2>
        <label for="nickname">Nickname</label> <input type="text"
                                                      class="input-block-level" placeholder="Nickname" id="nickname">
        <div class="btn-group">
            <label for="chatroom">Chatroom</label> <select size="1"
                                                           id="chatroom">
            <option>arduino</option>
            <option>java</option>
            <option>groovy</option>
            <option>scala</option>
        </select>
        </div>
        <button class="btn btn-large btn-primary" type="submit"
                id="enterRoom">Sign in</button>
    </form>
</div>
<!-- /container -->

<div class="container chat-wrapper">
    <form id="do-chat">
        <h2 class="alert alert-success"></h2>
        <div class="layer" id="chatmessages">
            <table id="response" class="table table-bordered"></table>
        </div>
        <fieldset>
            <legend>Enter your message..</legend>
            <div class="controls">
                <input type="text" class="input-block-level" placeholder="Your message..." id="message" style="height:60px"/>
                <input type="submit" class="btn btn-large btn-block btn-primary"
                       value="Send message" />
                <button class="btn btn-large btn-block" type="button" id="leave-room">Leave
                    room</button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>