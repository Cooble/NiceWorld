<?php

//print("error: ". (isset($_SESSION["error_message"])?$_SESSION["error_message"]:""));
if (isset($_GET["open_chat"])) {
    $id = $_GET["open_chat"];

    $_SESSION["current_group"] = $id;


    $_SESSION["current_group_info"] = getChatInfo($id);
    $_SESSION["current_group_name"] = $_SESSION["current_group_info"]["name"];
    $_SESSION["current_group_owner"] = getUserInfo($_SESSION["current_group_info"]["owner"])["name"];
    header("Refresh:0; url=index.php");
}
if (isset($_GET["addlike"])) {
    $messid = $_GET["messid"];
    likeMessage($messid, $_SESSION["userid"]);
    header("Refresh:0; url=index.php");
}
if (isset($_GET["removelike"])) {
    $messid = $_GET["messid"];
    unlikeMessage($messid, $_SESSION["userid"]);
    header("Refresh:0; url=index.php");
}
if (isset($_GET["exitgroup"])) {
    unset($_SESSION["current_group"]);
}
if (isset($_GET["destroy_g"])) {
    $isAdmin = $_SESSION["current_group_info"]["owner"] == $_SESSION["userid"];
    if($isAdmin){
        destroyGroupChat($_SESSION["current_group"]);
        unset($_SESSION["current_group"]);
        header("Refresh:0; url=index.php");
    }

}
if (isset($_GET["add_chat_member"])) {
    $id = $_GET["add_chat_member"];
    addMemberToGroup($_SESSION["current_group"], $id);
    header("Refresh:0; url=index.php");
}
if (isset($_GET["kickout"])) {
    $id = $_GET["kickout"];
    removeMemberFromGroup($_SESSION["current_group"], $id);
    header("Refresh:0; url=index.php");
}
if (isset($_POST["create_group_chat"])) {
    $groupname = $_POST["create_group_chat"];
    $groupId = createGroupChat($groupname, $_SESSION["userid"]);
    $_SESSION["current_group"] = $groupId;
    $_SESSION["current_group_name"] = $groupname;
    $_SESSION["current_group_info"] = getChatInfo($groupId);
    $_SESSION["current_group_owner"] = getUserInfo($_SESSION["current_group_info"]["owner"])["name"];
}
if (isset($_POST["message"])) {
    $message = $_POST["message"];
    if ($message != "") {

        $_SESSION["error_message"] = "posting message" . $message . " group: " . $_SESSION["current_group"] . " userid " . $_SESSION["userid"];
        addChatMessage($_SESSION["current_group"], $_SESSION["userid"], $message);
    }
}
?>

<script language='javascript'>
    function scrollDown() {
        var element = document.getElementById("chat_feed");
        element.scrollTop = element.scrollHeight * 2;

    }
    window.onload = scrollDown;

    function destroyChat(chatid){
        var retVal = confirm("You really want to destroy the chat group? \n(It will never come back!)");
        if(retVal){
            window.location.href='?destroy_g=1';
        }
    }
</script>

<div class="row">
    <div class="col-md-2">
        <div class="boxik">
            <?php
            if (isset($_SESSION["current_group"])) {
                $groupId = $_SESSION["current_group"];
                print("Current group: <br><div id='chat_group_title'> " . $_SESSION["current_group_name"] . "</div><br>");

                $friends = getFriendsNotMembersOfChat($_SESSION["userid"], $groupId, 10);
                echo('<br>Friends to add:<br>');
                for ($i = 0; $i < sizeof($friends); $i++) {
                    $id = $friends[$i]["id"];
                    $name = $friends[$i]["name"];
                    print("<a href='?add_chat_member=$id'>$name</a>");
                    print("<br>");
                }
                print("<a href='?exitgroup=1'>Back</a>");
            }
            ?>
        </div>
    </div>
    <div class="col-md-8">
        <?php
        if (isset($_SESSION["current_group"])) {
            $groupId = $_SESSION["current_group"];
            $messages = getChatMessages($_SESSION["current_group"]);
            echo("<div class ='scrollable' id='chat_feed' onload='scrollDown()'>");
            $sizeOfMessages = $messages ? sizeof($messages) : 0;
            if (!$sizeOfMessages) {
                print("
        <div class='chat_message'>
            <span class = 'chat_username'>@BOT</span><br>&nbsp There are no messages!<br>&nbsp Write some.
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
                if ($id == $_SESSION["userid"])
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
            print("
        <form method='post' id='send_message'>
        <input type='text' name='message' autofocus style='width: 100%' placeholder='message'>
        <input type='submit' value='Send Message' name='message_btn' class ='login_button' style='width: 100%'>
        </form>
        "
            );
        } else {
            echo("<div class='boxik2' style='width: 100%'>");
            $groups = getGroups($_SESSION["userid"]);
            if (sizeof($groups) == 0) {
                print("<br>You are not member of any groups, how pathetic!<br>");

            } else {
                print("<br>You are member of these groups:<br>");

                for ($i = 0; $i < sizeof($groups); $i++) {
                    $id = $groups[$i]["chatid"];
                    $name = getChatInfo($id)["name"];
                    print("<a href='?open_chat=$id'>($id)$name</a>");
                    print("<br>");
                }

            }


            print("
        <br>
        <form method='post' id='create_group_chat'>
        Create new GroupChat!<br>
        <input type='text' name='create_group_chat' placeholder='group_name'>
        <input type='submit' value='Create Group' name='create_group_chat_btn'>
        </form>
        "
            );
            echo("</div>");
        }
        ?>
    </div>
    <div class="col-md-2">
            <?php
            if (isset($_SESSION["current_group"])) {
                print("<div class='boxik'>");
                $groupId = $_SESSION["current_group"];
                $isAdmin = $_SESSION["current_group_info"]["owner"] == $_SESSION["userid"];
                $friends = getMembers($groupId, $_SESSION["userid"], 10);

                if ($isAdmin) {

                    print("<div id='chat_group_title'>Admin Panel</div><br>");

                    echo('<br>Members to kick out:<br>');
                    for ($i = 0; $i < sizeof($friends); $i++) {
                        $id = $friends[$i]["id"];
                        $name = $friends[$i]["name"];
                        print("<a href='?kickout=$id'>$name</a>");
                        print("<br>");
                    }
                    print("<br><div class = 'warning'><button onclick='destroyChat(\"$groupId\")'>Destroy groupChat</button></div>");



                } else {
                    echo("Chat owner: ".$_SESSION["current_group_owner"]."<br>");
                    echo("<div id='chat_group_title'>Members</div>");
                    for ($i = 0; $i < sizeof($friends); $i++) {
                        $id = $friends[$i]["id"];
                        $name = $friends[$i]["name"];
                        print($name);
                        print("<br>");
                    }
                }
                print(" </div>");
            }
            ?>
    </div>
</div>



