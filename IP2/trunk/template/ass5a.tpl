<html>
	<head>
		<title>Guestbook</title>
		<link href="../style/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<span class="head">
			Task 5a - Guestbook
		</span>
		<hr/>
		Leve a message :)
		<form action="/dsv/servlet/ass5a" method="POST">
			<table>
				<tr>
					<td>
						Name
					</td>
					<td>
						<input type="text" name="name"/>
					</td>
				</tr>
				<tr>
					<td>
						Email
					</td>
					<td>
						<input type="text" name="email"/>
					</td>
				</tr>
				<tr>
					<td>
						Homepage
					</td>
					<td>
						<input type="text" name="url"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						Message
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="message" rows="8" cols="50"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="Send"/>
					</td>
				</tr>
			</table>
		</form>
		<hr/>
		Messages
		<hr/>
		<!!--message-block-->
		<!--nr--> <a href="mailto:<!--email-->"><!--name--></a><br/>
		<a href="<!--url-->">Homepage</a><br/>
		Posted: <!--date--><br/>
		<!--message-->
		<hr/>
		<!!--message-block-->
	</body>
</html>