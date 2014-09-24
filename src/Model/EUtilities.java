package Model;

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
}
