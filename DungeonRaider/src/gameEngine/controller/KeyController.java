package gameEngine.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import gameEngine.character.Player;
import gameEngine.engine.Engine;

public class KeyController extends Observable
		implements KeyListener, FocusListener {

	/**
	 * Control Booleans for player and other key events
	 */

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean hurtPlayer;
	private boolean pickUp;
	private boolean run;
	private boolean useItem;
	private boolean attak;

	private Player player;
	private Engine engine;

	public KeyController(Player player, Engine engine) {
		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;
		this.hurtPlayer = false;
		this.player = player;
		this.pickUp = false;
		this.engine = engine;
		this.run = false;
		this.useItem = false;
		this.attak = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			// lets the player move up by setting the up field to true
			this.up = true;
		case KeyEvent.VK_W:
			/// lets the player move up by setting the up field to true
			this.up = true;
			break;
		case KeyEvent.VK_LEFT:
			// lets the player move left by setting the left field to true
			this.left = true;
			break;
		case KeyEvent.VK_A:
			// lets the player move left by setting the left field to true
			this.left = true;
			break;
		case KeyEvent.VK_S:
			// lets the player move by down setting the down field to true
			this.down = true;
			break;
		case KeyEvent.VK_DOWN:
			// lets the player move by down setting the down field to true
			this.down = true;
			break;
		case KeyEvent.VK_D:
			// lets the player move by right setting the right field to true
			this.right = true;
			break;
		case KeyEvent.VK_RIGHT:
			// lets the player move by right setting the right field to true
			this.right = true;
			break;
		case KeyEvent.VK_F:
			// lets the player pick up item by setting the pickUp field to true
			this.pickUp = true;
			break;
		case KeyEvent.VK_SHIFT:
			// lets the player run by setting the run field to true
			this.run = true;
			break;
		case KeyEvent.VK_G:
			// lets the player use item by setting the useItem field to true
			this.useItem = true;
			break;
		case KeyEvent.VK_SPACE:
			// lets the player attack by setting the attak field to true
			this.attak = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_W:
			// checks for the pause screen. the w key will work differently
			// depending on what screen it is in. w will move the cursor up
			if (engine.isPaused()) {
				engine.getPauseMenu().setUp(true);
			}
			// performs similar function as the pause screen
			if (player.isDead()) {
				engine.getYouDied().setUp(true);
			}
			// stops the up movement by setting up to false. update will check
			// for this. engine stops player up movement when it sees this
			// boolean
			this.up = false;
			break;

		case KeyEvent.VK_UP:
			// similar to above. Checks for the pause screen first
			if (engine.isPaused()) {
				engine.getPauseMenu().setUp(true);
			}
			if (player.isDead()) {
				engine.getYouDied().setUp(true);
			}
			// stops the up movement by setting the up boolean to false. update
			// will check for this and stop the movement
			this.up = false;

			break;
		case KeyEvent.VK_A:
			// stops the left movement by setting the left boolean to false.
			// update will stop the movement when it checks the boolean
			this.left = false;
			break;
		case KeyEvent.VK_LEFT:
			// stops the left movement by setting the left boolean to false.
			this.left = false;
			break;
		case KeyEvent.VK_S:
			// the s key has slightly different functionality in the pause
			// screen
			if (engine.isPaused()) {
				engine.getPauseMenu().setDown(true);
			}
			if (player.isDead()) {
				engine.getYouDied().setDown(true);
			}
			// stops the down movement by setting the down boolean to false
			this.down = false;
			break;

		case KeyEvent.VK_DOWN:
			// similar to above
			if (engine.isPaused()) {
				engine.getPauseMenu().setDown(true);
			}
			if (player.isDead()) {
				engine.getYouDied().setDown(true);
			}
			// sets the down boolean to false to stop the down movement
			this.down = false;
			break;

		case KeyEvent.VK_D:
			// stops the right movement by setting right to false.
			this.right = false;
			break;

		case KeyEvent.VK_RIGHT:
			// stops the right movement by setting right to false.
			this.right = false;
			break;

		case KeyEvent.VK_F:
			// stops the pick up function by setting the pick up to false.
			this.pickUp = false;
			break;

		case KeyEvent.VK_SPACE:
			// stops the attack by setting attak to false
			this.attak = false;
			break;

		case KeyEvent.VK_SHIFT:
			// player no longer runs when they release the shift key
			this.run = false;
			break;

		case KeyEvent.VK_G:
			// the player cant use items/potions once the player release the g
			// key. player needs to press g again to use item
			this.useItem = false;
			break;

		case KeyEvent.VK_ESCAPE:
			// if the player is not dead, esc will show different things
			// depending on what screen the player is in
			if (!engine.getPlayer().isDead()) {
				// if the player is in the pause menu screen, esc will close it
				if (this.engine.isPaused()) {
					this.engine.getPauseMenu().setPaused(false);
					engine.getSoundLibrary().playClip("cursorReady", -10f);
					break;
				}
				// opens the menu if its not in it originally
				else {
					this.engine.getPauseMenu().setPaused(true);
					engine.getSoundLibrary().playClip("cursorReady", -10f);
					this.engine.getPauseMenu().setIndex(0);
					break;
				}
			}
			break;

		case KeyEvent.VK_ENTER:
			// lets the pause menu know that an option has been entered.
			if (engine.isPaused()) {
				engine.getPauseMenu().setEnter(true);
			}
			//lets the dead screen know that an option has been entered
			if (player.isDead()) {
				engine.getYouDied().setEnter(true);
			}
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isPickUp() {
		return pickUp;
	}

	public boolean isAttak() {
		return attak;
	}

	public boolean isRun() {
		return run;
	}

	public void setPickUp(boolean pickUp) {
		this.pickUp = pickUp;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public boolean isUseItem() {
		return useItem;
	}

	public void setUseItem(boolean useItem) {
		this.useItem = useItem;
	}

	/**
	 * @return the hurtPlayer
	 */
	public boolean isHurtPlayer() {
		return hurtPlayer;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

}
