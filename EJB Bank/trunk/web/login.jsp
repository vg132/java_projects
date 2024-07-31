<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>VG Software Bank</title>
	</head>
	<body>
		Please login
		<form method="post" action="login.bank">
			Username: <input type="text" name="username"/><br/>
			Password: <input type="password" name="password"/><br/>
			User type:
			<select name="type">
				<option value="customer">Customer</option>
				<option value="admin">Admin</option>
			</select>
			<input type="submit" value="Login"/>
		</form>
	</body>
</html>