<html>
	<head>
		<title>RSS For You</title>
		<link href="../style/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="head">
			Task 12 - RSS For You
		</div>
		<hr/>
		Here you can view all your RSS feeds in one place. Add your feeds with  the form below and remove them by clicking on the Remove text.<br/>
		All feeds will be saved to a cookie on your computer so when you enter this page next time you will have the same feeds.
		<hr/>
		<form method="POST" action="/dsv/servlet/ass12">
			<input type="hidden" name="whattodo" value="add"/>
			Add RSS Channel: <input type="text" name="channel_url"/> <input type="submit" value="Add" />
		</form>
		<hr/>
		<table width="90%" align="center" cellpadding="1" cellspacing="1" border="0" bgcolor="#000000">
			<!!--rss-->
				<tr>
					<td>
						<!--data-->
					</td>
				</tr>
			<!!--rss-->
		</table>
	</body>
</html>