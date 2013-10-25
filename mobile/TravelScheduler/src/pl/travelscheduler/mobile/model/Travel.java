package pl.travelscheduler.mobile.model;

public class Travel
{
    private String source;
    private String destination;
    
    public Travel(String source, String destination)
    {
        this.source = source;
        this.destination = destination;
    }

    public String getSource()
    {
        return source;
    }

    public String getDestination()
    {
        return destination;
    }
}
