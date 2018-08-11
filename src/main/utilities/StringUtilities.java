package main.utilities;

import java.util.ArrayList;
import java.util.List;

public class StringUtilities { 
	
	public static List<String> decomposeValueSeperatedString(String sourceString, char delimiter){
		List<String> row = new ArrayList<String>();
		char[] searchArray = sourceString.toCharArray();
		int startPosition = 0;
		String addString;
		for (int i =0; i < searchArray.length;i++) {
			if(searchArray[i]==delimiter) {			
				addString = sourceString.substring(startPosition,i).replace("'", "");
				addString = addString.replace("\"", "");
				row.add(addString);			
				startPosition = i + 1;			
			}
		}
		row.add(sourceString.substring(startPosition).replace("\"", ""));	
		return row;
	} 

}
