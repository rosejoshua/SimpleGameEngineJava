// Joshua Rose
// Sep 27, 2022
// The is the controller portion of a model view controller & OOP
// designed simple Java game application. It handles keyboard and 
// mouse input to place images on a 2d space

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements MouseListener, KeyListener
{
private View view;
private Model model;

//keydown state
private boolean dDown;
private boolean fDown;
private boolean jDown;
private boolean kDown;
private boolean isRunning;
private boolean inEditMode;


	Controller(Model model)
	{
		this.model = model;
		isRunning = true;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	public boolean getIsRunning()
	{
		return isRunning;
	}

	//public boolean isInEditMode() {	return inEditMode; }

	//check keydown state and move image position accordingly
	public void update()
	{
		//boundary correction
		if (view.getHorizScrollPos() < 0)
			view.setHorizScrollPos(0);
	}

	//provide destination coordinates of mouse pointer location when clicked
	public void mousePressed(MouseEvent e)
	{
		if (inEditMode)
		{
			model.addPipeAtCoordsOrDeleteIfExists(e.getX() + view.getHorizScrollPos(), e.getY(), view.getPipe_image().getWidth(), view.getPipe_image().getHeight());
			if (e.getY() < 100)
			{
				System.out.println("break here");
			}
		}
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	public void keyPressed(KeyEvent e)
	{
		//resolve key events and set keydown bools accordingly
		switch(e.getKeyCode())
		{
			//enable edit mode
			case KeyEvent.VK_E:
			{
				inEditMode = !inEditMode;
				if (inEditMode)
				{
					System.out.println("In pipe edit mode!");
				}
				break;
			}
			//save object coords to file
			case KeyEvent.VK_S: model.saveToJson(); break;
			//load object coords from file
			case KeyEvent.VK_L: model.loadFromJson(); break;
			case KeyEvent.VK_F: fDown = true; break;
			case KeyEvent.VK_D: dDown = true; break;
			case KeyEvent.VK_J: jDown = true; break;
			case KeyEvent.VK_K: kDown = true; break;
			case KeyEvent.VK_Q: isRunning = false; break;
			case KeyEvent.VK_ESCAPE: isRunning = false; break;
		}
	}

	//resolve key events and set keydown bools accordingly
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_F: fDown = false; break;
			case KeyEvent.VK_D: dDown = false; break;
			case KeyEvent.VK_J: jDown = false; break;
			case KeyEvent.VK_K: kDown = false; break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public boolean isdDown()
	{
		return dDown;
	}

	public boolean isfDown()
	{
		return fDown;
	}

	public boolean isjDown()
	{
		return jDown;
	}

	public boolean isKDown()
	{
		return kDown;
	}
}
