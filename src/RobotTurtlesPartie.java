
//Nom="RobotTurtles Isep 2020";

import java.util.ArrayList;
import java.util.Random;

//classe joueur/tortue
class Joueur {
	private String Nom;
	private String Tortue;
	private int LigneTortue, ColonneTortue;
	public int LigneInitialeTortue, ColonneInitialeTortue;
	private int OrientationTortue; // BAS = 2; GAUCHE = 1; DROITE = 3; HAUT = 0;
	private int ActionDuTour; // 0: rien , 1:Compléter , 2:exécuter, 3:construire un mur, 4:tirer une carte
	public int NbCarteTiree, NbCarteDefaussee;
	// liste des cartes
	private ArrayList<String> PaquetCartes = new ArrayList<String>();// paquet de carte
	private ArrayList<String> MainDuJoueur = new ArrayList<String>();// la main du joueur
	private ArrayList<String> Programme = new ArrayList<String>(); // le programme
	private ArrayList<String> Defenses = new ArrayList<String>();// la liste des cartes de defense
	private ArrayList<String> Joyaux = new ArrayList<String>();// les joyaux gagnés par l'utilisateur
	// presentation des carte dans des grilles à 2 dimensions
	private String[][] GrilleCartes;// gere les position des cartes dans le pave du bas
	private String[][] GrilleJoyaux;// gere les position des cartes dans le pave du bas
	private String[][] GrilleMain;// gere les position des cartes dans le pave du bas
	private String[][] GrilleDefense;// gere les position des cartes dans le pave du bas
	private String[][] GrilleProgramme;// gere les position des cartes dans le pave du bas
	private int NbCarteMurPierre;
	private int NbCarteMurGlace;
	private int ModeDeJeux;// DeBase=1, Avancé=2 (avec carte bug et 3 manches)
	public int NbCarteJouees; // nb de carte jouées, ser pour melanger la defausse
	public boolean EnJeu;
	private static final int BAS = 2;
	private static final int GAUCHE = 1;
	private static final int DROITE = 3;
	private static final int HAUT = 0;

	// constructeur
	public Joueur(String nom, int key, int modeDeJeux, int nbCarteMurPierre, int nbCarteMurGlace) {
		Nom = nom;
		Tortue = "T" + key;

		NbCarteMurPierre = nbCarteMurPierre;
		NbCarteMurGlace = nbCarteMurGlace;
		ModeDeJeux = modeDeJeux;
		OrientationTortue = BAS;// vers le bas au démarrage
		ActionDuTour = 0;
		NbCarteTiree = 0;
		NbCarteDefaussee = 0;
		EnJeu = true;

		InitialiserJoueur();
		Joyaux.clear();// en debut de partie
	}

	// methodes
	public void InitialiserJoueur() {
		/*
		 * {"T1", "./images/turtle1.png"}, // tortue {"T2", "./images/turtle2.png"},
		 * {"T3", "./images/turtle3.png"}, {"T4", "./images/turtle4.png"},
		 * 
		 * {"R", "./images/RUBY.png"}, // joyau {"W", "./images/WALL.png"}, // mur de
		 * pierre {"I", "./images/ICE.png"}, // mur de glace {"E", "./images/bug.png"},
		 * // carte bug / erreur
		 * 
		 * {"B", "./images/cards/bleueCard.png"}, // avancer {"P",
		 * "./images/cards/purpleCard.png"}, // tourner à droide {"Y",
		 * "./images/cards/yellowCard.png"}, // tourner à gauche {"L",
		 * "./images/cards/LaserCard.png"}, // laser {"U",
		 * "./images/cards/UnknownCard.png"}, // carte a l'envers {"C",
		 * "./images/cards/TropheCard.png"}, // carte coupe / point gagné
		 * 
		 * {"Paquet", "./images/cards/paquet.png"}, // paquet de carte {"Defense",
		 * "./images/cards/bouclier.png"}, // liste des murs + carte bug {"Main",
		 * "./images/cards/main.png"}, // main du joueur {"Tortue",
		 * "./images/cards/tortuex.png"}, // tortue du joueur
		 */

		// initialisation des cartes
		PaquetCartes.clear();
		for (int i = 0; i < 18; i++)
			PaquetCartes.add("B" + i);// avancer
		for (int i = 0; i < 8; i++)
			PaquetCartes.add("Y" + i);// tourner a gauche
		for (int i = 0; i < 8; i++)
			PaquetCartes.add("P" + i);// tourner a droite
		for (int i = 0; i < 3; i++)
			PaquetCartes.add("L" + i);// laser

		Defenses.clear();
		for (int i = 0; i < NbCarteMurPierre; i++)
			Defenses.add("W" + i); // mur de pierre
		for (int i = 0; i < NbCarteMurGlace; i++)
			Defenses.add("I" + i); // mur de glace
		if (ModeDeJeux == 2)
			Defenses.add("E"); // carte bug/erreur

		MainDuJoueur.clear();
		Programme.clear();

		CreerGrilles();
		// distribuer les cartes pour le joueur
		// Distribuer();
	}

	private void CreerGrilles() {
		// grilles a vide
		GrilleCartes = new String[1][37];
		for (int col = 0; col < 37; col++)
			GrilleCartes[0][col] = "";

		GrilleJoyaux = new String[1][8];
		for (int col = 0; col < 8; col++)
			GrilleJoyaux[0][col] = "";

		GrilleMain = new String[1][8];
		for (int col = 0; col < 8; col++)
			GrilleMain[0][col] = "";

		GrilleDefense = new String[1][8];
		for (int col = 0; col < 8; col++)
			GrilleDefense[0][col] = "";

		GrilleProgramme = new String[1][37];
		for (int col = 0; col < 37; col++)
			GrilleProgramme[0][col] = "";

		/*
		 * GrilleJoyaux[0][0] = "Paquet"; GrilleDefense[0][0] = "Defense";
		 * GrilleMain[0][0] = "Main"; //GrilleProgramme[0][0] = "Programme";
		 * GrilleProgramme[0][0] = Tortue;
		 */

		// ligne des cartes pioche
		for (int i = 0; i < PaquetCartes.size(); i++) {
			GrilleCartes[0][i] = PaquetCartes.get(i);
		}

		// ligne joyaux gagnés
		for (int i = 0; i < Joyaux.size(); i++) {
			GrilleJoyaux[0][i + 1] = Joyaux.get(i);
		}
		// ligne defense
		for (int i = 0; i < Defenses.size(); i++) {
			GrilleDefense[0][i + 1] = Defenses.get(i);
		}
		// ligne main
		for (int i = 0; i < MainDuJoueur.size(); i++) {
			GrilleMain[0][i + 1] = MainDuJoueur.get(i);
		}
		// ligne programme
		if (Programme.size() > 0)
			GrilleProgramme[0][1] = "Programme";

		for (int i = 0; i < Programme.size(); i++) {
			GrilleProgramme[0][i + 1] = Programme.get(i);
		}

	}

	// les accesseurs get et les mutateurs set
	public String GetNomJoueur() {
		return Nom;
	}

	public int GetScore() {
		return Joyaux.size();
	}

	public String GetTortue() {
		return Tortue;
	}

	public int GetLigneTortue() {
		return LigneTortue;
	}

	public int GetColonneTortue() {
		return ColonneTortue;
	}

	public String GetOrientTortue() {
		String orient = "";
		switch (OrientationTortue) {
		case HAUT:
			orient = "Haut";
			break;
		case GAUCHE:
			orient = "Gauche";
			break;
		case BAS:
			orient = "Bas";
			break;
		case DROITE:
			orient = "Droite";
			break;
		default:
			break;
		}
		return orient;
	}

	public int GetOrientationTortue() {
		return OrientationTortue;
	}

	public ArrayList<String> GetPaquetCartes() {
		return PaquetCartes;
	}

	public ArrayList<String> GetMainDuJoueur() {
		return MainDuJoueur;
	}

	public ArrayList<String> GetProgramme() {
		return Programme;
	}

	public ArrayList<String> GetDefenses() {
		return Defenses;
	}

	public ArrayList<String> GetJoyaux() {
		return Joyaux;
	}

	// grilles du jeux pour interface graphique
	public String[][] GetGrillePaquetDeCartes() {
		return GrilleCartes;
	}

	public String[][] GetGrilleJoyaux() {
		return GrilleJoyaux;
	}

	public String[][] GetGrilleDefense() {
		return GrilleDefense;
	}

	public String[][] GetGrilleMain() {
		return GrilleMain;
	}

	public String[][] GetGrilleProgramme(int mode) {
		String[][] grille;
		if (mode == 1)
			grille = GrilleProgramme; // mode=1 afficher les cartes en mode clair et mode = 2 afficher les cartes face
										// cachée
		else {
			grille = new String[1][37];
			// copie de la grille
			for (int col = 0; col < 37; col++) {
				grille[0][col] = GrilleProgramme[0][col];
			}
			// brouillage des cartes du programme
			for (int col = 1; col < 37; col++) {
				if (grille[0][col] != "" && grille[0][col] != "E")
					grille[0][col] = "U" + grille[0][col];
			}
		}
		return grille;
	}

	public int GetActionDuTour() {
		return ActionDuTour;
	}

	public void SetActionDuTour(int action) {
		ActionDuTour = action;
		if (action == 0) {
			NbCarteTiree = 0;
			NbCarteDefaussee = 0;
		}
	}

	public void SetPositionTortue(int lig, int col, int mode) {
		LigneTortue = lig;
		ColonneTortue = col;
		if (mode == 1) {// mode initialisation
			LigneInitialeTortue = lig;
			ColonneInitialeTortue = col;
			EnJeu = true;
		}
	}

	public void SetOrientationTortue(int orientation) {
		OrientationTortue = orientation;
	}

	public void SetNomJoueur(String nom) {
		Nom = nom;
	}

	// methode propres au jeu
	public void RetourTortuePositionInitiale() {
		LigneTortue = LigneInitialeTortue;
		ColonneTortue = ColonneInitialeTortue;
		OrientationTortue = 2;
	}

	public boolean InverserLeProgramme() {

		ArrayList<String> programmeTmp = new ArrayList<String>();
		int j = 0;
		for (int i = Programme.size() - 1; i >= 0; i--) {
			programmeTmp.add(j++, Programme.get(i));
		}
		Programme = programmeTmp;
		CreerGrilles();
		return true;
	}

	public void NouveauJoyau(String codeCarte, int nbJoueur, int nbGagnant) {

		// nb de joyaux =nb points gagnées
		for (int i = 0; i < nbJoueur - nbGagnant; i++) {
			Joyaux.add("C" + i);
		}
		CreerGrilles();
	}

	public boolean AffecterUnBug() {

		Programme.add(0, "E");
		CreerGrilles();

		return true;
	}

	public boolean SupprimerCarteBug() {
		// recherche de carte bug
		for (int i = 0; i < Defenses.size(); i++) {
			if (Defenses.get(i).startsWith("E")) {
				Defenses.remove(i);
				CreerGrilles();
				return true;
			}
		}
		return false;
	}

	public void MelangerInstructions() {
		// nombre aléatoire entre 2 et 37
		Random random = new Random();
		for (int n = 0; n < 50; n++) {
			int indiceMelange = random.nextInt(20) + 1;
			for (int i = indiceMelange; i < PaquetCartes.size(); i++) {
				int newIndice = random.nextInt(37);
				newIndice = newIndice % PaquetCartes.size();
				String val = PaquetCartes.get(i);
				PaquetCartes.remove(i);
				PaquetCartes.add(newIndice, val);
			}
		}
		CreerGrilles();
	}

	public void Distribuer() {
		int j = 0;
		NbCarteTiree=0;
		NbCarteDefaussee=0;
		MainDuJoueur.clear();
		for (int i = 0; i < 5; i++) {
			MainDuJoueur.add(PaquetCartes.get(i));// on tire en haut de la pile des cartes!
			j++;
			NbCarteTiree++;
		}
		for (int i = 0; i < j; i++) {
			PaquetCartes.remove(0);
		}

		CreerGrilles();
	}

	public boolean TirerUneCarte() {
		if (NbCarteTiree >= 5) {
			return false;
		}
		int lg = MainDuJoueur.size();
		if (lg < 5) {
			MainDuJoueur.add(PaquetCartes.get(0)); // ajout en fin de liste
			PaquetCartes.remove(0);
			NbCarteTiree++;
		} else {
			return false;
		}
		CreerGrilles();
		return true;
	}

	public boolean DefausserUneCarte(String codeCarte) {
		if (NbCarteDefaussee >= 5)
			return false;
		// deplacer de main vers cartes
		for (int i = 0; i < MainDuJoueur.size(); i++) {
			if (MainDuJoueur.get(i).equals(codeCarte)) {
				PaquetCartes.add(MainDuJoueur.get(i)); // ajout en fin de liste
				MainDuJoueur.remove(i);
				NbCarteDefaussee++;
			}
		}

		this.CreerGrilles();
		return true;
	}

	public boolean DefausserLaMain() {
		// deplacer tout de main vers cartes
		int j = 0;
		for (int i = 0; i < MainDuJoueur.size() && NbCarteDefaussee < 5; i++) {
			PaquetCartes.add(MainDuJoueur.get(i)); // ajout en fin de liste
			NbCarteDefaussee++;
			// MainDuJoueur.remove(i);
			j++;
		}
		for (int i = 0; i < j; i++)
			MainDuJoueur.remove(0);

		CreerGrilles();

		if (MainDuJoueur.size() > 0)
			return false;
		return true;
	}

	public boolean InsererCarteAuProgramme(String codeCarte, boolean modeTest) {
		// deplacer de main vers programme
		for (int i = 0; i < MainDuJoueur.size(); i++) {
			if (MainDuJoueur.get(i).equals(codeCarte)) {
				Programme.add(MainDuJoueur.get(i)); // ajout en fin de liste
				MainDuJoueur.remove(i);
			}
		}
		if (!modeTest) {
			SetActionDuTour(1); // compléter programme
		}
		this.CreerGrilles();
		return true;
	}

	public boolean AnnulerLeProgramme() {
		// annulation après exécution
		// deplacer tout le programme vers main ou paquet
		for (int i = Programme.size() - 1; i >= 0; i--) {
			PaquetCartes.add(Programme.get(i)); // ajout en fin de liste si carte != bug
		}
		Programme.clear(); // enlever la carte bug

		this.CreerGrilles();

		return true;
	}

	public String GetMur(int type) {
		for (int i = 0; i < Defenses.size(); i++) {
			String codeCarte = Defenses.get(i);
			// if(type == 1)
			if (type == 1 && codeCarte.startsWith("W")) {
				// mur de pierre
				Defenses.remove(i);
				this.CreerGrilles();
				return codeCarte;
			}
			if (type == 2 && codeCarte.startsWith("I")) {
				// mur de glace
				Defenses.remove(i);
				CreerGrilles();
				return codeCarte;
			}
		}
		return "";
	}
}

//classe partie/maitre du jeu
public class RobotTurtlesPartie {
	private int NbManche, NbJoueur;
	public int NbJoueurEnJeu;
	private int ModeDeJeux;
	private int MancheCourante, JoueurCourant;
	private int NbRubyEnJeux, NbGagnant;
	private String[][] GrilleDeJeu;// gere le jeu : tortues, murs, joyaux
	private ArrayList<Joueur> ListJoueurs;
	// constantes pour orientation des tortues
	private static final int BAS = 2;
	private static final int GAUCHE = 1;
	private static final int DROITE = 3;
	private static final int HAUT = 0;
	private boolean ModeTest = false;// permet de ne pas changer de joueur pour faciliter les tests
	// constructeur

	public RobotTurtlesPartie(int nbManche, int nbJoueur, int modeDeJeux) {
		NbManche = nbManche;
		NbJoueur = nbJoueur;
		ModeDeJeux = modeDeJeux;

		MancheCourante = 1;
		Initialiser(nbManche, nbJoueur, modeDeJeux);
	}

	// methodes
	public void Initialiser(int nbManche, int nbJoueur, int modeDeJeux) {
		NbManche = nbManche;
		NbJoueur = nbJoueur;
		ModeDeJeux = modeDeJeux;
		NbJoueurEnJeu = NbJoueur;
		// le jeu de cartes , 4 tortues, 4 joyaux, 3*4 murs de pierre, 2*4 mure de
		// glace, 4*1 bug
		// 11*4 carte bleue, 11*4 carte purple, 11*4 cartes jaune, 11*4 carte laser
		/*
		 * {"T1", "./images/turtle1.png"}, // tortue {"T2", "./images/turtle2.png"},
		 * {"T3", "./images/turtle3.png"}, {"T4", "./images/turtle4.png"},
		 * 
		 * {"R", "./images/RUBY.png"}, // joyau {"W", "./images/WALL.png"}, // mur de
		 * pierre {"I", "./images/ICE.png"}, // mur de glace {"E", "./images/ICE.png"},
		 * // carte bug / erreur
		 * 
		 * {"B", "./images/cards/bleueCard.png"}, // avancer {"P",
		 * "./images/cards/purpleCard.png"}, // tourner à droite {"Y",
		 * "./images/cards/yellowCard.png"}, // tourner à gauche {"L",
		 * "./images/cards/LaserCard.png"}, // laser
		 */

		ListJoueurs = new ArrayList<Joueur>(NbJoueur);

		for (int i = 0; i < NbJoueur; i++) {
			ListJoueurs.add(i, new Joueur("Tortue" + (i + 1), i + 1, ModeDeJeux, 3, 2));
		}

		initialiserGrilles();
	}

	public void initialiserGrilles() {
		NbJoueurEnJeu = NbJoueur;
		// grilles a vide
		GrilleDeJeu = new String[8][8];

		for (int lig = 0; lig < 8; lig++)
			for (int col = 0; col < 8; col++)
				GrilleDeJeu[lig][col] = "";

		if (NbJoueur == 2) {
			// SetPositionTortue
			ListJoueurs.get(0).SetPositionTortue(0, 1, 1);
			ListJoueurs.get(1).SetPositionTortue(0, 5, 1);

			GrilleDeJeu[0][1] = "T1";// tortue 1
			GrilleDeJeu[0][5] = "T2";// tortue 2
			GrilleDeJeu[7][3] = "R";// ruby
			NbRubyEnJeux = 1;
		}
		if (NbJoueur == 3) {
			ListJoueurs.get(0).SetPositionTortue(0, 0, 1);
			ListJoueurs.get(1).SetPositionTortue(0, 3, 1);
			ListJoueurs.get(2).SetPositionTortue(0, 6, 1);

			GrilleDeJeu[0][0] = "T1";// tortue 1
			GrilleDeJeu[0][3] = "T2";// tortur 2
			GrilleDeJeu[0][6] = "T3";// tortur 3
			GrilleDeJeu[7][0] = "R";// ruby
			GrilleDeJeu[7][3] = "R";// ruby
			GrilleDeJeu[7][6] = "R";// ruby
			NbRubyEnJeux = 3;
		}

		if (NbJoueur == 4) {
			ListJoueurs.get(0).SetPositionTortue(0, 0, 1);
			ListJoueurs.get(1).SetPositionTortue(0, 2, 1);
			ListJoueurs.get(2).SetPositionTortue(0, 5, 1);
			ListJoueurs.get(3).SetPositionTortue(0, 7, 1);

			GrilleDeJeu[0][0] = "T1";// tortur 1
			GrilleDeJeu[0][2] = "T2";// tortur 2
			GrilleDeJeu[0][5] = "T3";// tortur 3
			GrilleDeJeu[0][7] = "T4";// tortur 4

			GrilleDeJeu[7][1] = "R";// ruby
			GrilleDeJeu[7][6] = "R";// ruby
			NbRubyEnJeux = 2;
		}
		NbGagnant = 0;
	}

	public void InverserLeProgramme() {
		ListJoueurs.get(JoueurCourant).InverserLeProgramme();
	}

	// les accesseurs get et mutateurs set
	public boolean GetModeTest() {
		return ModeTest;
	}

	public String GetTortue(int NumJoueur) {
		return ListJoueurs.get(NumJoueur).GetTortue();
	}

	public int GetLigneTortue(int joueurCourant) {
		return ListJoueurs.get(joueurCourant).GetLigneTortue();
	}

	public int GetColonneTortue(int joueurCourant) {
		return ListJoueurs.get(joueurCourant).GetColonneTortue();
	}

	public ArrayList<String> GetProgramme() {
		return ListJoueurs.get(JoueurCourant).GetProgramme();
	}

	public int GetNbJoueur() {
		return ListJoueurs.size();
	}

	public String GetNomJoueurCourant() {
		return ListJoueurs.get(JoueurCourant).GetNomJoueur();
	}

	public String GetOrientTortue() {
		return ListJoueurs.get(JoueurCourant).GetOrientTortue();
	}

	public int GetJoueurCourant() {
		return JoueurCourant + 1;
	}

	public int GetNumJoueurCourant() {
		return JoueurCourant;
	}

	public int GetNbCarteProgramme() {
		return ListJoueurs.get(JoueurCourant).GetProgramme().size();
	}

	public int GetNbCartePaquet() {
		return ListJoueurs.get(JoueurCourant).GetPaquetCartes().size();
	}

	public int GetScoreJoueur(int num) {
		if (num < NbJoueur)
			return ListJoueurs.get(num).GetScore();
		// si non
		return 0;

	}

	public int GetMancheCourante() {
		return MancheCourante;
	}

	public int GetNbManche() {
		return NbManche;
	}

	// get des grilles du jeu pour interface graphique
	public String[][] GetGrillePaquetDeCartes() {
		return ListJoueurs.get(JoueurCourant).GetGrillePaquetDeCartes();
	}

	public String[][] GetGrilleJoyaux() {
		// showMessageDialog(null, "JoueurCourant:"+JoueurCourant+"nb
		// joyaux:"+ListJoueurs.get(JoueurCourant).GetGrilleJoyaux()[0].length);
		return ListJoueurs.get(JoueurCourant).GetGrilleJoyaux();
	}

	public String[][] GetGrilleDefense() {
		return ListJoueurs.get(JoueurCourant).GetGrilleDefense();
	}

	public String[][] GetGrilleMain() {
		return ListJoueurs.get(JoueurCourant).GetGrilleMain();
	}

	public String[][] GetGrilleProgramme(int mode) {
		return ListJoueurs.get(JoueurCourant).GetGrilleProgramme(mode);
	}

	public String[][] GetGrilleDeJeu() {
		return GrilleDeJeu;
	}

	public void SetModeTest(boolean modeTest) {
		ModeTest = modeTest;
	}

	public void SetNomJoueur(int num, String nom) {
		if (NbJoueur <= num)
			return;
		ListJoueurs.get(num).SetNomJoueur(nom);
	}

	public void SetPremierJoueur(int nb) {
		// nombre aléatoire entre 0 et nb de joueurs
		Random random = new Random();
		int numJoueur;
		numJoueur = random.nextInt(nb);

		JoueurCourant = numJoueur;
	}

	// methode propres au jeu
	public void FinDuTour() {
		PasserAuJoueurSuivant();
	}

	public void MelangerInstructions() {
		for (int i = 0; i < ListJoueurs.size(); i++) {
			ListJoueurs.get(i).MelangerInstructions();
		}
	}

	public boolean AffecterUnBug(String codeCarte) {
		boolean ret = ListJoueurs.get(JoueurCourant).SupprimerCarteBug();
		if (ret) {
			if (codeCarte.endsWith("1"))
				return ListJoueurs.get(0).AffecterUnBug();
			if (codeCarte.endsWith("2"))
				return ListJoueurs.get(1).AffecterUnBug();
			if (codeCarte.endsWith("3"))
				return ListJoueurs.get(2).AffecterUnBug();
			if (codeCarte.endsWith("4"))
				return ListJoueurs.get(3).AffecterUnBug();
		}

		return false;
	}

	public void Distribuer() {
		// showMessageDialog(null, "partie.distribuer");
		for (int i = 0; i < ListJoueurs.size(); i++) {
			ListJoueurs.get(i).Distribuer();
			ListJoueurs.get(i).SetActionDuTour(0);
		}

	}

	public boolean TirerUneCarte() {
		return ListJoueurs.get(JoueurCourant).TirerUneCarte();

	}

	public boolean DefausserUneCarte(String codeCarte) {
		return ListJoueurs.get(JoueurCourant).DefausserUneCarte(codeCarte);
	}

	public boolean DefausserLaMain() {
		return ListJoueurs.get(JoueurCourant).DefausserLaMain();
	}

	public boolean DeposerUnMur(int typeMur, int keyCarte) {
		if (ListJoueurs.get(JoueurCourant).GetActionDuTour() != 0) {
			return false;
		}
		int lig = keyCarte / 10;
		int col = keyCarte % 10;

		// verifier l'encerclement
		String caseHaut, caseBas, caseGauche, caseDroite;
		caseHaut = caseBas = caseGauche = caseDroite = "";
		if (lig > 0)
			caseHaut = GrilleDeJeu[lig - 1][col];
		if (lig < 7)
			caseBas = GrilleDeJeu[lig + 1][col];
		if (col > 0)
			caseGauche = GrilleDeJeu[lig][col - 1];
		if (col < 7)
			caseDroite = GrilleDeJeu[lig][col + 1];
		if (typeMur == 1) {// pierre
			if (caseHaut.startsWith("T") || caseHaut.startsWith("R"))
				return false;
			if (caseBas.startsWith("T") || caseBas.startsWith("R"))
				return false;
			if (caseGauche.startsWith("T") || caseGauche.startsWith("R"))
				return false;
			if (caseDroite.startsWith("T") || caseDroite.startsWith("R"))
				return false;
		}
		// si mur disponible ?
		String carte = ListJoueurs.get(JoueurCourant).GetMur(typeMur);
		if (carte == "")
			return false;
		// sinon on place le mur
		GrilleDeJeu[lig][col] = carte;
		if (!ModeTest) {
			ListJoueurs.get(JoueurCourant).SetActionDuTour(3); // construire un mur
		}
		// passer au joueur suivant
		PasserAuJoueurSuivant();
		return true;
	}

	public boolean InsererCarteAuProgramme(String codeCarte) {
		return ListJoueurs.get(JoueurCourant).InsererCarteAuProgramme(codeCarte,ModeTest);
	}

	public boolean AnnulerLeProgramme() {
		boolean ret = ListJoueurs.get(JoueurCourant).AnnulerLeProgramme();
		if (ret == true) {
			PasserAuJoueurSuivant();
			return true;
		}
		return false;
	}

	private void SetPositionTortue(int lig, int col, int mode) {
		ListJoueurs.get(JoueurCourant).SetPositionTortue(lig, col, mode);
	}

	private void SetOrientationTortue(int numJoueur, int orientation) {
		ListJoueurs.get(numJoueur).SetOrientationTortue(orientation);
	}

	private int GetOrientationTortue(int numJoueur) {
		return ListJoueurs.get(numJoueur).GetOrientationTortue();
	}

	private void RetourTortuePositionInitiale(String carteTortue) {
		int numJoueur = -1;
		if (carteTortue.endsWith("1"))
			numJoueur = 0;
		if (carteTortue.endsWith("2"))
			numJoueur = 1;
		if (carteTortue.endsWith("3"))
			numJoueur = 2;
		if (carteTortue.endsWith("4"))
			numJoueur = 3;

		String cartetortue2 = GrilleDeJeu[ListJoueurs.get(numJoueur).LigneInitialeTortue][ListJoueurs.get(numJoueur).ColonneInitialeTortue];
		if (numJoueur >= 0) {
			int lig = ListJoueurs.get(numJoueur).GetLigneTortue();
			int col = ListJoueurs.get(numJoueur).GetColonneTortue();

			GrilleDeJeu[lig][col] = "";

			ListJoueurs.get(numJoueur).RetourTortuePositionInitiale();
			lig = ListJoueurs.get(numJoueur).GetLigneTortue();
			col = ListJoueurs.get(numJoueur).GetColonneTortue();
			GrilleDeJeu[lig][col] = ListJoueurs.get(numJoueur).GetTortue();
		}

		// si on trouve une tortue dans la position initiale d'une autre tortue
		if (cartetortue2.startsWith("T"))
			RetourTortuePositionInitiale(cartetortue2);
	}

	private void FaireDemiTourTortue(int numJoueur) {
		int orientation = GetOrientationTortue(numJoueur);
		orientation = (orientation + 2) % 4;
		SetOrientationTortue(numJoueur, orientation);
	}

	private void ExecuterProcedureAvancer() {
		int orientationTortue = GetOrientationTortue(JoueurCourant);
		String tortue = GetTortue(JoueurCourant);

		int ligTortue = GetLigneTortue(JoueurCourant);
		int colTortue = GetColonneTortue(JoueurCourant);

		// si avancer
		int newLigTortue = ligTortue;
		int newColTortue = colTortue;

		if (orientationTortue == BAS)
			newLigTortue++;// vers le bas;
		if (orientationTortue == HAUT)
			newLigTortue--;// vers le haut;
		if (orientationTortue == GAUCHE)
			newColTortue--;// vers la gauche;
		if (orientationTortue == DROITE)
			newColTortue++;// vers la droite;

		// sortie du plateau
		if (newLigTortue < 0 || newLigTortue > 7 || newColTortue < 0 || newColTortue > 7) {
			// position initiale
			RetourTortuePositionInitiale(GetTortue(JoueurCourant));
		} else if (newLigTortue >= 0 && newLigTortue < 8 && newColTortue >= 0 && newColTortue < 8) {
			String nouvelleCase = GrilleDeJeu[newLigTortue][newColTortue];

			if (nouvelleCase.startsWith("R")) {// un joyau gangé ! youpi !!
				ListJoueurs.get(JoueurCourant).NouveauJoyau(nouvelleCase, NbJoueur, ++NbGagnant);
				// nouvelle coordonnées de la tortue;
				ListJoueurs.get(JoueurCourant).EnJeu = false;
				NbJoueurEnJeu--;
				GrilleDeJeu[ligTortue][colTortue] = "";

				// GrilleDeJeu[newLigTortue][newColTortue]=tortue;
				// SetPositionTortue(newLigTortue, newColTortue,2);
			} else if (nouvelleCase.startsWith("W") || nouvelleCase.startsWith("I")) {
				// si carambolage avec un mur/demi-tour
				FaireDemiTourTortue(JoueurCourant);
			} else if (nouvelleCase.startsWith("T")) {
				// si carambolage avec une autre tortue/les deux tortues retournent à leur
				// position initiale
				GrilleDeJeu[ligTortue][colTortue] = "";
				GrilleDeJeu[newLigTortue][newColTortue] = "";

				RetourTortuePositionInitiale(GetTortue(JoueurCourant));
				RetourTortuePositionInitiale(nouvelleCase);// si tortue dans la nouvelle position
			} else {
				// avance dans une case vide
				GrilleDeJeu[ligTortue][colTortue] = "";
				GrilleDeJeu[newLigTortue][newColTortue] = tortue;
				SetPositionTortue(newLigTortue, newColTortue, 2);
			}

		}
	}

	private void ExecuterProcedureGauche() {
		// tourne dans le sens d'une montre
		// haut:0, gauche:1, bas:2, droite:3
		int orientation = ListJoueurs.get(JoueurCourant).GetOrientationTortue();
		orientation = (orientation + 1) % 4;
		ListJoueurs.get(JoueurCourant).SetOrientationTortue(orientation);
	}

	private void ExecuterProcedureDroite() {
		// tourne dans le sens contraire d'une montre
		// haut:0, gauche:1, bas:2, droite:3
		int orientation = ListJoueurs.get(JoueurCourant).GetOrientationTortue();
		orientation = (orientation - 1);
		if (orientation < 0)
			orientation += 4;
		ListJoueurs.get(JoueurCourant).SetOrientationTortue(orientation);
	}

	private void PasserAuJoueurSuivant() {
		//ListJoueurs.get(JoueurCourant).SetActionDuTour(0);
		// passer au joueur suivant si mode non test
		if (!ModeTest) {
			for (int i = 0; i < 4; i++) {
				JoueurCourant = (JoueurCourant + 1) % NbJoueur;
				if (ListJoueurs.get(JoueurCourant).EnJeu == true) {
					ListJoueurs.get(JoueurCourant).SetActionDuTour(0); // le joueur suivant peut jouer
					return;
				}
			}
		}
		else ListJoueurs.get(JoueurCourant).SetActionDuTour(0);
	}

	private boolean ExecuterProcedureLaser(int lig, int col) {
		if (GrilleDeJeu[lig][col].startsWith("I")) {
			// mur de glace / le mur est dissout
			GrilleDeJeu[lig][col] = "";
			return true;
		}
		if (GrilleDeJeu[lig][col].startsWith("W")) {
			// mur de pierre / rien ne se passe
			return true;
		}
		if (GrilleDeJeu[lig][col].startsWith("R")) {
			// ruby / le laser est refléchi, la tortue rebrousse chemin
			// GrilleDeJeu[ligTortue][colTortue]="";
			if (NbJoueur == 2) {
				// demie-tour
				FaireDemiTourTortue(JoueurCourant);
			} else {
				// position initiale
				RetourTortuePositionInitiale(GetTortue(JoueurCourant));
			}

			return true;
		}
		if (GrilleDeJeu[lig][col].startsWith("T")) {
			// tortue/ le laser a touché une tortue
			String nouvelleCase = GrilleDeJeu[lig][col];
			int numJoueurTouche = 0; // de la tortue touchée
			if (NbJoueur == 2) {
				// demie-tour
				FaireDemiTourTortue(numJoueurTouche);
			} else {
				// position initiale
				RetourTortuePositionInitiale(nouvelleCase);
			}
			return true;
		}
		return false;
	}

	public String ExecuterProgramme()// execution d'un programme
	{
		if (ListJoueurs.get(JoueurCourant).GetActionDuTour() != 0) {
			return "Impossible";
		}
		ArrayList<String> programme = GetProgramme();
		// si carte bug
		if (programme.size() > 0 && programme.get(0).startsWith("E")) {
			InverserLeProgramme();
			programme = GetProgramme();
		}

		int nbInstructionExecute = 0;
		int orientationTortue;
		int ligTortue;
		int colTortue;

		// excécution du programme
		for (int i = 0; i < programme.size(); i++) {
			String instruction = programme.get(i);
			// System.out.println("instruction:"+instruction);

			orientationTortue = GetOrientationTortue(JoueurCourant);
			ligTortue = GetLigneTortue(JoueurCourant);
			colTortue = GetColonneTortue(JoueurCourant);

			// si avancer
			if (instruction.startsWith("B")) {
				nbInstructionExecute++;
				ExecuterProcedureAvancer();
				ligTortue = GetLigneTortue(JoueurCourant);
				colTortue = GetColonneTortue(JoueurCourant);
			}
			// a droite / -1
			if (instruction.startsWith("P")) {
				nbInstructionExecute++;
				ExecuterProcedureDroite();
				orientationTortue = GetOrientationTortue(JoueurCourant);
			}
			// a gauche / +1
			if (instruction.startsWith("Y")) {
				nbInstructionExecute++;
				ExecuterProcedureGauche();
				orientationTortue = GetOrientationTortue(JoueurCourant);
			}
			// laser
			if (instruction.startsWith("L")) {
				nbInstructionExecute++;
				ligTortue = GetLigneTortue(JoueurCourant);
				colTortue = GetColonneTortue(JoueurCourant);
				boolean obstacleTrouve = false;
				// parcours selon orientation de la tortue
				obstacleTrouve = false;
				if (orientationTortue == BAS) {
					for (int lig = ligTortue + 1; lig < 8 && !obstacleTrouve; lig++) {
						int col = colTortue;
						obstacleTrouve = ExecuterProcedureLaser(lig, col);
					}
				} else if (orientationTortue == HAUT) {
					for (int lig = ligTortue - 1; lig > 0 && !obstacleTrouve; lig--) {
						int col = colTortue;
						obstacleTrouve = ExecuterProcedureLaser(lig, col);
					}
				} else if (orientationTortue == GAUCHE) {
					for (int col = colTortue - 1; col > 0 && !obstacleTrouve; col--) {
						int lig = ligTortue;
						obstacleTrouve = ExecuterProcedureLaser(lig, col);
					}
				} else if (orientationTortue == DROITE) {
					for (int col = colTortue + 1; col < 8 && !obstacleTrouve; col++) {
						int lig = ligTortue;
						obstacleTrouve = ExecuterProcedureLaser(lig, col);
					}
				}
			}
		}

		if (nbInstructionExecute < 1)
			return "Erreur";// erreur!

		// voir NbCarteJouees pour melanger de nouveau, si >=37 on atteint la defausse!
		ListJoueurs.get(JoueurCourant).NbCarteJouees += nbInstructionExecute;
		if (ListJoueurs.get(JoueurCourant).NbCarteJouees >= 37) {
			ListJoueurs.get(JoueurCourant).MelangerInstructions();
			ListJoueurs.get(JoueurCourant).NbCarteJouees = 0;
		}

		// fin de la manche
		if (NbJoueurEnJeu == 1 && MancheCourante < NbManche) {
			// fin de la manche
			MancheCourante++;
			// initialiser tous les joueurs
			for (int i = 0; i < ListJoueurs.size(); i++) {
				ListJoueurs.get(i).InitialiserJoueur();
			}
			initialiserGrilles();
			PasserAuJoueurSuivant();
			return "FinManche";
		}

		// fin de la partie
		if (NbJoueurEnJeu == 1 && MancheCourante == NbManche) {
			// fin de la partie
			PasserAuJoueurSuivant();
			return "FinPartie";
		}

		// vider le programme
		AnnulerLeProgramme();// y compris la carte bug

		return "ViderPRog";// continuer la partie
	}
}
