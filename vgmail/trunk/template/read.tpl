<table class="main" style="margin-top:10px;">
	<tr>
		<td>
			<table class="sub" style="background-color:#ffffff;">
				<tr>
					<td colspan="5">
						<b>Folder: <!--folder-path--></b>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="main" style="margin-top:10px;">
	<tr>
		<td>
			<table class="sub">
				<!!--header-->
					<tr class="header">
						<td>
							Subject
						</td>
						<td>
							<!--subject-->
						</td>
					</tr>
					<tr class="header">
						<td>
							From
						</td>
						<td>
							<!--from-->
						</td>
					</tr>
					<tr class="header">
						<td>
							Date
						</td>
						<td>
							<!--date-->
						</td>
					</tr>
					<tr class="header">
						<td valign="top">
							To
						</td>
						<td>
							<!--to-->
						</td>
					</tr>
					<tr class="header">
						<td valign="top">
							Cc
						</td>
						<td>
							<!--cc-->
						</td>
					</tr>
				<!!--header-->
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
						<!--mail-->
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<!!--attachements-->
<table class="main" style="margin-top:10px;">
	<tr>
		<td>
			<table class="sub" style="background-color:#ffffff;">
				<tr>
					<td colspan="3">
						Attachments:
					</td>
				</tr>
				<!!--download-->
					<tr>
						<td>
							<a href="JavaScript:download('<!--folder-path-->',<!--uid-->,<!--pid-->)"><!--file-name--></a>
						</td>
						<td>
							<!--size--> kb
						</td>
						<td>
							[<!--type-->]
						</td>
					</tr>
				<!!--download-->
			</table>
		</td>
	</tr>
</table>
<!!--attachements-->