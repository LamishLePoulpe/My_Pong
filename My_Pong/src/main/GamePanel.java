package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Ball;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public final int screenWidth = 768;
	public final int screenHeight = 512;
	public final int FPS = 60;

	Thread gameThread;
	KeyHandler keyH = new KeyHandler(this);

	public UI ui = new UI(this);
	public Player leftPlayer = new Player(this, keyH);
	public Player rightPlayer = new Player(this, keyH);
	public Ball ball = new Ball(this, keyH);

	public int second = 0;

	public int gameState;
	public final int startState = 0;
	public final int titleState = 1;
	public final int playState = 2;
	public final int pauseState = 4;
	public final int settingsState = 5;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame(int state) {
		gameState = state;
		leftPlayer.worldX = screenWidth - screenWidth + 10;
		leftPlayer.worldY = screenHeight / 2 - 25;
		rightPlayer.worldX = screenWidth - 20;
		rightPlayer.worldY = screenHeight / 2 - 25;
		ball.worldX = screenWidth / 2  - (ball.solidArea.width / 2);
		ball.worldY = screenHeight / 2;
		ui.leftScore = 0;
		ui.rightScore = 0;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS; // 0.016666 seconds
		double delta = 0;
		long lastT = System.nanoTime();
		long currentT;
		long timer = 0;

		while (gameThread != null) { // Game loop

			currentT = System.nanoTime();
			delta += (currentT - lastT) / drawInterval;
			timer += (currentT - lastT);
			lastT = currentT;

			if (delta >= 1) {
				// UPDATE : update object information
				update();

				// DRAW : draw the updated object information
				repaint();
				delta = 0;
			}

			if (timer >= 1000000000) {
				timer = 0;
				second++;
			}
		}
	}

	public void getPlayerDirection() {
		if (keyH.leftPlayerUpPressed == true)
			leftPlayer.Direction = "up";
		if (keyH.leftPlayerDownPressed == true)
			leftPlayer.Direction = "down";
		if (keyH.leftPlayerDownPressed == false && keyH.leftPlayerUpPressed == false)
			leftPlayer.Direction = "stay";
		if (keyH.rightPlayerUpPressed == true)
			rightPlayer.Direction = "up";
		if (keyH.rightPlayerDownPressed == true)
			rightPlayer.Direction = "down";
		if (keyH.rightPlayerDownPressed == false && keyH.rightPlayerUpPressed == false)
			rightPlayer.Direction = "stay";
	}

	public void update() {
		if (gameState == playState) {
			getPlayerDirection();
			leftPlayer.update();
			rightPlayer.update();
			ball.update();
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		ui.draw(g2);
		if (gameState == playState) {
			leftPlayer.draw(g2);
			rightPlayer.draw(g2);
			ball.draw(g2);
		}

		g2.dispose();
	}
}
