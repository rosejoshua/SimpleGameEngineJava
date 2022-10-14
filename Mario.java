import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

public class Mario extends Rectangle
{
    private Controller controller;
    private Model model;
    private View view;
    private BufferedImage[] sprites;
    private PhysicsTracker physicsTracker;
    private final double xVelocityWalkModifier = 0.15;
    private final double xVelocitySprintModifier = 0.18;
    private final double noInputXSlowdownFactor = 0.8;
    private final double maxXVelocityWalk = 4.0;
    private final double maxXVelocitySprint = 6.0;
    private final double maxYVelocity = 20.0;
    private final double jumpVelocity = -13.0;
    private final double gravity = 0.4;
    private final double heavyGravity = 0.8;
    private final int millisBetweenAnimationFrames = 100;
    private boolean canJump;
    private boolean facingBackwards;
    private int lastDrawnFrame;
    private int groundPixelIndex;
    private int prevXPos;
    private int prevYPos;
    private int tempCollisionXPixels;
    private int tempCollisionYPixels;

    public Mario(Controller controller, Model model, int resW, int resH, int startXPos, int startYPos, int width, int height, String spriteBaseFilename, int numSprites) throws IOException
    {
        super(true, startXPos, startYPos, width, height);
        this.controller = controller;
        this.model = model;
        this.groundPixelIndex = resH-1;
        sprites = new BufferedImage[numSprites];
        for (int i = 0; i < numSprites; i++)
        {
            sprites[i] = ImageIO.read(new File(spriteBaseFilename + (i+1) + ".png"));
        }
        physicsTracker = new PhysicsTracker();
        canJump = true;
        facingBackwards = false;
        lastDrawnFrame = 0;
        prevXPos = 0;
        prevYPos = 0;
        tempCollisionXPixels = 0;
        tempCollisionYPixels = 0;
    }

    public void update()
    {
        prevXPos = this.getXPos();
        prevYPos = this.getYPos();
        if (controller.isKDown())
        {
            if (canJump)
            {
                physicsTracker.addYVelocityWithLimit(jumpVelocity, maxYVelocity);
                canJump = false;
            }
        }

        if (controller.isfDown())
        {
            facingBackwards=false;
            if (physicsTracker.getXVelocity() < -0.1)
                physicsTracker.slowXVelocity(noInputXSlowdownFactor);
            if (controller.isjDown())
            {
                physicsTracker.addXVelocityWithLimit(xVelocitySprintModifier, maxXVelocitySprint);
            }
            else
            {
                physicsTracker.addXVelocityWithLimit(xVelocityWalkModifier, maxXVelocityWalk);
            }
        }
        else if (controller.isdDown())
        {
            facingBackwards = true;
            if (physicsTracker.getXVelocity() > 0.1)
                physicsTracker.slowXVelocity(noInputXSlowdownFactor);
            if (controller.isjDown())
            {
                physicsTracker.addXVelocityWithLimit(-xVelocitySprintModifier, maxXVelocitySprint);
            }

            else
            {
                physicsTracker.addXVelocityWithLimit(-xVelocityWalkModifier, maxXVelocityWalk);
            }
        }
        else {
            physicsTracker.slowXVelocity(noInputXSlowdownFactor);
        }

        this.setXPos((int)(this.getXPos() + physicsTracker.getXVelocity()));
        if(this.getXPos() < 0)
            this.setXPos(0);

        tempCollisionXPixels = 0;
        tempCollisionYPixels = 0;
        //x-axis collision check and correction here
        for (Pipe pipe : model.getPipesArrayList())
        {
            if (this.getCollisionPixelsYAxis(pipe) > 0 && this.getCollisionPixelsXAxis(pipe, view.getCameraXPos()) > 5)
            {
                if (this.getCollisionPixelsXAxis(pipe, view.getCameraXPos()) > tempCollisionXPixels)
                    tempCollisionXPixels = this.getCollisionPixelsXAxis(pipe, view.getCameraXPos());
                if (this.getCollisionPixelsYAxis(pipe) > tempCollisionYPixels)
                    tempCollisionYPixels = this.getCollisionPixelsYAxis(pipe);
            }
        }

        if (tempCollisionYPixels > 0 && tempCollisionXPixels > 0)
        {
            if (this.getXPos() > prevXPos)
            this.setXPos(this.getXPos() - tempCollisionXPixels + 5);
            else if (this.getXPos() < prevXPos)
            this.setXPos(this.getXPos() + tempCollisionXPixels - 5);
        }

        if (controller.isKDown())
        {
            physicsTracker.addYVelocityWithLimit(gravity, maxYVelocity);
        }
        else
        {
            physicsTracker.addYVelocityWithLimit(heavyGravity, maxYVelocity);
        }

        this.setYPos((int) (this.getYPos() + physicsTracker.getYVelocity()));

        tempCollisionXPixels = 0;
        tempCollisionYPixels = 0;
        //y axis collision check and correction here
        for (Pipe pipe : model.getPipesArrayList())
        {
            if (this.getCollisionPixelsYAxis(pipe) > 0 && this.getCollisionPixelsXAxis(pipe, view.getCameraXPos()) > 5)
            {
                if (this.getCollisionPixelsXAxis(pipe, view.getCameraXPos()) > tempCollisionXPixels)
                    tempCollisionXPixels = this.getCollisionPixelsXAxis(pipe, view.getCameraXPos());
                if (this.getCollisionPixelsYAxis(pipe) > tempCollisionYPixels)
                    tempCollisionYPixels = this.getCollisionPixelsYAxis(pipe);
            }
        }

        if (tempCollisionYPixels > 0 && tempCollisionXPixels > 0)
        {
            if (this.getYPos() > prevYPos)
            this.setYPos(this.getYPos() - tempCollisionYPixels);
            else if (this.getYPos() < prevYPos)
            this.setXPos(this.getXPos() + tempCollisionYPixels);
            this.physicsTracker.setYVelocityZero();
            if (!controller.isKDown())
                canJump = true;
        }

        if ((this.getYPos() + this.getHeight()) >= groundPixelIndex)
        {
            this.setYPos(groundPixelIndex - this.getHeight());
            physicsTracker.setYVelocityZero();
            if (!controller.isKDown())
                canJump = true;
        }

    }

    public BufferedImage getSprite()
    {
        if (Math.abs(physicsTracker.getXVelocity()) > 0.1)
        {
            if (controller.isjDown())
            {
                lastDrawnFrame = (int) (ZonedDateTime.now().toInstant().toEpochMilli() % (sprites.length * (millisBetweenAnimationFrames-millisBetweenAnimationFrames/3))) / (millisBetweenAnimationFrames-millisBetweenAnimationFrames/3);
            }
            else
            {
                lastDrawnFrame = (int) (ZonedDateTime.now().toInstant().toEpochMilli() % (sprites.length * millisBetweenAnimationFrames)) / millisBetweenAnimationFrames;
            }
        }
        if (!(Math.abs(physicsTracker.getYVelocity()) > 0.001) || canJump)
            return sprites[lastDrawnFrame];
        else return sprites[0];
    }

    public double getXVelocity()
    {
        return physicsTracker.getXVelocity();
    }

    public boolean isFacingBackwards()
    {
        return facingBackwards;
    }

    public void setView(View view)
    {
        this.view = view;
    }

    @Override 
    public String toString()
    {
        return "MARIO: canJump: " + canJump + ", facingBackwards: " + facingBackwards + ", lastDrawFrame: " + lastDrawnFrame + 
        ", groundPixelIndex: " + groundPixelIndex + ", prevXPos: " + prevXPos + ", prevYPos: " + prevYPos + 
        ", tempCollisionXPixels: " + tempCollisionXPixels + ", tempCollisionYPixels: " + tempCollisionYPixels;
    }
}
