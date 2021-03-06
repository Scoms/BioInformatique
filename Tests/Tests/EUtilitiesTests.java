package Tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;

import Files.FilesUtils;
import Model.EUtilities;
import Model.Species;

public class EUtilitiesTests {
	
	static String base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
	
	
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

		public static void FirstTest()
		{
			EUtilities eUtils = new EUtilities();
			   
			   String db = "protein";
			   String id_list = "194680922,50978626,28558982,9507199,6678417";
			   id_list = "28558982";
			   
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
			   
			   ArrayList<Species> species = new ArrayList<Species>();
			   species = EUtilities.getSpecies(data);
			   
			   for (Species spe : species) 
			   {
				   System.out.println(spe);
			   }
			   
			   System.out.println("\n phase 2 \n");
			   
			   String dbGene = "gene";    
			   String linkname = "protein_gene";
			   
			   url = base + "elink.fcgi?dbfrom=" + db + "&db=" + dbGene + "&id=" + id_list;
			   url += "&linkname=" +linkname + "&cmd=neighbor_history";

			   xml = getHTML(url);

			   web = EUtilities.getWebEnv(xml);
			   key = EUtilities.getQueryKey(xml);
			   
			   url = base + "esummary.fcgi?db=" + db + "&query_key=" + key + "&WebEnv=" + web;

			   System.out.println("\n xml \n");
			   System.out.println(key);
			   xml = getHTML(url);
			   RenderXML(xml);
		
			   url = base + "efetch.fcgi?db=" + dbGene + "&query_key=" + key + "&WebEnv=" +web ;//+ "&id_list=" + id_list;
			   url += "&rettype=xml&retmode=xml";

			   System.out.println("\n data \n");
			   System.out.println(url);
			   data = getHTML(url);
			   System.out.println("Render");
			   //RenderXML(data);
			   FilesUtils.SaveFile("test", data);
		}
	
		/*
		 * Récupère les génomes des chimpanzés. 
		 * Le résultat de la requete est le bon mais c'est lent et une erreur sort du try catch ...
		 * http://www.ncbi.nlm.nih.gov/books/NBK25498/ -> application 3 
		 */
		public static void SecondTest()
		{
			// chimpanzee peut etre remplacé par "human" par exemple. 
			String query = "chimpanzee[orgn]+AND+biomol+mrna[prop]";

			//query = "human[orgn]";
			String url = base + "esearch.fcgi?db=nucleotide&term=" + query + "&usehistory=y";

			String data = getHTML(url);
			RenderXML(data);

			String webEnv = EUtilities.getWebEnv(data);
			String key = EUtilities.getQueryKey(data);
			String countS = EUtilities.getCount(data);
			int count = Integer.parseInt(countS);
			//open(OUT, ">chimp.fna") || die "Can't open file!\n";
			
			int retmax = 500;
			int retstart;
			
			long start = System.currentTimeMillis();
			long chrono = System.currentTimeMillis();
			
			for (retstart = 0; retstart < count; retstart += retmax) 
			{
				String efetch_url = base + "efetch.fcgi?db=nucleotide&WebEnv=" + webEnv;
		        efetch_url += "&query_key=" + key + "&retstart=" + retstart;
		        efetch_url += "&retmax=" + retmax + "&rettype=gb&retmode=text";
		        
		        try 
		        {
			        System.out.print("read : ");
			        String content = getHTML(efetch_url);
			        
			        chrono = System.currentTimeMillis();
			        PrintTime(chrono - start);
			        System.out.print("write : ");
			        
			        start = System.currentTimeMillis(); 
			        FilesUtils.SaveFile("" + retstart, content);
			        chrono = System.currentTimeMillis();
			        PrintTime(chrono - start);
			        start = chrono;
			        System.out.println();
				} 
		        catch (Exception e) 
		        {
					System.out.println("ERROR");
				}
	        }
		}
		
		public static void GetArborescence()
		{
			String query = "Animals[orgn]";
			String url = base + "esearch.fcgi?db=nucleotide&term=" + query + "&usehistory=y";

			String data = getHTML(url);
			RenderXML(data);
		}
		
	   public static void main(String args[])
	   {
		   //FirstTest();
		   SecondTest();
		   
		   //GetArborescence();
	   }

	private static void RenderXML(String html) {
		   System.out.println((html));
		   System.out.println();
	}
	private static void PrintTime(long millis)
	{
		int seconds = (int) (millis / 1000) % 60 ;
		int minutes = (int) ((millis / (1000*60)) % 60);
		System.out.print(String.format("%d min, %d sec ", 
			    minutes, seconds));	
	}
}
