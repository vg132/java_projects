/******
Copyright (c) 2002, Multi-Agent Systems Lab, University of Massachusetts, Amherst. All rights reserved. 
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 

3. The names "Multi-Agent Systems Lab " and "University of Massachusetts" must not be used to endorse or promote products derived from this software without prior written permission. To obtain permission, contact info@mas.cs.umass.edu. 

THIS SOFTWARE IS PROVIDED BY UNIVERSITY OF MASSACHUSETTS AND OTHER CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
****/

package com.vgsoftware.vgutil.misc;

class OperandTest
{

		OperandTest()
		{
		}

		public static boolean isNumber(Object o)
		{
				if(o == null)
				{
						return false;
				}
				if(o instanceof Number)
				{
						return true;
				}
				if(o instanceof String)
				{
						try
						{
								Double d = Double.valueOf((String)o);
								return false;
						}
						catch(Exception exception) { }
				}
				return false;
		}

		public static boolean isString(Object o)
		{
				if(o == null)
				{
						return false;
				}
				if(o instanceof String)
				{
						return ((String)o).charAt(0) == '"';
				} else
				{
						return false;
				}
		}

		public static boolean isExpression(Object o)
		{
				if(o == null)
				{
						return false;
				}
				return o instanceof Expression;
		}

		public static boolean isConstant(Object o)
		{
				if(o == null)
				{
						return true;
				}
				if(o instanceof String)
				{
						String s = (String)o;
						if(s.equals("null"))
						{
								return true;
						}
				}
				return false;
		}

		public static boolean isVariable(Object o)
		{
				if(o == null)
				{
						return false;
				}
				return (o instanceof String) && ((String)o).charAt(0) != '"' && !isNumber(o) && !isConstant(o);
		}
}
