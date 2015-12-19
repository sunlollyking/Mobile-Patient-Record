<?php
require("config.inc.php");
if (!empty($_POST)) {
$should_resuscitate = $_POST['should_resuscitate'];
$nhs_id = $_POST['nhs_id'];

$query = "INSERT INTO patient_resuscitate (should_resuscitate, nhs_id)
VALUES ('$should_resuscitate', '$nhs_id')";
       
    
$query_params = array(
        ':should_resuscitate' => $_POST['should_resuscitate'],
	':nhs_id' => $_POST['nhs_id']
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
<form action="addPatientResuscitate.php" method="post">
Please enter the fields below and submit to define whether the patient would like to be resuscitated<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" /> <br />
<br />
Please Enter The Number 0 If The Patient Would NOT LIKE TO BE RESUSCITATED <br />
Otherwise it will be assumed that they would like to be resuscitated
<input type="text" name="should_resuscitate" placeholder="Resuscitation Preferences" /> <br />
<br />
<br />
<input type="submit" value="Add Medical History Data" />
<br />
<br />
<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>