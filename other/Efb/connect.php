<?php

$conn = NULL;
function connectI()
{
    global $conn;
    if ($conn != null)
        return;
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "fb";

    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
}

connectI();

function containsUser($name)
{
    global $conn;
    $sql = "SELECT * FROM users WHERE name='$name';";
    $result = $conn->query($sql);
    return $result && $result->num_rows != 0;
}

function login($usr, $pass)
{
    global $conn;
    $sql = "SELECT id FROM users WHERE name='$usr' and password='$pass';";
    $result = $conn->query($sql);
    return ($result && $result->num_rows != 0) ? $result->fetch_row()[0] : -1;
}

function register($usr, $pass)
{
    global $conn;

    $usr=htmlspecialchars($usr);
    //protect from evil usr
    $stmt = $conn->prepare("INSERT INTO users (name,password) VALUES (?,'$pass');");
    // "s" means the database expects a string
    $stmt->bind_param("s", $usr);
    $stmt->execute();
    $stmt->close();


    //$sql = "INSERT INTO users (name,password) VALUES ('$usr','$pass');";
   //$conn->query($sql);

    //and give up and let the sql injection happen because there is no time....
    //create personal feed
    $usrid = login($usr, $pass);
    $sql = "INSERT INTO chats (owner, name) VALUES ('$usrid',NULL);";
    $conn->query($sql);
    return $usrid;
}

function getRandomPeople($usrid, $count)
{
    $usrid=(int)$usrid;
    $count=(int)$count;
    global $conn;
    $sql =
        "SELECT name, id FROM users WHERE ( 
        users.id NOT IN ( select userid1 FROM friendships WHERE (userid0 = '$usrid') ) AND 
        users.id NOT IN ( select userid0 FROM friendships WHERE (userid1 = '$usrid') ) ) 
        AND id != '$usrid' ORDER BY RAND() LIMIT $count;";

    $result = $conn->query($sql);
    if (!$result)
        return false;
    return $result->fetch_all(MYSQLI_ASSOC);
}

function getFriends($usrid, $count)
{
    $usrid=(int)$usrid;
    $count=(int)$count;

    global $conn;
    $sql =
        "SELECT name, id FROM users WHERE ( 
        users.id IN ( select userid1 FROM friendships WHERE (userid0 = '$usrid') ) OR 
        users.id IN ( select userid0 FROM friendships WHERE (userid1 = '$usrid') ) ) 
        AND id != '$usrid' ORDER BY users.name LIMIT $count;";

    $result = $conn->query($sql);
    if (!$result)
        return false;
    return $result->fetch_all(MYSQLI_ASSOC);
}

function getFriendsNotMembersOfChat($usrid, $chatid, $count)
{
    $usrid=(int)$usrid;
    $count=(int)$count;
    $chatid=(int)$chatid;
    global $conn;
    $sql =
        "SELECT name, id FROM users WHERE ( 
        (users.id NOT IN (select userid FROM chat_members WHERE chat_members.chatid = '$chatid')) AND (
        users.id IN ( select userid1 FROM friendships WHERE (userid0 = '$usrid') ) OR 
        users.id IN ( select userid0 FROM friendships WHERE (userid1 = '$usrid') ) )) 
        AND id != '$usrid' ORDER BY users.name LIMIT $count;";

    $result = $conn->query($sql);
    if (!$result)
        return false;
    return $result->fetch_all(MYSQLI_ASSOC);
}

function areFriends($usrid0, $usrid1)
{
    $usrid0=(int)$usrid0;
    $usrid1=(int)$usrid1;
    global $conn;
    $sql = "SELECT COUNT(*) FROM friendships WHERE (userid0='$usrid0' and userid1='$usrid1' OR userid1='$usrid0' and userid0='$usrid1');";
    $result = $conn->query($sql);
    return !(!$result or $result->fetch_row()[0] == 0);
}

function addFriend($userid, $friendid)
{
    $userid=(int)$userid;
    $friendid=(int)$friendid;
    global $conn;

    $sql = "SELECT COUNT(*) FROM friendships WHERE (userid0='$userid' and userid1='$friendid' OR userid1='$userid' and userid0='$friendid');";
    $result = $conn->query($sql);
    if (!$result or $result->fetch_row()[0] == 0) {
        $sql = "INSERT INTO friendships (userid0,userid1) VALUES ('$userid','$friendid');";
        return $conn->query($sql);
    }
    return false;

}

function removeFriend($userid, $friendid)
{
    global $conn;

    $userid=(int)$userid;
    $friendid=(int)$friendid;

    $sql = "DELETE FROM friendships WHERE (userid0='$userid' and userid1='$friendid' OR userid1='$userid' and userid0='$friendid');";
    $result = $conn->query($sql);
    return $result;
}

//returns id of newly created chat
function createGroupChat($name, $ownerid)
{

    $ownerid=(int)$ownerid;
    global $conn;
    $name=htmlspecialchars($name);

    $sql = "INSERT INTO chats (name,owner) VALUES ('$name','$ownerid');";
    $result = $conn->query($sql);

    $sql = "SELECT id FROM chats WHERE (name='$name' and owner='$ownerid');";
    $result = $conn->query($sql);
    $chatid = $result->fetch_row()[0];

    $sql = "INSERT INTO chat_members (chatid,userid) VALUES ('$chatid','$ownerid');";
    $conn->query($sql);

    return $chatid;
}

function destroyGroupChat($groupId)
{
    global $conn;
    $groupId=(int)$groupId;

    $conn->query("DELETE FROM chats WHERE id='$groupId';");
    $conn->query("DELETE FROM chat_members WHERE chatid='$groupId';");
    $conn->query("DELETE likes.* FROM likes INNER JOIN messages ON likes.messageid =messages.id WHERE messages.chatid='$groupId';");
    $conn->query("DELETE FROM messages WHERE chatid='$groupId';");
}

function addMemberToGroup($groupid, $memberid)
{
    $groupid=(int)$groupid;
    $memberid=(int)$memberid;
    global $conn;

    $sql = "SELECT COUNT(*) FROM chat_members WHERE (chatid='$groupid' and userid='$memberid');";
    $result = $conn->query($sql);
    if ((!$result or $result->fetch_row()[0] == 0)) {
        $sql = "INSERT INTO chat_members (chatid,userid) VALUES ('$groupid','$memberid');";
        $conn->query($sql);
    }
}

function getMembers($groupid, $excludeuserid, $limit)
{
    $groupid=(int)$groupid;
    $excludeuserid=(int)$excludeuserid;
    $limit=(int)$limit;
    global $conn;
    $sql =
        "SELECT chat_members.userid AS 'id', users.name AS 'name' FROM chat_members INNER JOIN users ON users.id = chat_members.userid WHERE ( chat_members.chatid='$groupid' AND chat_members.userid!='$excludeuserid') ORDER BY chat_members.id LIMIT $limit;";
    //   $sql ="SELECT chat_members.userid AS 'id' FROM chat_members WHERE ( chat_members.chatid='$groupid' AND chat_members.userid!='$excludeuserid') ORDER BY chat_members.id LIMIT $limit;";

    $result = $conn->query($sql);
    if (!$result)
        return false;
    return $result->fetch_all(MYSQLI_ASSOC);
}

function removeMemberFromGroup($groupid, $memberid)
{
    $groupid=(int)$groupid;
    $memberid=(int)$memberid;

    global $conn;
    $sql = "DELETE FROM chat_members WHERE (chatid='$groupid' and userid='$memberid');";
    $result = $conn->query($sql);
}

function getGroups($memberid)
{
    $memberid=(int)$memberid;
    global $conn;
    $sql =
        "SELECT chatid FROM chat_members WHERE userid='$memberid';";

    $result = $conn->query($sql);
    if (!$result)
        return false;
    return $result->fetch_all(MYSQLI_ASSOC);
}

function getChatMessages($chatid)
{
    $chatid=(int)$chatid;
    global $conn;
    $sql =
        "SELECT message,dated,userid,id FROM messages WHERE chatid='$chatid' ORDER BY dated ASC;";

    $result = $conn->query($sql);
    if (!$result)
        return false;
    return $result->fetch_all(MYSQLI_ASSOC);
}

function addChatMessage($chatid, $memberid, $message)
{
    $chatid=(int)$chatid;
    $memberid=(int)$memberid;
    $message=htmlspecialchars($message);
    global $conn;
    $da = date("Y-m-d H:i:s");
    $sql = "INSERT INTO messages (message,chatid,userid,dated) VALUES ('$message','$chatid','$memberid','$da');";

    $result = $conn->query($sql);
    return $result;
}

function getUserInfo($userid)
{
    $userid=(int)$userid;
    global $conn;
    $sql =
        "SELECT name,email FROM users WHERE id='$userid';";

    $result = $conn->query($sql);
    if (!$result)
        return false;
    $bb = $result->fetch_all(MYSQLI_ASSOC);
    return $bb[0];
}

function getChatInfo($chatid)
{
    $chatid=(int)$chatid;
    global $conn;
    $sql = "SELECT owner,name FROM chats WHERE id='$chatid';";
    $result = $conn->query($sql);
    if (!$result)
        return false;
    return $result->fetch_all(MYSQLI_ASSOC)[0];
}

function likeMessage($messageid, $userid)
{
    $messageid=(int)$messageid;
    $userid=(int)$userid;

    global $conn;

    $sql = "SELECT COUNT(*) FROM likes WHERE (messageid='$messageid' and userid='$userid');";
    $result = $conn->query($sql);
    if ($result->fetch_row()[0]==0){
        $sql = "INSERT INTO likes (messageid,userid) VALUES ('$messageid','$userid');";
        $conn->query($sql);
    }
}
function getMessageInfo($messageid)
{
    $messageid=(int)$messageid;
    global $conn;

    $sql = "SELECT * FROM messages WHERE id='$messageid';";
    $result = $conn->query($sql);
    if($result)
        return $result->fetch_all(MYSQLI_ASSOC)[0];

    return false;
}

function unlikeMessage($messageid, $userid)
{
    $messageid=(int)$messageid;
    $userid=(int)$userid;

    global $conn;

    $sql = "DELETE FROM likes WHERE (messageid='$messageid' and userid='$userid');";
    $conn->query($sql);
}

function getMessageLikes($messageid)
{
    $messageid=(int)$messageid;
    global $conn;

    $sql = "SELECT COUNT(*) FROM likes WHERE (messageid='$messageid');";
    $result = $conn->query($sql);
    if (mysqli_num_rows($result) != 0)
        return $result->fetch_row()[0];
    return -1;
}

function isLiking($messageid, $userid)
{
    $messageid=(int)$messageid;
    $userid=(int)$userid;

    global $conn;

    $sql = "SELECT COUNT(*) FROM likes WHERE (messageid='$messageid' and userid='$userid');";
    $result = $conn->query($sql);

    return $result and $result->fetch_row()[0] == 1;
}

//return chatid of personal feed
function getPersonalFeedId($userid)
{
    $userid=(int)$userid;
    global $conn;
    $sql = "SELECT id FROM chats WHERE (owner='$userid' and name IS NULL);";

    $result = $conn->query($sql);
    return $result->fetch_row()[0];
}

//return chatid of conversation between two users, and or creates one if necessary
function getConversationFeedId($user0, $user1){
    global $conn;
    $user0=(int)$user0;
    $user1=(int)$user1;
    $result = $conn->query("SELECT id FROM chats s WHERE ((s.owner='$user0' and s.receiver='$user1') OR(s.owner='$user1' and s.receiver='$user0'));");
    if (mysqli_num_rows($result) === 0) {
        $conn->query("INSERT INTO chats (owner,receiver,name) VALUES ('$user0','$user1','$user0 and $user1');");
        $result = $conn->query("SELECT id FROM chats s WHERE ((s.owner='$user0' and s.receiver='$user1') OR(s.owner='$user1' and s.receiver='$user0'));");
    }
    return $result->fetch_row()[0];
}
//feed
function addPersonalFeed($userid, $message, $receiverid = -1)
{
    $userid=(int)$userid;
    $receiverid=(int)$receiverid;

    $message=htmlspecialchars($message);
    global $conn;
    $da = date("Y-m-d H:i:s");
    $chatid = -1;
    if ($receiverid == -1) {
        //write to personal feed
        $chatid = getPersonalFeedId($userid);
    } else {
        //write to friend
        $chatid = getConversationFeedId($userid, $receiverid);
    }
    if ($chatid == -1)
        return false;
    $sql = "INSERT INTO messages (message,chatid,userid,dated) VALUES ('$message','$chatid','$userid','$da');";
    $result = $conn->query($sql);
    return $result;
}

function getFriendsFeed($userid)
{
    $userid=(int)$userid;
    global $conn;

    $sql = "SELECT message,dated,userid,id FROM messages WHERE
  (chatid IN (SELECT id FROM chats WHERE (chats.name IS NULL and
                                          chats.owner IN (
                                            (SELECT userid1 FROM friendships WHERE(userid0='$userid' OR userid1='$userid')
                                             UNION
                                             SELECT userid0 FROM friendships WHERE(userid0='$userid' OR userid1='$userid')))
                                          AND chats.owner!='$userid'))) ORDER BY messages.dated ASC;";
    $result = $conn->query($sql);
    if (!$result)
        return false;
    return $result->fetch_all(MYSQLI_ASSOC);

}

?>
