<?php

$quote = "";
if(
    isset($_POST["username_l"]) and 
    isset($_POST["password_l"])){

    $usr = $_POST["username_l"];
    $pass = hash("sha256",$_POST["password_l"]);
    if(!isset($_SESSION["cookie_accept"])){
        $quote = "No Cookies, no Life!";
    }else {
        $success = login($usr, $pass);

        if ($success != -1) {

            $quote = "Logged in!";
            $_SESSION["user"] = $usr;
            $_SESSION["userid"] = $success;
            header("Refresh:0; url=index.php?userid=" . $success);

        } else {
            $quote = "Invalid username or password. Ouch!";
        }
    }
}

?>
<div class="col-md-4">
<div class="login_form">
    <form method="post">
        <table>
            <tr>
                <br>
            </tr>
            <tr>
                <td>Username:</td>
                <td>Password:</td>
            </tr>
            <tr>
                <td><input class = "login_form_input"  type="text" name="username_l" placeholder="username"></td>
                <td><input class = "login_form_input"  type="password" name="password_l" placeholder="password"></td>
                <td> <input class = "login_button" type="submit" value="Login"></td>
            </tr>
        </table>
        <div style="font-size: 12px">
        <?php
        if(!isset($quote) or $quote=="")
        echo(" Forgot password? That sucks.")
        ?>
        </div>
    </form>
</div>
<div style="font-size: 12px; color: orangered; font-weight: bold;">

<?php
    echo $quote;
?>
</div>
</div>
