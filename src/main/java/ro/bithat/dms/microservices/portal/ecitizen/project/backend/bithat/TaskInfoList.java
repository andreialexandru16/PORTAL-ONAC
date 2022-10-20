package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class TaskInfoList extends BaseModel {
    private List<TaskInfo> taskInfoList;
    private List<Status> statusList;
    private List<Priority> priorityList;

    public List<TaskInfo> getTaskInfoList() {
        if (taskInfoList == null)
            taskInfoList = new ArrayList<TaskInfo>();

        return taskInfoList;
    }

    public void setTaskInfoList(List<TaskInfo> taskInfoList) {
        this.taskInfoList = taskInfoList;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public List<Priority> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(List<Priority> priorityList) {
        this.priorityList = priorityList;
    }
}
