<?php


?>
<div class="col-md-2"></div>
<div class="col-md-2">
    <?php
print($_SESSION["user"]."<br>");

    ?>
    <button class = "login_button" onclick="window.location.href='?logout=1';" value="!">Log out with style</button><br>
    <a href='?logout=1'>Logout</a>
</div>