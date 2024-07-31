/******
Copyright (c) 2002, Multi-Agent Systems Lab, University of Massachusetts, Amherst. All rights reserved. 
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 

3. The names "Multi-Agent Systems Lab " and "University of Massachusetts" must not be used to endorse or promote products derived from this software without prior written permission. To obtain permission, contact info@mas.cs.umass.edu. 

THIS SOFTWARE IS PROVIDED BY UNIVERSITY OF MASSACHUSETTS AND OTHER CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
****/

package com.vgsoftware.vgutil.misc;

import java.text.ParseException;
import java.util.*;

public class Expression
{
	Object a;
	Object b;
	Object op;
	private int index;

	public Expression(String s)
	throws ParseException
	{
		a= null;
		b= null;
		op= null;
		index= 0;
		Vector v= new Vector();
		String t;
		for(StringTokenizer e=new StringTokenizer(s, " \t\r\n\"()&!|=<>-+*/%", true);e.hasMoreElements();v.addElement(t))
		{
			t=e.nextToken();
		}
		try
		{
			parseExpression(v);
		}
		catch (ParseException ex)
		{
			throw new ParseException(ex.getMessage() + " \"" + s + "\"",
				ex.getErrorOffset());
		}
	}
	
	protected Expression(Vector v)
	throws ParseException
	{
		a= null;
		b= null;
		op= null;
		index= 0;
		parseExpression(v);
	}
	
	public Object getOperand1()
	{
		return a;
	}
	
	public Object getOperand2()
	{
		return b;
	}
	
	public Object getOperator()
	{
		return op;
	}
	
	public boolean isTrue(Hashtable h)
	{
		Object o= evaluate(h);
		if (o == null)
		{
			return false;
		}
		if (o instanceof Boolean)
		{
			return ((Boolean)o).booleanValue();
		}
		else
		{
			System.err.println(
				"Warning: Top level expression returned non-boolean value");
			return false;
		}
	}
	
	public boolean isFalse(Hashtable h)
	{
		return !isTrue(h);
	}
	
	public Object evaluate(Hashtable h)
	{
		Object ao= null;
		Object bo= null;
		ao= convertOperand(a, h);
		if (op == null)
		{
			return(ao);
		}
		if (op.equals("||"))
		{
			if ((ao instanceof Boolean) && ((Boolean)ao).booleanValue())
			{
				return(new Boolean(true));
			}
			bo= convertOperand(b, h);
			if ((bo instanceof Boolean) && ((Boolean)bo).booleanValue())
			{
				return(new Boolean(true));
			}
			if (ao != null && !(ao instanceof Boolean))
			{
				System.err.println("Error, " + op + " has incorrect operand types");
			}
			else if (bo != null && !(bo instanceof Boolean))
			{
				System.err.println("Error, " + op + " has incorrect operand types");
			}
			else
			{
				return(new Boolean(false));
			}
		}
		else if (op.equals("&&"))
		{
			if ((ao instanceof Boolean) && !((Boolean)ao).booleanValue())
			{
				return(new Boolean(false));
			}
			bo= convertOperand(b, h);
			if ((ao instanceof Boolean) && (bo instanceof Boolean))
			{
				return(new Boolean(
					((Boolean)ao).booleanValue() && ((Boolean)bo).booleanValue()));
			}
			if (ao != null && !(ao instanceof Boolean))
			{
				System.err.println("Error, " + op + " has incorrect operand types");
			}
			else if (bo != null && !(bo instanceof Boolean))
			{
				System.err.println("Error, " + op + " has incorrect operand types");
			}
			else
			{
				return(new Boolean(false));
			}
		}
		if (bo == null)
		{
			bo= convertOperand(b, h);
		}
		if ((ao instanceof Number) && (bo instanceof String))
		{
			bo= new Double(bo.hashCode());
		}
		if ((bo instanceof Number) && (ao instanceof String))
		{
			ao= new Double(ao.hashCode());
		}
		if (op.equals("=="))
		{
			if (ao == null || bo == null)
			{
				return(new Boolean(ao == bo));
			}
			if ((ao instanceof Number) && (bo instanceof Number))
			{
				double ad= ((Number)ao).doubleValue();
				double bd= ((Number)bo).doubleValue();
				return(new Boolean(ad == bd));
			}
			else
			{
				return(new Boolean(ao.toString().equals(bo.toString())));
			}
		}
		if (op.equals("!="))
		{
			if (ao == null || bo == null)
			{
				return(new Boolean(ao != bo));
			}
			if ((ao instanceof Number) && (bo instanceof Number))
			{
				double ad= ((Number)ao).doubleValue();
				double bd= ((Number)bo).doubleValue();
				return(new Boolean(ad != bd));
			}
			else
			{
				return new Boolean(!ao.toString().equals(bo.toString()));
			}
		}
		if (bo != null)
		{
			if (op.equals("!"))
			{
				if (bo instanceof Boolean)
				{
					return(new Boolean(!((Boolean)bo).booleanValue()));
				}
				System.err.println("Error, " + op + " has incorrect operand types");
			}
			else if (ao != null)
			{
				if (op.equals(">"))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Boolean(ad > bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
				else if (op.equals("<"))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Boolean(ad < bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
				else if (op.equals(">="))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Boolean(ad >= bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
				else if (op.equals("<="))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Boolean(ad <= bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
				else if (op.equals("+"))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Double(ad + bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
				else if (op.equals("*"))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Double(ad * bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
				else if (op.equals("-"))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Double(ad - bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
				else if (op.equals("/"))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Double(ad / bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
				else if (op.equals("%"))
				{
					if ((ao instanceof Number) && (bo instanceof Number))
					{
						double ad= ((Number)ao).doubleValue();
						double bd= ((Number)bo).doubleValue();
						return(new Double(ad % bd));
					}
					System.err.println("Error, " + op + " has incorrect operand types");
				}
			}
		}
		return(null);
	}
	protected Object convertOperand(Object o, Hashtable h)
	{
		Object r;
		if (o instanceof Expression)
		{
			r= ((Expression)o).evaluate(h);
		}
		else if (o instanceof String)
		{
			String s= (String)o;
			if (s.charAt(0) == '"')
			{
				r= s.substring(1, s.length() - 1);
			}
			else if (s.equalsIgnoreCase("null"))
			{
				r= null;
			}
			else
			{
				r= h.get(s);
			}
			if (r instanceof String)
			{
				try
				{
					Double d= Double.valueOf((String)r);
					r= d;
				}
				catch (Exception exception)
				{
				}
			}
		}
		else
		{
			r= o;
		}
		return r;
	}
	protected void parseExpression(Vector v) throws ParseException
	{
		boolean neg= false;
		String t= nextToken(v);
		if (t == null)
		{
			throw new ParseException("Unexpected null token", index);
		}
		if (t.equals("("))
		{
			a= new Expression(v);
			t= nextToken(v);
			if (t == null)
			{
				throw new ParseException("Unexpected null token", index);
			}
			if (!t.equals(")"))
			{
				throw new ParseException("Token " + t + " is out of place", index);
			}
		}
		else if (OperatorTest.isSign(t))
		{
			v.insertElementAt(t, 0);
			v.insertElementAt("0", 0);
			a= new Expression(v);
		}
		else if (t.charAt(0) == '"')
		{
			a= t;
		}
		else if (Character.isDigit(t.charAt(0)))
		{
			a= Double.valueOf(t);
		}
		else if (Character.isLetter(t.charAt(0)) || t.charAt(0) == '_')
		{
			a= t;
		}
		else if (t.charAt(0) == '!')
		{
			op= t;
		}
		else
		{
			throw new ParseException("Token " + t + " is out of place", index);
		}
		if (v.isEmpty())
		{
			return;
		}
		if (op == null)
		{
			t= nextToken(v);
			if (t == null)
			{
				throw new ParseException("Unexpected null token", index);
			}
			if (t.equals(")"))
			{
				return;
			}
			if (OperatorTest.isOperator(t))
			{
				op= t;
			}
			else
			{
				throw new ParseException("Token " + t + " is out of place", index);
			}
		}
		t= nextToken(v);
		if (t == null)
		{
			throw new ParseException("Unexpected null token", index);
		}
		if (t.equals("("))
		{
			b= new Expression(v);
			t= nextToken(v);
			if (t == null)
			{
				throw new ParseException("Unexpected null token", index);
			}
			if (!t.equals(")"))
			{
				throw new ParseException("Token " + t + " is out of place", index);
			}
		}
		else if (OperatorTest.isSign(t))
		{
			v.insertElementAt(t, 0);
			v.insertElementAt("0", 0);
			b= new Expression(v);
		}
		else if (t.charAt(0) == '"')
		{
			b= t;
		}
		else if (Character.isDigit(t.charAt(0)))
		{
			b= Double.valueOf(t);
		}
		else if (Character.isLetter(t.charAt(0)) || t.charAt(0) == '_')
		{
			b= t;
		}
		else
		{
			throw new ParseException("Token " + t + " is out of place", index);
		}
	}
	protected String nextToken(Vector v)
	{
		String t= null;
		while (!v.isEmpty())
		{
			String s= (String)v.firstElement();
			v.removeElementAt(0);
			index += s.length();
			if (!s.equals(" ")
				&& !s.equals("\t")
				&& !s.equals("\n")
				&& !s.equals("\r"))
			{
				if (s.equals("\""))
				{
					t= s;
					while (!v.isEmpty())
					{
						s= (String)v.firstElement();
						v.removeElementAt(0);
						index += s.length();
						t= t + s;
						if (s.equals("\""))
						{
							break;
						}
					}
				}
				else if (s.equals("="))
				{
					t= s;
					s= (String)v.firstElement();
					if (s.equals("="))
					{
						v.removeElementAt(0);
						index += s.length();
						t= t + s;
					}
					else
					{
						System.err.println("Error: Unknown token \"" + t + "\"");
					}
				}
				else if (s.equals("|"))
				{
					t= s;
					s= (String)v.firstElement();
					if (s.equals("|"))
					{
						v.removeElementAt(0);
						index += s.length();
						t= t + s;
					}
					else
					{
						System.err.println("Error: Unknown token \"" + t + "\"");
					}
				}
				else if (s.equals("&"))
				{
					t= s;
					s= (String)v.firstElement();
					if (s.equals("&"))
					{
						v.removeElementAt(0);
						index += s.length();
						t= t + s;
					}
					else
					{
						System.err.println("Error: Unknown token \"" + t + "\"");
					}
				}
				else if (s.equals("!"))
				{
					t= s;
					s= (String)v.firstElement();
					if (s.equals("="))
					{
						v.removeElementAt(0);
						index += s.length();
						t= t + s;
					}
				}
				else if (s.equals(">"))
				{
					t= s;
					s= (String)v.firstElement();
					if (s.equals("="))
					{
						v.removeElementAt(0);
						index += s.length();
						t= t + s;
					}
				}
				else if (s.equals("<"))
				{
					t= s;
					s= (String)v.firstElement();
					if (s.equals("="))
					{
						v.removeElementAt(0);
						index += s.length();
						t= t + s;
					}
				}
				else if (s.endsWith("E") && Character.isDigit(s.charAt(0)))
				{
					t= s;
					s= (String)v.firstElement();
					if (s.equals("-") || s.equals("+"))
					{
						v.removeElementAt(0);
						index += s.length();
						t= t + s;
						s= (String)v.firstElement();
						if (Character.isDigit(s.charAt(0)))
						{
							v.removeElementAt(0);
							index += s.length();
							t= t + s;
						}
						else
						{
							System.err.println("Error: Unknown token \"" + t + "\"");
						}
					}
					else
					{
						System.err.println("Error: Unknown token \"" + t + "\"");
					}
				}
				else
				{
					t= s;
				}
				break;
			}
		}
		return t;
	}
	public Hashtable generateLinearForm()
	{
		if (isBooleanExpression())
		{
			return null;
		}
		if (a == null || b == null)
		{
			return null;
		}
		Hashtable atable;
		if (a instanceof Expression)
		{
			atable= ((Expression)a).generateLinearForm();
			if (atable == null)
			{
				return null;
			}
		}
		else
		{
			Object ao= simplifyOperand(a);
			if (ao == null)
			{
				return null;
			}
			atable= new Hashtable();
			if (ao instanceof String)
			{
				atable.put(ao, new Double(1.0D));
			}
			else if (ao instanceof Double)
			{
				atable.put("CONSTANT", ao);
			}
			else
			{
				return null;
			}
		}
		Hashtable btable;
		if (b instanceof Expression)
		{
			btable= ((Expression)b).generateLinearForm();
			if (btable == null)
			{
				return null;
			}
		}
		else
		{
			Object bo= simplifyOperand(b);
			if (bo == null)
			{
				return null;
			}
			btable= new Hashtable();
			if (bo instanceof String)
			{
				btable.put(bo, new Double(1.0D));
			}
			else if (bo instanceof Double)
			{
				btable.put("CONSTANT", bo);
			}
			else
			{
				return null;
			}
		}
		if (op.equals("+"))
		{
			for (Enumeration e= btable.keys(); e.hasMoreElements();)
			{
				String key= (String)e.nextElement();
				if (atable.containsKey(key))
				{
					Double ad= (Double)atable.get(key);
					Double bd= (Double)btable.get(key);
					Double rd= new Double(ad.doubleValue() + bd.doubleValue());
					atable.put(key, rd);
				}
				else
				{
					atable.put(key, btable.get(key));
				}
			}
		}
		else if (op.equals("-") || isComparisonExpression())
		{
			for (Enumeration e= btable.keys(); e.hasMoreElements();)
			{
				String key= (String)e.nextElement();
				if (atable.containsKey(key))
				{
					Double ad= (Double)atable.get(key);
					Double bd= (Double)btable.get(key);
					Double rd= new Double(ad.doubleValue() - bd.doubleValue());
					atable.put(key, rd);
				}
				else
				{
					Double bd= (Double)btable.get(key);
					Double rd= new Double(-1D * bd.doubleValue());
					atable.put(key, rd);
				}
			}
		}
		else if (op.equals("*") || op.equals("/"))
		{
			if (btable.size() == 1 && btable.containsKey("CONSTANT"))
			{
				Double bd= (Double)btable.get("CONSTANT");
				String key;
				Double rd;
				for (Enumeration e= new SafeEnumeration(atable.keys());
					e.hasMoreElements();
					atable.put(key, rd))
				{
					key= (String)e.nextElement();
					Double ad= (Double)atable.get(key);
					if (op.equals("*"))
					{
						rd= new Double(ad.doubleValue() * bd.doubleValue());
					}
					else
					{
						rd= new Double(ad.doubleValue() / bd.doubleValue());
					}
				}
			}
			else if (atable.size() == 1 && atable.containsKey("CONSTANT"))
			{
				Double ad= (Double)atable.get("CONSTANT");
				String key;
				Double rd;
				for (Enumeration e= new SafeEnumeration(btable.keys());
					e.hasMoreElements();
					btable.put(key, rd))
				{
					key= (String)e.nextElement();
					Double bd= (Double)btable.get(key);
					if (op.equals("*"))
					{
						rd= new Double(ad.doubleValue() * bd.doubleValue());
					}
					else
					{
						rd= new Double(ad.doubleValue() / bd.doubleValue());
					}
				}
				atable= btable;
			}
			else
			{
				return(null);
			}
		}
		else if (op.equals("%"))
		{
			return(null);
		}
		if (isComparisonExpression())
		{
			atable.put("OPERATOR", op);
		}
		return(atable);
	}
	protected Object simplifyOperand(Object o)
	{
		Object r= null;
		boolean var= false;
		if (o instanceof String)
		{
			String s= (String)o;
			if (s.charAt(0) == '"')
			{
				r= s.substring(1, s.length() - 1);
			}
			else if (s.equalsIgnoreCase("null"))
			{
				r= "null";
			}
			else
			{
				r= s;
				var= true;
			}
			if (r==null)
			{
				r= "null";
			}
			try
			{
				Double d= Double.valueOf((String)r);
				r= d;
			}
			catch (Exception exception)
			{
				if (!var)
				{
					r= new Double(o.hashCode());
				}
			}
		}
		else if (o instanceof Number)
		{
			r= new Double(((Number)o).doubleValue());
		}
		return(r);
	}
	
	public boolean isSatisfiable()
	{
		try
		{
			Expression exp= new Expression(toString());
			Hashtable comparisons= new Hashtable();
			System.err.println(exp);
			exp.replaceComparisons(comparisons);
			System.err.println(exp);
			Expression cexp;
			for (Enumeration e= comparisons.keys();
				e.hasMoreElements();
				System.err.println("\t\t" + cexp.generateLinearForm()))
			{
				String k= (String)e.nextElement();
				cexp= (Expression)comparisons.get(k);
				System.err.println("\t" + k + " -> " + cexp);
			}
			Vector variables= new Vector();
			for (Enumeration e= exp.findVariables();
				e.hasMoreElements();
				variables.addElement(e.nextElement()))
			{
			}
			Vector satisfies= new Vector();
			boolean vals[]= new boolean[variables.size()];
			int mods[]= new int[variables.size()];
			int num= (int)Math.pow(2D, variables.size());
			for (int j= 0; j < variables.size(); j++)
			{
				vals[j]= false;
				mods[j]= (int)Math.pow(2D, j);
			}
			for (int i= 0; i < num; i++)
			{
				Hashtable hash= new Hashtable();
				for (int j= 0; j < variables.size(); j++)
				{
					if (i % mods[j] == 0)
					{
						vals[j]= !vals[j];
					}
					hash.put(variables.elementAt(j), new Boolean(vals[j]));
				}
				if (exp.isTrue(hash))
				{
					satisfies.addElement(hash);
				}
			}
			for (Enumeration e= satisfies.elements();
				e.hasMoreElements();
				System.err.println(e.nextElement()))
			{
			}
		}
		catch (ParseException ex)
		{
			System.err.println("Error parsing expression: " + ex.toString());
		}
		return(false);
	}
	
	public void replaceComparisons(Hashtable h)
	{
		int variable= 0;
		if (OperandTest.isExpression(a))
		{
			if (((Expression)a).isComparisonExpression())
			{
				for (; h.containsKey("__" + variable); variable++)
				{
				}
				h.put("__" + variable, a);
				a= "__" + variable;
			}
			else
			{
				((Expression)a).replaceComparisons(h);
			}
		}
		if (OperandTest.isExpression(b))
		{
			if (((Expression)b).isComparisonExpression())
			{
				for (; h.containsKey("__" + variable); variable++)
				{
				}
				h.put("__" + variable, b);
				b= "__" + variable;
			}
			else
			{
				((Expression)b).replaceComparisons(h);
			}
		}
	}
	
	public Enumeration findVariables()
	{
		Hashtable h= new Hashtable();
		if (OperandTest.isVariable(a))
		{
			h.put(a, new Boolean(true));
		}
		else if (OperandTest.isExpression(a))
		{
			for (Enumeration e= ((Expression)a).findVariables();
				e.hasMoreElements();
				h.put(e.nextElement(), new Boolean(true)))
			{
			}
		}
		if (OperandTest.isVariable(b))
		{
			h.put(b, new Boolean(true));
		}
		else if (OperandTest.isExpression(b))
		{
			for (Enumeration e= ((Expression)b).findVariables();
				e.hasMoreElements();
				h.put(e.nextElement(), new Boolean(true)))
			{
			}
		}
		return(h.keys());
	}
	
	public boolean isBooleanExpression()
	{
		if (op == null && (convertOperand(a, new Hashtable()) instanceof Boolean))
		{
			return true;
		}
		else
		{
			return OperatorTest.isBoolean(op);
		}
	}
	
	public boolean isComparisonExpression()
	{
		return(OperatorTest.isComparison(op));
	}
	
	public boolean isMathematicalExpression()
	{
		return(OperatorTest.isMathematical(op));
	}
	
	public String toString()
	{
		if (op == null)
		{
			return(a.toString());
		}
		if (a == null)
		{
			return("(" + op + " " + b + ")");
		}
		else
		{
			return("(" + a + " " + op + " " + b + ")");
		}
	}
	
	public static void main(String argv[])
	{
		Hashtable h= new Hashtable();
		h.put("a", "a");
		h.put("b", "b");
		h.put("c", "c");
		h.put("n1", "1");
		h.put("n2", "2");
		h.put("n3", "3");
		h.put("d", "abba gold");
		h.put("Y", "16");
		h.put("X", "64");
		h.put("R", "20");
		try
		{
			Expression e= new Expression("(a == \"a\")");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(b == \"a\")");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("((b == \"a\") || (a != \"a\"))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e=
				new Expression("((((b != \"a\") && (a != \"b\")) && (c == c)) && (d == \"abba gold\"))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e.isSatisfiable();
			e= new Expression("(((1 == n1) && (n1 == 1)) && (n1 != n2))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e.isSatisfiable();
			e= new Expression("(1 == n2)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(1 != n2)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(1 < n2)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(1 <= n2)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(1 <= n1)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(1 > n2)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(1 >= n2)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(2 >= n2)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e.isSatisfiable();
			e=
				new Expression("((a == \"a\") && ((n1 >= 0) && ((n1 < 5) || (n1 < 0))))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e.isSatisfiable();
			e= new Expression("((b == \"a\") && ((n1 >= 0) && (n1 < 5)))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(fake == null)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(fake != NULL)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(!(a == NuLl))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e.isSatisfiable();
			e= new Expression("((n1 + 5) > 5)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("((n1 / 5) > 5)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(((n1 + n2) - n3) == 0)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e.isSatisfiable();
			e= new Expression("((n3 % n2) == n1)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(_n3s == null)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			for (Enumeration en= e.findVariables();
				en.hasMoreElements();
				System.err.println("\t" + en.nextElement()))
			{
			}
			e= new Expression("(3 + 2)");
			System.err.println(
				((Expression)e.getOperand1()).toString()
					+ " -> "
					+ ((Expression)e.getOperand1()).generateLinearForm());
			e= new Expression("(a == 3)");
			System.err.println(
				((Expression)e.getOperand1()).toString()
					+ " -> "
					+ ((Expression)e.getOperand1()).generateLinearForm());
			e= new Expression("((3 + 2) * a)");
			System.err.println(
				((Expression)e.getOperand1()).toString()
					+ " -> "
					+ ((Expression)e.getOperand1()).generateLinearForm());
			e= new Expression("((((a + (2 - b)) * 5) - (d + 6)) == (5 * (4 + d)))");
			System.err.println(
				((Expression)e.getOperand1()).toString()
					+ " -> "
					+ ((Expression)e.getOperand1()).generateLinearForm());
			e=
				new Expression(
					"(((((21.0 + R) >= X) && ((21.0 - R) <= X)) && ((24.0 + R) >= Y)) && ((24.0 - R) "
						+ "<= Y))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(-21.0 < (-21 --1))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(-n1 == (+0 +-1))");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(3.0E-1 == 0.3)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(3.2E-5 == 0.3)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(3.2E-5 == 0.32E-4)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
			e= new Expression("(3.2E+1 == 320.0E-1)");
			System.err.println(e.toString() + " -> " + e.isTrue(h));
		}
		catch (ParseException ex)
		{
			System.err.println(ex.toString());
		}
	}
}
