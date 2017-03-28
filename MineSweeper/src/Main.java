/*Minesweeper - Assigment 1 - ICOM4015
  By Kelvin Garc�a & Luis M. Cintr�n*/
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("MINESWEEPER");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		myFrame.setLocationRelativeTo(null);
		myFrame.setSize(400, 400);


		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);
		myPanel.generateMines();

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}
}