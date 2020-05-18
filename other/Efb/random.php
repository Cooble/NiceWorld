<?php
if(
    isset($_POST["friendadd"])){
addFriend($_SESSION["userid"],$_POST["friend_id"]);
    
}
$users = getRandomPeople($_SESSION["userid"],10);


    echo("Possible frinends <br>");
for ($i = 0;$i<sizeof($users);$i++){
    print($users[$i]["name"] . "  " . $users[$i]["id"]);
    print("<br>");
    }


echo("<br>Friendzones <br>");
$users = getFriends($_SESSION["userid"],10);

echo("usrid ".$_SESSION["userid"]);
for ($i = 0;$i<sizeof($users);$i++){
    print($users[$i]["name"]);
    print("<br>");
    }
?>

<form method="post" id="add_friend_form">
    Friendid: <input type="text" name="friend_id">
    <input type="submit" value="Add Friend" name="friendadd">
</form>
