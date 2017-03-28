import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;   //Last row has only one cell
	private static final int TOTAL_MINES = 10; //Amount of mines in a 9x9 grid
	private Random mineGen = new Random();

	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;

	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public int [][] numAdjMines = new int [TOTAL_COLUMNS][TOTAL_ROWS];
	public Boolean[][] mines = new Boolean [TOTAL_COLUMNS][TOTAL_ROWS]; //Determines if cell contains a mine
	public Boolean[][] uncoveredCells = new Boolean [TOTAL_COLUMNS][TOTAL_ROWS];

	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}



		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;
		// Variables used to generate numbers inside of cells
		
		int xPos;
		int yPos;
		int numMines;
		
		//Paint the background
		g.setColor(Color.GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);


		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {

			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));

		}

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {

			for (int y = 0; y < TOTAL_ROWS; y++) {

				{
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				}
			}
		}
		
		// Loop used to find the cells around the clicked cell and find if there is a mine closed by
		
		for (int x = 0; x < TOTAL_COLUMNS; x++){
			for (int y = 0; y < TOTAL_ROWS; y++){
				numMines = 0;
				if (!mines[x][y]){ //Cell is not a mine
					for (int i = x-1; i <= x+1; i++){
						for (int j = y-1; j <= y+1; j++){


							if ( i < 0 || i > TOTAL_COLUMNS-1 || j < 0 || j >TOTAL_ROWS-1){
								//Do nothing: out of colorArray bounds

							} else if (mines[i][j]){
								numMines++;
							}
						}
					}
				// Draws a number in a cell that is near a mine  8x8 around the cells
					
					xPos = x1 + GRID_X + (x * (INNER_CELL_SIZE )) + 10;
					yPos =  y1 + GRID_Y + (y * (INNER_CELL_SIZE )) + 20;
					g.setColor(Color.WHITE);
					switch (numMines) {
					case 1:
						g.drawString("1", xPos, yPos);
						break;
					case 2:
						g.drawString("2", xPos, yPos);
						break;
					case 3:
						g.drawString("3", xPos, yPos);
						break;
					case 4:
						g.drawString("4",xPos, yPos);
						break;
					case 5:
						g.drawString("5",xPos, yPos);
						break;
					case 6:
						g.drawString("6", xPos, yPos);
						break;
					case 7:
						g.drawString("7", xPos, yPos);
						break;
					default:
						break;
					}
				}
			}
		}
	}
	
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS|| y < 0 || y > TOTAL_ROWS+1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS|| y < 0 || y > TOTAL_ROWS+1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	public void generateMines(){
		for(int i =0; i<TOTAL_COLUMNS; i++){
			for(int j=0; j<TOTAL_ROWS; j++){
				mines[i][j]=false;
				uncoveredCells[i][j] = false;
				numAdjMines[i][j]= 0;
			}
		}
		int xMine, yMine;
		int setMines = 0;
		while (setMines < TOTAL_MINES){
			xMine = mineGen.nextInt(TOTAL_COLUMNS);
			yMine = mineGen.nextInt(TOTAL_ROWS);
			if (!mines[xMine][yMine]){
				setMines++;
				mines[xMine][yMine]=true;
				numAdjMines[xMine][yMine] = -1;
			}
		}

	}
	public void minePos(){
		for(int i = 0; i<TOTAL_COLUMNS; i++){
			for(int j = 0; j < TOTAL_ROWS; j ++){
				if(!this.mines[i][j])//There is no mine on the cell, the number will be assigned to the numadjmine
				{
					if( j >= 1 && this.mines[i][j-1]== true){
						numAdjMines[i][j]=+1;
					}
					if (j < TOTAL_ROWS-2 && this.mines[i][j+1] == true){
						numAdjMines[i][j+1]=+1;
					}
					if(i>= 1 && this.mines[i-1][j]==true){
						numAdjMines[i][j]=+1;
					}
					if(i< TOTAL_COLUMNS-2 && this.mines[i+1][j]==true){
						numAdjMines[i+1][j]=+1;
					}

					if((i>= 0 && j >=0)&& this.mines[i-1][j-1]== true){
						numAdjMines[i][j]+=1;
					}
					if (i <= TOTAL_COLUMNS-2 && j >= 1 && this.mines[i+1][j-1] == true) {
						numAdjMines[i][j] =+ 1;
					}
					if (i <= TOTAL_COLUMNS-2 && j <= TOTAL_ROWS-2 && this.mines[i+1][j+1] == true){ 
						numAdjMines[i][j] =+ 1;
					}
					if (i >= 1 && j <= TOTAL_ROWS-2 && this.mines[i-1][j+1] == true) {
						numAdjMines[i][j] =+ 1;
					}

				}
			}
		}
	}
	public void paintNearCells(int i, int j) {
		i = mouseDownGridX;
		j = mouseDownGridY;
		if (!this.mines[i][j]){
			if (j>=1 && (numAdjMines[i][j-1]==0)){
				if (!this.mines[i][j-1]){
					colorArray[i][j-1]=Color.LIGHT_GRAY;
					uncoveredCells[i][j-1]= true;
				}
			}
			if (j <= TOTAL_ROWS-2 && (numAdjMines[i][j+1] == 0)) {
				if (!this.mines[i][j+1]) { 
					colorArray[i][j+1] = Color.LIGHT_GRAY;
					uncoveredCells[i][j+1]  = true;
				}
			}
			if (i >= 1 && (numAdjMines[i-1][j] == 0)) {
				if (!this.mines[i-1][j]) { 
					colorArray[i-1][j] = Color.LIGHT_GRAY;
					uncoveredCells[i-1][j]  = true;
				}
			}
			if (i <=  TOTAL_COLUMNS-2 && (numAdjMines[i+1][j] == 0)) {
				if (!this.mines[i+1][j]) { 
					colorArray[i+1][j] = Color.LIGHT_GRAY;
					uncoveredCells[i+1][j]  = true;
				}
			}
			if (i >= 1 && j >= 1 && (numAdjMines[i-1][j-1] == 0)) { 
				if (!this.mines[i-1][j-1]) { 
					colorArray[i-1][j-1] = Color.LIGHT_GRAY;
					uncoveredCells[i-1][j-1]  = true;
				}
			}
			if (i <= TOTAL_COLUMNS-2 && j >= 1 && (numAdjMines[i+1][j-1] == 0)) {
				if (!this.mines[i+1][j-1]) { 
					colorArray[i+1][j-1] = Color.LIGHT_GRAY;
					uncoveredCells[i+1][j-1]  = true;
				}
			}
			if (i <= TOTAL_COLUMNS-2 && j <= TOTAL_ROWS-2 && (numAdjMines[i+1][j+1] == 0)) {
				if (!this.mines[i+1][j+1]) { 
					colorArray[i+1][j+1] = Color.LIGHT_GRAY;
					uncoveredCells[i+1][j+1]  = true;
				}
			}
			if (i >= 1 && j <= TOTAL_ROWS-2 && (numAdjMines[i-1][j+1] == 0)) {
				if (!this.mines[i-1][j+1]) { 
					colorArray[i-1][j+1] = Color.LIGHT_GRAY;
					uncoveredCells[i-1][j+1]  = true;
				}
			}
		}
	}

	public void gameWon(){
		int uncoveredTiles = 0;
		for (int i = 0; i<TOTAL_COLUMNS;i++){
			for (int j = 0; j<TOTAL_ROWS; j++){
				if(!this.mines[i][j]){
					if(!uncoveredCells[i][j]){
						break;
					}
					else {
						uncoveredTiles ++;
					}
				}
			}
		}
		if (uncoveredTiles == ((TOTAL_COLUMNS*(TOTAL_ROWS))-TOTAL_MINES)){
			JOptionPane.showMessageDialog(null, "CONGRATULATIONS!");
			System.exit(0);
		}
	}
	public void gameOver()
	{
		for (int i=0; i<TOTAL_COLUMNS; i++){
			for (int j=0; j<(TOTAL_ROWS); j++){
				if (this.mines[i][j]){
					colorArray[i][j] = Color.BLACK;
					repaint();
				}
			}
		}
		JOptionPane.showMessageDialog(null, "GAME OVER!");
		System.exit(1);
	}
}