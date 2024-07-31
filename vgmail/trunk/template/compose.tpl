<table class="main" style="margin-top:10px;">
	<tr>
		<td>
			<table class="sub" style="background-color:#ffffff;">
				<tr>
					<td colspan="5">
						<b>Compose Mail</b>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<form method="post" action="<!--context-->/servlet/send" name="compose">
	<table class="main" style="margin-top:10px;">
		<tr>
			<td>
				<table class="sub" style="background-color:#ffffff;">
					<tr>
						<td valign="top">
							To
						</td>
						<td>
							<input type="text" name="to" value="<!--to-->" size="64"/>
						</td>
						<td>
							<a href="JavaScript:popup('<!--context-->/servlet/attachment');">Attachments</a>
						</td>
					</tr>
					<tr>
						<td valign="top">
							Cc
						</td>
						<td>
							<input type="text" name="cc" value="<!--cc-->" size="64"/>
						</td>
						<td rowspan="3">
							<select size="5" name="files">
							</select>
						</td>
					</tr>
					<tr>
						<td valign="top">
							BCC
						</td>
						<td>
							<input type="text" name="bcc" value="<!--bcc-->" size="64"/>
						</td>
					</tr>
					<tr>
						<td>
							Subject
						</td>
						<td>
							<input type="text" name="subject" value="<!--subject-->" size="64"/>
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
						<td>
							<input type="submit" value="Send" />
						</td>
					</tr>
					<tr>
						<td>
							<textarea name="message" rows="20" cols="76" wrap="virtual"><!--mail-text--></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" value="Send" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>