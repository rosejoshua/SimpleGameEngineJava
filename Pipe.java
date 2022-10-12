// Joshua Rose
// Sep 27, 2022
// Used to represent a Pipe object which is a set of xy coords for drawing an image on screen,
// also contains methods for creating respective Json versions of each object for saving,
// as well as the converse operation of creating a Pipe object from Json version for loading

public class Pipe extends Rectangle
{
    private int xPos;
    private int yPos;
    private final static int pipeWidth = 55;
    private final static int pipeHeight = 400;

    public Pipe(int xPos, int yPos)
    {
        super();
        solidBody = true;
        this.xPos = xPos;
        this.yPos = yPos;
        this.setWidth(pipeWidth);
        this.setHeight(pipeHeight);
    }

    public int getXPos()
    {
        return xPos;
    }
    //return Json representation of Pipe object
    public Json toJson()
    {
        Json jsonObj = Json.newObject();
        jsonObj.add("xPos", xPos);
        jsonObj.add("yPos", yPos);
        return jsonObj;
    }

    public int getYPos()
    {
        return yPos;
    }
}
