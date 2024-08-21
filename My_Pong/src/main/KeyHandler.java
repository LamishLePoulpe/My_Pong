package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	public boolean start, leftPlayerUpPressed, leftPlayerDownPressed, rightPlayerUpPressed, rightPlayerDownPressed;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		switch (code) {
		case KeyEvent.VK_Z:
			leftPlayerUpPressed = true;
			break;
		case KeyEvent.VK_S:
			leftPlayerDownPressed = true;
			break;
		case KeyEvent.VK_UP:
			if (gp.gameState == gp.titleState) {
				gp.ui.select = (gp.ui.select == 0) ? 1 : gp.ui.select - 1;
			}
			rightPlayerUpPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			if (gp.gameState == gp.titleState) {
				gp.ui.select = (gp.ui.select == 1) ? 0 : gp.ui.select + 1;
			}
			rightPlayerDownPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			if (gp.gameState == gp.startState)
				gp.gameState = gp.titleState;
			if (gp.gameState == gp.playState && gp.ui.end != true) {
				start = true;
				gp.ui.pressSpace = "";
			}
			if (gp.ui.end == true) {
				gp.ui.end = false;
				gp.setupGame(gp.titleState);
			}
			break;
		case KeyEvent.VK_ENTER:
			switch (gp.ui.select) {
			case 0:
				gp.gameState = gp.playState;
				break;
			case 1:
				System.exit(0);
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		switch (code) {
		case KeyEvent.VK_Z:
			leftPlayerUpPressed = false;
			break;
		case KeyEvent.VK_S:
			leftPlayerDownPressed = false;
			break;
		case KeyEvent.VK_UP:
			rightPlayerUpPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			rightPlayerDownPressed = false;
			break;
		}
	}
}
