package Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FilesUtils {

	public static String path = "TestFiles/";
	
	public static void SaveFile(String fileName, String content)
	{
		PrintWriter writer;
		try {
			File file = new File(path + fileName + ".txt");
			writer = new PrintWriter(file, "UTF-8");
			writer.println(content);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
