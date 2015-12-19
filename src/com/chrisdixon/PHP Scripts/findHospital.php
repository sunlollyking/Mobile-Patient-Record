<?php
require("config.inc.php");
$query = "";
if (!empty($_POST)) {
      $query = " 
	        SELECT 
			* 
			FROM 
			hospital
			WHERE 
			locality = :locality 
			";
               
    $query_params = array(
        ':locality' => $_POST['locality'],
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
    $response["message"] = "Hospitals(s) Found";
    $response["hospitals"]   = array();
    
    foreach ($rows as $row) {
        $hospital             = array();
        $hospital["hospital_name"] = $row["hospital_name"];
        $hospital["longitude"]    = $row["longitude"];
        $hospital["latitude"]  = $row["latitude"];
	$hospital["phone_number"] = $row["phone_number"];
	$hospital["locality"] = $row["locality"];
		
     array_push($response["hospitals"], $hospital);
	 
	 } 
echo json_encode($response);
}
    
    if(!$rows){
	 
    $response["success"] = 0;
    $response["message"] = "No Hospitals Found!";
    die(json_encode($response));
} 
}else {
?>
		<h1>Search for a hospital</h1> 
		<form action="findHospital.php" method="post"> 
		   <input type="text" name="locality" placeholder="Locality"/> 
		    <br /><br /> 
		    <input type="submit" value="Search for hospital" /> 
		</form> 
		
	<?php
}

?>