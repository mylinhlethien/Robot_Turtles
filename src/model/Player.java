package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Player implements Move {
	public static Scanner scanner = new Scanner(System.in);
	
	String color;
	String direction;
	String name;
	private ArrayDeque<Card> deck = new ArrayDeque<>();
	private ArrayList<Card> handCards;
	private ArrayDeque<Card> program;
	private ArrayList<Blocks> blocks;
	public static int passageOrder;
}
