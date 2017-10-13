package gameEngine.character;

public interface Character {

	public int lightAttack();
	public int heavyAttack();

	public void walkLeft();
	public void walkRight();
	public void walkUp();
	public void walkDown();

	public void runLeft();
	public void runRight();
	public void runUp();
	public void runDown();


}
