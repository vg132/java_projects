<?php	
	require "functions.php";

	if((isset($_GET['pass']))&&(isset($_GET['url']))&&(isset($_GET['title'])))
	{
		if(strcmp($_GET['pass'],"f1BbChAtUrLUp")==0)
		{
			download($_GET['url'],$_GET['title'],$_GET['open']);
			echo "OK!";
		}
	}
?>