package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Player implements Move {
	public static Scanner scanner = new Scanner(System.in);
	
	String color;
	public char direction;
	String name;
	private ArrayDeque<Card> deck = new ArrayDeque<>();
	private ArrayList<Card> handCards;
	private ArrayDeque<Card> program;
	private ArrayList<Blocks> blocks;
	public int passageOrder;
	
	public Player() {
		this.color = "blue";
		this.direction = 'S';
		this.name = "";
		this.deck = new ArrayDeque<Card>();
		this.handCards = new ArrayList<Card>();
		this.program = new ArrayDeque<Card>();
		this.blocks = new ArrayList<Blocks>();
		this.passageOrder = 1;
	}
	
	//getters and setters
	public String getName() {
		return name;
	}
	
	public ArrayDeque<Card> getDeck() {
		return deck;
	}
	
	public void setDeck(ArrayDeque<Card> deck) {
		this.deck = deck;
	}
	
	public ArrayList<Card> getHandCards() {
		return handCards;
	}
	
	public void setMain(ArrayList<Card> handCards) {
		this.handCards = handCards;
	}
	
	public ArrayDeque<Card> getProgram() {
		return program;
	}
	
	public void setProgram(ArrayDeque<Card> program) {
		this.program = program;
	}
	
	public ArrayList<Blocks> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Blocks> blocks) {
		this.blocks = blocks;
	}
}
