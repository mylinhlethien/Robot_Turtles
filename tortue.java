

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import static javax.swing.JOptionPane.showMessageDialog;

public class tortue {

	
	public static class RobotTurtles implements ActionListener {
			
			// variables pour panel Parametres
			JPanel panelParam ;
			
			JLabel lblLangue; // libelle langue
			String[]  appLangue;//liste des langues
			JComboBox<String> comboLangue; // combo langue
			
			JLabel lblMode; // libelle mode
			String[]  appMode;//liste des mode
			JComboBox<String> comboMode; // combo mode
			
			JLabel lblNbJoueur; // libelle Nb Joueur
			String[]  appNbJoueur;//liste des Nb Joueur
			JComboBox<String> comboNbJoueur; // combo Nb Joueur
			
			JLabel lblNbManche; // libelle Nb Manche
			String[]  appNbManche;//liste des Nb Manche
			JComboBox<String> comboNbManche; // combo Nb Manche			
			
			JButton btnInitialiser, btnDistribuer, btnJouer, btnAbandonner, btnFin;
			String toolTipInitialiser, toolTipDistribuer, toolTipJouer, toolTipAbandonner, toolTipFin;
			
			RobotTurtles(){
				int w,h;
				// bordure des panel
				Border panelBorder = new LineBorder(Color.DARK_GRAY, 5);
				
				//creation de la frame principale
				JFrame frame = new JFrame(" Robot Turtles");
				w=1200;h=900+50;
				frame.setSize(w,h);
				frame.setLayout(new BorderLayout()); 
				frame.setBackground(Color.WHITE);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Container frameContenaire = frame.getContentPane();
				frameContenaire.removeAll();
				//frame.setVisible(true);
				
				// on divise l'ecran en 2 parties verticales Gauche et droite
				JPanel panelGauche = new JPanel(); 
				w=270;h=900;
				panelGauche.setPreferredSize(new Dimension(w,h));
				panelGauche.setBackground(Color.WHITE);
				frame.add(panelGauche, BorderLayout.WEST);
				
				JPanel panelDroite = new JPanel(); 
				w=920;h=900;
				panelDroite.setPreferredSize(new Dimension(w,h));
				panelDroite.setBackground(Color.WHITE);
				frame.add(panelDroite, BorderLayout.EAST);
				
				// dans panel Gauche on met horizentalement parametre et Etat
				//JPanel panelParam = new JPanel(); 
				w=270;h=700;
				panelParam = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
				panelParam.setPreferredSize(new Dimension(w,h));
				panelParam.setBackground(Color.WHITE);
				panelParam.setBorder(panelBorder);
				panelGauche.add(panelParam, BorderLayout.EAST);
				
				JPanel panelEtat = new JPanel(); 
				w=270;h=200;
				panelEtat.setPreferredSize(new Dimension(w,h));
				panelEtat.setBackground(Color.WHITE);
				panelEtat.setBorder(panelBorder);
				panelGauche.add(panelEtat, BorderLayout.EAST);
				
				
				// dans panel droite on met horizentalement Grille et User
				JPanel panelGrille = new JPanel(); 
				w=920;h=700;
				panelGrille.setPreferredSize(new Dimension(w,h));
				panelGrille.setBackground(new Color(76,153,0));//green
				panelGrille.setBorder(panelBorder);
				panelDroite.add(panelGrille);
				
				JPanel panelUser = new JPanel(); 
				w=920;h=200;
				panelUser.setPreferredSize(new Dimension(w,h));
				panelUser.setBorder(panelBorder);
				panelUser.setBackground(new Color(76,153,0));//green
				panelDroite.add(panelUser);
				
				InitPanelParam();
				frame.setVisible(true);
			}
			//String toolTipInitialiser, toolTipDistribuer, toolTipJouer, toolTipAbandonner, toolTipFin;
			
		private void Initialiser()
		{
			showMessageDialog(null, toolTipInitialiser);
		}
		private void Distribuer()
		{
			showMessageDialog(null, toolTipDistribuer);
		}
		private void Abandonner()
		{
			showMessageDialog(null, toolTipAbandonner);
		}
		private void Sortie()
		{
			showMessageDialog(null, toolTipFin);
		}
		private void Jouer()
		{
			showMessageDialog(null, toolTipJouer);
		}
		
		
		private void AddVideToPanelParam(int pWp,int pHp)
		{
			JPanel pan=new JPanel();
			pan.setPreferredSize(new Dimension(pWp,pHp));
			pan.setBorder(new EmptyBorder(0, 0, 0, 0));
			panelParam.add(pan);
		}
		private void AddnewFieldParam(Component c1, Component c2,int pWp,int pHp, int pWc, int pHc)
		{
			JPanel pan=new JPanel();
			//int w=250,h=35;
			int w=pWp,h=pHp;
			
			pan.setPreferredSize(new Dimension(w,h));
			pan.setBorder(new EmptyBorder(0, 0, 0, 0));
			//pan.setBackground(Color.yellow);
			
			//w=100;h=30; ,120, 30
			w=pWc;h=pHc;
			JPanel pan_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pan_1.setPreferredSize(new Dimension(w-20,h));
			//pan_1.setBackground(Color.RED);
			pan_1.setBorder(new EmptyBorder(0, 0, 0, 0));
			pan_1.add(c1);
			
			//w=140;h=30;
			JPanel pan_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pan_2.setPreferredSize(new Dimension(w+20,h));
			//pan_2.setBackground(Color.blue);
			pan_2.setBorder(new EmptyBorder(0, 0, 0, 0));
			if( c2 != null) pan_2.add(c2);
			
			pan.add(pan_1);
			pan.add(pan_2);
			panelParam.add(pan);
		}
		private void InitPanelParam()
		{
			
			// panel vide en haut
			AddVideToPanelParam(250,300);
						
			int wp=250; int hp=35;
			int w=120; int h=30;
			// (1) langue
			lblLangue = new JLabel("");
			appLangue= new String[2];//liste des langues
			appLangue[0]="";
			appLangue[1]="";
			comboLangue = new JComboBox<String>(appLangue);
			AddnewFieldParam(lblLangue, comboLangue,wp,hp,w,h); 
			//AddnewFieldParam(lblLangue, comboLangue);
			comboLangue.addActionListener(this);
			
			// (2) Mode
			lblMode = new JLabel("");
			appMode= new String[2];//liste des modes
			appMode[0]="";
			appMode[1]="";
			comboMode= new JComboBox<String>(appMode);
			AddnewFieldParam(lblMode, comboMode,wp,hp,w,h);
			comboMode.addActionListener(this);
			
			// (3) Nombre de joueur
			lblNbJoueur = new JLabel("");
			appNbJoueur= new String[3];//liste des modes
			appNbJoueur[0]="";
			appNbJoueur[1]="";
			appNbJoueur[2]="";
			comboNbJoueur= new JComboBox<String>(appNbJoueur);
			AddnewFieldParam(lblNbJoueur, comboNbJoueur,wp,hp,w,h);
			comboNbJoueur.addActionListener(this);
					
			// (4) Nombre de manche
			lblNbManche = new JLabel("");
			appNbManche= new String[2];//liste des manche
			appNbManche[0]="";
			appNbManche[1]="";
			comboNbManche= new JComboBox<String>(appNbManche);
			AddnewFieldParam(lblNbManche, comboNbManche,wp,hp,w,h);
			comboNbManche.addActionListener(this);
			
			// panel vide de separation
			AddVideToPanelParam(250,50);
			
			
			// boutons
			// btnInitialiser, btnDistribuer, btnJouer, btnAbandonner, btnFin;
			w=80; h=30;
			btnInitialiser = new JButton("Initialiser");// pour test
			btnInitialiser.addActionListener(this);
			btnInitialiser.setBackground(Color.white);
			btnInitialiser.setForeground(Color.BLUE);
			
			btnDistribuer = new JButton("Distribuer");// pour test
			btnDistribuer.addActionListener(this);
			btnDistribuer.setBackground(Color.white);
			btnDistribuer.setForeground(Color.BLUE);
			
			btnJouer = new JButton("Jouer");// pour test
			btnJouer.addActionListener(this);
			btnJouer.setBackground(Color.white);
			btnJouer.setForeground(Color.BLUE);
			
			btnAbandonner = new JButton("Abandonner");// pour test
			btnAbandonner.addActionListener(this);
			btnAbandonner.setBackground(Color.white);
			btnAbandonner.setForeground(Color.BLUE);
			
			
			btnFin = new JButton("Sortie");// pour test
			btnFin.addActionListener(this);
			btnFin.setBackground(Color.white);
			btnFin.setForeground(Color.BLUE);
			
			AddnewFieldParam(btnInitialiser, btnDistribuer,wp,hp+10,120,40); //
			AddnewFieldParam(btnJouer, btnAbandonner,wp,hp+10,120,40);
			AddnewFieldParam(btnFin, null,wp,hp+10,120,40);
			SetLangue();
		}
		
		private void SetLangue()
		{
			int indexLangue=comboLangue.getSelectedIndex();
			int indexMode=comboMode.getSelectedIndex();
			int indexNbJoueur=comboNbJoueur.getSelectedIndex();
			int indexNbManche=comboNbManche.getSelectedIndex();
			
			System.out.println("SetLangue");//msg console
			
			if(indexLangue == 0) // français
			{
				 //btnInitialiser.setText("Initialiser");
				
				//langue
				lblLangue.setText("Langue :");
				appLangue[0]="Français";
				appLangue[1]="Anglais";
				DefaultComboBoxModel<String> Model = new DefaultComboBoxModel<String>(appLangue); //Assign Model data to ComboBoxes from Array
				comboLangue.setModel(Model);
				comboLangue.setSelectedIndex(indexLangue);
				
				//mode
				lblMode.setText("Mode :");
				appMode[0]="Mode de base";
				appMode[1]="Mode avancé";
				Model = new DefaultComboBoxModel<String>(appMode); //Assign Model data to ComboBoxes from Array
				comboMode.setModel(Model);
				comboMode.setSelectedIndex(indexMode);
				
				//Nb Joueur
				lblNbJoueur.setText("Nb de joueurs :");
				appNbJoueur[0]="Deux joueurs";
				appNbJoueur[1]="Trois joueurs";
				appNbJoueur[2]="Quatre joueurs";
				
				Model = new DefaultComboBoxModel<String>(appNbJoueur); //Assign Model data to ComboBoxes from Array
				comboNbJoueur.setModel(Model);
				comboNbJoueur.setSelectedIndex(indexNbJoueur);
				
				//Nb Manche
				lblNbManche.setText("Nb de manches :");
				appNbManche[0]="Une manche";
				appNbManche[1]="Trois manches";
				
				Model = new DefaultComboBoxModel<String>(appNbManche); //Assign Model data to ComboBoxes from Array
				comboNbManche.setModel(Model);
				comboNbManche.setSelectedIndex(indexNbManche);
				
				// boutons
				btnInitialiser.setText("Initialiser");
				btnDistribuer.setText("Distribuer");
				btnJouer.setText("Jouer");
				btnAbandonner.setText("Abandonner");
				btnFin.setText("Sortie");
				
	//String toolTipInitialiser, toolTipDistribuer, toolTipJouer, toolTipAbandonner, toolTipFin;
				
				
				toolTipInitialiser= "Initialisation et affichage dans la grille des cartes pour les utilisateurs déclarés et en fonction du mode ";
				toolTipDistribuer= "Initialisation Grille pour jouer en fonction du nb des joueur + distribution des cartes pour les utilisateurs";
				toolTipAbandonner= "Abandon de la manche en cours";
				toolTipFin= "Fin de la partie est sortie du programme";
				toolTipJouer= "Début de la manche";
				
				btnInitialiser.setToolTipText(toolTipInitialiser);// souris+description
				btnDistribuer.setToolTipText(toolTipDistribuer);
				btnJouer.setToolTipText(toolTipJouer);
				btnAbandonner.setToolTipText(toolTipAbandonner);
				btnFin.setToolTipText(toolTipFin);
				
			}
			
			if(indexLangue == 1) // anglais
			{
				 //btnInitialiser.setText("Initialize");
				
				lblLangue.setText("Language :");
				 appLangue[0]="French";
				 appLangue[1]="English";
				 DefaultComboBoxModel<String> Model = new DefaultComboBoxModel<String>(appLangue); //Assign Model data to ComboBoxes from Array
				 comboLangue.setModel(Model);
				comboLangue.setSelectedIndex(indexLangue);
				 
				//mode
				lblMode.setText("Mode :");
				appMode[0]="Basic mode";
				appMode[1]="Advanced mode";
			     Model = new DefaultComboBoxModel<String>(appMode); //Assign Model data to ComboBoxes from Array
				comboMode.setModel(Model);
				comboMode.setSelectedIndex(indexMode);
				
				//Nb Joueur
				lblNbJoueur.setText("Nb of players :");
				appNbJoueur[0]="Two players";
				appNbJoueur[1]="Three players";
				appNbJoueur[2]="Four players";
				
				Model = new DefaultComboBoxModel<String>(appNbJoueur); //Assign Model data to ComboBoxes from Array
				comboNbJoueur.setModel(Model);
				comboNbJoueur.setSelectedIndex(indexNbJoueur);
				
				//Nb Manche
				lblNbManche.setText("Nb of rounds :");
				appNbManche[0]="One round";
				appNbManche[1]="Three rounds";
				
				Model = new DefaultComboBoxModel<String>(appNbManche); //Assign Model data to ComboBoxes from Array
				comboNbManche.setModel(Model);
				comboNbManche.setSelectedIndex(indexNbManche);
				
				// boutons
				btnInitialiser.setText("Initialize");
				btnDistribuer.setText("Distribute");
				btnJouer.setText("GO");
				btnAbandonner.setText("Cancel");
				btnFin.setText("Exit");
				toolTipInitialiser = "Initialization and display in the map grid for registered users and depending on the mode";
				toolTipDistribuer = "Initialization Grid to play according to the number of players + distribution of cards for users";
				toolTipAbandonner = "Abandonment of the round in progress";
				toolTipFin = "End of the game has left the program";
				toolTipJouer = "Beginning of the round";
				
				btnInitialiser.setToolTipText(toolTipInitialiser);
				btnDistribuer.setToolTipText(toolTipDistribuer);
				btnJouer.setToolTipText(toolTipJouer);
				btnAbandonner.setToolTipText(toolTipAbandonner);
				btnFin.setToolTipText(toolTipFin);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object sourceAction = arg0.getSource();
			if(sourceAction == comboLangue){
				this.SetLangue();
			} 
			if(sourceAction == comboMode){
				this.SetLangue();
			} 
			if(sourceAction == comboNbJoueur){
				this.SetLangue();
			} 
			if(sourceAction == comboNbManche){
				this.SetLangue();
			} 
			if(sourceAction == btnInitialiser){
				this.Initialiser();
				
			} 
			if(sourceAction == btnDistribuer){
				this.Distribuer();
				
			} 
			if(sourceAction == btnAbandonner){
				this.Abandonner();
				
			} 
			if(sourceAction == btnFin){
				this.Sortie();
				
			} 
			if(sourceAction == btnJouer){
				this.Jouer();
				
			} 
		}
	}

	
	public static void main(String[] args) {
		RobotTurtles partie = new RobotTurtles();
		
	}
		
}
