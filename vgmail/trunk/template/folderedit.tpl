<table class="main" style="margin-top:10px;">
	<tr>
		<td>
			<table class="sub" style="background-color:#ffffff;">
				<tr>
					<td colspan="5">
						<b>Manage Folders</b>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<form method="post" action="<!--context-->/servlet/pagebuilder">
	<input type="hidden" name="whattodo" value="folderedit"/>
	<input type="hidden" name="folderinfo" value="newfolder"/>
	<input type="hidden" name="refresh"/>
	<table class="main" style="margin-top:10px;">
		<tr>
			<td>
				<table class="sub" style="background-color:#ffffff;">
					<tr>
						<td valign="top">
							Create Folder
						</td>
					</tr>
					<tr>
						<td valign="top">
							<input type="text" name="newfolder"/>
						</td>
					</tr>
					<tr>
						<td valign="top">
							as a subfolder of
						</td>
					</tr>
					<tr>
						<td valign="top">
							<select name="root">
								<!!--folders-->
									<option value="<!--folder-->"><!--folder--></option>
								<!!--folders-->
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>