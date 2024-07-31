<html>
	<head>
		<title>Simple File Upload System</title>
		<link href="../style/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="head">
			Task 3 - Simple File Upload System
		</div>
		<hr/>
		<form method="POST" action="/dsv/servlet/ass3" enctype="multipart/form-data">
			<input type="file" name="file"/><br/>
			<input type="submit" value="Upload"/>
		</form>
		<hr/>
		<table>
			<!!--file-->
				<tr>
					<td>
						<!--name-->
					</td>
					<td>
						<!--type-->
					</td>
					<td>
						<!--size-->
					</td>
					<td>
						<a href="<!--url-->">View</a>
					</td>
				</tr>
			<!!--file-->
		</table>
	</body>
</html>