<?php
$quote = "";
if (

    isset($_POST["username"]) and
    isset($_POST["password"]) and
    $_POST["username"] != "" and
    $_POST["password"] != ""
) {


    $usr = $_POST["username"];
    $pass = hash("sha256", $_POST["password"]);
    if (!isset($_SESSION["cookie_accept"])) {
        $quote = "No Cookies, no Life!";
    } else if (containsUser($usr)) {
        $quote = "Username already taken";
    } else {
        $success = register($usr, $pass);
        $quote = $success ? "Registered" : "Cannot register";
        if ($success) {
            // $success = login($usr, $pass);

            $_SESSION["user"] = $usr;
            $_SESSION["userid"] = $success;
            header("Refresh:0; url=index.php?userid=" . $success);

            //   }
        }
    }
}
?>

<div class="row">
    <div class="col-md-6">
        <img class="wanderer" src="dims.png">
    </div>
    <div class="col-md-6">
        <div style="padding: 20px">
            <div class="register_form_child" style="font-size: 32px">
                Create a new Account!
            </div>
            <form method="post" id="register_form">
                <input type="text" class="register_form_child" name="username" placeholder="Username"><br>
                <input type="password" class="register_form_child" name="password" placeholder="Password"><br>
                <input type="submit" class="register_form_child register_form_child_btn" value="Register">
            </form>

            <?php
            if (isset($quote) and $quote != "")
                print(" <br><div class='warning'>$quote </div>");
            ?>
        </div>
    </div>
</div>


