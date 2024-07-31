<?php

	class chat
	{
		var $events=array();
		
		function add_event($event)
		{
			array_push($this->events,$event);
		}
		
		function get_events()
		{
			return($events);
		}
	}

	class chat_event
	{
		var $name;
		var $enterys=array();
		var $start;
		var $end;
		
		function set_name($name)
		{
			$this->name=$name;
		}
		
		function get_name()
		{
			return($name);
		}
		
		function set_start($start)
		{
			$this->start=$start;
		}
		
		function get_start()
		{
			return($start);
		}
		
		function set_end($end)
		{
			$this->end=$end;
		}
		
		function add_entery($entery)
		{
			array_push($this->enterys,$entery);
		}
		
		function get_enterys()
		{
			return($enterys);
		}
	}

	class chat_entery
	{
		var $name;
		var $nice_name;
		var $open;
		var $close;
		
		function set_name($name)
		{
			$this->name=$name;
		}
		
		function get_name()
		{
			return($name);
		}
		
		function set_nice_name($nice_name)
		{
			$this->nice_name=$nice_name;
		}
		
		function get_nice_name()
		{
			return($nice_name);
		}
		
		function set_open($open)
		{
			$this->open=$open;
		}
		
		function get_open()
		{
			return($open);
		}
		
		function set_close()
		{
			$this->close=$close;
		}
		
		function get_close()
		{
			return($close);
		}
	}

	function get_events($events)
	{
		$xml_parser=xml_parser_create();
		$event_parser=new event_schedule_parser($events);
		xml_set_object($xml_parser,&$event_parser);
		xml_set_element_handler($xml_parser,"startElement","endElement");
		
		$xml_content=get_data("chat.xml");
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
		var $entery;
		
		function event_schedule_parser($events)
		{
			$this->events=&$events;
		}
		
		function startElement($parser, $tag_name, $attrs)
		{
			echo "Start: " . $tag_name . ".<br/>";
		/*
			if($tagName=="EVENT")
			{
				if(strtotime("now")<strtotime($attrs['END'] . " 23:59"))
				{
					$this->c_data->comming_events = $this->c_data->comming_events . sprintf("%s, från %s till %s. Chatten öppnar 10 minuter innan varje pass.<br/>",$attrs['NAME'],$attrs['START'],$attrs['END']);
					if(strtotime("now")>strtotime($attrs['START']))
					{
						$this->c_data->current_event=$attrs['NAME'];
					}
				}
			}
			else if($tagName=="ENTERY")
			{
				if(($this->nextFound=="no")&&(strtotime("now")<strtotime($attrs['OPEN'])))
				{
					$this->c_data->comming_events=$this->c_data->comming_events . sprintf("&nbsp;&nbsp;<b>Nästa pass är %s och chatten öppnar %s.</b><br/>",$attrs['EVENT'],strftime("%Y-%m-%d %H:%M",strtotime($attrs['OPEN']  . "+1 hour")));
					$this->nextFound="yes";
				}
				if((strtotime("now")>strtotime($attrs['OPEN'])) && (strtotime("now")<strtotime($attrs['CLOSE'])))
				{
					$this->c_data->current_event=$this->c_data->current_event . ", " . $attrs['EVENT'] . ". Chatten stänger " . strftime("%H:%M",strtotime($attrs['CLOSE'] . "+1 hour"));
					$this->c_data->show_chat="yes";
				}
			}*/
		}
		
		function endElement($parser, $tag_name)
		{
			echo "End: " . $tag_name . ".<br/>";
		}
	}





	function get_schedule($c_data)
	{
		$xml_parser = xml_parser_create();
		$chat_parser = new chat_schedule_parser(&$c_data);
		xml_set_object($xml_parser,&$chat_parser);
		xml_set_element_handler($xml_parser, "startElement", "endElement");

		$contents=get_data("chat.xml");
		if(!xml_parse($xml_parser,$contents))
		{
			printf("Unable to parse: XML error: %s at line %d",xml_error_string(xml_get_error_code($xml_parser)), xml_get_current_line_number($xml_parser));
		}
		xml_parser_free($xml_parser);
	}

	class chat_data
	{
		var $comming_events;
		var $current_event;
		var $show_chat="no";
	}

	class chat_schedule_parser
	{
		var $nextFound="no";
		var $c_data;
		
		function chat_schedule_parser($c_data)
		{
			$this->c_data=&$c_data;
		}

		function startElement($parser, $tagName, $attrs)
		{
			if($tagName=="EVENT")
			{
				if(strtotime("now")<strtotime($attrs['END'] . " 23:59"))
				{
					$this->c_data->comming_events = $this->c_data->comming_events . sprintf("%s, från %s till %s. Chatten öppnar 10 minuter innan varje pass.<br/>",$attrs['NAME'],$attrs['START'],$attrs['END']);
					if(strtotime("now")>strtotime($attrs['START']))
					{
						$this->c_data->current_event=$attrs['NAME'];
					}
				}
			}
			else if($tagName=="ENTERY")
			{
				if(($this->nextFound=="no")&&(strtotime("now")<strtotime($attrs['OPEN'])))
				{
					$this->c_data->comming_events=$this->c_data->comming_events . sprintf("&nbsp;&nbsp;<b>Nästa pass är %s och chatten öppnar %s.</b><br/>",$attrs['EVENT'],strftime("%Y-%m-%d %H:%M",strtotime($attrs['OPEN']  . "+1 hour")));
					$this->nextFound="yes";
				}
				if((strtotime("now")>strtotime($attrs['OPEN'])) && (strtotime("now")<strtotime($attrs['CLOSE'])))
				{
					$this->c_data->current_event=$this->c_data->current_event . ", " . $attrs['EVENT'] . ". Chatten stänger " . strftime("%H:%M",strtotime($attrs['CLOSE'] . "+1 hour"));
					$this->c_data->show_chat="yes";
				}
			}
		}

		function endElement($parser, $tagName)
		{
		}
	}



?>