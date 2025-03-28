package dto;

import java.time.LocalDate;

public class SUp_ProjectDTO {
    
    private int project_id;
    private String project_name;
    private String description;
    private String status;       
    private LocalDate estimated_launch;     

    public SUp_ProjectDTO() {
        this.project_id = 0;
        this.project_name = "";
        this.description = "";
        this.status = "";
        this.estimated_launch = LocalDate.now();
    }

    public SUp_ProjectDTO(int project_id, String project_name, String description, String status, LocalDate estimated_launch) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.description = description;
        this.status = status;
        this.estimated_launch = estimated_launch;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEstimated_launch() {
        return estimated_launch;
    }

    public void setEstimated_launch(LocalDate estimated_launch) {
        this.estimated_launch = estimated_launch;
    }

    @Override
    public String toString() {
        return "SUp_ProjectDTO{" + "project_id=" + project_id + ", project_name=" + project_name + ", description=" + description + ", status=" + status + ", estimated_launch=" + estimated_launch + '}';
    }
     
}
