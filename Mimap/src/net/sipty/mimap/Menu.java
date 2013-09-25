package net.sipty.mimap;

public class Menu {
	
	// declarations
	private static Boolean showMenu=false,
						   showOptions=false;
	//private final int menuLeftX=GameScreen.getMenuLeftX()+1, menuRightX=GameScreen.getMenuLeftX()+59, menuTopY=GameScreen.getMenuTopY()+45;
	private final static int menuX=50,	// menu coords 
							 menuY=700,
							 optionsX=GameScreen.getMenuRightX()+5,	// options coords
							 optionsY=menuY-40;
	
	// constructor
	public Menu() {
	}
	
	public static void draw() {
		// menu button
		GameScreen.game.font.draw(GameScreen.game.batch, "Menu", menuX, menuY);
		
		// menu drop-down
		if(showMenu) {
			GameScreen.game.font.draw(GameScreen.game.batch, "Restart", menuX, menuY-20);
			GameScreen.game.font.draw(GameScreen.game.batch, "Options", menuX, menuY-40);
			GameScreen.game.font.draw(GameScreen.game.batch, "Quit", menuX, menuY-60);
		}

		// options menu
		if(showOptions) {
			GameScreen.game.font.draw(GameScreen.game.batch, "Option 1", optionsX, optionsY);
			GameScreen.game.font.draw(GameScreen.game.batch, "Option 2", optionsX, optionsY-20);
			GameScreen.game.font.draw(GameScreen.game.batch, "Option 3", optionsX, optionsY-40);
		}
	}

	// setters & getters
	
	public static void setShowMenu(Boolean showMenu) {
		Menu.showMenu = showMenu;
	}

	public static Boolean getShowMenu() {
		return showMenu;
	}

	public static Boolean getShowOptions() {
		return showOptions;
	}

	public static void setShowOptions(Boolean showOptions) {
		Menu.showOptions = showOptions;
	}

	
}
