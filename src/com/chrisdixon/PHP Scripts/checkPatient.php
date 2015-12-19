<?php
//Check to see whether the fields have text data
if (!empty($_POST)) {
//Save whatever has been placed into the post box referenceNo and save to a variable of the same name
$referenceNo = $_POST['referenceNo'];
//Create an url variable taking the user back to the system homepage
$url = "http://homepages.cs.ncl.ac.uk/c.dixon4/"; 
$result = $url . '' . $referenceNo;
//Relocate the user to the new concatenated URL
header ("Location: $result"); 
}
//Otherwise display a message illustrating no fields have been entered
else{
echo "Please Enter Fields";
}

?>
<form action="checkPatient.php" method="post">
Please enter the reference number supplied from the mobile application to access the patient's hospital data
<br />
<br />
<input type="text" name="referenceNo" placeholder="Patient Reference Number" />
<br />
<br />

<input type="submit" value="Search For Patient" />
<br />
<br />

<a href="createNewPatient.php">Please Click To Go Back To Create Patient Screen</a> 
</form>