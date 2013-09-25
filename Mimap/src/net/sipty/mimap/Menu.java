package net.sipty.mimap;

public class Menu {
	
	// declarations
	private static Boolean showMenu=false,
						   showOptions=false,
						   showQuit=false,
						   showRestart=false;
	//private final int menuLeftX=GameScreen.getMenuLeftX()+1, menuRightX=GameScreen.getMenuLeftX()+59, menuTopY=GameScreen.getMenuTopY()+45;
	private final static int leftMenuX=50,	// menu coords 
							 rightMenuX=GameScreen.getMenuRightX()+5,
							 menuY=700,
							 restartY=menuY-20,	// restart coords	
							 optionsY=menuY-40,	// options coords
							 quitY=menuY-60;	// quit coords
	
	// constructor
	public Menu() {
	}
	
	public static void draw() {
		// menu button
		GameScreen.game.font.draw(GameScreen.game.batch, "Menu", leftMenuX, menuY);
		
		// menu drop-down
		if(showMenu) {
			GameScreen.game.font.draw(GameScreen.game.batch, "Restart", leftMenuX, restartY);
			GameScreen.game.font.draw(GameScreen.game.batch, "Options", leftMenuX, optionsY);
			GameScreen.game.font.draw(GameScreen.game.batch, "Quit", 	leftMenuX, quitY);
		}

		// restart
		if(showRestart) {
			GameScreen.game.font.draw(GameScreen.game.batch, "Are you sure?", rightMenuX, restartY);
			GameScreen.game.font.draw(GameScreen.game.batch, "No  /  Yes", rightMenuX, restartY-20);
		}
		// options menu
		if(showOptions) {
			GameScreen.game.font.draw(GameScreen.game.batch, "Option 1", rightMenuX, optionsY);
			GameScreen.game.font.draw(GameScreen.game.batch, "Option 2", rightMenuX, optionsY-20);
			GameScreen.game.font.draw(GameScreen.game.batch, "Option 3", rightMenuX, optionsY-40);
		}
		
		// quit confirmation
		if(showQuit) {
			GameScreen.game.font.draw(GameScreen.game.batch, "Are you sure?", rightMenuX, quitY);
			GameScreen.game.font.draw(GameScreen.game.batch, "No  /  Yes", 	  rightMenuX, quitY-20);
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

	public static Boolean getShowQuit() {
		return showQuit;
	}

	public static void setShowQuit(Boolean showQuit) {
		Menu.showQuit = showQuit;
	}

	public static Boolean getShowRestart() {
		return showRestart;
	}

	public static void setShowRestart(Boolean showRestart) {
		Menu.showRestart = showRestart;
	}

	
}
