package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class displayMenu extends JFrame implements ActionListener{
	public GridBagConstraints contrainte = new GridBagConstraints();
	private static final long serialVersionUID = 1L;
	private JButton play_game = new JButton("Nouvelle partie");
	private JButton quit_game = new JButton("Quitter le jeu");
	
	
	public static void main(String[] args) {
		new displayMenu();
	}
	
	/**
	 * Construit un objet API correspondant à un GridBagLayout qui est l'accueil de l'application
	 */
	public displayMenu() {
		super("Accueil du jeu Robot Turtle");
		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		
		// Taille
		contrainte.fill = GridBagConstraints.BOTH; 
		// Padding externe (bottom, left, right, top)
		contrainte.insets = new Insets(10,10,10,10);
		// Padding interne
		contrainte.ipady = contrainte.anchor = GridBagConstraints.CENTER;
		
		play_game.addActionListener(this);
		play_game.setActionCommand("lancer");
		
		quit_game.addActionListener(this);
		quit_game.setActionCommand("quitter");
		
		// Cellule où commence l'affichage
		contrainte.gridx = 0; contrainte.gridy = 0;
		// Nombre de cellules par colonne / ligne
		contrainte.gridheight = 2; contrainte.gridwidth = 2;
		pan.add(new JLabel("Bienvenue dans le jeu Robot Turtles!",SwingConstants.CENTER),contrainte);
		
		contrainte.gridx = 0; contrainte.gridy = 2;
		contrainte.gridheight = 1; contrainte.gridwidth = 1;
		pan.add(play_game,contrainte);
		
		contrainte.gridx = 1; contrainte.gridy = 2;
		contrainte.gridheight = 1; contrainte.gridwidth = 1;
		pan.add(quit_game,contrainte);
		
		this.add(pan);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800,400);
	}
		
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand().equals("lancer")) {
			new Window();
		}
		if(ev.getActionCommand().equals("quitter")){
			System.exit(0);
		}
	}
}

