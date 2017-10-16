package gameEngine.character;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import gameEngine.controller.KeyController;
import gameEngine.controller.MouseController;
import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.item.Item;
import gameEngine.item.Weapon;
import gameEngine.map.Map;
import gameEngine.sprite.AnimatedSprite;
import gameEngine.sprite.Sprite;
import gameEngine.util.Box;
import gameEngine.util.Position;
import library5.StatModifier;


/**
 * Need to implement equiping of weapon then test if attacking works because right now null pointer exeception
 * when trying to find weapon range
 * @author Gabriel Tennent
 *
 */

public class Player implements Character, GameObject {

	private int hp;
	private int hpMax;
	private int gold;
	private int x;
	private int y;

	/**
	 * Item and bounding box variables
	 */
	private boolean primaryEquipped;
	private Weapon primaryWeapon;
	private Inventory inventory;
	private Sprite spriteImage;
	private Box playerBoundBox;
	private Box pickUpRadius;

	public static final int SPEED = 2;
	public static final int SPRINT = 7;
	private int zoom;
	private Queue<Integer> damageQueue;
	
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	//0 == right, 1 == left, 2 == up, 3 == down. This is based off the Player.png
	private int direction = 0;


	public Player(Position center, int stamina, Sprite playerSprite, int zoom, int hp, int hpMax) {
		this.damageQueue = new PriorityQueue<>();
		this.spriteImage = playerSprite;
		this.zoom = zoom;
		this.zoom = 3;
		this.x = center.getX() - (playerSprite.getWidth() / 2 * zoom);
		this.y = center.getY() - (playerSprite.getHeight() / 2 * zoom);
		this.hp = hp;
		this.hpMax = hpMax;
		this.playerBoundBox = new Box(x, y, playerSprite.getWidth()*zoom, playerSprite.getHeight()*zoom);
		//Pick up radius of the player starts half of the players width to the left of the player and
		//extends to twice the players width meaning the pick up radius is twice the size of the player
		this.pickUpRadius	= new Box(x-((playerSprite.getWidth()*zoom)), y-((playerSprite.getHeight()*zoom)),
				(playerSprite.getWidth()*zoom)*4, (playerSprite.getHeight()*zoom)*4);
		this.inventory = new Inventory(20);
		this.primaryEquipped = false;
		//this.primaryWeapon = new Weapon("start", 0, 0, 0, 10, 100, 10);
		this.sprite = playerSprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			this.animatedSprite = (AnimatedSprite) playerSprite;
		}
		updateDirection();
	}

	private void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimationRange( direction*8, direction * 8 + 7 );
		} 
	}
	@Override
	public int heavyAttack() {
		return primaryWeapon.getDamage();
	}

	@Override
	public void walkLeft() {
		this.x -= SPEED;
		this.playerBoundBox.setX(this.x-=SPEED);
	}

	@Override
	public void walkRight() {
		this.x += SPEED;
		this.playerBoundBox.setX(this.x+=SPEED);
	}

	@Override
	public void walkUp() {
		this.y -= SPEED;
		this.playerBoundBox.setY(this.y-=SPEED);
	}

	@Override
	public void walkDown() {
		this.y += SPEED;
		this.playerBoundBox.setY(this.y+=SPEED);
	}

	public void runLeft() {
		this.x -= SPRINT;
		this.playerBoundBox.setX(this.x-=SPRINT);
	}

	public void runRight() {
		this.x += SPRINT;
		this.playerBoundBox.setX(this.x += SPRINT);
	}

	public void runUp() {
		this.y -= SPRINT;
		this.playerBoundBox.setY(this.y -= SPRINT);
	}

	public void runDown() {
		this.y += SPRINT;
		this.playerBoundBox.setY(this.y += SPRINT);
	}

	public void interact() {

	}

	/**
	 * Renders the players current position
	 */
	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		//renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(), spriteImage.getWidth(), x, y, zoom, zoom);
		
		//introducing the animated sprite here. initially rendering a static sprite.
		if(animatedSprite != null)
			renderer.renderSprite(animatedSprite, playerBoundBox.getX(), playerBoundBox.getY(), xZoom, yZoom);
		else if(sprite != null)
			renderer.renderSprite(sprite, playerBoundBox.getX(), playerBoundBox.getY(), xZoom, yZoom);
		else
			renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(), spriteImage.getWidth(), x, y, zoom, zoom);
	}

	/**
	 * Updates the player dependant on the key and mouse controllers in the engine and what booleans have been activated
	 * via the user pushing keys / the mouse.
	 */
	@Override
	public void update(Engine engine) {
		KeyController keyBinds = engine.getKeyBinds();
		Map currentMap = engine.getCurrentMap();
		MouseController mouseActions = engine.getMouseListener();

		int width = spriteImage.getWidth()*zoom;
		int height = spriteImage.getHeight()*zoom;

		//for the player animated sprites
		boolean didMove = false;
		int newDirection = direction ;
		
		/**
		 * Player walking connection with key controller:
		 * Also checks for player connection with walls
		 */
		if(!keyBinds.isRun()) {
			if (keyBinds.isUp()) {
				if(checkBoundry(currentMap, x + width, y - SPEED + height/2))
					if(checkBoundry(currentMap, x, y - SPEED + height/2)) {
						newDirection = 2;
						didMove = true;
						walkUp();
					}
			}
			if (keyBinds.isDown()) {
				if(checkBoundry(currentMap, x + width, y + height + SPEED))
					if(checkBoundry(currentMap, x, y + height + SPEED)) {
						newDirection = 3;
						didMove = true;
						walkDown();
					}
			}
			if (keyBinds.isLeft()) {
				if(checkBoundry(currentMap, x - SPEED, y + height/2))
					if(checkBoundry(currentMap, x - SPEED, y + height)) {
						newDirection = 1;
						didMove = true;
						walkLeft();
					}
			}
			if (keyBinds.isRight()) {
				if(checkBoundry(currentMap, x + width + SPEED, y + height/2))
					if(checkBoundry(currentMap, x + width + SPEED, y + height)) {
						newDirection = 0;
						didMove = true;
						walkRight();
					}
			}
		}

		/**
		 * Player running connection with key controller:
		 * Also checks for player connection with walls
		 */
		if(keyBinds.isRun()) {
			if (keyBinds.isUp()) {
				if(checkBoundry(currentMap, x + width, y - SPRINT + height/2))
					if(checkBoundry(currentMap, x, y - SPRINT + height/2)) {
						newDirection = 2;
						didMove = true;
						runUp();
					}
			}
			if (keyBinds.isDown()) {
				if(checkBoundry(currentMap, x + width, y + height + SPRINT))
					if(checkBoundry(currentMap, x, y + height + SPRINT)) {
						newDirection = 3;
						didMove = true;
						runDown();
					}
			}
			if (keyBinds.isLeft()) {
				if(checkBoundry(currentMap, x - SPRINT, y + height/2))
					if(checkBoundry(currentMap, x - SPRINT, y + height)) {
						newDirection = 1;
						didMove = true;
						runLeft();
					}
			}
			if (keyBinds.isRight()) {
				if(checkBoundry(currentMap, x + width + SPRINT, y + height/2))
					if(checkBoundry(currentMap, x + width + SPRINT, y + height)) {
						newDirection = 0;
						didMove = true;
						runRight();
					}
			}
		}

		//Checking if player is attempting to pick up and whether there is anything to pick up
		if(keyBinds.isPickUp()) {
			this.pickUpRadius	= new Box(x, y,
					(spriteImage.getWidth()*zoom), (spriteImage.getHeight()*zoom));
			//need to check if any of the items locations
			List<Item> itemsOnMap = engine.getCurrentMap().getItems();
			for(Item item : itemsOnMap) {
				Position itemPos = item.getPosition();
				if(pickUpRadius.contains(itemPos.getX(), itemPos.getY())){
					this.inventory.add(item);
					item.setPickedUp(true);
					itemsOnMap.remove(item);
					break;
				}
			}
		}

		/**
		 * Returns left clicks and their co-ordinates to tell where for the character to attack
		 */
		while(!mouseActions.getAttackPositions().isEmpty()) {
			//Gets the attack position from the mouse controller
			Position attackPos = mouseActions.getAttackPos();
			//Prevents player from being able to run and attack at the same time
			keyBinds.setRun(false);
			int mx = attackPos.getX();
			int my = attackPos.getY();
			if(primaryEquipped)	attack(mx, my, engine);
		}

		/**
		 * Updates the players hpbar if they have taken damage
		 */
		if(!damageQueue.isEmpty()) {
			int damage = damageQueue.poll();
			this.hp += damage;
		}
		
		/**
		 * Attempts to use a players item to heal the player
		 */
		if(keyBinds.isUseItem()) {
			if(!this.inventory.getInventory().isEmpty()) {
			this.hp += 10;
			this.inventory.removeItem();
			keyBinds.setUseItem(false);
			}
		}

		/**
		 * Updates camera
		 */
		//only update the direction if the player moves in a different direction
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
		
		if(!didMove) {
			//makes sure that the sprite doesnt stop mid movement
			animatedSprite.reset();
		}
		
		this.updateCamera(engine.getRenderer().getCamera());
		
		//update the counter 
		if(didMove) {
			animatedSprite.update(engine);
		}
	}

	/**
	 *
	 * @param mx
	 * @param my
	 * @param engine
	 */
	@Override
	public void attack(int mx, int my, Engine engine) {
		int width = spriteImage.getWidth() * zoom;
		int height = spriteImage.getHeight() * zoom;
		Position center = engine.getRenderer().getCamera().getCenter();
		List<Monster> monsters = engine.getCurrentMap().getRealMonster();
		//right attack calculated from the position of the camera
		if(mx > center.getX()) {
			//right side bounding box = to range of weapon and half player height
			Box rightPrimaryAttackRad = new Box(x + width, y, primaryWeapon.getRange(), height/2);
			//attackRight();
			for(Monster monster : monsters) {
				if(rightPrimaryAttackRad.contains(monster.getBoundingBox())) {
					monster.damage(heavyAttack());
					
					System.out.println(monster.getHealth());
					//checks if monster died from the attack
					checkForMonsterDeath(monster);
				}
			}
		}
		//left attack calculated from the position of the camera
		if(mx < center.getX()) {
			//left side bounding box = to range of weapon and half player height
			Box leftPrimaryAttackRad = new Box(x-primaryWeapon.getRange(), y, primaryWeapon.getRange(), height/2);
			//attackLeft();
			for(Monster monster : monsters) {
				if(leftPrimaryAttackRad.contains(monster.getBoundingBox())) {
					monster.damage(heavyAttack());
					System.out.println(monster.getHealth());
					//checks if monster died from the attack
					checkForMonsterDeath(monster);
					
				}
			}
		}
	}
	
	// check to see if the monster dies from player attack. Player gets gold if monster dies from player attack
	public void checkForMonsterDeath(Monster monster) {
		if(monster.getHealth()<=0) {
			this.gold = this.gold + 100;
		}	
	}
	
	/**
	 * Updates the camera's position to center the player
	 *
	 * @param camera
	 */
	private void updateCamera(Box camera) {
		camera.setX(x - (camera.getWidth() / 2) + (this.spriteImage.getWidth() * zoom / 2));
		camera.setY(y - (camera.getHeight() / 2) + (this.spriteImage.getHeight() * zoom / 2));
	}


	/**
	 * Getters and setters for the player class
	 */

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @return the hpMax
	 */
	public int getHpMax() {
		return hpMax;
	}
	
	public boolean isDead() {
		if(hp <= 0) return true;
		else return false;
	}

	/**
	 * Damages player with positive numbers, heals player with negative numbers
	 *
	 * @param i
	 */
	public void damage(int i) {
		this.damageQueue.offer(-i);
	}

	public Queue<Integer> getDamageQueue() {
		return damageQueue;
	}

	public int getGoldTotal() {
		return gold;
	}

	public void spendGold(int amountSpent) {
		gold = gold - amountSpent;
	}

	public void collectgold(int amountRecieved) {
		gold = gold + amountRecieved;;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Sprite getSpriteImage() {
		return spriteImage;
	}

	public int getSpeed() {
		return SPEED;
	}

	public int getZoom() {
		return zoom;
	}

	public Box getPlayerBoundBox() {
		return playerBoundBox;
	}

	public void setPlayerBoundBox(Box playerBoundBox) {
		this.playerBoundBox = playerBoundBox;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
