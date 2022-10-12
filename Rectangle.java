public abstract class Rectangle
{
    boolean solidBody;
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public Rectangle()
    {
    }

    public Rectangle(boolean solidBody, int xPos, int yPos, int width, int height)
    {
        this.solidBody = solidBody;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public Rectangle(boolean solidBody, int xPos, int yPos)
    {
        this.solidBody = solidBody;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getCollisionPixelsXAxis(Rectangle rectangle)
    {
        if (this.xPos + this.width < rectangle.getXPos() && this.xPos > rectangle.getXPos() + rectangle.getWidth())
            return 0;
        else
        {
            int collisionPixels = this.width;

            if (this.xPos < rectangle.getXPos())
            {
                collisionPixels -= (rectangle.getXPos() - this.getXPos());
            }

            if (this.xPos + this.width > rectangle.getXPos() + rectangle.getWidth())
            {
                collisionPixels -= (this.getXPos() + this.width - (rectangle.getXPos() + rectangle.getWidth()));
            }

            return Math.max(collisionPixels, 0);
        }
    }

    public int getCollisionPixelsXAxis(Rectangle rectangle, int xOffset)
    {
        if (xOffset + this.xPos + this.width < rectangle.getXPos() && ((this.xPos + xOffset) > (rectangle.getXPos() + rectangle.getWidth())))
            return 0;
        else
        {
            int collisionPixels = this.width;

            if ((this.xPos + xOffset) < rectangle.getXPos())
            {
                collisionPixels -= (rectangle.getXPos() - (this.getXPos()+ xOffset));
            }

            if ((xOffset + this.xPos + this.width) > (rectangle.getXPos() + rectangle.getWidth()))
            {
                collisionPixels -= ((xOffset + this.getXPos() + this.width) - (rectangle.getXPos() + rectangle.getWidth()));
            }

            return Math.max(collisionPixels, 0);
        }
    }

    public int getCollisionPixelsYAxis(Rectangle rectangle)
    {
        if (this.yPos + this.height < rectangle.getYPos() && this.yPos > rectangle.getYPos() + rectangle.getHeight())
            return 0;
        else
        {
            int collisionPixels = this.height;

            if (this.yPos < rectangle.getYPos())
            {
                //change here
                collisionPixels -= (this.height - (this.yPos + this.height - rectangle.getYPos()));
            }

            if (this.yPos + this.height > rectangle.getYPos() + rectangle.getHeight())
            {
                collisionPixels -= (this.getYPos() + this.height - rectangle.getYPos() + rectangle.getHeight());
            }

            return Math.max(collisionPixels, 0);
        }
    }

    public boolean isSolidBody()
    {
        return solidBody;
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setXPos(int xPos)
    {
        this.xPos = xPos;
    }

    public void setYPos(int yPos)
    {
        this.yPos = yPos;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }
}
