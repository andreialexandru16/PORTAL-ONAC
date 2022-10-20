package ro.bithat.dms.microservices.dmsws.file;
public class FileList
{
    private String serverPath;

    private String path;

    private FileLinks[] fileLinks;

    private String name;

    private String id;

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

    public FileLinks[] getFileLinks ()
    {
        return fileLinks;
    }

    public void setFileLinks (FileLinks[] fileLinks)
    {
        this.fileLinks = fileLinks;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [serverPath = "+serverPath+", path = "+path+", fileLinks = "+fileLinks+", name = "+name+", id = "+id+"]";
    }
}