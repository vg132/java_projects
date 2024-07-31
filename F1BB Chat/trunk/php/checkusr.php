<?php
	$username=$_GET['username'];
	$password=$_GET['password'];

	$db = mysql_connect("localhost", "bossen","iRi99A");
	mysql_select_db("f1bb_com_-_forum",$db);
	$result = mysql_query("SELECT mgroup, name,restrict_post, id FROM f1bb_members WHERE name='".$username."' AND password='".$password."'",$db);

	if($row = mysql_fetch_row($result))
	{
		echo "ok-f1bb-chat|".$row[0]."|".$row[1]."|".$row[2]."|".$row[3];
	}
	else
	{
		echo "ko";
	}
	mysql_close($db);
?>