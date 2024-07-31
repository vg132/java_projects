<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>vgMail</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<script src="../script/menu.js" type="text/javascript"></script>
		<link href="../style/main.css" rel="stylesheet" type="text/css" />
	</head>
	<body onload="javascript:recreate('<!--menu-state-->');">
		<form action="<!--context-->/servlet/attachment" method="post" name="download">
			<input type="hidden" name="folder" />
			<input type="hidden" name="uid" />
			<input type="hidden" name="pid" />
		</form>
		<form action="<!--context-->/servlet/pagebuilder" method="post" name="pagebuilder">
			<input type="hidden" name="state" />
			<input type="hidden" name="whattodo" />
			<input type="hidden" name="folder" />
			<input type="hidden" name="startpage" />
			<input type="hidden" name="uid" />
		</form>
		<div id="banner">
			<!--header-->
		</div>
		<div id="leftcontent">
			<!--menu-->
		</div>
		<div id="maincontent">
			<!--content-->
		</div>
	</body>
</html>