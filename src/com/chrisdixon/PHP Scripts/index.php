<?php

//Loads and connects to the MySQL Database
require("config.inc.php");

//So long as the boxes have been filled allow the script to execute
if (!empty($_POST)) {
 //Prepares a query to return the id, username and password from medical_professionals table where the username is the same as the username in the posted input
    $query = " 
            SELECT 
                id, 
                username, 
                password
            FROM medical_professionals
            WHERE 
                username = :username 
        ";
    //Creates a variable called query_params which will hold any variables that should be used in the query
    $query_params = array(
        ':username' => $_POST['username']
    );

    //Executes the query using the given parameters
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Problem. Please Reload And Contact System Admin";
        die(json_encode($response));
        
    }
    
    //Creates a variable to determine whether the user's information has been validated, it is initialised to false 
     $login_ok = false;
    //Save a variable called passwordFromPost containing whatever the user inputted into the post box  
		$passwordFromPost = $_POST['password'];
	//Creates a hashed version of the password to help protect the data if infiltrated
		$passwordhash = SHA1($passwordFromPost);
		
	//Fetch all the rows from the query and save it to variable row
    $row = $stmt->fetch();
//Created a variable called hashedPasswordFromDB where a row contains password
	$hashedPasswordFromDB=$row['password'];
//If the hashed password from the database is equal to the hashed password entered in the form box
  if (SHA1($hashedPasswordFromDB)== $passwordhash) {
//Set login to true and allow the user entry
        $login_ok = true; 
		}
//If either box has not had any input do not allow the user to progress and keep login as a false variable
if(empty($_POST['password']) OR empty($_POST['username'])) { 
$login_ok = false;
 }
		
 
    // If the user has been successful in their login we return JSON data indicating so and push them to the next page using javascript
    if ($login_ok) {
        $response["success"] = 1;
        $response["message"] = "Login successful!";
	echo json_encode($response);
echo '<script type="text/javascript"> window.location = "http://homepages.cs.ncl.ac.uk/c.dixon4/createNewPatient.php";</script>'; 
	} else {
//If the user has not been successful in their login do not allow them further in the program and reload the login using javascript
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
	die(json_encode($response));
echo '<script type="text/javascript"> window.location = "http://homepages.cs.ncl.ac.uk/c.dixon4/index.php";</script>';
    }
//HTML Describing the layout and look of the webpage
}else {
?>

		<body bgcolor="#E6E6FA">
		<h1>Mobile Patient Record: Login</h1> 
		<form action="index.php" method="post"> 
	<img src = "Images/icon.png" alt = "logo" style="float:right;">
		    Username:<br /> 
		    <input type="text" name="username" placeholder="Enter Username" /> 
		    <br /><br /> 
		    Password:<br /> 
		    <input type="password" name="password" placeholder="Enter Password" value="" /> 
		    <br /><br /> 
		    <input type="submit" value="Login" />
		 
		</form> 
		
	<?php
}

?> 
     
		

