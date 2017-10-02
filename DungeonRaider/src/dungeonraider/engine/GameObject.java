package dungeonraider.engine;

public interface GameObject {
	/**
	 * Called everytime possible.
	 * 
	 * @param renderer
	 * @param xZoom
	 * @param yZoom
	 */
	public void render(Renderer renderer, int xZoom, int yZoom);

	/**
	 * Called at 60 fps
	 * 
	 * @param engine
	 */
	public void update(Engine engine);
}
