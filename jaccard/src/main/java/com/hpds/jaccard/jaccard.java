package com.hpds.jaccard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class jaccard 
{
	private final static int INDEX_SIZE = 200;
	static String mapName ="D:/學科/網路資訊檢索與文字探勘/hash/name/";
	
    public static void main( String[] args ) throws IOException
    {
        String query = new String("化學反應");
        String q = new String("");
        int number = 0;
        
        q = extract(query);
        System.out.println(q);
        
        number = count(q);
        String[] subQuery = new String[number];
        subQuery = parse(q, number);
        
        Map<String, String>[] map = new Map[number];
        
        String[] fileName = new String[number];
        
        for(int a = 0; a < number; a++){
        	fileName[a] = match(subQuery[a]);
        	System.out.println(fileName[a]);
        }
        
        
    }
    
    public static String extract(String query) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        //System.out.println(segmenter.process(txt, SegMode.INDEX).toString());
        
        String temp = segmenter.process(query, SegMode.INDEX).toString();
        //System.out.println(temp);
        
        return temp;
    }
    
    public static int count(String q) {
		int count = -1;
		
		for(int i = 0; i < q.length(); i++) {
			if(q.charAt(i) == '[') {
				count++;
			}
		}
		System.out.println(count);
		return count;
    }
    
    public static String[] parse(String q, int num) {
		StringBuffer[] temp = new StringBuffer[num];
		String[] result = new String[num];
		int i = 0;
		Boolean first = false;
		Boolean start = false;
		Boolean record = false;
		
		for(int x = 0; x < q.length(); x++) {
			if(first == true){
				start = true;
		    	first = false;
		    }
		    	
			if(q.charAt(x) == '[' && first == false && start == false){
				first = true;
			}
				
			if(start == true) {
				 if(q.charAt(x) == '[' && record == false) {
					 record = true;
					 temp[i] = new StringBuffer("");
				 } else if(q.charAt(x) == ',' && record == true) {
					 record =false;
					 i++;
				 } else if(record == true && q.charAt(x) != ' ' && q.charAt(x) != '\n' && q.charAt(x) != '\r' && q.charAt(x) != '\"') {
					 temp[i].append(q.charAt(x));
				 }
			}
		}
		
		for(int y = 0; y < num; y++) {
			result[y] = new String("");
			result[y] = temp[y].toString();
		}
		
		return result;
    }
    
    public static String match(String sub) throws IOException {
    	int hash = 0;
    	hash = sub.charAt(0) % INDEX_SIZE;
    	
    	FileReader fr = new FileReader(mapName + hash + ".txt");
		BufferedReader br = new BufferedReader(fr);
		StringBuffer sb = new StringBuffer("");
		
		int ch;
	    while ((ch=br.read()) != -1) {
			sb.append((char)ch);
		}
	    
	    fr.close();
	    
	    Boolean recordK = true;
	    Boolean recordV = false;
	    StringBuffer comp = new StringBuffer("");
	    StringBuffer result = new StringBuffer("");
	    
	    
	    for(int i = 0; i < sb.length(); i++) {
	    	if(recordK == true && sb.charAt(i) == ':'){
	    		recordK = false;
	    		//System.out.println(comp.toString());
	    		if(sub.equals(comp.toString())) {
	    			recordV = true;
	    		}
	    		comp = new StringBuffer("");
	    	} else if (recordK == true && sb.charAt(i) != ' ' && sb.charAt(i) != (char)10 && sb.charAt(i) != (char)13) {
	    		comp.append(sb.charAt(i));
	    	} else if ( recordV == false && recordK == false && sb.charAt(i) == (char)10) {
	    		recordK = true;
	    	}
	    	
	    	if(recordV == true && sb.charAt(i) != ':') {
	    		result.append(sb.charAt(i));
	    		System.out.println(sb.charAt(i));
	    	} else if (recordV == true && (int)sb.charAt(i) == 10) {
	    		return result.toString();
	    	}
	    }
		
		return "0";
    }
}
