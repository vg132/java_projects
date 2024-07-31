<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=us-ascii"/>
		<title>Free Email sender!</title>
		<link href="../style/style.css" rel="stylesheet" type="text/css"/>
		<script language="JavaScript">
		<!--
			function remove()
			{
				if(document.mailform.files.selectedIndex!=-1)
				{
					document.mailform.whattodo.value="remove";
					document.mailform.filename.value=document.mailform.files.options[document.mailform.files.selectedIndex].value
					document.mailform.submit();
				}
			}
			
			function send()
			{
				document.mailform.whattodo.value="send";
				document.mailform.submit();			
			}
			
			function add()
			{
				document.mailform.whattodo.value="add";
				document.mailform.submit();
			}
		-->
		</script>
	</head>
	<body>
		<div class="head">
			Task 4b - Send a Free Multipart Email!
		</div>
		<hr/>
		<form method="post" action="/dsv/servlet/ass4b" name="mailform" enctype="multipart/form-data">
			<input type="hidden" name="whattodo"/>
			<input type="hidden" name="filename"/>
			<table>
				<tr>
					<td>
						To
					</td>
					<td>
						<input type="text" name="to" value="<!--to-->"/>
					</td>
				</tr>
				<tr>
					<td>
						Cc
					</td>
					<td>
						<input type="text" name="cc" value="<!--cc-->"/>
					</td>
				</tr>
				<tr>
					<td>
						Bcc
					</td>
					<td>
						<input type="text" name="bcc" value="<!--bcc-->"/>
					</td>
				</tr>
				<tr>
					<td>
						From
					</td>
					<td>
						<input type="text" name="from" value="<!--from-->"/>
					</td>
				</tr>
				<tr>
					<td>
						Subject
					</td>
					<td>
						<input type="text" name="subject" value="<!--subject-->"/>
					</td>
				</tr>
				<tr>
					<td>
						Password
					</td>
					<td>
						<input type="password" name="password"/>
					</td>
				</tr>
				<tr>
					<td valign="top">
						Attached files<br/>
					</td>
					<td>
						<select size="10" name="files">
							<!!--files-->
							<option value="<!--value-->"><!--name--></option>
							<!!--files-->
						</select>
						<br/>
						<input type="button" onclick="JavaScript:remove();" value="Remove File"/>
					</td>
				</tr>
				<tr>
					<td>
						Add File<br/>
					</td>
					<td>
						<input type="file" name="file"/><br/>
						<input type="button" value="Add File" onClick="JavaScript:add();"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">Message</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="message" rows="8" cols="50"><!--message--></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="button" value="send" onClick="JavaScript:send();"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
