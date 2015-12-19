<?php
require("config.inc.php");
if (!empty($_POST)) {
$priority = $_POST['priority'];
$nhs_id = $_POST['nhs_id'];
$history = $_POST['history'];


$query = "INSERT INTO patient_family_history (nhs_id, history, priority)
VALUES ('$nhs_id', '$history', '$priority')";
       
    
$query_params = array(
        ':priority' => $_POST['priority'],
	':nhs_id' => $_POST['nhs_id'],
	':history' => $_POST['history']
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
<form action="addPatientFamilyHistory.php" method="post">
Please enter the fields below and submit to add a details of the patient's family's medical history:<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" /> <br />
<br />
<input type="text" name="history" placeholder="Family History" /> <br />
<br />
<br />
<input type="text" name="priority" placeholder="What Is The Priority Of The History" /> <br />
<br />
<br />
<input type="submit" value="Add Family Medical History Data" />
<br />
<br />
<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>