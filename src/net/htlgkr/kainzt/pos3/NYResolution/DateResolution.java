package net.htlgkr.kainzt.pos3.NYResolution;

import net.htlgkr.kainzt.pos3.NYResolution.enums.MyCategory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateResolution {

  private String title;
  private String description;
  private Date deadline;
  private boolean done;
  private int priority;
  private MyCategory category;
  private Date creationDate;
  private Date doneDate;

  public DateResolution() {
  }
  public Resolution toResolution(){
    Resolution resolution = new Resolution();
    resolution.setTitle(title);
    resolution.setDescription(description);
    resolution.setDeadline(deadline==null?null:deadline.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    resolution.setDone(done);
    resolution.setPriority(priority);
    resolution.setCategory(category);
    resolution.setCreationDate(creationDate==null?null:creationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    resolution.setDoneDate(doneDate==null?null:doneDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    return resolution;
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

  public Date getDeadline() {
    return deadline;
  }

  public void setDeadline(Date deadline) {
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

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getDoneDate() {
    return doneDate;
  }

  public void setDoneDate(Date doneDate) {
    this.doneDate = doneDate;
  }
}
