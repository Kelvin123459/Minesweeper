import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:	
			Component l = e.getComponent();
			while (!(l instanceof JFrame)) {
				l = l.getParent();
				if (l == null) {
					return;
				}
			}
		JFrame click = (JFrame)l;
		MyPanel myRightPanel = (MyPanel) click.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myRightInsets = click.getInsets();
		int cx1 = myRightInsets.left;
		int cy1 = myRightInsets.top;
		e.translatePoint(-cx1, -cy1);
		int cx = e.getX();
		int cy = e.getY();
		myRightPanel.x = cx;
		myRightPanel.y = cy;
		myRightPanel.mouseDownGridX = myRightPanel.getGridX(cx, cy);
		myRightPanel.mouseDownGridY = myRightPanel.getGridY(cx, cy);
	      break;
	      default:
	    	  
	    	  //do nothing
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed							
							Color newColor = Color.LIGHT_GRAY;
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							myPanel.repaint();
						}
					}
				}
			myPanel.repaint();
			break;
		case 3:		
			Component l = e.getComponent();
			while (!(l instanceof JFrame)) {
				l = l.getParent();
				if (l == null) {
					return;
				}
			}
			JFrame click = (JFrame)l;
			MyPanel myRightPanel = (MyPanel) click.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myRightInsets = click.getInsets();
			int cx1 = myRightInsets.left;
			int cy1 = myRightInsets.top;
			e.translatePoint(-cx1, -cy1);
			int cx = e.getX();
			int cy = e.getY();
			myRightPanel.x = cx;
			myRightPanel.y = cy;
			int RightgridX = myRightPanel.getGridX(cx, cy);
			int RightgridY = myRightPanel.getGridY(cx, cy);
			if ((myRightPanel.mouseDownGridX == -1) || (myRightPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				
			} else if ((RightgridX == -1) || (RightgridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else if ((myRightPanel.mouseDownGridX != RightgridX) || (myRightPanel.mouseDownGridY !=RightgridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else { 
							if(myRightPanel.colorArray[myRightPanel.mouseDownGridX][myRightPanel.mouseDownGridY].equals(Color.LIGHT_GRAY) ){
								break; //Do nothing
							}
							if(myRightPanel.colorArray[myRightPanel.mouseDownGridX][myRightPanel.mouseDownGridY].equals(Color.RED)){
								myRightPanel.colorArray[myRightPanel.mouseDownGridX][myRightPanel.mouseDownGridY] = Color.WHITE;
								myRightPanel.repaint();
								break;
								
							}
							
							myRightPanel.colorArray[myRightPanel.mouseDownGridX][myRightPanel.mouseDownGridY]= Color.RED;
							myRightPanel.repaint();
							
						}
			myRightPanel.repaint();
			
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}