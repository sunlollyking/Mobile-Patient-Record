<?php
require("config.inc.php");
if (!empty($_POST)) {
$priority = $_POST['priority'];
$nhs_id = $_POST['nhs_id'];
$date_started = $_POST['date_started'];
$date_finished = $_POST['date_finished'];
$dosage = $_POST['dosage'];
$name = $_POST['name'];

$query = "INSERT INTO patient_drug (nhs_id, priority, name, date_started, date_finished, dosage)
VALUES ('$nhs_id', '$priority', '$name', '$date_started', '$date_finished', '$dosage')";
       
    
$query_params = array(
        ':priority' => $_POST['priority'],
	':nhs_id' => $_POST['nhs_id'],
	':name' => $_POST['name'],
	':date_started' => $_POST['date_started'],
	':date_finished' => $_POST['date_finished'],
	':dosage' => $_POST['dosage']
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
<form action="addPatientDrug.php" method="post">
Please enter the fields below and submit to add a details of any medication a patient is currently taking:<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" /> <br />
<br />
<input type="text" name="name" placeholder="Medication Name" /> <br />
<br />
<br />
<input type="text" name="priority" placeholder="What Is Drug Priority" /> <br />
<br />
<br />
<input type="text" name="dosage" placeholder="Enter Dosage Here" /> <br />
<br />
<br />
Please Enter The Date The Prescription Was Started:
This Should Be In The Form Of 'YYYY-MM-DD' <br /> <br />
<input type="text" name="date_started" placeholder="Date Started" /> <br />
<br />
Please Enter The Date The Prescription Will Finish:
This Should Be In The Form Of 'YYYY-MM-DD' <br /> <br />
<input type="text" name="date_finished" placeholder="Date Finished" /> <br />
<br />
<input type="submit" value="Add Concern" />
<br />
<br />
<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>