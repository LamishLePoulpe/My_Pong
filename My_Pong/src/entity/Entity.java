package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class Entity {

	public int worldX, worldY;
	public int speed;
	public Rectangle solidArea = new Rectangle();
	public boolean collisionUp = false;
	public boolean collisionDown = false;

	public String Direction = "stay";
	GamePanel gp;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}
}
