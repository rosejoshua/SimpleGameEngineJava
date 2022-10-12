// Joshua Rose
// Sep 27, 2022
// The is the view portion of a model view controller & OOP
// designed simple Java game application. This class contains
// library components for Jpanel, Graphics, BufferedImage, 
// File manip and contains horizontal scroll position data

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

class View extends JPanel
{
	private BufferedImage pipe_image;
	private Model model;

	private Mario mario;
	private int horizScrollPos = 0;
	private int groundOffset;
	private int marioAmountOffCenter;

	View(Controller controller, Model model, Mario mario, int groundOffset)
	{
		controller.setView(this);
		this.model = model;
		this.mario = mario;
		this.groundOffset = groundOffset;
		this.marioAmountOffCenter = 0;
		try 
		{
			this.pipe_image = ImageIO.read(new File("pipe.png"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public void paintComponent(Graphics graphics)
	{
		graphics.setColor(Color.CYAN);
		graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		graphics.setColor(Color.gray);
		graphics.drawLine(0, groundOffset, 2000, groundOffset);
		marioAmountOffCenter = (mario.getXPos() + mario.getWidth()/2) - (this.getWidth()/2);
		if (marioAmountOffCenter > 0)
		{
			horizScrollPos += marioAmountOffCenter;
			mario.setXPos(mario.getXPos() - marioAmountOffCenter);
		}
		else if (marioAmountOffCenter < 0 && horizScrollPos > 0)
		{
			horizScrollPos += marioAmountOffCenter;
			mario.setXPos(mario.getXPos() + -marioAmountOffCenter);
		}
		for (Pipe p : model.getPipesArrayList())
		{

			graphics.drawImage(this.pipe_image, p.getXPos() - horizScrollPos, p.getYPos(), null);
		}
		if (mario.getXVelocity() >= 0.0 && (!mario.isFacingBackwards()))
			graphics.drawImage(mario.getSprite(), mario.getXPos(), mario.getYPos(), null);
		else
			graphics.drawImage(mario.getSprite(), mario.getXPos() + mario.getWidth(), mario.getYPos(),-mario.getWidth(), mario.getHeight(), null);
	}

	public BufferedImage getPipe_image()
	{
		return pipe_image;
	}

	public Model getModel()
	{
		return model;
	}

	public int getHorizScrollPos()
	{
		return horizScrollPos;
	}

	public void setHorizScrollPos(int horizScrollPos)
	{
		this.horizScrollPos = horizScrollPos;
	}
}
