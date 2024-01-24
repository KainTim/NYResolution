package net.htlgkr.kainzt.pos3.NYResolution;

import net.htlgkr.kainzt.pos3.NYResolution.enums.MyCategory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Resolution {
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private int priority;
    private MyCategory category;
    private LocalDate creationDate;
    private LocalDate doneDate;

    public Resolution() {

    }

    public Resolution(String title, String description, LocalDate deadline, boolean done, int priority, MyCategory category, LocalDate creationDate, LocalDate doneDate) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.priority = priority;
        this.category = category;
        this.creationDate = creationDate;
        this.doneDate = doneDate;
    }

    public DateResolution toDateResolution(){
        DateResolution dateResolution = new DateResolution();
        dateResolution.setTitle(title);
        dateResolution.setDescription(description);
        dateResolution.setDeadline(deadline==null?null:Date.from(deadline.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dateResolution.setDone(done);
        dateResolution.setPriority(priority);
        dateResolution.setCategory(category);
        dateResolution.setCreationDate(creationDate==null?null:Date.from(creationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dateResolution.setDoneDate(doneDate==null?null:Date.from(doneDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return dateResolution;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MyCategory getCategory() {
        return category;
    }

    public void setCategory(MyCategory category) {
        this.category = category;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Resolution{");
        sb.append("title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", deadline=").append(deadline);
        sb.append(", done=").append(done);
        sb.append(", priority=").append(priority);
        sb.append(", category=").append(category);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", doneDate=").append(doneDate);
        sb.append('}');
        return sb.toString();
    }
}