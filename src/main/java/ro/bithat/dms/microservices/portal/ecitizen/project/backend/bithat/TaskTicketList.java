package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TaskTicketList extends BaseModel {
    private List<TaskTicket> taskTicketList;

    public List<TaskTicket> getTaskTicketList() {
        return taskTicketList;
    }

    public void setTaskTicketList(List<TaskTicket> taskTicketList) {
        this.taskTicketList = taskTicketList;
    }
}
