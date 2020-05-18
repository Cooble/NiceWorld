<?php
//call only one time per login
if(!isset($_SESSION["first_time"])){
    $_GET["current_feed_friends"]=1;
    $_GET["open_feed"]=1;
    $_SESSION["first_time"]=1;
}

//print("error: ". (isset($_SESSION["error_message"])?$_SESSION["error_message"]:""));
if(isset($_GET["open_feed"]))
{
    if(isset($_GET["current_feed_friends"])){
        $_SESSION["current_feed_personal"]=false;
        $_SESSION["current_feed_conversation"] = false;
        $_SESSION["friend_feed"]=getFriendsFeed($_SESSION["userid"]);
        $_SESSION["current_feed_name"]="Feed of your friends";
    }else {
        unset($_SESSION["friend_feed"]);
        $friendFeedid = $_GET["open_feed"];
        $_SESSION["current_feed_personal"] = isset($_GET["current_feed_personal"]);

        $_SESSION["current_feed_conversation"] = false;
        $_SESSION["current_feed"] = getPersonalFeedId($friendFeedid);
        if ($_SESSION["current_feed_personal"])
            $_SESSION["current_feed_name"] = "Your feed";
        else
            $_SESSION["current_feed_name"] = "Feed of " . getUserInfo($friendFeedid)["name"];
        //header("Refresh:0; url=index.php");
    }
}
if(isset($_GET["open_conversation"]))
{
    $friendFeedid = $_GET["open_conversation"];
    unset($_SESSION["friend_feed"]);
    $_SESSION["current_feed_personal"]=false;
    $_SESSION["current_feed_conversation"]=true;
    $_SESSION["current_feed"]=getConversationFeedId($_SESSION["userid"],$friendFeedid);
    $_SESSION["current_feed_name"] = "Conversation with ".getUserInfo($friendFeedid)["name"];

    header("Refresh:0; url=index.php");
}
if(isset($_GET["exitgroup"]))
{
    unset($_SESSION["friend_feed"]);
    unset($_SESSION["current_feed"]);
}
if (isset($_GET["addlike"])) {
    $messid = $_GET["messid"];
    if(getMessageInfo($messid)["owner"]!= $_SESSION["userid"])
        likeMessage($messid, $_SESSION["userid"]);
    //header("Refresh:0; url=index.php");
}
if (isset($_GET["removelike"])) {
    $messid = $_GET["messid"];

    unlikeMessage($messid, $_SESSION["userid"]);
   // header("Refresh:0; url=index.php");
}
if(isset($_POST["feed"]))
{
    $message = $_POST["feed"];
    addChatMessage( $_SESSION["current_feed"], $_SESSION["userid"],$message);
   // header("Refresh:0; url=index.php");
}


?>
<div class="row">
    <div class="col-md-3">
        <div class="boxik">
            <?php
            if (isset($_SESSION["current_feed"]) or isset($_SESSION["friend_feed"])) {
                print("<div id='chat_group_title'> " . $_SESSION["current_feed_name"] . "</div>");
                print("<a href='?exitgroup=1'>Back</a>");
            }
            $friends = getFriends($_SESSION["userid"],10);
            echo("<div id='chat_group_title'>Your friends feeds:</div>");

            for ($i = 0;$i<sizeof($friends);$i++){
                $id =  $friends[$i]["id"];
                $name = $friends[$i]["name"];
                print("<a href='?open_feed=$id'>$name</a> <a href='?open_conversation=$id'>&nbsp (Chat)</a>");
                print("<br>");
            }
            print("<br>");
            print("<a href='?open_feed=".$_SESSION["userid"]."&current_feed_personal=1'>Open Personal feed</a><br>");
            print("<a href='?open_feed=".$_SESSION["userid"]."&current_feed_friends=1'>Feed of your friends</a>");
            ?>
        </div>
    </div>
    <div class="col-md-7">
        <?php
        if (isset($_SESSION["current_feed"]) or isset($_SESSION["friend_feed"])) {
            if(isset($_SESSION["friend_feed"]))
                $messages=$_SESSION["friend_feed"];
            else
                $messages = getChatMessages($_SESSION["current_feed"]);

            echo("<br><div class ='scrollable' id='chat_feed' onload='scrollDown()'>");
            $sizeOfMessages = $messages ? sizeof($messages) : 0;
            if (!$sizeOfMessages) {
                print("
        <div class='chat_message'>
            <span class = 'chat_username'>@BOT</span><br>&nbsp There are no messages!<br>
        </div>
        "
                );
            }
            for ($i = 0; $i < $sizeOfMessages; $i++) {
                $id = $messages[$i]["userid"];
                $username = getUserInfo($id)["name"];
                $mess = $messages[$i]["message"];
                $messid = $messages[$i]["id"];
                $da = $messages[$i]["dated"];
                $stringu = "heeee";
                $likeNumber = getMessageLikes($messid);
                $isLiking = isLiking($messid, $_SESSION["userid"]);
                $classi = $isLiking ? "like_button" : "like_button_blank";
                $getcom = $isLiking ? "removelike=1" : "addlike=1";
                $likeText = " <span class='likebox'>
                    <button class='$classi' onclick='window.location.href=\"?messid=$messid&$getcom\";'></button>
                    </span>";
                if ($id == $_SESSION["userid"] or $_SESSION["current_feed_conversation"])
                    $likeText = "";
                print("
        <div class='chat_message'>
            <span class = 'chat_username'>@$username($id)</span> <span class = 'chat_time'>[$da]</span>  <span class = 'chat_time'>&nbsp ($likeNumber likes)</span>
             $likeText
            <br>&nbsp $mess

        </div>
        "
                );
            }
            echo("</div>");
            $butonText = $_SESSION["current_feed_personal"]?"Add feed":"Send";
            if($_SESSION["current_feed_conversation"] or $_SESSION["current_feed_personal"]) {
                print("
                    <form method='post' id='send_message'>
                    <input type='text' name='feed' autofocus style='width: 100%' placeholder='message'>
                    <input type='submit' value='$butonText' name='feed_btn' class ='login_button' style='width: 100%'>
                    </form>
                    "
                );
            }
        }
        else {
            $friends = getFriends($_SESSION["userid"],10);
            echo("<br><div id='chat_group_title'>Your friends feeds:</div>");
            for ($i = 0;$i<sizeof($friends);$i++){
                $id =  $friends[$i]["id"];
                $name = $friends[$i]["name"];
                print("<a href='?open_feed=$id'>$name</a>&nbsp <a href='?open_conversation=$id'>(Chat)</a>");
                print("<br>");
            }
            print("<br>");
            print("<a href='?open_feed=".$_SESSION["userid"]."&current_feed_friends=1'>Feed of your friends</a><br>");
            print("<a href='?open_feed=".$_SESSION["userid"]."&current_feed_personal=1'>Open Personal feed</a>");
        }
        ?>
    </div>
    <div class="col-md-2"></div>
</div>

<script language='javascript'>

    function scrollDown () {
        var element = document.getElementById("chat_feed");
        element.scrollTop = element.scrollHeight;
    }
    window.onload= scrollDown;
</script>

