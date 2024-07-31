<form action="<!--context-->/servlet/pagebuilder" method="post" name="maillist">
	<input type="hidden" name="state" />
	<input type="hidden" name="whattodo" />
	<input type="hidden" name="folder" />
	<input type="hidden" name="startpage" />
	<!--page-browser-->
	<table class="main" style="margin-top:10px;">
		<tr>
			<td>
				<table class="sub">
					<tr>
						<td colspan="5">
							<b>Folder: <!--folder-path--></b>
						</td>
					</tr>
					<tr>
						<td colspan="5">
							Move Selected To:<br/>
							<select name="moveto">
							<!!--folders-->
								<option value="<!--folder-->"><!--folder--></option>
							<!!--folders-->
							</select>
							<!!--move-->
								<input type="button" value="Move" onclick="JavaScript:moveMessages('<!--real-folder-path-->');"/>
								<input type="button" value="Undelete" onclick="JavaScript:undelete('<!--real-folder-path-->');"/>
								<input type="button" value="Delete" onclick="JavaScript:del('<!--real-folder-path-->');"/>
								<input type="button" value="Expung" onclick="JavaScript:expung('<!--real-folder-path-->');"/>
							<!!--move-->
						</td>
					</tr>
					<tr class="header">
						<td class="select">
							&nbsp;
						</td>
						<td class="from">
							<b>From</b>
						</td>
						<td class="recived">
							<b>Date</b>
						</td>
						<td class="flags">
							&nbsp;
						</td>
						<td class="subject">
							<b>Subject</b>
						</td>
					</tr>
					<!!--mail-->
						<tr class="<!--class-->">
							<td>
							<input type="checkbox" name="msg[<!--uid-->]" value="<!--uid-->" class="forminput"/>
							</td>
							<td>
								<!--from-->
							</td>
							<td class="recived">
								<!--recived-->
							</td>
							<td class="flags">
								<!--flags-->
							</td>
							<td>
								<a href="JavaScript:read('<!--real-folder-path-->',<!--uid-->)"><!--subject--></a>
							</td>
						</tr>
					<!!--mail-->
					<!!--no-mail-->
						<tr class="color1">
							<td colspan="5">
								<center><b>No messages</b></center>
							</td>
						</tr>
					<!!--no-mail-->
					<tr class="fotter">
						<td colspan="5">
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
	<!--page-browser-->
</form>