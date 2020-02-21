//package jeuu;
package jeu;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class getWordsFromCharactersInDictionary  {
	static ArrayList<String> wordsList;
	private static Arbre arbreTest;
	static String longWord;
	static int index,j=-1;
	
	public String getWordsExistingInDictionary(char charSet[]){
		int l=charSet.length;
                //System.out.println(" l ==  "+l+" charSet==  "+new String(charSet));
	 //Reading the dictionary content and making tree with it
		BufferedReader br;
		arbreTest=new Arbre();
		wordsList=new ArrayList<String>();
		try {

			br = new BufferedReader(new FileReader(new Menu().getDicoFileName()));
			String line = "";
			/*lire le fichier ligne par ligne avec un mot par ligne */
			 while ((line = br.readLine()) != null) { 
			    	/*ajout les lettre du mot a l'arbre*/
                                 if(line.trim().length()<=9)
				 	arbreTest.insert(line);
                                        //System.out.println("Line read == "+line);
				 	//System.out.println(line);
		        }
		     br.close();
                     

		} 
		catch (FileNotFoundException e) {

			System.out.println(e.getLocalizedMessage());
		} 

		catch (IOException e) {

			System.out.println(e.getLocalizedMessage());
		}
	// End of reading content
                printAllKLength(charSet, l);
                    int i=0;
                    longWord=wordsList.get(0);
                    for(i=1;i<wordsList.size();i++){
                            if(longWord.length()<wordsList.get(i).length())
                                    longWord=wordsList.get(i);
                            //System.out.println(wordsList.get(i));
                    }
		
		return longWord;
	}
	
	// The method that prints all possible strings of length k. It is
	// mainly a wrapper over recursive function printAllKLengthRec()
	public void printAllKLength(char set[], int k) {
		int n = set.length;	 
                //System.out.println("printAllKLength == "+new String(set)+" k == "+k);
                printAllKLengthRec(set, "", n, k);
	}

	// The main recursive method to print all possible strings of length k
	public void printAllKLengthRec(char set[], String prefix, int n, int k) {
		// Base case: k is 0, print prefix
               // System.out.println("prefix === "+prefix+" set == "+new String(set)+" k == "+k);
		if ((k == 0)) {
			String match=arbreTest.getMatchingPrefix(prefix);
			if(!match.equalsIgnoreCase("") && wordsList.contains(match)==false){
				wordsList.add(match);
                                //System.out.println(match);
                        }
                        //JOptionPane.showMessageDialog(null, wordsList.get(k+1));
			return;
		}

		// One by one add all characters from set and recursively 
		// call for k equals to k-1
		
			for (int i = 0; i < n; ++i) {
				// Next character of input added
				String newPrefix = prefix + set[i]; 
				// k is decreased, because we have added a new character
				printAllKLengthRec(set, newPrefix, n, k - 1); 
			}
	}
}
