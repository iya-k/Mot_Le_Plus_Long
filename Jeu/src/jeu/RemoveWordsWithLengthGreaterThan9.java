/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RemoveWordsWithLengthGreaterThan9 {

public static void main(String[] args) throws IOException{
    String dicoF="src/dicoSp.txt";
    String line="";
    String[] dicoWords;
    int countWord=0,i=0;
       System.out.println(" inside");
    try{
         System.out.println(" inside");
        BufferedReader br=new BufferedReader(new FileReader(dicoF));
        while((line=br.readLine())!=null){
            if(line.length()<=9){
               countWord++;
            }
        }
        dicoWords=new String[countWord];
        br.close();
        BufferedReader br1=new BufferedReader(new FileReader(dicoF));
        while((line=br1.readLine())!=null){
            if(line.length()<=9){
                dicoWords[i]=line;
                i++;
            }
        }
        br1.close();
        PrintWriter fp = new PrintWriter(new FileWriter(dicoF));
        fp.print("");
        fp.flush();
        fp.close();
        PrintWriter fp1 = new PrintWriter(new FileWriter(dicoF));
        for (String dicoWord:dicoWords) {
            fp1.println(dicoWord);
             System.out.println(dicoWord);
        }
        fp.flush();
        fp.close();

        
    }catch(FileNotFoundException e){
        e.printStackTrace();
    }
}
}
