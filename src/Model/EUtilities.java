package Model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EUtilities {
	
	public static String ReadXML(String xml)
	{
		String res = "";
		
		return res;
	}
	
	public static String getWebEnv(String xml)
	{
		String res ="";
		Pattern  start = Pattern.compile("<WebEnv>.*</WebEnv>");
		Matcher m = start.matcher(xml);
		
		if(m.find())
		{
			res = (xml.substring(m.start() + 8,m.end() - 9)); 
		}
		
		return res;
	}
	
	public static String getQueryKey(String xml)
	{
		String res ="";
		Pattern  start = Pattern.compile("<QueryKey>.*</QueryKey>");
		Matcher m = start.matcher(xml);
		
		if(m.find())
		{
			res = (xml.substring(m.start() + 10,m.end() - 11)); 
		}
		
		return res;
	}
	
	public static ArrayList<Species> getSpecies(String xml)
	{
		char open = '[';
		char close = ']';
		
		ArrayList<Species> res = new ArrayList<Species>();
	
		int skip, end, endGenome, start;
		start = -1;
		end = 0;
		endGenome = 0;
		skip = 0;
		
		while(-1 != end)
		{
			start = xml.indexOf(open,skip) + 1;
			end = xml.indexOf(close,skip);
			endGenome = xml.indexOf(open, end);
	
			if(end != -1)
			{
				endGenome = endGenome == -1 ? xml.length() : endGenome;
				Species spe = new Species(xml.substring(start, end) , xml.substring(end +1 , endGenome));
				res.add(spe);	
			}

			skip = endGenome;
		}
	
		return res;
	}
}
