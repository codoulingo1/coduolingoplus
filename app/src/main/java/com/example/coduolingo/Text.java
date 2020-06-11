package com.example.coduolingo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Text {
    public static boolean findstring(String word, String text)//boolean: if word in text : true, else: false
    {
        return text.indexOf(word) > -1;
    }
    public static String replace (String text, char oldltr, char key, int num){// replace function
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
        int b =-a.length;
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
    public static List<String> SEbetween(String text, char start, char stop){
        String bet = "";
        String st = "";
        boolean is_end = false;
        String end = "";
        boolean is_start = false;
        char[] a = text.toCharArray();
        for (char i : a) {
            Log.d("hello", String.valueOf(i));
            if  (i==stop && is_start){
                is_end = true;
            }
            else if  (Objects.equals(i, start) && !is_end){
                is_start = true;
            }
            if (is_start){
                if(!is_end) {
                    bet = bet + i + " ";
                }else{
                    end = end + i + " ";
                }
            }
            else{
                st = st + i + " ";
            }
        }
        Log.d("end", bet);
        final List<String> t;
        t = new ArrayList<String>();
        Log.d("check", "st" + st + "bet" + bet + "end" + end);
        try{
            t.add(st.substring(0, st.length() - 1));
        }catch (Exception e){
            t.add(st);
        }
        try{
            t.add(bet.substring(0, bet.length() - 1));
        }catch (Exception e){
            t.add(bet);
        }
        try {
            t.add(end.substring(0, end.length() - 1));
        }catch (Exception e){
            t.add(end);
        }
        return t;
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
    public static List<Integer> betweenIndex(String text, char start, char stop){
        List<Integer> end = new ArrayList<Integer>();
        boolean is_start = false;
        char[] a = text.toCharArray();
        for (int i =0; i<a.length; i++) {
            //Log.d("hello", String.valueOf(a[i]));
            if(a[i]==stop & is_start==true){
                end.add(i);
                is_start=false;
            }
            if (is_start==true){
                end.add(i);
            }
            if  (a[i]==start){
                is_start = true;
                end.add(i);
            }
        }
        Log.d("end", end.toString());
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
    public static String getRandomString(final int sizeOfRandomString)
    {
        String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
    public static boolean eqnova(String a, String b, int from_n){
        char[] text = a.toCharArray();
        boolean eq = true;
        boolean err1 = false;
        char[] check = b.toCharArray();
        check = Arrays.copyOfRange(check, from_n, check.length);
        text = Arrays.copyOfRange(text, from_n, text.length);
        if(text.length-check.length > 1 || text.length-check.length<-1){
            eq=false;
        }
        Log.d(String.valueOf(text.length), String.valueOf(check.length));
        for (int i = from_n; i < check.length; i++) {
            try {
                if (text[i] != check[i]) {
                    if (!err1) {
                        err1 = true;
                    } else if (err1) {

                        eq = false;
                    }
                }
            }catch (Exception e){

            }
        }
        return eq;
    }
}
