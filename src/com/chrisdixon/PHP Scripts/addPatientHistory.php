<?php
require("config.inc.php");
if (!empty($_POST)) {
$priority = $_POST['priority'];
$nhs_id = $_POST['nhs_id'];
$history = $_POST['history'];
$date  = $_POST['date'];

$query = "INSERT INTO patient_family_history (nhs_id, priority, history, date)
VALUES ('$nhs_id', '$priority','$history', $date)";
       
    
$query_params = array(
        ':priority' => $_POST['priority'],
	':nhs_id' => $_POST['nhs_id'],
	':history' => $_POST['history'],
	':date' => $_POST['date']
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
<form action="addPatientHistory.php" method="post">
Please enter the fields below and submit to add a details of the patient's medical history:<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" /> <br />
<br />
<input type="text" name="history" placeholder="Medical History" /> <br />
<br />
<br />
<input type="text" name="priority" placeholder="What Is The Priority Of The History" /> <br />
<br />
Please Enter The Date Of The Medical History:
This Should Be In The Form Of 'YYYY-MM-DD' <br /> <br />
<input type="text" name="date" placeholder="Date Of History" /> <br />
<br />

<input type="submit" value="Add Medical History Data" />
<br />
<br />
<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>