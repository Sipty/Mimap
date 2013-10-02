package net.sipty.mimap;

public class Menu {
	
	// declarations
	private static Boolean showMenu=false,
						   showOptions=false,
						   showQuit=false,
						   showRestart=false;
	//private final int menuLeftX=GameScreen.getMenuLeftX()+1, menuRightX=GameScreen.getMenuLeftX()+59, menuTopY=GameScreen.getMenuTopY()+45;
	private final static int leftMenuX=50,	// menu coords 
							// rightMenuX=GameScreen.getMenuRightX()+5,
							 rightMenuX=110,
							 menuY=718,
							 restartY=menuY-20,	// restart coords	
							 optionsY=menuY-40,	// options coords
							 quitY=menuY-60;	// quit coords
	
	// constructor
	public Menu() {
	}
	
	public static void draw() {
		// menu button
		InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Menu", leftMenuX, menuY);
		
		// menu drop-down
		if(showMenu) {
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Restart", leftMenuX, restartY);
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Options", leftMenuX, optionsY);
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Quit", 	leftMenuX, quitY);
		}

		// restart
		if(showRestart) {
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Are you sure?", rightMenuX, restartY);
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "No  /  Yes", rightMenuX, restartY-20);
		}
		// options menu
		if(showOptions) {
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Option 1", rightMenuX, optionsY);
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Option 2", rightMenuX, optionsY-20);
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Option 3", rightMenuX, optionsY-40);
		}
		
		// quit confirmation
		if(showQuit) {
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "Are you sure?", rightMenuX, quitY);
			InHouseScreen.game.font.draw(InHouseScreen.game.batch, "No  /  Yes", 	  rightMenuX, quitY-20);
		}
	}

	// setters & getters
	
	
	
	public static void setShowMenu(Boolean showMenu) {
		Menu.showMenu = showMenu;
	}

	public static int getMenuY() {
		return menuY;
	}

	public static int getLeftMenuX() {
		return leftMenuX;
	}

	public static int getRightMenuX() {
		return rightMenuX;
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
