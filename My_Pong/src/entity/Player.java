package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		solidArea.height = 60;
		solidArea.width = 10;
		speed = 4;
	}
	
	public void update() {
		switch (Direction) {
		case "up":
			worldY += (collisionUp == false) ? -speed : 0;
			if (worldY - speed <= 0) {
				collisionUp = true;
				Direction = "stay";
			}
			
			else
				collisionUp = false;
			break;
		case "down":
			worldY += (collisionDown == false) ? speed : 0;
			if (worldY + speed >= gp.screenHeight - solidArea.height) {
				collisionDown = true;
				Direction = "stay";
			}
			else
				collisionDown = false;
			break;
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.white);
		g2.fillRect(worldX, worldY, solidArea.width, solidArea.height);
		g2.drawRect(worldX, worldY, solidArea.width, solidArea.height);
	}

}
