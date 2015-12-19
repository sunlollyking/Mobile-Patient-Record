<?php
require("config.inc.php");
if (!empty($_POST)) {
$img_url = $_POST['img_url'];
$nhs_id = $_POST['nhs_id'];

$query = "INSERT INTO patient_image (nhs_id, img_url)
VALUES ('$nhs_id', '$img_url')";
       
    
$query_params = array(
        ':img_url' => $_POST['img_url'],
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
<form action="addPatientImage.php" method="post">
Please enter the fields below and submit to add a picture of the patient to the database<br />
<input type="text" name="nhs_id" placeholder="NHS ID Number" /> <br />
<br />
<input type="text" name="history" placeholder="URL Address Of Image" /> <br />
<br />
<br />
<input type="submit" value="Add Medical History Data" />
<br />
<br />
<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>