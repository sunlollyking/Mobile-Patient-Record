<?php
require("config.inc.php");
if (!empty($_POST)) {
$concerns = $_POST['concerns'];
$nhs_id = $_POST['nhs_id'];
$date_raised = $_POST['date_raised'];

$query = "INSERT INTO patient_concerns (nhs_id, concerns, date_raised)
VALUES ('$nhs_id', '$concerns', '$date_raised')";
       
    
$query_params = array(
        ':nhs_id' => $_POST['nhs_id'],
	':concerns' => $_POST['concerns'],
	':date_raised' => $_POST['date_raised']
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
<form action="addPatientConcerns.php" method="post">
Please enter the fields below and submit to add a details of any concerns about a patient that the NHS should know about:<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" /> <br />
<br />
<input type="text" name="concerns" placeholder="Patient Concern" /> <br />
<br />
<br />
Please Enter The Date The Issue Was Raised:
This Should Be In The Form Of 'YYYY-MM-DD' <br /> <br />
<input type="text" name="date_raised" placeholder="Date Raised" /> <br />
<br />

<input type="submit" value="Add Concern" />
<br />
<br />
<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>