package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DirLinkList
{
    private String result;

    private DirLink[] dirLink;

    private String extendedInfo;

    private String info;

    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
    {
        this.result = result;
    }

    public DirLink[] getDirLink ()
    {
        return dirLink;
    }

    public void setDirLink (DirLink[] dirLink)
    {
        this.dirLink = dirLink;
    }

    public String getExtendedInfo ()
    {
        return extendedInfo;
    }

    public void setExtendedInfo (String extendedInfo)
    {
        this.extendedInfo = extendedInfo;
    }

    public String getInfo ()
    {
        return info;
    }

    public void setInfo (String info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+", dirLink = "+dirLink+", extendedInfo = "+extendedInfo+", info = "+info+"]";
    }
}