<?php
//Loads and connects to the MySQL Database
require("config.inc.php");
if (!empty($_POST)) {
$priority = $_POST['priority'];
$allergy = $_POST['allergy'];
$nhs_id = $_POST['nhs_id'];
$date_diagnosed = $_POST['date_diagnosed'];

$query = "INSERT INTO patient_allergy (nhs_id, allergy, priority, date_diagnosed)
VALUES ('$nhs_id', '$allergy', '$priority', '$date_diagnosed')";
       
    
$query_params = array(
        ':nhs_id' => $_POST['nhs_id'],
	':allergy' => $_POST['allergy'],
	':priority' => $_POST['priority'],
	':date_diagnosed' => $_POST['date_diagnosed']
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
<form action="addPatientAllergy.php" method="post">
Please enter the fields below and submit to add data of a patient:<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" /> <br />
<br />
<input type="text" name="allergy" placeholder="Enter The Name Of The Allergy" /> <br />
<br />
<input type="text" name="priority" placeholder="Priority Level Of Allergy" /> <br /> <br />
Please Enter The Date The Patient Was Diagnosed:
This Should Be In The Form Of 'YYYY-MM-DD' <br /> <br />
<input type="text" name="date_diagnosed" placeholder="Date Of Diagnosis" /> <br />
<br />
<input type="submit" value="Add An Allergy" />
<br />
<br />
<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>