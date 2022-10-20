package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

public class Priority {
    private String priority_name;
    private String priority_color;
    private String priority_tr;

    public Priority() {
    }

    public Priority(String priority_name, String priority_color, String priority_tr) {
        this.priority_name = priority_name;
        this.priority_color = priority_color;
        this.priority_tr = priority_tr;
    }

    public String getPriority_name() {
        return priority_name;
    }

    public void setPriority_name(String priority_name) {
        this.priority_name = priority_name;
    }

    public String getPriority_color() {
        return priority_color;
    }

    public void setPriority_color(String priority_color) {
        this.priority_color = priority_color;
    }

    public String getPriority_tr() {
        return priority_tr;
    }

    public void setPriority_tr(String priority_tr) {
        this.priority_tr = priority_tr;
    }
}
