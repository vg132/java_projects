<html>
	<head>
		<title>Add parts to your mail</title>
		<link href="../style/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
<!--
	function remove()
	{
		if(document.mailform.files.selectedIndex!=-1)
			document.mailform.filename.value=document.mailform.files.options[document.mailform.files.selectedIndex].value
	}
-->
</script>
	</head>
	<body>
		<div class="head">
			Add files to your email.
		</div>
		<hr/>
		<form action="/dsv/servlet/ass4b" method="post" name="mailform">
			<input type="hidden" name="whattodo"/>
			<input type="hidden" name="filename"/>
			<table>

			</table>
		</form>
	</body>
</html>