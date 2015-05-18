package com.database.util;


/**
 * ��ȡ����
 * @author Shadow
 *
 */
public class StrCounterUtil {
	public static int count(String text,String sub){  
        int count =0, start =0;  
        while((start=text.indexOf(sub,start))>=0){  
            start += sub.length();  
            count ++;  
        }  
        return count;  
    }  
}	
