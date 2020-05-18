<?php
include("connect.php");
session_start();
if(!isset($_SESSION["wall"]))
    $_SESSION["wall"]="feed";
?>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <link rel="icon" href="icon.png">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <title>FB UltraLite</title>
<body>
<div class="container-fluid" style="padding: 0">
    <div id="head">
        <div class="row">
            <div class="col-md-4">
                <h1>FB uLite</h1>
            </div>
            <div class="col-md-4">
                <h1>
                    <?php
                    if (isset($_SESSION["big_title"]))
                        echo($_SESSION["big_title"]);
                    ?>
                </h1>
            </div>
            <?php
            if (!isset($_SESSION["user"]))
                include("login.php");
            else
                include("account_frame.php");
            ?>
        </div>
    </div>
    <div class="row" style="background-color: #000322">
        <div class="col-md-4 text-center">
            <?php
            if (isset($_SESSION["user"]))
                echo("<a class ='pointy' href='?wall=chats'>Chats</a>");
            ?>
        </div>
        <div class="col-md-4 text-center">
            <?php
            if (isset($_SESSION["user"]))
                echo("<a class ='pointy'  href='?wall=friends'>Friends</a>   ");
            ?>
        </div>
        <div class="col-md-4 text-center">
            <?php
            if (isset($_SESSION["user"]))
                echo("<a class ='pointy'  href='?wall=feed'>Feeds</a>   ");
            ?>
        </div>
    </div>
    <?php

    if (isset($_GET["logout"])) {

        session_destroy();
        session_start();
        $_SESSION["cookie_accept"] = true;
        header("Refresh:0; url=index.php");
    }
    if (isset($_GET["wall"])) {
        $_SESSION["wall"] = $_GET["wall"];
        header("Refresh:0; url=index.php");
    }
    if (!isset($_SESSION["user"])) {
        include("register.php");
    } else if (isset($_SESSION["wall"])) {
        switch ($_SESSION["wall"]) {
            case "friends":
                include("friends.php");
                break;
            case "chats":
                include("chats.php");
                break;
            case "feed":
                include("feed.php");
                break;
            default:

                break;
        }
    } else {

    }
    ?>
</div>
<footer>
    <?php
    if (!isset($_SESSION["cookie_accept"]))
        include("cookie.php");
    ?>

</footer>
</body>
</html>

