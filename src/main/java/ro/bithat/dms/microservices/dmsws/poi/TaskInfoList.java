package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class TaskInfoList extends BaseModel {
    private List<TaskInfo> taskInfoList;

    public List<TaskInfo> getTaskInfoList() {
        if (taskInfoList == null)
            taskInfoList = new ArrayList<TaskInfo>();

        return taskInfoList;
    }

    public void setTaskInfoList(List<TaskInfo> taskInfoList) {
        this.taskInfoList = taskInfoList;
    }
}
