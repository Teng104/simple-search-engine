package com.hpds.mapName;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class mapName 
{
	static String AbsPath = "D:/學科/網路資訊檢索與文字探勘/map";
	private final static int INDEX_SIZE = 200;
	
    public static void main( String[] args ) throws IOException
    {
    	Map<String, String>[] map = new Map[INDEX_SIZE];
    	for(int c = 0; c < INDEX_SIZE; c++) {
    		map[c] = new HashMap<String, String>();
    	}
    	
    	File folder = new File(AbsPath);
    	String[] list = folder.list();
    	int[] num = new int[list.length];
    	
    	for (int i = 0; i < list.length; i++) {
    		num[i] = 0;
    		num[i] = count(list[i]);
    	}
    	
    	for (int c = 177; c < INDEX_SIZE; c++) {
    		for (int x = 0; x < list.length; x++) {
        		FileReader fr = new FileReader(AbsPath + "/" + list[x]);
        		BufferedReader br = new BufferedReader(fr);
        		StringBuffer[] key = new StringBuffer[num[x]];
        		//System.out.println(num[x]);
        		int k = 0;
        		int index = 0;
        		Boolean first = false;
        		Boolean start = false;
        		Boolean recordK = false;    		
      
        		int ch;
        	    while ((ch=br.read()) != -1) {
        	    	if(first == true){
        	    		start = true;
        	    		first = false;
        	    	}
        	    	
        			while((char)ch == '{' && first == false && start == false){
        				first = true;
        				System.out.println("Start to parse file " + list[x]);
        				break;
        			}
        			
        			if(start == true) {
        				 if((char)ch == '"' && recordK == false) {
        					 recordK = true;
        					 key[k] = new StringBuffer("");
        				 } else if ((char)ch == '"' && recordK == true) {
        					 recordK =false;
        					 k++;
        				 } else if (recordK == true && (char)ch != ' ' && (char)ch != '\n' && (char)ch != '\r') {
        					 key[k].append((char)ch);
        				 }
        			}
        	    }
        		fr.close();
        		
        		for (int y = 0; y < k; y++){
        	    	String t = key[y].toString();
        	    	if(!t.isEmpty()){
        	    		index = t.charAt(0) % INDEX_SIZE;
        	    	}
        	    	if(index == c){
        	    		String s = map[index].get(t);
            		    map[index].put(t, (s == null)?list[x]:s + "," + list[x]);
        	    	}
        	    }
        	}
        		System.out.println(c);
        		printMap(map[c], c);
    	}
    	
    	System.out.println("Done.");
    }
    
    public static int count(String fName) throws IOException {
       	FileReader fr = new FileReader(AbsPath + "/" + fName);
    	BufferedReader br = new BufferedReader(fr);
    	int count = 0;
    	Boolean check = false;
    	
    	int ch;
    	while((ch=br.read()) != -1) {
    		if((char)ch == '"' && check == false){
    			check = true;
    		} else if ((char)ch == ':' && check == true) {
    			count++;
    		} else if ((char)ch == '"' && check == true) {
    			
    		} else {
    			check = false;
    		}
    	}
    	fr.close();
    	//System.out.println(count);
    	return count;
    }
    
    public static void printMap(Map<String, String> map, int index) {
    	Boolean first = true;
    	BufferedWriter bw= null;
    	
        try {
        	bw = new BufferedWriter( new FileWriter("D:/學科/網路資訊檢索與文字探勘/hash/name/" + index + ".txt"));
        	for (Map.Entry<String, String> entry : map.entrySet()) {
        		if(first == true) {
        			bw.write(entry.getKey() + ":" + entry.getValue());
        			first = false;
        		} else {
        			bw.write("\r\n" + entry.getKey() + ":" + entry.getValue());
        		}
        		//System.out.println(entry.getKey() + ", " + entry.getValue());
            }
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
