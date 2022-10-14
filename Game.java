// Joshua Rose
// Sep 27, 2022
// The is the Java file containing the main method and
// application loop portion of a model view controller & OOP
// designed simple Java game application. This is where the 
// model, view and controller objects are originally instantiated
// and passed around. 
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.io.IOException;

public class Game extends JFrame
{
	private Model model;
	private Controller controller;
	private View view;

	private Mario mario;

	public Game() throws IOException
	{
		int resW = 1000;
		int resH = 600;
		int groundOffset = 50;

		model = new Model();
		controller = new Controller(model);
		//95x61 sprites
		mario = new Mario(controller, model, resW, resH-groundOffset,100, 100, 61, 95, "mario", 5);
		view = new View(controller, model, mario, resH - groundOffset -1);
		mario.setView(view);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
		this.setTitle("A4 Side Scroller");
		this.setSize(resW, resH);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void run() throws IOException
	{
		model.loadFromJson();
		// application/game loop
		while(controller.getIsRunning())
		{
			controller.update();
			mario.update();
			view.repaint(); //Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); //Updates Screen

			// Sleep for 40 millis (simple frame capping)
			try 
			{
				Thread.sleep(10);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		//todo: save JSON here before exit
		System.exit(0);
	}

	@Override
	public String toString()
	{
		return "Game{" +
				"model=" + model +
				", controller=" + controller +
				", view=" + view +
				", mario=" + mario +
				'}';
	}

	public static void main(String[] args) throws IOException
	{
		Game game = new Game();
		game.run();
	}
}
