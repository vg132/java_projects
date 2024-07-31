<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html lang=en xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<title>F1 Bulletin Board -> Chat</title>
		<meta http-equiv=content-type content="text/html; charset=iso-8859-1">

		<style type='text/css'>
			/* FIX IE6 Scrollbars bug - Leave this in! */
			html { overflow-x: auto; }

			/* Body entry, change forum page background colour, default font, font size, etc. Leave text-align:center to center board content
			#ipwrapper will set text-align back to left for the forum. Any other tables / divs you use must use text-align:left to re-align
			the content properly. This is a work around to a known Internet Explorer bug */
			BODY { font-family: Verdana, Tahoma, Arial, sans-serif; font-size: 11px; color: #000; margin:0px;padding:0px;background-color:#FFF; text-align:center }
			TABLE, TR, TD { font-family: Verdana, Tahoma, Arial, sans-serif; font-size: 11px; color: #000; }

			/* MAIN WRAPPER: Adjust forum width here. Leave margins alone to auto-center content */
			#ipbwrapper { text-align:left; width:95%; margin-left:auto;margin-right:auto }

			a:link, a:visited, a:active { text-decoration: underline; color: #000 }
			a:hover { color: #465584; text-decoration:underline }

			fieldset.search { padding:6px; line-height:150% }
			label      { cursor:pointer; }
			form       { display:inline; }
			img        { vertical-align:middle; border:0px }
			img.attach { border:2px outset #EEF2F7;padding:2px }

			.googleroot  { padding:6px; line-height:130% }
			.googlechild { padding:6px; margin-left:30px; line-height:130% }
			.googlebottom, .googlebottom a:link, .googlebottom a:visited, .googlebottom a:active { font-size:11px; color: #3A4F6C; }
			.googlish, .googlish a:link, .googlish a:visited, .googlish a:active { font-size:14px; font-weight:bold; color:#00D; }
			.googlepagelinks { font-size:1.1em; letter-spacing:1px }
			.googlesmall, .googlesmall a:link, .googlesmall a:active, .googlesmall a:visited { font-size:10px; color:#434951 }

			li.helprow { padding:0px; margin:0px 0px 10px 0px }
			ul#help    { padding:0px 0px 0px 15px }

			option.cat { font-weight:bold; }
			option.sub { font-weight:bold;color:#555 }
			.caldate   { text-align:right;font-weight:bold;font-size:11px;color:#777;background-color:#DFE6EF;padding:4px;margin:0px }

			.warngood { color:green }
			.warnbad  { color:red }

			#padandcenter { margin-left:auto;margin-right:auto;text-align:center;padding:14px 0px 14px 0px }

			#profilename { font-size:28px; font-weight:bold; }
			#calendarname { font-size:22px; font-weight:bold; }

			#photowrap { padding:6px; }
			#phototitle { font-size:24px; border-bottom:1px solid black }
			#photoimg   { text-align:center; margin-top:15px }

			#ucpmenu    { line-height:150%;width:22%; border:1px solid #345487;background-color: #F5F9FD }
			#ucpmenu p  { padding:2px 5px 6px 9px;margin:0px; }
			#ucpcontent { background-color: #F5F9FD; border:1px solid #345487;line-height:150%; width:auto }
			#ucpcontent p  { padding:10px;margin:0px; }

			#ipsbanner { position:absolute;top:1px;right:5%; }
			#logostrip { padding: 0px;margin: 0px;background-color: #426AC1;border: 1px solid #345487; }
			#submenu   { border:1px solid #BCD0ED;background-color: #DFE6EF;font-size:10px;margin:3px 0px 3px 0px;color:#3A4F6C;font-weight:bold;}
			#submenu a:link, #submenu  a:visited, #submenu a:active { font-weight:bold;font-size:10px;text-decoration: none; color: #3A4F6C; }
			#userlinks { border:1px solid #C2CFDF; background-color: #F0F5FA }

			#navstrip  { font-weight:bold;padding:6px 0px 6px 0px; }

			.activeuserstrip { background-color:#BCD0ED; padding:6px }

			/* Form stuff (post / profile / etc) */
			.pformstrip { background-color: #D1DCEB; color:#3A4F6C;font-weight:bold;padding:7px;margin-top:1px }
			.pformleft  { background-color: #F5F9FD; padding:6px; margin-top:1px;width:25%; border-top:1px solid #C2CFDF; border-right:1px solid #C2CFDF; }
			.pformleftw { background-color: #F5F9FD; padding:6px; margin-top:1px;width:40%; border-top:1px solid #C2CFDF; border-right:1px solid #C2CFDF; }
			.pformright { background-color: #F5F9FD; padding:6px; margin-top:1px;border-top:1px solid #C2CFDF; }

			/* Topic View elements */
			.signature   { font-size: 10px; color: #339; line-height:150% }
			.postdetails { font-size: 10px }
			.postcolor   { font-size: 12px; line-height: 160% }

			.normalname { font-size: 12px; font-weight: bold; color: #003 }
			.normalname a:link, .normalname a:visited, .normalname a:active { font-size: 12px }
			.unreg { font-size: 11px; font-weight: bold; color: #900 }

			.post1 { background-color: #F5F9FD }
			.post2 { background-color: #EEF2F7 }
			.postlinksbar { background-color:#D1DCEB;padding:7px;margin-top:1px;font-size:10px; background-image: url(../style_images/1/tile_sub.gif) }

			/* Common elements */
			.row1 { background-color: #F5F9FD }
			.row2 { background-color: #DFE6EF }
			.row3 { background-color: #EEF2F7 }
			.row4 { background-color: #E4EAF2 }

			.darkrow1 { background-color: #C2CFDF; color:#4C77B6; }
			.darkrow2 { background-color: #BCD0ED; color:#3A4F6C; }
			.darkrow3 { background-color: #D1DCEB; color:#3A4F6C; }

			.hlight { background-color: #DFE6EF }
			.dlight { background-color: #EEF2F7 }

			.titlemedium { font-weight:bold; color:#3A4F6C; padding:7px; margin:0px; background-image: url(../style_images/1/tile_sub.gif) }
			.titlemedium  a:link, .titlemedium  a:visited, .titlemedium  a:active  { text-decoration: underline; color: #3A4F6C }

			/* Main table top (dark blue gradient by default) */
			.maintitle { vertical-align:middle;font-weight:bold; color:#FFF; padding:8px 0px 8px 5px; background-image: url(../style_images/1/tile_back.gif) }
			.maintitle a:link, .maintitle  a:visited, .maintitle  a:active { text-decoration: none; color: #FFF }
			.maintitle a:hover { text-decoration: underline }

			/* tableborders gives the white column / row lines effect */
			.plainborder { border:1px solid #345487;background-color:#F5F9FD }
			.tableborder { border:1px solid #345487;background-color:#FFF; padding:0; margin:0 }
			.tablefill   { border:1px solid #345487;background-color:#F5F9FD;padding:6px;  }
			.tablepad    { background-color:#F5F9FD;padding:6px }
			.tablebasic  { width:100%; padding:0px 0px 0px 0px; margin:0px; border:0px }

			.wrapmini    { float:left;line-height:1.5em;width:25% }
			.pagelinks   { float:left;line-height:1.2em;width:35% }

			.desc { font-size:10px; color:#434951 }
			.edit { font-size: 9px }


			.searchlite { font-weight:bold; color:#F00; background-color:#FF0 }

			#QUOTE { font-family: Verdana, Arial; font-size: 11px; color: #465584; background-color: #FAFCFE; border: 1px solid #000; padding-top: 2px; padding-right: 2px; padding-bottom: 2px; padding-left: 2px }
			#CODE  { font-family: Courier, Courier New, Verdana, Arial;  font-size: 11px; color: #465584; background-color: #FAFCFE; border: 1px solid #000; padding-top: 2px; padding-right: 2px; padding-bottom: 2px; padding-left: 2px }

			.copyright { font-family: Verdana, Tahoma, Arial, Sans-Serif; font-size: 9px; line-height: 12px }

			.codebuttons  { font-size: 10px; font-family: verdana, helvetica, sans-serif; vertical-align: middle }
			.forminput, .textinput, .radiobutton, .checkbox  { font-size: 12px; font-family: verdana, helvetica, sans-serif; vertical-align: middle }

			.thin { padding:6px 0px 6px 0px;line-height:140%;margin:2px 0px 2px 0px;border-top:1px solid #FFF;border-bottom:1px solid #FFF }

			.purple { color:purple;font-weight:bold }
			.red    { color:red;font-weight:bold }
			.green  { color:green;font-weight:bold }
			.blue   { color:blue;font-weight:bold }
			.orange { color:#F90;font-weight:bold }
		</style>
	</head>
	<body>
<?php
	require "functions.php";

	$chat=new chat;
	get_events(&$chat);

?>
		<div id="ipbwrapper">
			<div id="logostrip">
				<a href="http://www.f1bb.com/f1forum/index.php?">
					<img alt="F1BB.com" src="../style_images/1/f1bb_logo.jpg" border=0>
				</a>
			</div>
			<br/>
			<div id="navstrip" align="left">
				<img src="../style_images/1/nav.gif"/>&nbsp;
				<a href="http://www.f1bb.com/f1forum/index.php">F1 Bulletin Board</a>
				&nbsp;-&gt;&nbsp;
				<a href="http://www.f1bb.com/f1forum/chat/">Chat</a>
			</div>
			
<?php
	if(is_a($chat->get_current_event(),'chat_entery'))
	{
?>
			<br/>
			<div class="tableborder">
				<div class="maintitle" align="left">
					<img src="../style_images/1/nav_m.gif" border="0"  alt="&gt;" width="8" height="8" />
					&nbsp;<a href="http://www.f1bb.com/f1forum/index.php">F1BB Chat</a>
				</div>
				<br/>
				<table cellSpacing="1" cellPadding="4" width="100%" border="0">
					<tbody>
						<tr>
							<td>
								<applet code="com.vgsoftware.f1bb.client.F1BBChatClient.class" archive="http://f1bb.vgsoftware.com/chat/F1BBChatClient.jar" width="750" height="400">
									<param name="server" value="f1bb.vgsoftware.com"/>
									<param name="port" value="2001"/>
								</applet>
							</td>
						</tr>
					</tbody>
		  	</table>
			</div>
<?php
	}
?>
			<br/>
			<div class="tableborder">
				<div class="maintitle" align="left">
					<img src="../style_images/1/nav_m.gif" border="0"  alt="&gt;" width="8" height="8" />
					&nbsp;Schema för chatten
				</div>
				<br/>
				<table cellSpacing=1 cellPadding=4 width="100%" border=0>
					<tbody>
<?php
	$events=$chat->get_events();
	$pos=1;
	foreach($events as $event)
	{
		echo "<tr><td>" . strftime("%Y-%m-%d",strtotime($event->get_start() . "+1 hour")) . " - " . strftime("%Y-%m-%d",strtotime($event->get_end() . "+1 hour")) . " " . $event->get_name() . "<br/>";
		if($pos==1)
		{
			$enterys=$event->get_enterys();
			foreach($enterys as $entery)
			{
				if((strtotime("now")>strtotime($entery->get_open()))&&(strtotime("now")<strtotime($entery->get_close())))
					echo "&nbsp;&nbsp;<b>" . $entery->get_nice_name() . " - Start " . strftime("%H:%M",strtotime($entery->get_open() . "+1 hour")) . ", slut " . strftime("%H:%M",strtotime($entery->get_close() . "+1 hour")) . "</b><br/>";
				else
					echo "&nbsp;&nbsp;" . $entery->get_nice_name() . " - Start " . strftime("%H:%M",strtotime($entery->get_open() . "+1 hour")) . "<br/>";
			}
		}
		echo "</td></tr>";
		$pos++;
	}
?>
					</tbody>
		  	</table>
			</div>
			<br/>
			<div class="tableborder">
				<div class="maintitle" align="left">
					<img src="../style_images/1/nav_m.gif" border="0"  alt="&gt;" width="8" height="8" />
					&nbsp;Tidigare chat sessioner
				</div>
				<br/>
				<table cellSpacing="1" cellPadding="4" width="100%" border="0">
					<tbody>
<?php
							echo get_data("./transcripts.txt");
?>
					</tbody>
		  	</table>
			</div>
		</div>
		<p>
			&nbsp;
		</p>
	</body>
</html>
