<?php
if(isset($_POST["friendadd"]))
{
    addFriend($_SESSION["userid"],$_POST["friend_id"]);
}
if(isset($_GET["add_friend"]))
{
    $id = $_GET["add_friend"];
    addFriend($_SESSION["userid"], $id);
    header("Refresh:0; url=index.php");
}
if(isset($_GET["remove_friend"]))
{
    $id = $_GET["remove_friend"];
    removeFriend($_SESSION["userid"], $id);
    header("Refresh:0; url=index.php");
}
?>



<div class="row">
    <div class="col-md-6">
        <div class="boxik">
            <?php
            $users = getRandomPeople($_SESSION["userid"],15);
            echo("<div id='chat_group_title'>You may know: (Click to make them yours!)</div>");
            for ($i = 0;$i<sizeof($users);$i++){
                $id =  $users[$i]["id"];
                $name = $users[$i]["name"];
                print("<a href='?add_friend=$id'>$name</a>");
                print("<br>");
            }
            ?>
            <form method="post" id="add_friend_form">
                Friendid: <input type="text" name="friend_id">
                <input type="submit" value="Add Friend" name="friendadd">
            </form>
        </div>
    </div>
    <div class="col-md-6">
        <div class="boxik">
        <?php
        $friends = getFriends($_SESSION["userid"],10);
        echo("<div id='chat_group_title'>Your Friends: (Click to make them disappear!)</div>");
        for ($i = 0;$i<sizeof($friends);$i++){
            $id =  $friends[$i]["id"];
            $name = $friends[$i]["name"];
            print("<a href='?remove_friend=$id'>$name</a>");
            print("<br>");
        }
        ?>
    </div>
    </div>
</div>