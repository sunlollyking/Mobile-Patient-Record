<?php
require("config.inc.php");
if (!empty($_POST)) {
$blood_group = $_POST['blood_group'];
$nhs_id = $_POST['nhs_id'];

$query = "INSERT INTO patient_blood_group (nhs_id, blood_group)
VALUES ('$nhs_id', '$blood_group')";
       
    
$query_params = array(
        ':nhs_id' => $_POST['nhs_id'],
	':blood_group' => $_POST['blood_group']
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
<form action="addPatientBloodGroup.php" method="post">
Please enter the fields below and submit to add a patient's blood group:<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" /> <br />
<br />
<input type="text" name="blood_group" placeholder="Blood Group" /> <br />
<br />
<br />
<input type="submit" value="Add Blood Group" />
<br />
<br />
<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>