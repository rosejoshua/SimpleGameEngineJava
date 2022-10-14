// Joshua Rose
// Sep 27, 2022
// The is the model portion of a model view controller & OOP
// designed simple Java game application. It handles keyboard and
// mouse input to move an image around the frame

import java.util.ArrayList;

class Model
{
    private ArrayList<Pipe> pipesArrayList;
    private String jsonFilename = "map.json";
    Model()
    {
        pipesArrayList = new ArrayList<Pipe>();
    }

    public ArrayList<Pipe> getPipesArrayList()
    {
        return pipesArrayList;
    }

    // attempt to add Pipe object at given coords from clickEvent, if Pipe origin plus width and height contains
    // the click event coords, delete pipe object (delete on click functionality)
    public boolean addPipeAtCoordsOrDeleteIfExists(int clickX, int clickY, int pipeWidth, int pipeHeight)
    {
        boolean wasCreated = false;

        for (Pipe pipe : pipesArrayList)
        {
            if (clickX >= pipe.getXPos() && clickX < pipe.getXPos() + pipeWidth)
            {
                if (clickY >= pipe.getYPos() && clickY < pipe.getYPos() + pipeHeight)
                {
                    pipesArrayList.remove(pipe);
                    return wasCreated;
                }
            }
        }
        pipesArrayList.add(new Pipe(clickX, clickY));
        wasCreated = true;

        return wasCreated;
    }

    // use file manip operations from Json class to save Pipe object data in Json form
    public boolean saveToJson()
    {
        try
        {
            Json jsonObj = Json.newObject();
            Json tempList = Json.newList();
            for (Pipe pipe : pipesArrayList)
            {
                tempList.add(pipe.toJson());
            }
            jsonObj.add("list", tempList);
            jsonObj.save(jsonFilename);
            System.out.println("saved to " + jsonFilename);
            return true;
        } catch (Exception e)
        {
            System.err.println("error: failed to save json file");
            System.err.println(e.getMessage());
            return false;
        }
    }

    // use file manip operations from Json class to populate ArrayList of Pipe objects and replace private instance pipesArrayList
    public void loadFromJson()
    {
        try
        {
            ArrayList<Pipe> newPipesArrayList = new ArrayList<>();
            Json tempJsonObj = Json.load(jsonFilename).get("list");
            for (int i = 0; i < tempJsonObj.size(); i++)
            {
                newPipesArrayList.add(new Pipe((int)tempJsonObj.get(i).getLong("xPos"), (int)tempJsonObj.get(i).getLong("yPos")));
            }
            
            pipesArrayList = newPipesArrayList;
            
        } catch (Exception e)
        {
            System.err.println("error: failed to load json file");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String toString()
    {
        return "Model{" +
                "pipesArrayList=" + pipesArrayList +
                ", jsonFilename='" + jsonFilename + '\'' +
                '}';
    }
}