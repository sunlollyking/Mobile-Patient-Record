<?php
require("config.inc.php");
if (!empty($_POST)) {
 $full_name = $_POST['full_name'];
$house_Number = $_POST['house_Number'];
$postcode = $_POST['postcode'];
$gender = $_POST['gender'];
$age = $_POST['age'];
$nhs_id = $_POST['nhs_id'];

$query = "INSERT INTO patient (nhs_id, full_name, house_Number, postcode, age, gender)
VALUES ('$nhs_id', '$full_name', '$house_Number', '$postcode', '$age', '$gender')";
       
    
$query_params = array(
        ':nhs_id' => $_POST['nhs_id'],
	':full_name' => $_POST['full_name'],
	':house_Number' => $_POST['house_Number'],
	':postcode' => $_POST['postcode'],
	':age' => $_POST['age'],
	':gender' => $_POST['gender']


    );



try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }
}

else{
echo "Please Enter Fields";
}


?>
<form action="createNewPatient.php" method="post">
Please enter the fields below and submit to create a new patient:<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" />
<input type="text" name="full_name" placeholder="Full Name" />
<input type="text" name="house_Number" placeholder="House Number" />
<input type="text" name="postcode" placeholder="Postcode" />
<input type="text" name="age" placeholder="Age Of Patient" />
<input type="text" name="gender" placeholder="Patient's Gender" />
<br />
<input type="submit" value="Create Patient" />
<br />
<br />
<a href="addPatientAllergy.php">Add Allergy Data To An Existing Patient</a> 
<br />
<br />
<a href="addPatientBloodGroup.php">Add Data About A Patient's Blood Group</a> 
<br />
<br />
<a href="addPatientConcerns.php">Add Any Medical Concerns A Doctor Or Patient Has About Their Situation</a> 
<br />
<br />
<a href="addPatientDrug.php">Add Details Of What Medication A Patient Is Taking</a> 
<br />
<br />
<a href="addPatientFamilyHistory.php">Add Details Of A Patient's Family History</a> 
<br />
<br />
<a href="addPatientHistory.php">Add Details Of A Patient's Medical History</a> 
<br />
<br />
<a href="addPatientImage.php">Add An Image Of A Patient</a> 
<br />
<br />
<a href="addPatientResuscitate.php">Define Whether The Patient Would Like To Be Resuscitated Or Not</a> 
<br />
<br />
<br />
If wanting to check if patient data has been sent to a hospital please click the link below
<a href="checkPatient.php">Find Patient's Hospital Data</a> 
<br />
<br />
</form>