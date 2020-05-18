<?php
if(isset($_POST["accept_cookie_btn"])){
$_SESSION["cookie_accept"]=true;
 header("Refresh:0; url=index.php?userid=".$success);
}
?>
<div class='cookie_alert'>
    <div class = 'cookie_child'>
        If you wanna use this site, you are gonna be overcome by cookies. Muhaha!
    </div>

    <div class = 'cookie_child'>
          <form method="post" id="accept cookie" >
              <input  name = 'accept_cookie_btn' type="submit" value="Accept all those yummy cookies">
              <input type="button" onclick="window.location.replace('https://www.w3schools.com');" value="Daga, kotowaru!">
          </form>
    </div>
</div>