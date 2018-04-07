<?php


$user_name = 'root';
$host_name = 'localhost';
$db_name = 'project';
$user_pass = 'hamburger';



$first_name = $_POST['firstname'];
$last_name = $_POST['lastname'];
$e_mail = $_POST['email'];
$pass_word = $_POST['password'];

$con = Mysqli_connect($host_name,$user_name,$user_pass,$db_name);
if ($con){
	$cam = "SELECT * FROM login WHERE email LIKE '$e_mail'";
$fav = mysqli_query($con,$cam);
if ($fav)
{
if (mysqli_num_rows($fav) > 0)
{
$ball=array("aresponse"=>'User already exist'); 
echo json_encode ($ball);
}
else
{

	$query = "INSERT INTO login(firstname,lastname,email,password) VALUES ('$first_name','$last_name','$e_mail','$pass_word')";
	$ba = mysqli_query($con,$query);
	if ($ba){
		$goal=array("response"=>'Thank you '.$first_name.',for registering'); 
echo json_encode ($goal);
	}
	else
	{
		$goal=array("response"=>'Unable to run SQL query 2'); 
echo json_encode ($goal);
	}
}
}
else
{
	$goal=array("response"=>'Unable to run SQL query 1'); 
echo json_encode ($goal);
}
}
else
{
	$goal=array("response"=>'Unable to connect to database'); 
echo json_encode ($goal); 
}

?>
