import java.util.Comparator;

class SortByStart implements Comparator<Node> 
{ 
    public int compare(Node a, Node b) 
    { 
        return (int)(a.start-b.start); 
    } 
} 