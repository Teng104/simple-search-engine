package com.hpds.parse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parse 
{
	static String AbsPath = "D:/學科/網路資訊檢索與文字探勘/extract";
    public static void main( String[] args ) throws IOException
    {
    	
    	File folder = new File(AbsPath);
    	String[] list = folder.list();
    	int number;
    	
    	for (int i = 0; i < list.length; i++) {
    		number = count(list[i]);
    		parse(list[i], number);
    	}
    }
    
    public static int count(String fName) throws IOException {
    	FileReader fr = new FileReader(AbsPath + "/" + fName);
		BufferedReader br = new BufferedReader(fr);
		int count = 0;
		
		int ch;
		while((ch=br.read()) != -1) {
			if((char)ch == '['){
				count++;
			}
		}
		fr.close();
		System.out.println(count);
		return count;
    }
    
    public static void parse(String fName, int num) throws IOException {
    	FileReader fr = new FileReader(AbsPath + "/" + fName);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer[] temp = new StringBuffer[num];
		int i = 0;
		Boolean first = false;
		Boolean start = false;
		Boolean record = false;
		
		int ch;
	    while ((ch=br.read()) != -1) {
	    	if(first == true){
	    		start = true;
	    		first = false;
	    	}
	    	
			while((char)ch == '[' && first == false && start == false){
				first = true;
				System.out.println("Start to parse file " + fName);
				break;
			}
			
			if(start == true) {
				 if((char)ch == '[' && record == false) {
					 record = true;
					 temp[i] = new StringBuffer("");
				 } else if((char)ch == ',' && record == true) {
					 record =false;
					 i++;
				 } else if(record == true && (char)ch != ' ' && (char)ch != '\n' && (char)ch != '\r' && (char)ch != '\"') {
					 temp[i].append((char)ch);
				 }
			}
		}
	    
	    fr.close();
	    
	    Map<String, Integer> map = new HashMap<String, Integer>();
	    for (int x = 0; x < i; x++){
	    	String t = temp[x].toString();
	    	Integer c = map.get(t);
		    map.put(t, (c == null)?1:c+1);
	    }
	    printMap(map, fName);
    }
    
    public static void printMap(Map<String, Integer> map, String fName) {
    	Boolean first = true;
    	BufferedWriter bw= null;
    	
        try {
        	bw = new BufferedWriter( new FileWriter("D:/學科/網路資訊檢索與文字探勘/map/" + fName));
        	bw.write('{');
        	for (Map.Entry<String, Integer> entry : map.entrySet()) {
        		if(first == true) {
        			bw.write("\"" + entry.getKey() + "\":" + entry.getValue());
        			first = false;
        		} else {
        			bw.write(",\"" + entry.getKey() + "\":" + entry.getValue());
        		}
            }
        	bw.write('}');
        } catch ( IOException e) {
        } finally {
            try {
            	if ( bw != null)
                bw.close( );
            } catch ( IOException e) {
            }
        }
    }
}
