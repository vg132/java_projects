//========================================================================
//Global state variable used to recreate menu
var strVisible="";

//========================================================================
//Open popup window
function popup(url)
{
	window.open(url, "Attachments","resizable=yes,scrollbars=auto,status=0,width=560,height=310");
}

//========================================================================
//Recreate menu
function recreate(elements)
{
	var i;
	while((i=elements.indexOf("|-|"))!=-1)
	{
		expandMenu(elements.substring(0,i));
		elements=elements.substring(i+3);
	}
}

//========================================================================

function expung(folder)
{
	document.maillist.state.value=strVisible;
	document.maillist.whattodo.value="expung";
	document.maillist.folder.value=folder;
	document.maillist.submit();
}

function undelete(folder)
{
	document.maillist.state.value=strVisible;
	document.maillist.whattodo.value="undelete";
	document.maillist.folder.value=folder;
	document.maillist.submit();
}

function del(folder)
{
	document.maillist.state.value=strVisible;
	document.maillist.whattodo.value="delete";
	document.maillist.folder.value=folder;
	document.maillist.submit();
}

function moveMessages(folder)
{
	document.maillist.state.value=strVisible;
	document.maillist.whattodo.value="movemail";
	document.maillist.folder.value=folder;
	document.maillist.submit();
}

function folderEdit()
{
	document.pagebuilder.state.value=strVisible;
	document.pagebuilder.whattodo.value="folderedit";
	document.pagebuilder.submit();
}

function compose()
{
	document.pagebuilder.state.value=strVisible;
	document.pagebuilder.whattodo.value="compose";
	document.pagebuilder.submit();
}

function download(folder, uid, pid)
{
	document.download.folder.value=folder;
	document.download.uid.value=uid;
	document.download.pid.value=pid;
	document.download.submit();
}

function read(theFolder, uid)
{
	document.pagebuilder.state.value=strVisible;
	document.pagebuilder.whattodo.value="read";
	document.pagebuilder.folder.value=theFolder;
	document.pagebuilder.uid.value=uid;
	document.pagebuilder.submit();
}

function getFolder(theFolder, thePage)
{
	document.pagebuilder.state.value=strVisible;
	document.pagebuilder.whattodo.value="folder";
	document.pagebuilder.folder.value=theFolder;
	document.pagebuilder.startpage.value=thePage;
	document.pagebuilder.submit();
}

function replace(theFolder)
{
	return(theFolder.replace(/\s/,'_|_'));
}

//========================================================================
//Show or Hide table with images
function expandMenu(theTable)
{
	var strlen=theTable.length;
	var i=strVisible.indexOf(theTable);
	if(document.getElementById(theTable).style.display=='none')
	{
		document.getElementById(theTable).style.display='block';
		document.getElementById(theTable+'p').src='../images/menu/collapse.gif';
		if(i==-1)
		{
			strVisible+=theTable+"|-|";
		}
	}
	else
	{
		document.getElementById(theTable).style.display='none';
		document.getElementById(theTable+'p').src='../images/menu/expand.gif';
		if(i!=-1)
		{

			var tmp=strVisible.substring(0,i);
			tmp+=strVisible.substring(i+strlen+3);
			strVisible=tmp;
		}
	}
}