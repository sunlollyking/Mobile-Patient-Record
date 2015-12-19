<?php

require("config.inc.php");

if (!empty($_POST)) {
    //gets user's info based off of a username.
    $query = " 
	      SELECT * FROM patient_allergy WHERE nhs_id = :nhsID";
               
    $query_params = array(
        ':nhsID' => $_POST['nhsID'],
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }$rows = $stmt->fetchAll();


 if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Allergies(s) Found";
    $response["patientAllergies"]   = array();

    foreach ($rows as $row) {
        $patientAllergies             = array();
        $patientAllergies["priority"] = $row["priority"];
	$patientAllergies["allergy"] = $row["allergy"];	
	$patientAllergies["date_diagnosed"] = $row["date_diagnosed"];		
     array_push($response["patientAllergies"], $patientAllergies);
	 
	 } 
} else{
$response["success"] = 1;
    $response["message"] = "Nothing Found";
    $response["patientAllergies"]   = null;




} 


$query = " 
	      SELECT * FROM patient_blood_group WHERE nhs_id = :nhsID";
               
    $query_params = array(
        ':nhsID' => $_POST['nhsID'],
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
      
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }$rows = $stmt->fetchAll();
	
	if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Blood Group Found";
    $response["patientBloodGroup"]   = array();
    
    foreach ($rows as $row) {
        $patientBloodGroup             = array();
        $patientBloodGroup["blood_group"] = $row["blood_group"];
      array_push($response["patientBloodGroup"], $patientBloodGroup);
	 
	 }

	 
} else{
$response["success"] = 1;
    $response["message"] = "Nothing Found";
    $response["patientBloodGroup"]   = null;
}


$query = " 
	      SELECT * FROM patient_resuscitate WHERE nhs_id = :nhsID";
               
    $query_params = array(
        ':nhsID' => $_POST['nhsID'],
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }$rows = $stmt->fetchAll();
	
	if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Resuscitation Details Found";
    $response["patientResuscitate"]   = array();
    
    foreach ($rows as $row) {
        $patientResuscitate             = array();
	$patientResuscitate["organ_donor"] = $row["organ_donor"];
        $patientResuscitate["should_resuscitate"] = $row["should_resuscitate"];
      array_push($response["patientResuscitate"], $patientResuscitate);
	 
	 }

	 
} else{
$response["success"] = 1;
    $response["message"] = "Nothing Found";
    $response["patientResuscitate"]   = null;
}


	 $query = " 
	      SELECT * FROM patient_concerns WHERE nhs_id = :nhsID";
               
    $query_params = array(
        ':nhsID' => $_POST['nhsID'],
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }$rows = $stmt->fetchAll();
	
	if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Patient Concerns Found";
    $response["patientConcerns"]   = array();
    
    foreach ($rows as $row) {
        $patientConcerns             = array();
        $patientConcerns["concerns"] = $row["concerns"];
		$patientConcerns["date_raised"] = $row["date_raised"];
      array_push($response["patientConcerns"], $patientConcerns);
	 
	 }
	}

else{
$response["success"] = 1;
    $response["message"] = "Nothing Found";
    $response["patientConcerns"]   = null;
}

$query = " 
	      SELECT * FROM patient_drug WHERE nhs_id = :nhsID";
               
    $query_params = array(
        ':nhsID' => $_POST['nhsID'],
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
                $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }$rows = $stmt->fetchAll();
	
	if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Patient Taking Medication Found Found";
    $response["patientDrug"]   = array();
    
    foreach ($rows as $row) {
        $patientDrug             = array();
        $patientDrug["priority"] = $row["priority"];
		$patientDrug["name"] = $row["name"];
		$patientDrug["date_started"] = $row["date_started"];
		$patientDrug["date_finished"] = $row["date_finished"];
		$patientDrug["dosage"] = $row["dosage"];
      array_push($response["patientDrug"], $patientDrug);	 
	 }
	}

else{
$response["success"] = 1;
    $response["message"] = "Nothing Found";
    $response["patientDrug"]   = null;
}
	$query = " 
	      SELECT * FROM patient_family_history WHERE nhs_id = :nhsID";
               
    $query_params = array(
        ':nhsID' => $_POST['nhsID'],
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }$rows = $stmt->fetchAll();
	
	if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Patient's Family History Found";
    $response["patientFamilyHistory"]   = array();
    
    foreach ($rows as $row) {
        $patientFamilyHistory             = array();
        $patientFamilyHistory["priority"] = $row["priority"];
		$patientFamilyHistory["history"] = $row["history"];
		array_push($response["patientFamilyHistory"], $patientFamilyHistory);	 
	 }
	 
}
else{
$response["success"] = 1;
    $response["message"] = "Nothing Found";
    $response["patientFamilyHistory"]   = null;
}
$query = " 
	      SELECT * FROM patient_history WHERE nhs_id = :nhsID";
               
    $query_params = array(
        ':nhsID' => $_POST['nhsID'],
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }$rows = $stmt->fetchAll();
	
	if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Patient Medical History Found";
    $response["patientHistory"]   = array();
    
    foreach ($rows as $row) {
        $patientHistory             = array();
        $patientHistory["priority"] = $row["priority"];
		$patientHistory["history"] = $row["history"];
		$patientHistory["date"] = $row["date"];
		array_push($response["patientHistory"], $patientHistory);	 
	 }
	} 
else{
$response["success"] = 1;
    $response["message"] = "Nothing Found";
    $response["patientHistory"]   = null;
}
	 $query = " 
	      SELECT * FROM patient_image WHERE nhs_id = :nhsID";
               
    $query_params = array(
        ':nhsID' => $_POST['nhsID'],
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }$rows = $stmt->fetchAll();
	
	if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Medical Records Found!";
    $response["patientImage"]   = array();
    
    foreach ($rows as $row) {
        $patientImage             = array();
        $patientImage["img_url"] = $row["img_url"];
		array_push($response["patientImage"], $patientImage);	 
	 }
}
else{
$response["success"] = 1;
    $response["message"] = "Nothing Found";
    $response["patientImage"]   = null;
}
echo json_encode($response);
   }
else {
?>
		<h1>GetRecords!</h1> 
		<form action="medicalRecord.php" method="post"> 
		    Patient Name:<br /> 
		    <input type="text" name="nhsID" placeholder="nhsID" /> 
		     <br /><br /> 
		    <input type="submit" value="Search for patient" /> 
		</form> 
		
	<?php
}

?>