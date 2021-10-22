
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

	private int speed_factor;
	private Timer timer;
	private JPanel[][] tiles = new JPanel[25][25];
	private int snake_length = 3;
	private int head_pos;
	private ArrayList<Point> snake = new ArrayList<Point>();
	private int x_cord;
	private int y_cord;
	private int score;
	Point top_of_snake;
	Point score_point;
	boolean lose;
	boolean boundary;
	Subject subject;
	private int direction; 
	
	private ScorePanel scorePanel;
	


	public GamePanel(ScorePanel panel) {

		this.setLayout(new GridLayout(25,25));
		this.setBackground(Color.black);

		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();

		speed_factor = 100;

		timer = new Timer(speed_factor,this);
		timer.start();

		addTiles();
		startingPoint();
		initializeSnake();

		// test fruit

		

		score_point = new Point(13,15);
		
		tiles[score_point.x][score_point.y].setBackground(Color.red);

		lose = false; //start by not losing the game
		subject.setState("Game Start");
		boundary = false; //snake has not hit a boundary

		//score point

		
		//get the score panel
		scorePanel = panel;
		
		// intiial score.
		setScore(0);

		
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 800);

		if(loseCheck()) {
			System.out.println("You have lost. Game over");
			subject.setState("You have lost. Game over");
			JOptionPane.showMessageDialog(this, "Game Over!");
			return;
		}


		//snake movement (in a method later?)
		//left  
		if(direction == 1 ) {			
			moveLeft();
		}

		if(direction == 2 ) {
			moveUp();
		}

		if(direction == 3 ) {
			moveDown();
		}

		if(direction == 4 ) {			
			moveRight();
		}


	}


	private boolean loseCheck() {


		if(boundary) {
			return true;
		}

		Point hold = snake.get(snake.size()-1);

		for(int i=0; i<snake.size()-1; i++){
			if(snake.get(snake.size()-1).equals(snake.get(i))){
				System.out.println("LOOOOOOOOOOOOOSE");
				subject.setState("You have lost. Game over");
				return true;
			}
		}

		return false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
		addActionListner(scorePanel);

		// Speeding up the snake
		if(System.currentTimeMillis()%1000==0 && !(speed_factor < 30)) {
			timer.setDelay(speed_factor-=5);
		}

		if(loseCheck()){
			timer.stop();
			return;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Keys for moving snake

		if(e.getKeyCode() == 37) {// left
			if (direction!=4){
				direction  = 1;
			}
		}

		if(e.getKeyCode() == 38) { //up
			if(direction!=2){
				direction  = 3;		
			}
		}

		if(e.getKeyCode() == 39) { // right
			if(direction!=1){
				direction  = 4;
			}
		}

		if(e.getKeyCode() == 40) { //down
			if(direction!=3){
				direction  = 2; 
			}
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}


	public void initializeSnake(){
		//initial snake
		for(int i=0;i<snake_length;i++){
			tiles[x_cord][y_cord].setBackground(Color.green);


			Point snake_point  = new Point();	
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);

			x_cord+=1;			
		}
		x_cord--;
	}

	public void startingPoint(){
		x_cord = 5;
		y_cord = 16;	
		direction = 4; 

	}


	private void addTiles() {
		for (y_cord=0; y_cord<25; y_cord++){
			for(x_cord=0; x_cord<25; x_cord++){
				tiles[x_cord][y_cord] = new JPanel();
				//tiles[x_cord][y_cord].setBorder(BorderFactory.createLineBorder(Color.pink));
				
				tiles[x_cord][y_cord].setBorder(BorderFactory.createLineBorder(Color.black));
				
				add(tiles[x_cord][y_cord]);
				tiles[x_cord][y_cord].setBackground(Color.black);	
			}
		}
	}

	private void moveLeft(){
		head_pos = x_cord - 1; // figure out why this works exactly to avoid errors
		if(head_pos >= 0  ){

			x_cord = head_pos;				
			tiles[x_cord][y_cord].setBackground(Color.green);

			Point snake_point  = new Point();
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);


			if(!(tiles[score_point.x][score_point.y].equals(tiles[x_cord][y_cord]))) {
				tiles[snake.get(0).x][snake.get(0).y].setBackground(Color.black);
				snake.remove(0);
			}

			else {

				System.out.println("We went here!");
				snake_length++; //does it matter?
				setScore(getScore() + 10);
				point_generator();
				//do nothing else I guess
			}

			head_pos --;
		}

		else {
			boundary = true;
			System.out.println("You lost");
			subject.setState("You Lost");
			return;
		}
	}
	private void moveRight(){
		head_pos = x_cord + 1; // figure out why this works exactly to avoid errors
		if(head_pos <=24   ){

			x_cord = head_pos;				
			tiles[x_cord][y_cord].setBackground(Color.green);


			Point snake_point  = new Point();
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);

			if(!(tiles[score_point.x][score_point.y].equals(tiles[x_cord][y_cord]))) {
				tiles[snake.get(0).x][snake.get(0).y].setBackground(Color.black);
				snake.remove(0);
			}

			else {

				System.out.println("We went here!");
				snake_length++; //does it matter?
				setScore(getScore() + 10);
				point_generator();
				//do nothing else I guess
			}



			head_pos ++;
		}

		else {
			System.out.println("You lost");
			subject.setState("You lost");
			boundary = true;
			return;
		}
	}
	private void moveUp(){
		head_pos = y_cord+1;
		//conversion of current head position (in terms of x) now in terms of y)
		System.out.println(head_pos);

		if(head_pos < 25  ){

			y_cord = head_pos;				
			tiles[x_cord][y_cord].setBackground(Color.green);



			Point snake_point  = new Point();
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);

			if(!(tiles[score_point.x][score_point.y].equals(tiles[x_cord][y_cord]))) {
				tiles[snake.get(0).x][snake.get(0).y].setBackground(Color.black);
				snake.remove(0);
			}

			else {

				System.out.println("We went here!");
				snake_length++; //does it matter?
				setScore(getScore() + 10);
				point_generator();
				//do nothing else I guess
			}

			//tiles[x_cord][y_cord-snake_length].setBackground(Color.white);
			head_pos ++;
		}

		else {
			System.out.println("You lost");
			subject.setState("You lost");
			boundary = true;
			return;
		}


	}
	private void moveDown(){ // the down is actually up I think...
		head_pos = y_cord-1;

		if(head_pos >= 0  ){
			top_of_snake = snake.get(snake.size()-1);

			y_cord = head_pos;				
			tiles[x_cord][y_cord].setBackground(Color.green);

			Point snake_point  = new Point();
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);

			if(!(tiles[score_point.x][score_point.y].equals(tiles[x_cord][y_cord]))) {
				tiles[snake.get(0).x][snake.get(0).y].setBackground(Color.black);
				snake.remove(0);
			}

			else {

				System.out.println("We went here!");
				snake_length++; //does it matter?
				setScore(getScore() + 10);
				point_generator();
				//do nothing else I guess
			}

			head_pos --;



		}


		else {
			boundary = true;
			System.out.println("You lost");
			subject.setState("You lost");
			return;
		}
	}



	private void point_generator(){
		
		Random rand = new Random();
		int randomNum = rand.nextInt(24);
		score_point.x = randomNum;
		randomNum = rand.nextInt(24);
		score_point.y = randomNum;
		
		System.out.println("We would have a red at  X: "+ score_point.x + " and at Y : "+ score_point.y);
		tiles[score_point.x][score_point.y].setBackground(Color.red);
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addActionListner(ScorePanel scorePanel) {
		// TODO Auto-generated method stub

		scorePanel.setScore(getScore());
		System.out.println( score + " is the current score!!!!!!!!");
	}

}


