package com.example.coduolingo;

import android.util.Log;

public class Text {
    public static boolean findstring(String word, String text)//boolean: if word in text : true, else: false
    {
        return text.indexOf(word) > -1;
    }
    public static String replace (String text, char oldltr, char key, int num){
        int n = 0;
        String end = "";
        for ( char i : text.toCharArray()){
            if (i==oldltr){
                if(n>=num){
                end+=key;}
                else{
                    end+=i;
                }
                n++;

            }
            else{
                end+=i;
            }
        }
        return end;
    }

    public static int FindstrLocate(String word, String text)//find "word locate in string "text"
    {
        int end = 0;
        int check_split = 0;
        String[] parts = text.split(" ");
        for (String ii : parts){
            if (ii==word){
                end=check_split;
            }
            check_split++;
        }
        return  end;
    }
    public static String[] spacelit(String text){
        String[] parts = text.split(" ");
        return parts;
    }
    public static int FindarrayLocate(String word, String[] text)//find "word" locate in array
    {
        int end = 0;
        int check_split = 0;
        for (String ii : text){
            if (ii==word){
                end=check_split;
            }
            check_split++;
        }
        return  end;
    }
    public static String stopDot(String word){//stop in the first dot of string "text"
        char[] a = word.toCharArray();
        int b=-a.length;
        StringBuilder str = new StringBuilder();
        for (char i : a){
            if(i==".".charAt(0)){
                break;
            }
            else{
                str.append(i);
            }
        }
        return str.toString();
    }
    public static String between(String text, String start, String stop){
        String end = "";
        boolean is_start = false;
        String[] a = text.split(" ");
        for (String i : a) {
            Log.d("hello", i);
            if(i.equals(stop)){
                break;
            }
            if (is_start==true){
                end = end + i + " ";
            }
            if  (i.equals(start)){
                is_start = true;
            }
        }
        Log.d("end", end);
        return end.substring(0, end.length() - 1);
    }
    public static String IfwordIs(String text, String start){
        String end = "";
        boolean is_start = false;
        String[] a = text.split(" ");
        for (String i : a) {
            Log.d("hello", i);
            if (is_start==true){
                end = end + i + " ";
            }
            if  (i.equals(start)){
                is_start = true;
            }
        }
        Log.d("end", end);
        return end.substring(0, end.length() - 1);
    }
    public static String toSpace(String text){
        String end = "";
        char[] a = text.toCharArray();
        for (char i : a) {
            Log.d("hello", Character.toString(i));
            end = end + i + " ";

        }
        Log.d("end", end);
        return end;
    }
    public static String betweenLetter(String text, String start, String stop){
        String end = "";
        boolean is_start = false;
        String[] a = text.split(" ");
        for (String i : a) {
            Log.d("hello", i);
            if(findstring(stop, i)){
                break;
            }
            if (is_start==true){
                end = end + i;
            }
            if  (findstring(start, i)){
                is_start = true;
            }
        }
        Log.d("end", end);
        return end;
    }
    public static String join(String text, String join){
        String[] a = text.split(" ");
        String ending = "";
        for(String word : a){
            ending+= word + join;
        }
        return ending.substring(0, ending.length() - 1);
    }
}
