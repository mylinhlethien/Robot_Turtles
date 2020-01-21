
/*
 Interface graphique
 */
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


//classe tuile permet d'afficher les cartes 
class Tuile extends JButton implements MouseListener {
	String CodeCarte;
	int ligne, colonne;
	int whith, height;
	int Key;// ligne*10+colonne
	int Emplacement; // 1 panel jeu, 2,3,4,5 panels joueur
	private static final int PANEL_JEU = 1;
	private static final int PANEL_CARTE = 2;
	private static final int PANEL_DEFENSE = 3;
	private static final int PANEL_MAIN = 4;
	private static final int PANEL_PROGRAMME = 5;
	public Image imgTuile;
	public String imgPathTuile;

	RobotTurtlesPartie Partie;
	RobotTurtlesInterface Frame;

	public Tuile(RobotTurtlesPartie partie, RobotTurtlesInterface frame, int i, int j, int emplacement,	String codeCarte) {
		ligne = i;
		colonne = j;
		whith = 52;
		height = 62;
		Emplacement = emplacement;
		CodeCarte = codeCarte;
		imgPathTuile = "./images/background_blanc.png";
		Partie = partie;
		Frame = frame;

		Key = (ligne * 10) + colonne; // key de la tuile
		imgTuile = null;
		ImageIcon imageIcon = new ImageIcon(imgPathTuile);
		Image image = imageIcon.getImage();
		image = RedimentionnerImage(image, whith, height);
		imgTuile = image;

		this.addMouseListener(this); // ajoute actions souris sur la tuile
	}

	public void paintComponent(Graphics g) {
		g.drawImage(imgTuile, 1, 1, null);
	}
	public static Image RedimentionnerImage(Image source, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(source, 0, 0, width, height, null);
		g.dispose();
		return img;
	}
	public Image RedimentionnerImageFromPath(String path, int w, int h) {
		Image imgResized = null;
		ImageIcon imageIcon = new ImageIcon(path);
		Image image = imageIcon.getImage();
		image = RedimentionnerImage(image, w, h);
		imgResized = image;
		return imgResized;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Tuile tuile = (Tuile) arg0.getSource();
		
		int event= arg0.getButton();
		String msg = "";
		boolean retOk;
		
		switch (tuile.CodeCarte) {
		case "Paquet":
			if ( event == MouseEvent.BUTTON1 && tuile.CodeCarte.equals("Paquet")) {
				//clic gauche sur carte paquet
				retOk = tuile.Partie.TirerUneCarte();
				if (!retOk) msg += tuile.Frame.GetMsg(21);//"Main complète ou nombre max de cartes tirées atteint!";
				tuile.Frame.AfficherGrillesBas();
			}
			break; 
		case "Défense":
			// aucun traitement pour carte défense
			break; 
		case "Main":
			if ( event == MouseEvent.BUTTON3 && tuile.CodeCarte.equals("Main"))  {
				//clic droit sur carte main
				retOk = tuile.Partie.DefausserLaMain();
				if (!retOk) msg += tuile.Frame.GetMsg(22);//"Nb max de cartes défaussées atteint";
				tuile.Frame.AfficherGrillesBas();
			}
			break; 
		default:
				//grille du jeu, celle du haut
				if( tuile.Emplacement == PANEL_JEU && event == MouseEvent.BUTTON1  &&  tuile.CodeCarte.startsWith("T")) {
					//carte tortue
					//affecter un bug
					msg += tuile.Frame.GetMsg(23);//"bug affecté";
					retOk=Partie.AffecterUnBug(tuile.CodeCarte);
					if (!retOk) msg += tuile.Frame.GetMsg(24);//"Pas de bug disponible!";
					tuile.Frame.AfficherGrilleHaut(1,null);
					tuile.Frame.AfficherGrillesBas();
				}
				if( tuile.Emplacement == PANEL_JEU &&  tuile.CodeCarte =="") {
					if(event == MouseEvent.BUTTON1 &&  tuile.CodeCarte =="") {
						//clic gauche sur case vide : déposer un mur de pierre
						retOk=Partie.DeposerUnMur(1,tuile.Key);
						if (!retOk) msg += tuile.Frame.GetMsg(25);//"Encerclement ou pas de murs disponibles ou opération interdite!";
						tuile.Frame.AfficherGrilleHaut(1,null);
						tuile.Frame.AfficherGrillesBas();
					}
					if(event == MouseEvent.BUTTON3 &&  tuile.CodeCarte =="") {
						//clic droit sur case vide : déposer un mur de glace
						retOk=Partie.DeposerUnMur(2,tuile.Key);
						if (!retOk) msg += tuile.Frame.GetMsg(26);//"Encerclement ou pas de murs disponibles ou opération interdite!";
						tuile.Frame.AfficherGrilleHaut(1,null);
						tuile.Frame.AfficherGrillesBas();
					}

				}
				//grille defense, en bas
				if( tuile.Emplacement == PANEL_DEFENSE &&  tuile.CodeCarte !="") {
					//aucun traitement
				}
				//grille main, en bas
				if(tuile.Emplacement == PANEL_MAIN && event == MouseEvent.BUTTON1 &&  tuile.CodeCarte !="") {
					//clic gauche
					retOk = tuile.Partie.InsererCarteAuProgramme(tuile.CodeCarte);
					tuile.Frame.AfficherGrillesBas();
				}
				if( tuile.Emplacement == PANEL_MAIN && event == MouseEvent.BUTTON3 &&  tuile.CodeCarte !="") {
					//clic droit
					retOk = tuile.Partie.DefausserUneCarte(tuile.CodeCarte);
					if (!retOk) msg="Nb max de cartes défaussées atteint";
					tuile.Frame.AfficherGrillesBas();
				}
				//grille programme, en bas
				if(tuile.Emplacement == PANEL_PROGRAMME && event == MouseEvent.BUTTON1  &&  tuile.CodeCarte.startsWith("T")) {
					//clic gauche sur carte tortue
					String newKey = tuile.Partie.ExecuterProgramme();
					if (newKey == "Erreur") msg += tuile.Frame.GetMsg(27);//"Erreur d'exécution ou programme vide!";
					if (newKey == "FinManche") msg += tuile.Frame.GetMsg(28);//"Fin de la manche!";
					if (newKey == "FinPartie") msg += tuile.Frame.GetMsg(29);//"Fin de la partie!";
					if (newKey == "Impossible") msg += tuile.Frame.GetMsg(30);//"Impossible d'exécuter, essayez au prochain tour";
					//else tuile.Key=newKey;
					tuile.Frame.AfficherGrilleHaut(1,null);
					tuile.Frame.AfficherGrillesBas();
				} 
				if(tuile.Emplacement == PANEL_PROGRAMME && event == MouseEvent.BUTTON3  &&  tuile.CodeCarte.startsWith("T")) {
					//clic droit sur carte tortue
					tuile.Partie.FinDuTour();
					tuile.Frame.AfficherGrillesBas();
				} 
		}
		
		//message au joueur si alert
		if (msg != "") {
			showMessageDialog(null, msg);
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		Tuile tuile = (Tuile) arg0.getSource();
		String msg = "";
		boolean modeTest = tuile.Partie.GetModeTest();
		if(modeTest) {
			 msg = "Key:" + tuile.Key+", Code:"+tuile.CodeCarte + ", ";
		}

		switch (tuile.CodeCarte) {
		case "Paquet":
			msg += tuile.Frame.GetMsg(1);//", Clic pour tirer une carte";
			msg += " / Nb="+ tuile.Partie.GetNbCartePaquet();
			break; 
		case "Defense":
			break; 
		case "Main":
			msg += tuile.Frame.GetMsg(2);//", Clic droit pour defausser toute la main";
			break; 
		default:
			//grille du haut
			if( tuile.Emplacement == PANEL_JEU &&  tuile.CodeCarte.startsWith("T")) {
				//carte tortue
				if (modeTest) {
					String orient=tuile.Partie.GetOrientTortue();
					msg += "Orient:"+orient +", ";
				}
				msg += tuile.Frame.GetMsg(3);// "Clic  pour affecter un bug";
			}
			if( tuile.Emplacement == PANEL_JEU &&  tuile.CodeCarte =="") {
					//case vide
				msg += tuile.Frame.GetMsg(4);// "Clic droit/gauche pour déposer un mur";
			}
			//defense
			if( tuile.Emplacement == PANEL_DEFENSE &&  tuile.CodeCarte !="") {
				if(tuile.CodeCarte.startsWith("W")) {
					msg += tuile.Frame.GetMsg(5);//"Clic gauche dans la grille pour déposer ce mur";
				}
				if(tuile.CodeCarte.startsWith("I")) {
					msg += tuile.Frame.GetMsg(6);//"Clic droit dans la grille pour déposer ce mur";
				}
			}
			//main
			if( tuile.Emplacement == PANEL_MAIN &&  tuile.CodeCarte !="") {
				msg += tuile.Frame.GetMsg(7);//"Clic pour ajouter la carte au programme & Clic droit pour défausser la carte";
			}
			//programme
			if( tuile.Emplacement == PANEL_PROGRAMME &&  tuile.CodeCarte.startsWith("T") ) {
				msg += tuile.Frame.GetMsg(8)+Partie.GetNomJoueurCourant()+ ", ";//"Nom:"+Partie.GetNomJoueurCourant() + ", ";
				msg += tuile.Frame.GetMsg(9);//"clic pour exécuter le programme, clic droit pour passer le tour";
				msg += tuile.Frame.GetMsg(10) + tuile.Partie.GetNbCarteProgramme();//" / Nb cartes ="+ tuile.Partie.GetNbCarteProgramme();
			}
			break;
		}
		this.setToolTipText(msg);
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}

//classe grille permet de créer une grille de n X m cases 
class PanelGrille extends JPanel {
	private static final long serialVersionUID = 1L;
	public Image imgBackground;
	RobotTurtlesPartie Partie;
	//constructeur
	public PanelGrille(RobotTurtlesPartie partie, RobotTurtlesInterface frame, int nbLig, int nbCol, int panlWith,
			int panlHeight, int emplacement) {
		Partie = partie;
		
		Dimension size = new Dimension(nbCol*50, nbLig*60);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		GridLayout grid = new GridLayout(nbLig, nbCol);
		setLayout(grid);
		// creation de la grille avec des tuiles vides
		for (int i = 1; i <= nbLig; i++) {
			for (int j = 1; j <= nbCol; j++) {
				Tuile tuile = new Tuile(partie, frame, i, j, emplacement, "");
				
				this.add(tuile);
			}
		}
	}
}

//fenètre principale de l'application
public class RobotTurtlesInterface implements ActionListener {
	RobotTurtlesPartie Partie;
	// variables pour panel Parametres
	JPanel panelParam;
	JLabel lblLangue; // libelle langue
	String[] appLangue;// liste des langues
	JComboBox<String> comboLangue; // combo langue
	JLabel lblMode; // libelle mode
	String[] appMode;// liste des mode
	JComboBox<String> comboMode; // combo mode
	JLabel lblNbJoueur; // libelle Nb Joueur
	String[] appNbJoueur;// liste des Nb Joueur
	JComboBox<String> comboNbJoueur; // combo Nb Joueur
	JLabel lblNbManche; // libelle Nb Manche
	String[] appNbManche;// liste des Nb Manche
	JComboBox<String> comboNbManche; // combo Nb Manche
	JLabel lblUtilisateurCourant; // libelle UtilisateurCourant
	String[] appUtilisateurCourant;// liste des UtilisateurCourant
	JComboBox<String> comboUtilisateurCourant; // UtilisateurCourant
	JButton btnInitialiser, btnMelanger, btnDistribuer, btnJouer, btnAbandonner, btnFin;
	String toolTipInitialiser, toolTipMelanger, toolTipDistribuer, toolTipJouer, toolTipAbandonner, toolTipFin;
	JButton btnTestAffCarte, btnTestAffProg, btnTestRetJeu; // pour test
	int indexLangue, indexMode, indexNbJoueur, indexNbManche, indexUtilisateurCourant;
	//variables pour panel Etat
	JPanel panelResultat;
	JTextField TxtNomJoueur1, TxtNomJoueur2, TxtNomJoueur3, TxtNomJoueur4;
	JTextField TxtScore1, TxtScore2, TxtScore3, TxtScore4;
	JCheckBox CheckboxTest;
	JTextField TxtLibellManche, TxtNumManche;// libelle et Numéro de la manche
	//variables pour panel Utilisateur
	PanelGrille PanelDefense,PanelJoyaux,PanelMain,PanelProgramme;
	//variables pour panel Grille
	PanelGrille PanelGrille;
	
	//constructeur
	RobotTurtlesInterface(RobotTurtlesPartie partie) {
		Partie = partie;
		int w, h;
		w = 910;
		h = 810;
		Border panelBorder = new LineBorder(Color.DARK_GRAY, 2);//bordure des panels

		// creation de la frame principale
		JFrame frame = new JFrame(" Robot Turtles");
		
		frame.setSize(w, h);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 CheckboxTest = new JCheckBox("Test");
		 CheckboxTest.setSelected(false);
		// add to a container
		frame.add(CheckboxTest);
		CheckboxTest.addActionListener(this);
		frame.setVisible(true);

		// on divise l'ecran en 2 parties verticales Gauche et droite
		JPanel panelGauche = new JPanel();
		int wg = 270;
		
		panelGauche.setPreferredSize(new Dimension(wg, h));
		panelGauche.setBackground(Color.WHITE);
		frame.add(panelGauche, BorderLayout.WEST);

		JPanel panelDroite = new JPanel();
		int wd = 515;
		panelDroite.setPreferredSize(new Dimension(wd, h));
		panelDroite.setBackground(Color.WHITE);
		frame.add(panelDroite, BorderLayout.EAST);

		// dans panel Gauche on met horizontalement parametre et Etat //JPanel
		int hdh = 500;
		panelParam = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelParam.setPreferredSize(new Dimension(wg, hdh));
		panelParam.setBackground(Color.WHITE);
		panelParam.setBorder(panelBorder);

		panelGauche.add(panelParam, BorderLayout.NORTH);

		int hgb = 255;
		panelResultat = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelResultat.setPreferredSize(new Dimension(wg, hgb));
		panelResultat.setBackground(Color.WHITE);
		panelResultat.setBorder(panelBorder);
		panelGauche.add(panelResultat, BorderLayout.SOUTH);

		int wgrille = 425;
		int hgrille = 500;
		PanelGrille = new PanelGrille(partie, this, 8, 8, wgrille, hgrille,  1);

		PanelGrille.setPreferredSize(new Dimension(wgrille, hgrille));
		PanelGrille.setBackground(Color.WHITE);
	
		panelDroite.add(PanelGrille);

		int hgrille2 = 60;
		PanelJoyaux = new PanelGrille(Partie, this, 1, 8, wgrille, hgrille2, 2);
		PanelJoyaux.setPreferredSize(new Dimension(wgrille, hgrille2));
		PanelJoyaux.setBackground(Color.WHITE);
		panelDroite.add(PanelJoyaux);
		
		PanelDefense = new PanelGrille(Partie, this, 1, 8, wgrille, hgrille2,  3);
		PanelDefense.setPreferredSize(new Dimension(wgrille, hgrille2));
		PanelDefense.setBackground(Color.WHITE);
		panelDroite.add(PanelDefense);
		
		PanelMain = new PanelGrille(Partie, this, 1, 8, wgrille, hgrille2, 4);
		PanelMain.setPreferredSize(new Dimension(wgrille, hgrille2));
		PanelMain.setBackground(Color.WHITE);
		panelDroite.add(PanelMain);
		
		PanelProgramme = new PanelGrille(Partie, this, 1, 8, wgrille, hgrille2, 5);
		PanelProgramme.setPreferredSize(new Dimension(wgrille,hgrille2));
		PanelProgramme.setBackground(Color.WHITE);
		panelDroite.add(PanelProgramme);

		DessinerPanelParam();
		DessinerpanelResultat();

		SetLangue();
		frame.pack();
		frame.setVisible(true);
	}

	//methodes pour dessiner le panel parametre
	private void AddLignePanelParam(Component c1, Component c2, int pWp, int pHp, int pWc, int pHc) {
		JPanel pan = new JPanel();

		int w = pWp, h = pHp;

		pan.setPreferredSize(new Dimension(w, h));
		pan.setBorder(new EmptyBorder(0, 0, 0, 0));

		w = pWc;
		h = pHc;
		JPanel pan_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pan_1.setPreferredSize(new Dimension(w - 20, h));
		pan_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		pan_1.add(c1);

		JPanel pan_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pan_2.setPreferredSize(new Dimension(w + 20, h));
		pan_2.setBorder(new EmptyBorder(0, 0, 0, 0));
		if (c2 != null)	pan_2.add(c2);

		pan.add(c1);
		pan.add(c2);
		panelParam.add(pan);
	}
	private void AddLigneVidePanelParam(RobotTurtlesInterface frame, int pWp, int pHp, String imgPath) {
		JPanel pan = new JPanel(new FlowLayout(FlowLayout.CENTER));

		pan.setPreferredSize(new Dimension(pWp, pHp));
		pan.setBorder(new EmptyBorder(0, 0, 0, 0));
		pan.setBackground(Color.WHITE);
		if (imgPath != "") {
			Tuile tuile = new Tuile(Partie, frame, 0, 0, 0, "");
			Image img = tuile.RedimentionnerImageFromPath(imgPath, pWp, pHp);
			tuile.imgTuile = img;
			pan.setLayout(new BorderLayout());
			pan.add(tuile, BorderLayout.CENTER);
		}
		panelParam.add(pan);
	}
	private void AddTestPanelParam(int pWp, int pHp) {
		JPanel pan = new JPanel();

		int w = pWp, h = pHp;
		pan.setPreferredSize(new Dimension(w, h));
		pan.setBorder(new EmptyBorder(0, 0, 0, 0));

		btnTestRetJeu = new JButton("Jeu");// retour au jeu
		btnTestRetJeu.addActionListener(this);
		btnTestRetJeu.setBackground(Color.BLUE);
		btnTestRetJeu.setForeground(Color.WHITE);

		btnTestAffCarte = new JButton("Aff.Cartes");// cartes pour test
		btnTestAffCarte.addActionListener(this);
		btnTestAffCarte.setBackground(Color.BLUE);
		btnTestAffCarte.setForeground(Color.WHITE);

		btnTestAffProg = new JButton("Aff Prog.");// programme pour test
		btnTestAffProg.addActionListener(this);
		btnTestAffProg.setBackground(Color.BLUE);
		btnTestAffProg.setForeground(Color.WHITE);
	
		pan.add(btnTestAffCarte);
		pan.add(btnTestAffProg);
		pan.add(btnTestRetJeu);
		
		panelParam.add(pan);
	}
	private void DessinerPanelParam() {
		AddLigneVidePanelParam(this, 250, 120, "./images/background.jpeg");

		int wp = 250;
		int hp = 35-5;
		int w = 120-20;
		int h = 30-10;
		
		// (1) langue
		lblLangue = new JLabel("");
		appLangue = new String[2];// liste des langues
		appLangue[0] = "";
		appLangue[1] = "";
		comboLangue = new JComboBox<String>(appLangue);
		AddLignePanelParam(lblLangue, comboLangue, wp, hp, w, h);
		comboLangue.addActionListener(this);

		// (2) Mode
		lblMode = new JLabel("");
		appMode = new String[2];// liste des modes
		appMode[0] = "";
		appMode[1] = "";
		comboMode = new JComboBox<String>(appMode);
		AddLignePanelParam(lblMode, comboMode, wp, hp, w, h);
		comboMode.addActionListener(this);

		// (3) Nombre de joueur
		lblNbJoueur = new JLabel("");
		appNbJoueur = new String[3];// liste des modes
		appNbJoueur[0] = "";
		appNbJoueur[1] = "";
		appNbJoueur[2] = "";
		comboNbJoueur = new JComboBox<String>(appNbJoueur);
		AddLignePanelParam(lblNbJoueur, comboNbJoueur, wp, hp, w, h);
		comboNbJoueur.addActionListener(this);

		// (4) Nombre de manche
		lblNbManche = new JLabel("");
		appNbManche = new String[2];// liste des manche
		appNbManche[0] = "";
		appNbManche[1] = "";
		comboNbManche = new JComboBox<String>(appNbManche);
		AddLignePanelParam(lblNbManche, comboNbManche, wp, hp, w, h);
		comboNbManche.addActionListener(this);

		// (5) Utilisateur Courant
		lblUtilisateurCourant = new JLabel("");
		appUtilisateurCourant = new String[5];// liste des manches
		String nomJoueur1="AAAAA";
		String nomJoueur2="BBBBB";
		String nomJoueur3="CCCCC";
		String nomJoueur4="DDDDD";
		
		appUtilisateurCourant[0] = "";
		appUtilisateurCourant[1] = nomJoueur1;
		appUtilisateurCourant[2] =nomJoueur2;
		appUtilisateurCourant[3] = nomJoueur3;
		appUtilisateurCourant[4] = nomJoueur4;
		
		
		
		comboUtilisateurCourant = new JComboBox<String>(appUtilisateurCourant);
		comboUtilisateurCourant.addActionListener(this);

		AddLignePanelParam(lblUtilisateurCourant, comboUtilisateurCourant, wp, hp, w, h);

		// panel vide de separation
		AddLigneVidePanelParam(this, 250, 5, "");

		// boutons
		// btnInitialiser, btnMelanger, btnDistribuer, btnJouer, btnAbandonner, btnFin;
		w = 80;
		h = 30;
		btnInitialiser = new JButton("Initialiser");// pour test
		btnInitialiser.addActionListener(this);
		btnInitialiser.setBackground(Color.white);
		btnInitialiser.setForeground(Color.BLUE);

		btnMelanger = new JButton("Mélanger");// pour test
		btnMelanger.addActionListener(this);
		btnMelanger.setBackground(Color.white);
		btnMelanger.setForeground(Color.BLUE);

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

		AddLignePanelParam(btnInitialiser, btnMelanger, wp, hp + 10, 120, 40);
		AddLignePanelParam(btnDistribuer, btnJouer, wp, hp + 10, 120, 40);
		AddLignePanelParam(btnAbandonner, btnFin, wp, hp + 10, 120, 40);

		// panel test
		AddTestPanelParam(wp, 40);
	}
	//methodes pour dessiner le panel etat
	private void AddLignePanelResultat(Color couleur, Component c1, Component c2, int pWp, int pHp, int pWc, int pHc) {
		JPanel pan = new JPanel();
		int w = pWp, h = pHp;
		w=250;h=35;
		c1.setBackground(couleur);
		c2.setBackground(couleur);
		c1.setForeground(Color.WHITE);
		c2.setForeground(Color.WHITE);

		pan.setPreferredSize(new Dimension(w, h));
		pan.setBorder(new EmptyBorder(0, 0, 0, 0));
		pan.setBackground(couleur);

		w = pWc;
		h = pHc;
		JPanel pan_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pan_1.setPreferredSize(new Dimension(w - 20, h));
		pan_1.setBackground(couleur);
		pan_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		pan_1.add(c1);

		JPanel pan_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pan_2.setPreferredSize(new Dimension(w + 20, h));
		pan_2.setBackground(couleur);
		pan_2.setBorder(new EmptyBorder(0, 0, 0, 0));
		pan_2.add(c2);

		pan.add(pan_1);
		pan.add(pan_2);

		panelResultat.add(pan);

	}
	private void DessinerpanelResultat() {
		// System.out.println("DessinerpanelResultat");//msg console
		
		int wp = 250;
		int hp = 30;
		int w = 90;
		int h = 30;
		
		Font font1 = new Font("SansSerif", Font.BOLD, 14);
		
		
		TxtLibellManche = new JTextField("Manche");
		TxtLibellManche.setBorder(null);
		TxtLibellManche.setFont(font1);
		TxtLibellManche.setEditable(false);
		
		TxtNumManche = new JTextField(": 1 / 1");
		TxtNumManche.setBorder(null);
		TxtNumManche.setFont(font1);
		TxtNumManche.setEditable(false);
		
		comboMode.addActionListener(this);
		TxtScore1 = new JTextField(": 0");
		TxtScore2 = new JTextField(": 0");
		TxtScore3 = new JTextField(": 0");
		TxtScore4 = new JTextField(": 0");

		TxtNomJoueur1 = new JTextField(appUtilisateurCourant[1]);
		TxtNomJoueur2 = new JTextField(appUtilisateurCourant[2]);
		TxtNomJoueur3 = new JTextField(appUtilisateurCourant[3]);
		TxtNomJoueur4 = new JTextField(appUtilisateurCourant[4]);


		TxtNomJoueur1.setFont(font1);
		TxtNomJoueur2.setFont(font1);
		TxtNomJoueur3.setFont(font1);
		TxtNomJoueur4.setFont(font1);

		TxtScore1.setFont(font1);
		TxtScore2.setFont(font1);
		TxtScore3.setFont(font1);
		TxtScore4.setFont(font1);

		TxtNomJoueur1.setBorder(null);
		TxtNomJoueur2.setBorder(null);
		TxtNomJoueur3.setBorder(null);
		TxtNomJoueur4.setBorder(null);

		TxtScore1.setBorder(null);
		TxtScore2.setBorder(null);
		TxtScore3.setBorder(null);
		TxtScore4.setBorder(null);

		TxtScore1.setEditable(false);
		TxtScore2.setEditable(false);
		TxtScore3.setEditable(false);
		TxtScore4.setEditable(false);

		TxtNomJoueur1.addActionListener(this);
		TxtNomJoueur2.addActionListener(this);
		TxtNomJoueur3.addActionListener(this);
		TxtNomJoueur4.addActionListener(this);

		//
		AddLignePanelResultat(new Color(6, 111, 74), TxtLibellManche, TxtNumManche, wp, hp + 10, w, h);// bleu
		
		JPanel pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pan.setPreferredSize(new Dimension(wp, hp-10));
		pan.setBorder(new EmptyBorder(0, 0, 0, 0));
		pan.setBackground(Color.WHITE);
		panelResultat.add(pan);
		
		
		AddLignePanelResultat(new Color(23, 134, 245), TxtNomJoueur1, TxtScore1, wp, hp + 10, w, h);// bleu
		AddLignePanelResultat(new Color(186, 56, 74), TxtNomJoueur2, TxtScore2, wp, hp + 10, w, h);// rouge
		AddLignePanelResultat(new Color(220, 111, 58), TxtNomJoueur3, TxtScore3, wp, hp + 10, w, h);// oranger
		AddLignePanelResultat(new Color(155, 139, 222), TxtNomJoueur4, TxtScore4, wp, hp + 10, w, h);// mauve
	}
	//methode pour afficher les cartes dans les deux grilles
	public void AfficherGrille(String[][] grille, PanelGrille panel, int emplacement) {
		Image imgTuile;
		Tuile oTuile = null;

		//affichage du joueur courant qui a peut-être changé MMMM
		comboUtilisateurCourant.setSelectedIndex(Partie.GetJoueurCourant());

		// emplacement 1 = Grille de jeu et 2=Grilles joueur
		Component[] grilleGraphique = panel.getComponents();;
		String[][] grilleCartes = grille;
		
		// affichage des données
		for (int l = 0; l < grilleGraphique.length; l++) {
			Component oComponent = grilleGraphique[l];
			if (oComponent instanceof Tuile) {
				oTuile = (Tuile) oComponent;
				int i = l / 8;
				int j = l % 8;
				int key = (i * 10) + j;
				oTuile.Key = key;
				String CodeCarte = grilleCartes[i][j];
				String path = "";
				oTuile.CodeCarte = CodeCarte;
				oTuile.Partie=Partie;
				oTuile.Emplacement=emplacement;
				
				if(CodeCarte.startsWith("T1")) path = "./images/turtle1.png";
				if(CodeCarte.startsWith("T2")) path = "./images/turtle2.png";
				if(CodeCarte.startsWith("T3")) path = "./images/turtle3.png";
				if(CodeCarte.startsWith("T4")) path = "./images/turtle4.png";
				
				if(CodeCarte.startsWith("R")) path = "./images/RUBY.png";
				if(CodeCarte.startsWith("W")) path = "./images/WALL.png";
				if(CodeCarte.startsWith("I")) path = "./images/ICE.png";
				if(CodeCarte.startsWith("E")) path = "./images/cards/bug.png";
				if(CodeCarte.startsWith("B")) path = "./images/cards/blueCard.png";
				if(CodeCarte.startsWith("P")) path = "./images/cards/purpleCard.png";
				if(CodeCarte.startsWith("Y")) path = "./images/cards/yellowCard.png";
				if(CodeCarte.startsWith("L")) path = "./images/cards/LaserCard.png";
				if(CodeCarte.startsWith("U")) path = "./images/cards/UnknownCard.png";
				if(CodeCarte.startsWith("C")) path = "./images/cards/TropheCard.png";
				
				if(CodeCarte.startsWith("Paquet")) path = "./images/paquet.png";
				if(CodeCarte.startsWith("Defense")) path = "./images/bouclier.png";
				if(CodeCarte.startsWith("Main")) path = "./images/main.png";
				if(CodeCarte.equals("") ) path = "./images/background_blanc.png";
				
				oTuile.imgPathTuile = path;
				imgTuile = oTuile.RedimentionnerImageFromPath(oTuile.imgPathTuile, oTuile.whith, oTuile.height);
				oTuile.imgTuile = imgTuile;
				oTuile.repaint();
			}
		}
	
		PanelGrille.repaint();
		PanelDefense.repaint();
	}
	public void AfficherGrilleHaut(int mode, String[][] grille) {

		if (mode == 1) AfficherGrille(Partie.GetGrilleDeJeu(),PanelGrille,1);//si jeu
		if (mode == 2) AfficherGrille(grille,PanelGrille,1);// si test cartes
		if (mode == 3) AfficherGrille(grille,PanelGrille,1);// si test programme
	}
	public void AfficherGrillesBas() {
		AfficherGrille(Partie.GetGrilleJoyaux(),PanelJoyaux,2);
		AfficherGrille(Partie.GetGrilleDefense(),PanelDefense,3);
		AfficherGrille(Partie.GetGrilleMain(),PanelMain,4);
		AfficherGrille(Partie.GetGrilleProgramme(2),PanelProgramme,5);
		
		
		//affichage de la manche courante
		TxtNumManche.setText(":"+Partie.GetMancheCourante()+" / "+Partie.GetNbManche());
		
		//affichage des scores
		TxtScore1.setText(""+Partie.GetScoreJoueur(0));
		TxtScore2.setText(""+Partie.GetScoreJoueur(1));
		TxtScore3.setText(""+Partie.GetScoreJoueur(2));
		TxtScore4.setText(""+Partie.GetScoreJoueur(3));
	}
	private void AfficherGrilleDuJoueurTest(int mode) {
		String[][] grilleDeJeu;
		grilleDeJeu = new String[8][8];

		// initialisation
		for (int lig = 0; lig < 8; lig++)
			for (int col = 0; col < 8; col++)
				grilleDeJeu[lig][col] = "";
		//
		if(mode == 2) 
		{
			// affichage carte du joueur
			String[][] grillePaquetDeCartes=Partie.GetGrillePaquetDeCartes();
			for (int col = 0; col < grillePaquetDeCartes[0].length; col++) // grillePaquetDeCarte est à une dimension et on veut l'afficher dans la grille à 2 dimensions
			{
				int i = col / 8;
				int j = col % 8;
				grilleDeJeu[i][j] = grillePaquetDeCartes[0][col];
				//showMessageDialog(null, "carte:"+grilleDeJeu[i][j]);
			}
		}
		
		if(mode == 3)
		{
			// affichage programme :
			String[][] grilleProgramme=Partie.GetGrilleProgramme(1);
			for (int col = 1; col < grilleProgramme[0].length; col++)// grilleProgramme est à une dimension et on veut l'afficher dans la grille à 2 dimensions
			{
				int i = (col-1) / 8;
				int j = (col-1) % 8;
				grilleDeJeu[i][j] = grilleProgramme[0][col];
			}
		}
		
		AfficherGrilleHaut(mode, grilleDeJeu);
	}

	//methodes pour ajuster panel resultat : scores, manche et nb de joueurs
	private void AjusterPanelResultat() {
		// System.out.println("SetpanelResultat index:"+index);//msg console

		int indexNbDeManche=comboNbManche.getSelectedIndex();
		if (indexNbDeManche == 0) {
			TxtNumManche.setText(": 1 / 1");
		}
		if (indexNbDeManche == 1) {
			TxtNumManche.setText(": 1 / 3");
		}
		
		int indexNbDeJoueur=comboNbJoueur.getSelectedIndex();
		
		TxtNomJoueur1.setVisible(true);
		TxtNomJoueur2.setVisible(true);
		TxtNomJoueur3.setVisible(true);
		TxtNomJoueur4.setVisible(true);

		TxtScore1.setVisible(true);
		TxtScore2.setVisible(true);
		TxtScore3.setVisible(true);
		TxtScore4.setVisible(true);

		if (indexNbDeJoueur == -1) {
			TxtNomJoueur1.setVisible(false);
			TxtNomJoueur2.setVisible(false);

			TxtScore1.setVisible(false);
			TxtScore2.setVisible(false);
		}
		if (indexNbDeJoueur == 0) {
			TxtNomJoueur3.setVisible(false);
			TxtNomJoueur4.setVisible(false);

			TxtScore3.setVisible(false);
			TxtScore4.setVisible(false);
		}
		if (indexNbDeJoueur == 1) {
			TxtNomJoueur4.setVisible(false);

			TxtScore4.setVisible(false);
		}
		panelResultat.repaint();
	}

	//methodes des actionPerformed
	private void Initialiser() {
		// System.out.println("Initialiser");//msg console

		int nbManche = 1;
		if (comboNbManche.getSelectedIndex() == 1)
			nbManche = 3;
		
		indexNbJoueur = comboNbJoueur.getSelectedIndex();
		int nbJoueur = 2;
		nbJoueur = 2;
		if (comboNbJoueur.getSelectedIndex() == 1)
			nbJoueur = 3;
		if (comboNbJoueur.getSelectedIndex() == 2)
			nbJoueur = 4;

		int modeDeJeux = 1;// DeBase
		if (comboMode.getSelectedIndex() == 1)	modeDeJeux = 2;//Avancé

		// initalisation de la partie
		Partie.Initialiser(nbManche, nbJoueur, modeDeJeux);
		//juste pour test
		//CheckboxTest.setSelected(false);
		Partie.SetModeTest(CheckboxTest.isSelected());
		btnTestRetJeu.setEnabled(false);
		btnTestAffCarte.setEnabled(false);
		btnTestAffProg.setEnabled(false);
		
		
		//choix aléatoire du premier joueur
		Partie.SetPremierJoueur(nbJoueur);
		int numJoueur = Partie.GetJoueurCourant();
		comboUtilisateurCourant.setSelectedIndex(numJoueur);
		
		//initialisation des noms des joueurs
		if (nbJoueur >= 0 ) {//2,3 ou 4
			Partie.SetNomJoueur(0, comboUtilisateurCourant.getItemAt(1));
			Partie.SetNomJoueur(1, comboUtilisateurCourant.getItemAt(2));
		}
		if (nbJoueur > 2) {//2 ou 3
			Partie.SetNomJoueur(2, comboUtilisateurCourant.getItemAt(3));
		}
		if (nbJoueur > 3) {//4
			Partie.SetNomJoueur(3, comboUtilisateurCourant.getItemAt(4));
		}
	}
	private void Melanger() {
		Partie.MelangerInstructions();
	}
	private void Distribuer() {
		//showMessageDialog(null, toolTipDistribuer);
		Partie.Distribuer();
		this.AfficherGrillesBas();
	}
	public String GetMsg (int num) {
		switch(num) {
		// tooltip
		case 1: 
			if (indexLangue == 0) return "Clic pour tirer une carte";
			if (indexLangue == 1) return "Clic to take a card";
			break; 
		case 2:
			if (indexLangue == 0) return "Clic droit pour defausser toute la main";
			if (indexLangue == 1) return "Right click to discard the whole hand";
			break;
		case 3: 
			if (indexLangue == 0) return "Clic pour affecter un bug";
			if (indexLangue == 1) return "Click to assign a bug";
			break;
		case 4: 
			if (indexLangue == 0) return "Clic droit/gauche pour déposer un mur";
			if (indexLangue == 1) return "Right / left click to drop a wall";
			break;
		case 5: 
			if (indexLangue == 0) return "Clic dans la grille pour déposer ce mur";
			if (indexLangue == 1) return "Click in the grid to drop this wall";
			break;
		case 6: 
			if (indexLangue == 0) return "Clic droit dans la grille pour déposer ce mur";
			if (indexLangue == 1) return "Right click in the grid to drop this wall";
			break;
		case 7: 
			if (indexLangue == 0) return "Clic pour ajouter la carte au programme & Clic droit pour défausser la carte";
			if (indexLangue == 1) return "Click to add the card to the program & Right click to discard the card";
			break;
		case 8: 
			if (indexLangue == 0) return "Nom:";
			if (indexLangue == 1) return "Name:";
			break;
		case 9: 
			if (indexLangue == 0) return "clic pour exécuter le programme, clic droit pour passer le tour";
			if (indexLangue == 1) return "click to execute the program, right click to pass the round";
			break;
		case 10: 
			if (indexLangue == 0) return " / Nb de cartes= ";
			if (indexLangue == 1) return " / Nb of cards= ";
			break;
		// messages utilisateur
		case 21: 
			if (indexLangue == 0) return "Main complète ou nombre max de cartes tirées atteint!";
			if (indexLangue == 1) return "Full hand or maximum number of cards drawn reached!";
			break;
		case 22: 
			if (indexLangue == 0) return "Nb max de cartes défaussées atteint";
			if (indexLangue == 1) return "Max number of discarded cards reached";
			break;
		case 23: 
			if (indexLangue == 0) return "bug affecté";
			if (indexLangue == 1) return "affected bug";
			break;
		case 24: 
			if (indexLangue == 0) return "Pas de bug disponible!";
			if (indexLangue == 1) return "No bug available!";
			break;
		case 25: 
			if (indexLangue == 0) return "Encerclement ou pas de murs disponibles ou opération interdite!";
			if (indexLangue == 1) return "Surrounding or no walls available or operation prohibited!";
			break;
		case 26: 
			if (indexLangue == 0) return "Encerclement ou pas de murs disponibles ou opération interdite!";
			if (indexLangue == 1) return "Surrounding or no walls available or operation prohibited!";
			break;
		case 27: 
			if (indexLangue == 0) return "Erreur d'exécution ou programme vide!";
			if (indexLangue == 1) return "Runtime error or empty program!";
			break;
		case 28: 
			if (indexLangue == 0) return "Fin de la manche!";
			if (indexLangue == 1) return "End of the round!";
			break;
		case 29: 
			if (indexLangue == 0) return "Fin de la partie!";
			if (indexLangue == 1) return "Game over!";
			break;
		case 30: 
			if (indexLangue == 0) return "Impossible d'exécuter, essayez au prochain tour";
			if (indexLangue == 1) return "Unable to execute, try next pass";
			break;			
			}
			return "";
	}
	private void SetLangue() {
		indexLangue = comboLangue.getSelectedIndex();
		indexMode = comboMode.getSelectedIndex();
		indexNbJoueur = comboNbJoueur.getSelectedIndex();
		indexNbManche = comboNbManche.getSelectedIndex();
		indexUtilisateurCourant = comboUtilisateurCourant.getSelectedIndex();

		// System.out.println("SetLangue");//msg console

		if (indexLangue == 0) // français
		{
			// langue
			lblLangue.setText("Langue :");
			appLangue[0] = "Français";
			appLangue[1] = "Anglais";
			DefaultComboBoxModel<String> ModelLangue = new DefaultComboBoxModel<String>(appLangue); 
			comboLangue.setModel(ModelLangue);
			comboLangue.setSelectedIndex(indexLangue);

			// mode
			lblMode.setText("Mode :");
			appMode[0] = "Mode de base";
			appMode[1] = "Mode avancé";
			DefaultComboBoxModel<String> ModelMode = new DefaultComboBoxModel<String>(appMode); 
			comboMode.setModel(ModelMode);
			comboMode.setSelectedIndex(indexMode);

			// Nb Joueur
			lblNbJoueur.setText("Nb de joueurs :");
			appNbJoueur[0] = "Deux joueurs";
			appNbJoueur[1] = "Trois joueurs";
			appNbJoueur[2] = "Quatre joueurs";

			DefaultComboBoxModel<String> ModelNbJoueur = new DefaultComboBoxModel<String>(appNbJoueur); 
			comboNbJoueur.setModel(ModelNbJoueur);
			comboNbJoueur.setSelectedIndex(indexNbJoueur);

			// Nb Manche
			lblNbManche.setText("Nb de manches :");
			appNbManche[0] = "Une manche";
			appNbManche[1] = "Trois manches";

			DefaultComboBoxModel<String> ModelNbManche = new DefaultComboBoxModel<String>(appNbManche); 
			comboNbManche.setModel(ModelNbManche);
			comboNbManche.setSelectedIndex(indexNbManche);

			// Utilisateur Courant
			lblUtilisateurCourant.setText("Joueur Courant :");
			DefaultComboBoxModel<String> ModelUtilisateurCourant = new DefaultComboBoxModel<String>(
					appUtilisateurCourant); // Assign Model data to ComboBoxes from Array
			comboUtilisateurCourant.setModel(ModelUtilisateurCourant);
			comboUtilisateurCourant.setSelectedIndex(indexUtilisateurCourant);

			// boutons
			btnInitialiser.setText("Initialiser");
			btnMelanger.setText("Mélanger");
			btnDistribuer.setText("Distribuer");
			btnJouer.setText("Jouer");
			btnAbandonner.setText("Abandonner");
			btnFin.setText("Sortie");

			toolTipInitialiser = "Initialisation et affichage dans la grille des cartes pour les utilisateurs déclarés et en fonction du mode";
			toolTipMelanger = "Melanger et affichage dans la grille des cartes pour les utilisateurs déclarés et en fonction du mode";
			toolTipDistribuer = "Initialisation Grille pour jouer en fonction du nb des joueur + distribution des cartes pour les utilisateurs";
			toolTipAbandonner = "Abandon de la manche en cours";
			toolTipFin = "Fin de la partie est sortie du programme";
			toolTipJouer = "Début de la manche";

			btnInitialiser.setToolTipText(toolTipInitialiser);
			btnMelanger.setToolTipText(toolTipMelanger);
			btnDistribuer.setToolTipText(toolTipDistribuer);
			btnJouer.setToolTipText(toolTipJouer);
			btnAbandonner.setToolTipText(toolTipAbandonner);
			btnFin.setToolTipText(toolTipFin);
			
			TxtLibellManche.setText("Manche");
		}
		if (indexLangue == 1) // anglais
		{
			lblLangue.setText("Language :");
			appLangue[0] = "French";
			appLangue[1] = "English";
			DefaultComboBoxModel<String> ModelLangue = new DefaultComboBoxModel<String>(appLangue); 
			comboLangue.setModel(ModelLangue);
			comboLangue.setSelectedIndex(indexLangue);

			// mode
			lblMode.setText("Mode :");
			appMode[0] = "Basic mode";
			appMode[1] = "Advanced mode";
			DefaultComboBoxModel<String> ModelMode = new DefaultComboBoxModel<String>(appMode); 
			comboMode.setModel(ModelMode);
			comboMode.setSelectedIndex(indexMode);

			// Nb Joueur
			lblNbJoueur.setText("Nb of players :");
			appNbJoueur[0] = "Two players";
			appNbJoueur[1] = "Three players";
			appNbJoueur[2] = "Four players";

			DefaultComboBoxModel<String> ModelNbJoueur = new DefaultComboBoxModel<String>(appNbJoueur); 
			comboNbJoueur.setModel(ModelNbJoueur);
			comboNbJoueur.setSelectedIndex(indexNbJoueur);

			// Nb Manche
			lblNbManche.setText("Nb of rounds :");
			appNbManche[0] = "One round";
			appNbManche[1] = "Three rounds";

			DefaultComboBoxModel<String> ModelNbManche = new DefaultComboBoxModel<String>(appNbManche); 
			comboNbManche.setModel(ModelNbManche);
			comboNbManche.setSelectedIndex(indexNbManche);

			// Utilisateur Courant
			lblUtilisateurCourant.setText("Current player :");
			DefaultComboBoxModel<String> ModelUtilisateurCourant = new DefaultComboBoxModel<String>(
					appUtilisateurCourant); // Assign Model data to ComboBoxes from Array
			comboUtilisateurCourant.setModel(ModelUtilisateurCourant);
			comboUtilisateurCourant.setSelectedIndex(indexUtilisateurCourant);

			// boutons
			btnInitialiser.setText("Initialize");
			btnMelanger.setText("Mix");
			btnDistribuer.setText("Distribute");
			btnJouer.setText("GO");
			btnAbandonner.setText("Cancel");
			btnFin.setText("Exit");
			toolTipInitialiser = "Initialization and display in the map grid for registered users and depending on the mode";
			toolTipMelanger = "Mix and display in the map grid for registered users and according to the mode";
			toolTipDistribuer = "Initialization Grid to play according to the number of players + distribution of cards for users";
			toolTipAbandonner = "Abandonment of the round in progress";
			toolTipFin = "End of the game has left the program";
			toolTipJouer = "Beginning of the round";

			btnInitialiser.setToolTipText(toolTipInitialiser);
			btnMelanger.setToolTipText(toolTipMelanger);
			btnDistribuer.setToolTipText(toolTipDistribuer);
			btnJouer.setToolTipText(toolTipJouer);
			btnAbandonner.setToolTipText(toolTipAbandonner);
			btnFin.setToolTipText(toolTipFin);
			
			TxtLibellManche.setText("Round");
		}
	}
	private void Abandonner() {
		//showMessageDialog(null, toolTipAbandonner);
		btnInitialiser.setEnabled(true);
		btnMelanger.setEnabled(true);
		btnDistribuer.setEnabled(true);
	}
	private void Sortie() {
		//showMessageDialog(null, toolTipFin);
		System.exit(0); 
	}
	private void Jouer() {
		//showMessageDialog(null, toolTipJouer);
		btnInitialiser.setEnabled(false);
		btnMelanger.setEnabled(false);
		btnDistribuer.setEnabled(false);
	}

	// actionPerformed
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object sourceAction = arg0.getSource();
		//arg0.consume();
		if (sourceAction == comboLangue) {
			this.SetLangue();
		}
		if (sourceAction == comboMode) {
			//this.SetLangue();
		}
		if (sourceAction == comboNbJoueur) {
			this.AjusterPanelResultat();
			//this.SetLangue();
		}
		if (sourceAction == comboNbManche) {
			this.AjusterPanelResultat();
			//this.SetLangue();
		}
		if (sourceAction == comboUtilisateurCourant) {
			//pour test
			//Partie.SetJoueurCourant(comboUtilisateurCourant.getSelectedIndex() - 1);
		}
		if (sourceAction == btnInitialiser) {
			this.Initialiser();
			this.AfficherGrilleHaut(1, null);
		}
		if (sourceAction == btnMelanger) {
			this.Melanger();
		}
		if (sourceAction == btnDistribuer) {
			this.Distribuer();

		}
		if (sourceAction == btnAbandonner) {
			this.Abandonner();

		}
		if (sourceAction == btnFin) {
			this.Sortie();

		}
		if (sourceAction == btnJouer) {
			this.Jouer();

		}

		if (sourceAction == TxtNomJoueur1) {
			String nom=TxtNomJoueur1.getText();
			appUtilisateurCourant[1] = nom;
			Partie.SetNomJoueur(1-1, nom);
			this.SetLangue();
		}
		if (sourceAction == TxtNomJoueur2) {
			//appUtilisateurCourant[2] = TxtNomJoueur2.getText();
			String nom=TxtNomJoueur2.getText();
			appUtilisateurCourant[2] = nom;
			Partie.SetNomJoueur(2-1, nom);
			this.SetLangue();
		}
		if (sourceAction == TxtNomJoueur3) {
			//appUtilisateurCourant[3] = TxtNomJoueur3.getText();
			String nom=TxtNomJoueur3.getText();
			appUtilisateurCourant[3] = nom;
			Partie.SetNomJoueur(3-1, nom);
			this.SetLangue();
		}
		if (sourceAction == TxtNomJoueur4) {
			//appUtilisateurCourant[4] = TxtNomJoueur4.getText();
			String nom=TxtNomJoueur4.getText();
			appUtilisateurCourant[4] = nom;
			Partie.SetNomJoueur(4-1, nom);
			this.SetLangue();
		}
		//CheckboxTest
		if (sourceAction == CheckboxTest) {
			//showMessageDialog(null, "CheckboxTest");
			Partie.SetModeTest(CheckboxTest.isSelected());
			btnTestRetJeu.setEnabled(CheckboxTest.isSelected());
			btnTestAffCarte.setEnabled(CheckboxTest.isSelected());
			btnTestAffProg.setEnabled(CheckboxTest.isSelected());
			
		}
		if (sourceAction == btnTestRetJeu) {
			this.AfficherGrilleHaut(1, null);
		}
		if (sourceAction == btnTestAffCarte) {
			this.AfficherGrilleDuJoueurTest(2);
		}
		if (sourceAction == btnTestAffProg) {
			this.AfficherGrilleDuJoueurTest(3);
		}
	}
	
	//programme principal
	public static void main(String[] args) {
		// partie par defaut
		
		int nbManche=1; int nbJoueur=2; int modeDeJeux=1;
		
		RobotTurtlesPartie partie= new RobotTurtlesPartie(nbManche,nbJoueur,modeDeJeux);
		RobotTurtlesInterface frame = new RobotTurtlesInterface(partie);
	}
		
}
