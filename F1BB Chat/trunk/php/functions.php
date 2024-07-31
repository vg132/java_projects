<?php

	class chat
	{
		var $events=array();
		var $current_event=null;
		
		function chat()
		{
		}
		
		function add_event($event)
		{
			array_push($this->events,$event);
		}
		
		function get_events()
		{
			return($this->events);
		}
		
		function set_current_event($event)
		{
			$this->current_event=$event;
		}
		
		function get_current_event()
		{
			return($this->current_event);
		}
	}

	class chat_event
	{
		var $name;
		var $enterys=array();
		var $start;
		var $end;
		
		function chat_event()
		{
		}
		
		function set_name($name)
		{
			$this->name=$name;
		}
		
		function get_name()
		{
			return($this->name);
		}
		
		function set_start($start)
		{
			$this->start=$start;
		}
		
		function get_start()
		{
			return($this->start);
		}
		
		function set_end($end)
		{
			$this->end=$end;
		}
		
		function get_end()
		{
			return($this->end);
		}
		
		function add_entery($entery)
		{
			array_push($this->enterys,$entery);
		}
		
		function get_enterys()
		{
			return($this->enterys);
		}
	}

	class chat_entery
	{
		var $name;
		var $nice_name;
		var $open;
		var $close;
		
		function chat_entery()
		{
		
		}
		
		function set_name($name)
		{
			$this->name=$name;
		}
		
		function get_name()
		{
			return($this->name);
		}
		
		function set_nice_name($nice_name)
		{
			$this->nice_name=$nice_name;
		}
		
		function get_nice_name()
		{
			return($this->nice_name);
		}
		
		function set_open($open)
		{
			$this->open=$open;
		}
		
		function get_open()
		{
			return($this->open);
		}
		
		function set_close($close)
		{
			$this->close=$close;
		}
		
		function get_close()
		{
			return($this->close);
		}
	}

	function get_events($events)
	{
		$xml_parser=xml_parser_create();
		$event_parser=new event_schedule_parser(&$events);
		xml_set_object($xml_parser,&$event_parser);
		xml_set_element_handler($xml_parser,"startElement","endElement");
		
		$xml_content=implode('',file("chat.xml"));
		if(!xml_parse($xml_parser,$xml_content))
		{
			printf("Unable to parse: XML error: %s at line %d",xml_error_string(xml_get_error_code($xml_parser)), xml_get_current_line_number($xml_parser));
		}
		xml_parser_free($xml_parser);
	}
	
	class event_schedule_parser
	{
		var $events;
		var $event;
		
		function event_schedule_parser($events)
		{
			$this->events=&$events;
		}
		
		function startElement($parser, $tag_name, $attrs)
		{
			if($tag_name=="EVENT")
			{
				if(strtotime("now")<strtotime($attrs['END'] . " 23:59"))
				{
					$this->event=new chat_event();
					$this->event->set_name($attrs['NAME']);
					$this->event->set_start($attrs['START']);
					$this->event->set_end($attrs['END']);
				}
			}
			else if(($tag_name=="ENTERY")&&($this->event!=null))
			{
				$entery=new chat_entery();
				$entery->set_name($attrs['EVENT']);
				$entery->set_nice_name($attrs['NICENAME']);
				$entery->set_open($attrs['OPEN']);
				$entery->set_close($attrs['CLOSE']);
				
				//check if its the current event
				if((strtotime("now")>strtotime($entery->get_open()))&&(strtotime("now")<strtotime($entery->get_close())))
					$this->events->set_current_event($entery);
				$this->event->add_entery($entery);
			}
		}
		
		function endElement($parser, $tag_name)
		{
			if($tag_name=="EVENT")
			{
				$this->events->add_event($this->event);
				$this->event=null;
			}
		}
	}

	// File functions for auto publishing of chat sessions.
	function download($url, $title, $open)
	{
		$contents=get_data($url);
		$filename=strtotime("now") . ".html";

		if(!$handle=fopen("./transcripts/" . $filename,"w"))
		{
			echo "Cannot open file ($filename)";
			exit;
		}
		if(!fwrite($handle,$contents))
		{
			echo "Cannot write to file ($filename)";
			exit;
		}
		fclose($handle);

		$filecontent=implode('',file("./transcripts.txt"));
		$filecontent="<tr><td><a href=\"transcripts/" . $filename . "\">" . $title . "</a> (" . $open . ")</td></tr>\n" . $filecontent;
		if(is_writable("./transcripts.txt"))
		{
			if(!$handle=fopen("./transcripts.txt",'w'))
			{
				echo "Cannot open file ($filename)";
				exit;
			}
			if(!fwrite($handle,$filecontent))
			{
				echo "Cannot write to file ($filename)";
				exit;
			}
			fclose($handle);
		}
	}

	// Helper functions
	// Read data from a url and return the content.	
	function get_data($url)
	{
		if(!$handle=fopen(str_replace(" ","+",$url), "rb"))
		{
			echo "Cannot open file ($url).";
			exit;
		}
		$contents = "";
		while(!feof($handle))
		{
			$contents.=fread($handle,8192);
		}
		fclose($handle);
		return($contents);
	}
?>