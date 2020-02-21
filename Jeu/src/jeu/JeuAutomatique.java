package jeu;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.text.Normalizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JeuAutomatique extends JFrame{
    /**
     * 
     */
    private javax.swing.JLabel jlabel;
    private int xaxis=15;
    private static String[] splitWords;
    private static String word;
    private static String wordMixed;
    private static final long serialVersionUID = 1L;
    private JPanel jpanelTitle,jpanelSaisi;
    private JLabel titleText,propositionText,votreReponse,reponseOrdinateur,dureeRecherch,dureeRecherchValue;
    
    private JTextField votreProposition,votreReponseText,reponseOrdinateurText;
    private JButton buttonJouer;
    private JButton buttonRejouer;
    private JButton buttonRetour;
    private Container c;
    char charValues[]={};
    private static Arbre arbreInstance;
    getWordsFromCharactersInDictionary getWords;
    static String response="",response1="";
    /*private JTextField charTextBox;*/
    long lEndTime=0,lStartTime=0,tempsEcoule;

    public JeuAutomatique(){
        super("Mot le plus long");
        setSize(700, 450);
        setLocationRelativeTo(null);
        c=getContentPane();
        setLayout(null);
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setLayout(null);
        setVisible(true);
        try{
        int randInt = randInt(1,getnbrline());
        word= getword(randInt);
         Random r = new Random();
        wordMixed = scramble( r, word );
        String str2 = wordMixed;
        String[] str1Array =  str2.split("");
        splitWords=str1Array;
        }catch(Exception e){
            e.printStackTrace();
        }
        for (String splitWord : splitWords) {
            jlabel = new javax.swing.JLabel("" + splitWord);
            //jlabel.setText();
            jlabel.setHorizontalAlignment(JLabel.CENTER);
            jlabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
            jlabel.setSize(40, 40);
            jlabel.setLocation(xaxis,80);
            xaxis=xaxis+30;
            c.add(jlabel);
    }}
  public static int getnbrline() throws IOException {
       arbreInstance=new Arbre();
       BufferedReader in = new BufferedReader(new FileReader(new Menu().getDicoFileName()));
        int lines=0;
        try{
                String line = "";
            /*lire le fichier ligne par ligne avec un mot par ligne */
                while ((line = in.readLine()) != null) {
                          arbreInstance.insert(line);
                    lines++;
                }
            }
            catch (IOException e) {
		 System.out.println(e.getLocalizedMessage());
	    }
        in.close();
        return lines;
        
}
  
  public static String scramble( Random random, String inputString ){
    // Convert your string into a simple char array:
    char a[] = inputString.toCharArray();
    // Scramble the letters using the standard Fisher-Yates shuffle, 
    for( int i=0 ; i<a.length-1 ; i++ ){
        int j = random.nextInt(a.length-1);
        // Swap letters
        char temp = a[i]; a[i] = a[j];  a[j] = temp;
    }       
    return new String( a );
}
    
public static int randInt(int min, int max) {

    
    Random rand = null;
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);

    return randomNum;
}
public static String getword(int n) throws IOException{
    String word;
        try (BufferedReader in = new BufferedReader(new FileReader(new Menu().getDicoFileName()))) {
            word = "";
            for (int i = 0; i<n; i++)
                word=in.readLine();
                in.close();
            }
        System.out.println(word);
        System.out.println(new Menu().getDicoFileName());
    return processedWord(word);
}
/*public static List<String> combi(String[] choix, List<String> listeString, int nbLettre) {
		List<String> nouvelleListeString = new ArrayList<String>();
 
		for (String stringReference : choix) {
			for (String string : listeString) {
				if (string.length() == nbLettre) {
					return listeString;
				}
				nouvelleListeString.add(stringReference + string);
			}
		}
		return combi(choix, nouvelleListeString, nbLettre);
}*/
    public void initComponents(){
        jpanelTitle=new JPanel();
        jpanelTitle.setLayout(null);
        titleText=new JLabel("Cherchez le mot le plus long possible avec ces lettres :");
        titleText.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleText.setSize(600, 30);
        titleText.setLocation(5,10);
        jpanelTitle.setSize(650, 50);
        jpanelTitle.setLocation(40, 15);
        jpanelTitle.add(titleText);
        c.add(jpanelTitle);
        // Le Panel qui contiendra les cases des characteres proposes
        /*jpanelCase=new JPanel();
        jpanelCase.setLayout(null);
        jpanelCase.setSize(500, 80);
        jpanelCase.setLocation(40, 80);
        c.add(jpanelCase);*/
       
        /* default text boxes 
        for (String splitWord : splitWords) {
            jlabel = new javax.swing.JLabel("" + splitWord);
            //jlabel.setText();
            jlabel.setHorizontalAlignment(JLabel.CENTER);
            jlabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
            jlabel.setSize(40, 40);
            jlabel.setLocation(xaxis,80);
            xaxis=xaxis+30;
            jpanelCase.add(jlabel);
        }        
	*/ //end of default textboxes
	
		
        jpanelSaisi=new JPanel();
        jpanelSaisi.setLayout(null);
        jpanelSaisi.setSize(600, 80);
        jpanelSaisi.setLocation(40, 170);
        //jpanelSaisi.setBackground(Color.black);
        c.add(jpanelSaisi);
        propositionText=new JLabel("<html>Votre <br> Proposition</html>");
        propositionText.setFont(new Font("Times New Roman",Font.CENTER_BASELINE,14));
        propositionText.setSize(100,50);
        propositionText.setLocation(5, 10);
        jpanelSaisi.add(propositionText);
        votreProposition=new JTextField();
        votreProposition.setSize(370, 30);
        votreProposition.setFont(new Font("Times New Roman",Font.BOLD,24));
        votreProposition.setLocation(110, 15);
        jpanelSaisi.add(votreProposition);
        buttonJouer=new JButton("Jouer");
        buttonJouer.setFont(new Font("Times New Roman",Font.PLAIN,14));
        buttonJouer.setSize(90, 30);
        buttonJouer.setLocation(490, 15);
        jpanelSaisi.add(buttonJouer);
        buttonJouer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
		if( !votreProposition.getText().equalsIgnoreCase("")){
                   charValues=word.toCharArray();
                   //System.out.println("charValues == "+charValues[0]+""+charValues[1]);
                    String valuesChar=word;
                    if((!valuesChar.equalsIgnoreCase(""))&&(!votreProposition.getText().equalsIgnoreCase(""))){
                        //arbreInstance=new Arbre();
                        getWords=new getWordsFromCharactersInDictionary();
                        lStartTime = System.currentTimeMillis();
                        response=arbreInstance.getMatchingPrefix(votreProposition.getText().trim());
                        if(!response.equalsIgnoreCase("")){
                            response1=word;//getWords.getWordsExistingInDictionary(charValues);
                            lEndTime = System.currentTimeMillis();
                            tempsEcoule=(lEndTime-lStartTime)/1000;
                            if(response.length()<response1.length()){
                                votreReponseText.setText(votreProposition.getText());
                                reponseOrdinateurText.setText(response1);
                                if(tempsEcoule==0)
                                    dureeRecherchValue.setText(""+tempsEcoule*1000+" secondes");
                                else
                                    dureeRecherchValue.setText(""+tempsEcoule+" secondes");
                            }else{
                                votreReponseText.setText(votreProposition.getText());
                                reponseOrdinateurText.setText(response);
                                if(tempsEcoule==0)
                                    dureeRecherchValue.setText(""+tempsEcoule*1000+" secondes");
                                else
                                    dureeRecherchValue.setText(""+tempsEcoule+" secondes");
                                }
                        }else{
                            lStartTime = System.currentTimeMillis();
                            response=getWords.getWordsExistingInDictionary(charValues);
                            lEndTime = System.currentTimeMillis();
                            tempsEcoule=(lEndTime-lStartTime)/1000;
                            votreReponseText.setText(votreProposition.getText());
                            if(response.equalsIgnoreCase(""))
                                JOptionPane.showMessageDialog(null, "Pas de mot correspondant");
                            else
                                reponseOrdinateurText.setText(response);
                            if(tempsEcoule==0)
                                dureeRecherchValue.setText(""+tempsEcoule*1000+" secondes");
                            else
                                dureeRecherchValue.setText(""+tempsEcoule+" secondes");
                        }
                        votreProposition.setText("");
                    }
		}else{
                    JOptionPane.showMessageDialog(null,"Entrez votre proposition");
		}
            }
			
	});
        buttonRejouer=new JButton("Rejouer");
        buttonRejouer.setFont(new Font("Times New Roman",Font.PLAIN,14));
        buttonRejouer.setSize(90, 30);
        buttonRejouer.setLocation(530, 295);
        c.add(buttonRejouer);
        buttonRejouer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
        new JeuAutomatique().setVisible(true);
            }
        });
        
        buttonRetour=new JButton("Retour au Menu");
        buttonRetour.setFont(new Font("Times New Roman",Font.PLAIN,14));
        buttonRetour.setSize(120, 30);
        buttonRetour.setLocation(530, 335);
        c.add(buttonRetour);
        buttonRetour.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
             setVisible(false);
        new Menu().setVisible(true);
            }
        });
	// Response components
        votreReponse=new JLabel("Votre Reponse :");
        votreReponse.setSize(150, 30);
        votreReponse.setLocation(40, 255);
        c.add(votreReponse);
        reponseOrdinateur=new JLabel("L'ordinateur a trouve :");
        reponseOrdinateur.setSize(150, 30);
        reponseOrdinateur.setLocation(40, 295);
        c.add(reponseOrdinateur);
        votreReponseText=new JTextField();
        votreReponseText.setSize(250, 30);
        votreReponseText.setFont(new Font("Times New Roman",Font.BOLD,24));
        votreReponseText.setLocation(200, 255);
        votreReponseText.setEditable(false);
        c.add(votreReponseText);
        reponseOrdinateurText=new JTextField();
        reponseOrdinateurText.setSize(250, 30);
        reponseOrdinateurText.setLocation(200, 295);
        reponseOrdinateurText.setFont(new Font("Times New Roman",Font.BOLD,24));
        reponseOrdinateurText.setEditable(false);
        c.add(reponseOrdinateurText);
        dureeRecherch=new JLabel("Duree de Recherche :");
        dureeRecherch.setSize(150, 30);
        dureeRecherch.setLocation(40, 340);
        c.add(dureeRecherch);
        dureeRecherchValue=new JLabel("00h:00min:00s");
        dureeRecherchValue.setSize(150, 30);
        dureeRecherchValue.setFont(new Font("Times New Roman",Font.BOLD,24));
        dureeRecherchValue.setLocation(250, 340);
        c.add(dureeRecherchValue);
	 // end of Response components
    }
    
    public static String processedWord(String txt){
        
        String normalized = Normalizer.normalize(txt, Normalizer.Form.NFD);        
        String ascii2 = normalized.replaceAll("[^\\p{ASCII}]", "");
        return ascii2;
    }

    /*
    public static void main(String[] args) throws IOException{
        BufferedReader br;
        arbreInstance=new Arbre();
        int randInt = randInt(1,getnbrline());
        System.out.println(randInt);
        word= getword(randInt);
        System.out.println(word);
         Random r = new Random();
        System.out.println("Before: " + word );
        wordMixed = scramble( r, word );
        System.out.println("After : " + word );
             String str2 = wordMixed;
        String[] str1Array =  str2.split("");
        splitWords=str1Array;
        for (int i=0; i<str1Array.length;i++){
            System.out.println(str1Array[i]);
            //new dynamiclabel(str1Array);
        }
        try {
            br = new BufferedReader(new FileReader(".//src//dico.txt"));
            String line = "";
            /*lire le fichier ligne par ligne avec un mot par ligne 
            while ((line = br.readLine()) != null) { 
                /*ajout les lettre du mot a l'arbre
                arbreInstance.insert(line);
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
	new JeuAutomatique();
    }
*/

}
