import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Board extends JFrame {
	JLayeredPane layeredPane;
	JPanel Board;
	static int[][] Cell = new int[8][8];
	static int number_players = 2;   
	
	
	//0 représente une case vide
	// 1 représente un joyau
	// 2 représente une tortue
	// 3 représente un mur de pierre
	// 4 représente un mur de glace
		
		
	//quand il y a 2 ou 3 joueurs, la dernière colonne est constituée de murs de pierre
	
	public static void setWall() {
		for (int i = 0; i < 8; i++) {
			Cell[i][7] = 3;
		}
	}
	
	public static void emptyBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; i < 8; j++) {
				Cell[i][j]= 0 ;
			}
		}
	}
	
	public static int[][] initialisation() {
		switch (number_players) {
		case 2: 
			emptyBoard();
			setWall();
			Cell[0][1] = 2;
			Cell[0][5] = 2;
			Cell[7][3] = 1;
			break;
		case 3:
			emptyBoard();
			setWall();
			Cell[0][0] = 2;
			Cell[0][3] = 2;
			Cell[0][6] = 2;
			Cell[7][0] = 1;
			Cell[7][3] = 1;
			Cell[7][6] = 1;
			break;
		case 4:
			emptyBoard();
			Cell[0][0] = 2;
			Cell[0][2] = 2;
			Cell[0][5] = 2;
			Cell[0][7] = 2;
			Cell[7][1] = 1;
			Cell[7][6] = 1;
		}
		return Cell;
	}
	
	
	
	//constructeur
	public Board() {
		
	Dimension boardSize = new Dimension(600, 600);
 
  //  Use a Layered Pane for this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		 
		//Add a chess board to the Layered Pane 
		 
		Board = new JPanel();
		layeredPane.add(Board, JLayeredPane.DEFAULT_LAYER);
		Board.setLayout( new GridLayout(8, 8) );
		Board.setPreferredSize( boardSize );
		Board.setBounds(0, 0, boardSize.width, boardSize.height);
		 
		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel( new BorderLayout() );
			Board.add( square );
			
			
			int row = (i / 8) % 2;
			  if (row == 0)
			  square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
			  else
			  square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
			  
		}
		
		
	}
	public void paintComponent(Graphics g) {
	    try {
	      Image img = ImageIO.read(new File("./images/background.jpeg"));
	      g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }                
	}
	
	public static void afficherPlateauConsole() {
    	System.out.println("    0 1 2 3 4 5 6 7 \n  +-----------------+");
    	String total = "";
    	for (int i = 0; i <= 7; i++) {
    		String ligne = i + " | ";
        	for (int j = 0; j <= 7; j++) {
        		ligne = ligne + Cell[i][j] + " ";
        	}
        	total = total + ligne + "| \n";
        }
    	System.out.println(total + "  +-----------------+");
    }

	
	public static void main(String[] args) {
		/*JFrame frame = new Board();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);*/
		emptyBoard();
		setWall();
		afficherPlateauConsole();
		
	}
	
}
