package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EUtilitiesTests {
	
	static String urlPrefix = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
	
	
	public static String getHTML(String urlToRead) {
	      URL url;
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      try {
	         url = new URL(urlToRead);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return result;
	   }

	   public static void main(String args[])
	   {
		   String db ="gene";
		   String query = "asthma[mesh]+AND+leukotrienes[mesh]+AND+2009[pdat]";

		   String base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
		   String url = base + "esearch.fcgi?db="+db+"&term="+query+"&usehistory=y";
		   
		   System.out.println(getHTML(url));
	   }
}
