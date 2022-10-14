import java.util.Iterator;

public class PipeList implements Iterable<Pipe>
{
    private Pipe[] pipeList;
    private int currentSize;

    public PipeList(Pipe[] pipeList)
    {
        this.pipeList = pipeList;
        this.currentSize = this.pipeList.length;
    }

    public PipeList()
    {
        this.pipeList = new Pipe[10];
        this.currentSize = this.pipeList.length;
    }

    public void add(Pipe pipe)
    {
        if (pipeList[pipeList.length] != null)
        {
            Pipe[] tempList = new Pipe[pipeList.length*2];
            for (int i = 0; i < pipeList.length; i++) {
                tempList[i] = pipeList[i];
                pipeList = tempList;
                this.currentSize = pipeList.length;
            }
        }
    }

    @Override
    public Iterator<Pipe> iterator() {
        Iterator<Pipe> it = new Iterator<Pipe>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < currentSize && pipeList[currentIndex] != null;
            }

            @Override
            public Pipe next() {
                return pipeList[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
