package ro.bithat.dms.microservices.dmsws.file;
public class RootFolder
{
    private String serverPath;

    private String path;

    private String name;

    private Long id;

    public String getServerPath ()
    {
        return serverPath;
    }

    public void setServerPath (String serverPath)
    {
        this.serverPath = serverPath;
    }

    public String getPath ()
    {
        return path;
    }

    public void setPath (String path)
    {
        this.path = path;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Long getId ()
    {
        return id;
    }

    public void setId (Long id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [serverPath = "+serverPath+", path = "+path+", name = "+name+", id = "+id+"]";
    }
}