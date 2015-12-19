<?php

require("config.inc.php");

if (!empty($_POST)) {
    //gets user's info based off of a username.
    $query = " 
	      SELECT * FROM medical_professionals_log WHERE username = :username ORDER BY current_t_d DESC";
               
    $query_params = array(
        ':username' => $_POST['username'],
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
    $response["message"] = "Log Found";
    $response["userLog"]   = array();
    
    foreach ($rows as $row) {
        $userLog          = array();
        $userLog["username"] = $row["username"];
	$userLog["log"] = $row["log"];	
	$userLog["current_t_d"] = $row["current_t_d"];		
     array_push($response["userLog"], $userLog);
	 } 
echo json_encode($response);
}
    
    if(!$rows){
	 
    $response["success"] = 0;
    $response["message"] = "No Log Data Found!";
    die(json_encode($response));
} 
}else {
?>
		<h1>Get Log Data</h1> 
		<form action="getLogData.php" method="post"> 
		    Patient Name:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		     <br /><br /> 
		    <input type="submit" value="Log Search" /> 
		</form> 
		
	<?php
}

?>