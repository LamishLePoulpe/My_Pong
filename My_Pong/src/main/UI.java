package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class UI implements ActionListener {
	
	GamePanel gp;
	Graphics2D g2;
	
	Timer alphaTimer = new Timer(30, this);
	float alphaValue = 1f;

	Font bring;
	Font arial_40;
	Font arial_20;
	
	private String text;
	public String pressSpace = "Press Space to start !";
	
	public int select = -1;
	public int leftScore = 0;
	public int rightScore = 0;
	
	public boolean end = false;
	
	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			InputStream is = getClass().getResourceAsStream("/font/Bring.ttf");
			bring = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_20 = new Font("Arial", Font.PLAIN, 20);
	}

	public int getXForCenteredText(String text) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;

		return x;
	}
	
	public void drawStartScreen() {

		BufferedImage bkgd = null;
		// Brand Presentation
		if (gp.second >= 1 && gp.second < 7) {
			alphaTimer.start();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
			try {
				bkgd = ImageIO.read(getClass().getResourceAsStream("/Menu/brand.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g2.drawImage(bkgd, 0, 0, null);
		}
		// Pass to Title
		if (gp.second == 7) {
			gp.gameState = gp.titleState;
			select = 0;
		}
	}
	
	public void drawTitleScreen() {

		// Background
		int x, y = 0;

		// Title Name
		text = "Pong";
		g2.setFont(bring);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 96F));
		g2.setColor(Color.white);
		g2.drawString(text, getXForCenteredText(text), gp.screenHeight / 5);

		// Menu
		g2.setFont(arial_40);

		text = "NEW GAME";
		x = getXForCenteredText(text);
		y = gp.screenHeight / 2;
		g2.drawString(text, x, y);
		if (select == 0) {
			g2.setColor(Color.white);
			g2.drawString(text, x, y - 2);
			g2.setColor(Color.white);
		}

		text = "QUIT";
		x = getXForCenteredText(text);
		y += 75; 
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (select == 1) {
			g2.setColor(Color.white);
			g2.drawString(text, x, y - 2);
			g2.setColor(Color.white);
		}
	}

	public void drawScore() {		
		
		int x, y;
		
		text = leftScore + "" + "    SCORE    " + rightScore + "";
		x = getXForCenteredText(text);
		y = 50;
		g2.drawString(text, x, y);
		
	}

	public void setScore(String player) {
		if (player == "left") {
			leftScore++;
		}
		else
			rightScore++;
	}
	
	public void endGame() {
		end = true;
		g2.setFont(arial_40);
		if (leftScore == 5) {
			text = "Left player win";
			int x = getXForCenteredText(text);
			int y = gp.screenHeight / 2 - 50;
			g2.drawString(text, x, y);
		} else {
			text = "Right player win";
			int x = getXForCenteredText(text);
			int y = gp.screenHeight / 2 - 50;
			g2.drawString(text, x, y);
		}
		g2.setFont(arial_20);
		text = "Press Space to back Menu";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2 - 20;
		g2.drawString(text, x, y);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		alphaValue -= 0.007;

		if (alphaValue < 0) {
			alphaValue = 0;
			alphaTimer.stop();
		}

	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.startState)
			drawStartScreen();
		if (gp.gameState == gp.titleState)
			drawTitleScreen();
		if (gp.gameState == gp.playState) {
			drawScore();
			g2.setFont(arial_20);
			int x = getXForCenteredText(pressSpace);
			int y = gp.screenHeight / 2 - 50;
			if (pressSpace != null && leftScore < 5 && rightScore < 5)
				g2.drawString(pressSpace, x, y);
			if (leftScore == 5 || rightScore == 5) {
				endGame();
			}
		}
	}
}
