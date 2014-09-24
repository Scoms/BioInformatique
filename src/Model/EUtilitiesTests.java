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
		   EUtilities eUtils = new EUtilities();
		   
		   String db = "protein";
		   String id_list = "194680922,50978626,28558982,9507199,6678417";
		   
		   String base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
		   String url = base + "epost.fcgi?db=" + db + "&id=" + id_list;
		   
		   String xml = getHTML(url);
		   
		   String web = EUtilities.getWebEnv(xml);
		   String key = EUtilities.getQueryKey(xml);

		   url = base + "esummary.fcgi?db="+db+"&query_key="+key+ "&WebEnv=" +web;

		   String docsums = getHTML(url);
		   System.out.println("Docsums :");
		   RenderXML(docsums);
		   
		   url = base + "efetch.fcgi?db="+db+"&query_key=" + key + "&WebEnv="+web;
		   url += "&rettype=fasta&retmode=text";

		   String data = getHTML(url);

		   System.out.println("Data :");
		   RenderXML(data);
	   }

	private static void RenderXML(String html) {
		// TODO Auto-generated method stub
		
		   System.out.println((html));
		   System.out.println();
	}
}
