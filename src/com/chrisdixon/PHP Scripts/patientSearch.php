<?php
 
//Connects to the MySQL databases
require("config.inc.php");
 $query = "";
if (!empty($_POST)) {
 
//Creates an array of all the fields in POST
$fields = array('personName','houseNumber','postcode');
//A variable holding data with the same order the corresponding columns in the database
$columns = array('full_name','house_number','postcode');
$sqlColumns = array();
$query_params = array();
//Check every field and if it is not blank then add them to $addToQuery
for($i=0;$i<count($fields);$i++){
if(isset($_POST[$fields[$i]]) && $_POST[$fields[$i]] != ''){
//Creates part of the query that will search for that field
$sqlColumns[] = $columns[$i] . '= :' . $fields[$i];
//Add the value that is being searched for
$query_params[$fields[$i]] = $_POST[$fields[$i]];
}
}
 
//Check to ensure at least two text boxes have been entered
if(count($sqlColumns) >= 2){
//Creates the SQL query
$query = 'SELECT * FROM patient WHERE ' . implode(' AND ',$sqlColumns);
 

}else{
echo "Please fill out at least 2 fields";
}
 
 
try {
//Executes the query with $queryParams holding the values
$stmt = $db->prepare($query);
$result = $stmt->execute($query_params);
}
catch (PDOException $ex) {
$response["success"] = 0;
$response["message"] = "Database Error. Reload And Contact System Admin";
die(json_encode($response));
 
}$rows = $stmt->fetchAll();
 
if ($rows) {
$response["success"] = 1;
$response["message"] = "Patient(s) Found";
$response["patients"] = array();
 
foreach ($rows as $row) {
$patient = array();
$patient["personName"] = $row["full_name"];
$patient["houseNumber"] = $row["house_Number"];
$patient["postcode"] = $row["postcode"];
$patient["age"] = $row["age"];
$patient["gender"] = $row["gender"];
$patient["nhsID"] = $row["nhs_id"];
 
array_push($response["patients"], $patient);
 
}
echo json_encode($response);
}
 
if(!$rows){
 
$response["success"] = 0;
$response["message"] = "No Patient Available!";
die(json_encode($response));
}
}else {
?>
<h1>Search for a patient</h1>
<form action="patientSearch.php" method="post">
Patient Name:<br />
<input type="text" name="personName" placeholder="Name" />
<br /><br />
House Number:<br />
<input type="text" name="houseNumber" placeholder="Enter House/Flat Number"/>
<br /><br />
Postcode:<br />
<input type="text" name="postcode" placeholder="Enter Postcode"/>
<br /><br />
<input type="submit" value="Search for patient" />
</form>
 
<?php
}
 
?>