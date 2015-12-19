<?php
require("config.inc.php");
if (!empty($_POST)) {
var_dump($_POST);
 $username = $_POST['username'];
$log = $_POST['log'];

$query = "INSERT INTO medical_professionals_log (username, log)
VALUES ('$username', '$log')";
       
    
$query_params = array(
        ':username' => $_POST['username'],
	':log' => $_POST['log']
    );



try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error. Reload And Contact System Admin";
        die(json_encode($response));
        
    }
}

else{
echo "Please Enter Fields";
}

?>

		<form action="postMedicalProfessionalLog.php" method="post">
		    Medical Professional Name:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
			<input type="text" name="log" placeholder="log" /> 
		     <input type="submit" value="Insert Details" /> 
		</form> 
		<?php

?>