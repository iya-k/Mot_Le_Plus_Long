//package jeuu;
package jeu;
  import java.util.HashMap;
 
// Arbre Node, which stores a character and the children in a HashMap
class ArbreNode {
    public ArbreNode(char ch)  {
        value = ch;
        children = new HashMap<Character, ArbreNode>();
        estFin = false;
    }
    public HashMap<Character,ArbreNode> getChildren() {   return children;  }
    public char getValue()                           {   return value;     }
    public void setFinMot(boolean val)                {   estFin = val;     }
    public boolean isEnd()                           {   return estFin;    }
 
    private char value;
    private HashMap<Character,ArbreNode> children;
    private boolean estFin;
}
 
// Implementation de larbre Actuel
class Arbre {
    // Constructeur
    public Arbre()   {     root = new ArbreNode((char)0);       }    
 
    // Method pour ajouter nouveau mot dans larbre
    public void insert(String word)  {
 
        //  Recuperer la longueur du mot
        int length = word.length();
        ArbreNode travers = root;
 
        // Parcourir les lettres du mots
        for( int level = 0; level < length; level++)
        {
            HashMap<Character,ArbreNode> child = travers.getChildren();
            char ch = word.charAt(level);
 
            // If there is already a child for current character of given word
            if( child.containsKey(ch))
                travers = child.get(ch);
            else   // Sinon on cree un nouveau node
            {
                ArbreNode temp = new ArbreNode(ch);
                child.put( ch, temp );
                travers = temp;
            }
        }
 
        // mettre find de larbre a true si c'est un child node
        travers.setFinMot(true);
    }
 
    // The main method that finds out the longest string 'input'
    public String getMatchingPrefix(String input)  {
        String result = ""; // Initializer la chaine resultante 
        int length = input.length();  // Find length of the input string       
 
        // Initialize reference to traverse through Arbre
        ArbreNode travers = root;   
 
        // Iterate through all characters of input string 'str' and traverse
        // down the Arbre
        int level, prevMatch = 0;
        for( level = 0 ; level < length; level++ )
        {
            // Find current character of str
            char ch = input.charAt(level);    
 
            // HashMap of current Arbre node to traverse down
            HashMap<Character,ArbreNode> child = travers.getChildren();                        
 
            // See if there is a Arbre edge for the current character
            if( child.containsKey(ch) )
            {
               result += ch;          //Update result
               travers = child.get(ch); //Update travers to move down in Arbre
 
               // If this is end of a word, then update prevMatch
               if( travers.isEnd() )
                    prevMatch = level + 1;
            }
            else  break;
        }
 
        // If the last processed character did not match end of a word,
        // return the previously matching prefix
        /*if( result.equals(input) )
            return result;    
    	else 
    		return "";*/
        if( !travers.isEnd() )
                return result.substring(0, prevMatch);        
 
        else return result;
    }
 
    private ArbreNode root;
}