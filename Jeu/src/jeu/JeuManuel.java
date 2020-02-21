//package jeuu;
package jeu;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JeuManuel extends JFrame{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel jpanelTitle,jpanelCase,jpanelSaisi;
    private JLabel titleText,propositionText,votreReponse,reponseOrdinateur,dureeRecherch,dureeRecherchValue;
    private JComboBox<Integer> numberChars;
    private JTextField votreProposition,votreReponseText,reponseOrdinateurText;
    private JButton buttonJouer;
    private JButton buttonRejouer;
    private JButton buttonRetour;
    private Container c;
    private int numberTextBox=0;
    char charValues[]={};
    private static Arbre arbreInstance;
    getWordsFromCharactersInDictionary getWords;
    static String response="",response1="";
    /*private JTextField charTextBox;*/
    long lEndTime=0,lStartTime=0,tempsEcoule;

    public JeuManuel(){
        super("Mot le plus long");
        setSize(700, 450);
        setLocationRelativeTo(null);
        c=getContentPane();
        setLayout(null);
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setLayout(null);
        ajoutDesMotAuDico();
        setVisible(true);
    }
    public void initComponents(){
        jpanelTitle=new JPanel();
        jpanelTitle.setLayout(null);
        titleText=new JLabel("Faites entrer vos lettres (entre 3 et 9), votre propositon et lancez la recherche");
        titleText.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleText.setSize(600, 30);
        titleText.setLocation(5,10);
        jpanelTitle.setSize(650, 50);
        jpanelTitle.setLocation(40, 15);
        jpanelTitle.add(titleText);
        c.add(jpanelTitle);
        // Le Panel qui contiendra les cases des characteres proposes
        jpanelCase=new JPanel();
        jpanelCase.setLayout(null);
        jpanelCase.setSize(500, 80);
        jpanelCase.setLocation(40, 80);
        c.add(jpanelCase);
        numberChars=new JComboBox<Integer>();
        numberChars.setBackground(Color.white);
        int gap=80,sizeElement=40,dist=0;
        for(int i=3;i<=9;i++)
          numberChars.addItem(i);
        numberChars.setSize(65, 30);
        numberChars.setLocation(550, 100);
        c.add(numberChars);
        // default text boxes 
        for(int i=0;i<3;i++){
            final JTextField charTextBox=new JTextField();
            charTextBox.setHorizontalAlignment(JTextField.CENTER);
            charTextBox.setText("");
            charTextBox.setSize(40, 40);
            charTextBox.setLocation(gap+dist,20 );
            charTextBox.addKeyListener(new KeyAdapter(){
                
                public void keyPressed(KeyEvent ke){
                    if((charTextBox.getText().length()>=1)||((ke.getKeyCode()>=KeyEvent.VK_0)&&( ke.getKeyCode()<=KeyEvent.VK_9))){
                        JOptionPane.showMessageDialog(null, "Only one Character");
                        charTextBox.setText("");
                    }else
                        charTextBox.setFont(new Font("Times New Roman",Font.BOLD,24));
                }
            });
            gap=gap+sizeElement;
            jpanelCase.add(charTextBox);
            dist=dist+5;
        }
	// end of default textboxes
	numberChars.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                jpanelCase.removeAll();
                jpanelCase.repaint();
                if(e.getStateChange()==ItemEvent.SELECTED){
                    numberTextBox=(int)e.getItem();
                    int gap=80,sizeElement=40,dist=0;
                    for(int i=0;i<numberTextBox;i++){
                        final JTextField charTextBox=new JTextField();
                        charTextBox.setHorizontalAlignment(JTextField.CENTER);
                        charTextBox.setText("");
                        charTextBox.setSize(40, 40);
                        charTextBox.setLocation(gap+dist,20 );
                        charTextBox.addKeyListener(new KeyAdapter(){
                            public void keyPressed(KeyEvent ke){
                                if((charTextBox.getText().length()>=1)||((ke.getKeyCode()>KeyEvent.VK_0)&&(ke.getKeyCode()<KeyEvent.VK_9))){
                                        JOptionPane.showMessageDialog(null, "Only one Character");
                                        charTextBox.setText("");
                                }else
                                        charTextBox.setFont(new Font("Times New Roman",Font.BOLD,24));
                            }
			});
                        gap=gap+sizeElement;
                        jpanelCase.add(charTextBox);
                        dist=dist+5;
                    }
                    votreReponseText.setText("");
                    reponseOrdinateurText.setText("");
                }
                
            }
	});
		
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
                int increment=0;
                for(int k=0;k<jpanelCase.getComponentCount();k++){
                    if(!((JTextField) (jpanelCase.getComponent(k))).getText().isEmpty()){
                        increment++;
                    }
                }
		System.out.println(increment);
		if(increment==jpanelCase.getComponentCount()&&( !votreProposition.getText().equalsIgnoreCase(""))){
                     getWords=new getWordsFromCharactersInDictionary();
                    charValues=new char[(int) numberChars.getSelectedItem()];
                    for(int k=0;k<jpanelCase.getComponentCount();k++){
                            charValues[k]=((JTextField) (jpanelCase.getComponent(k))).getText().charAt(0);
                    }
                    System.out.println(" charValues == "+new String(charValues));
                    String valuesChar=new String(charValues);
                        //arbreInstance=new Arbre();
                        lStartTime = System.currentTimeMillis();
                        response=arbreInstance.getMatchingPrefix(votreProposition.getText().trim());
                        if(!response.equalsIgnoreCase("")){
                            response1=getWords.getWordsExistingInDictionary(charValues);
                            System.out.println(" response 1 === "+response1);
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
                            //reponseOrdinateurText.setText(response);
                            if(tempsEcoule==0)
                                dureeRecherchValue.setText(""+tempsEcoule*1000+" secondes");
                            else
                                dureeRecherchValue.setText(""+tempsEcoule+" secondes");
                        }
						
			for(int k=0;k<jpanelCase.getComponentCount();k++){
                            ((JTextField) (jpanelCase.getComponent(k))).setText("");
			}
			votreProposition.setText("");
		}else{
                    JOptionPane.showMessageDialog(null,"Please Enter all the characters and proposed word");
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
                
        new JeuManuel().setVisible(true);
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
    
    public void ajoutDesMotAuDico(){
        BufferedReader br;
        arbreInstance=new Arbre();
        try {
            br = new BufferedReader(new FileReader(new Menu().getDicoFileName()));
            String line = "";
          //lire le fichier ligne par ligne avec un mot par ligne 
            while ((line = br.readLine()) != null) { 
                /*ajout les lettre du mot a l'arbre*/
                    arbreInstance.insert(line);
                //System.out.println(line);
            }
            br.close();
            System.out.println(new Menu().getDicoFileName());
	} 
	catch (FileNotFoundException e) {

            System.out.println(e.getLocalizedMessage());
	} 

	catch (IOException e) {

            System.out.println(e.getLocalizedMessage());
	}
    }

  /*	
    public static void main(String[] args){
        BufferedReader br;
        arbreInstance=new Arbre();
        try {
            br = new BufferedReader(new FileReader("src/dico.txt"));
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
	new JeuManuel();
    }
*/

}
