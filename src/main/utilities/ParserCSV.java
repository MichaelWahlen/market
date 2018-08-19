package main.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParserCSV {
		
		private ParserCSV() {			
		}
		
		public static List<String> parseToStrings(File sourceFile) {
		BufferedReader reader = null;
	    String line = null;
	    List<String> parsedRows = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader (sourceFile.getPath()));							
			while((line = reader.readLine()) != null) {
                parsedRows.add(line);
            }
			reader.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}       
		return parsedRows;		
	}

	

	


}
