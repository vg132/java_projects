<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>vgMail - Attachments</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<link href="../style/main.css" rel="stylesheet" type="text/css" />
		<script language="javascript">
			<!--
				function sendFileList()
				{
					var index=0;
					var files=document.upload.files.length;
					window.opener.document.compose.files.options.length=0;
					for(index=0;index<files;index++)
					{
						opt=window.opener.document.createElement("option");
						opt.text=document.upload.files[index].text;
						window.opener.document.compose.files[index]=opt;
					}
				}
				
				function removeFile()
				{
					document.upload.whattodo.value="remove";
					document.upload.submit();
				}
			-->
		</script>
	</head>
	<body onload="javascript:sendFileList();">
		<table class="main" style="margin-top:10px;">
			<tr>
				<td>
					<table class="sub" style="background-color:#ffffff;">
						<tr>
							<td colspan="5">
								<b>vgMail - Attachments</b>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table class="main" style="margin-top:10px;">
			<tr>
				<td>
					<table class="sub" style="background-color:#ffffff;">
						<tr>
							<td colspan="5">
								<!--status-->
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<form name="upload" action="<!--context-->/servlet/attachment" method="post" enctype="multipart/form-data">
			<input type="hidden" name="whattodo" />
			<table class="main" style="margin-top:10px;">
				<tr>
					<td>
						<table class="sub" style="background-color:#ffffff;">
							<tr>
								<td>
									<input type="file" name="file" size="64"/>
								</td>
							</tr>
							<tr>
								<td>
									<input type="submit" value="Add File" />
								</td>
							</tr>
							<tr>
								<td>
									<select size="5" name="files">
										<!!--files-->
										<option value="<!--value-->"><!--name--></option>
										<!!--files-->
									</select>
									<br/>
									Total size: <!--total-size--> kb<br/>
									Free space: <!--free-space--> kb
								</td>
							</tr>
							<tr>
								<td>
									<input type="button" value="Remove" onClick="JavaScript:removeFile()"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>