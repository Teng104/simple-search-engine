package com.hpds.extract;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class Extract 
{
    public static void main( String[] args ) throws IOException
    {
    	String AbsPath = "C:/Bitnami/wampstack-5.5.30-0/apache2/htdocs/Data";
    	File folder = new File(AbsPath);
    	String[] list = folder.list();
    	
    	//System.out.println(list.length);
    	for (int i = 0; i < list.length; i++) {
    		FileReader fr = new FileReader(AbsPath + "/" + list[i]);
    		BufferedReader br = new BufferedReader(fr);
    		StringBuffer sb = new StringBuffer("");
    		
    		int ch;
    	    while ((ch=br.read()) != -1) {
    			//System.out.println(text);
    			sb.append((char)ch);
    		}
    	    
    	    //System.out.println(sb.toString());
    	    extract(sb.toString(), list[i]);
    		
    		fr.close();
    	}
    }
    
    public static void extract(String txt, String fName) throws IOException {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        //System.out.println(segmenter.process(txt, SegMode.INDEX).toString());
        
        String temp = segmenter.process(txt, SegMode.INDEX).toString();
        //System.out.println(temp);
        
        BufferedWriter bw= null;
        try {
        	bw = new BufferedWriter( new FileWriter("D:/學科/網路資訊檢索與文字探勘/extract/" + fName));
            bw.write(temp);
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
