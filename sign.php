<?php
$user_name = 'root';
$host_name = 'localhost';
$db_name = 'boston';
$user_pass = 'hamburger';

$user = $_POST['user'];
$pass = $_POST['pass'];


$con = Mysqli_connect($host_name,$user_name,$user_pass,$db_name);
if ($con)
{
$cam = "SELECT firstname,email FROM login WHERE email = '$user' AND password ='$pass'";
$fav = mysqli_query($con,$cam);
$lab = mysqli_num_rows($fav);
$q = $lab > 0 ;

if ($q){
$row = mysqli_fetch_assoc($fav);
	$name = $row["firstname"];
	$email = $row["email"];
	$goal=array("name"=>$name,"email"=>$email );
	echo json_encode ($goal);
 }
 else
{
$goal=array("response"=>'Username/Password incorrect'); 
echo json_encode ($goal);
	
} 
}
else
{
	 $goal=array("response"=>'Unable to connect to database'); 
echo json_encode ($goal);
}

?>
