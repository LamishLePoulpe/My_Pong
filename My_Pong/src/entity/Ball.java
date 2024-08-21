package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import main.KeyHandler;

public class Ball extends Entity {

	private double moveX;
	private double moveY;
	private Rectangle ball = new Rectangle();
	KeyHandler keyH;

	public Ball(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;

		solidArea.height = 10;
		solidArea.width = 10;

		ball = solidArea;
		speed = 4;
		moveX = speed;
		moveY = speed;

	}

	public void update() {
		if (keyH.start == true)
			move_ball();
	}

	private void move_ball() {
		worldX += moveX;
		worldY += moveY;
		if (worldY <= 0)
			moveY = +speed;
		if (worldY >= gp.screenHeight - ball.height)
			moveY = -speed;
		if (worldX + ball.width >= gp.rightPlayer.worldX && worldY >= gp.rightPlayer.worldY
				&& worldY <= gp.rightPlayer.worldY + gp.rightPlayer.solidArea.height)
			moveX = -moveX;
		if (worldX <= gp.leftPlayer.worldX + gp.leftPlayer.solidArea.width && worldY >= gp.leftPlayer.worldY
				&& worldY <= gp.leftPlayer.worldY + gp.leftPlayer.solidArea.height)
			moveX = -moveX;
		if (worldX < 10) {
			worldY = gp.screenHeight / 2;
			worldX = gp.screenWidth / 2;
			moveX = speed;
			gp.ui.setScore("right");
			gp.ui.pressSpace = "Press Space to start !";
			keyH.start = false;
		}
		if (worldX > gp.screenWidth - ball.width - 10) {
			worldY = gp.screenHeight / 2;
			worldX = gp.screenWidth / 2;
			moveX = speed;
			gp.ui.setScore("left");
			gp.ui.pressSpace = "Press Space to start !";
			keyH.start = false;
		}
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.white);
		g2.fillRect(worldX, worldY, solidArea.width, solidArea.height);
		g2.drawRect(worldX, worldY, solidArea.width, solidArea.height);
	}

}
