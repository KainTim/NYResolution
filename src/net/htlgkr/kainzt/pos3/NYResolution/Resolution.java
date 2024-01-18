package net.htlgkr.kainzt.pos3.NYResolution;

import net.htlgkr.kainzt.pos3.NYResolution.enums.MyCategory;

import java.time.LocalDate;

public record Resolution (String title,
                          String description,
                          LocalDate deadline,
                          boolean done,
                          int priority,
                          MyCategory category,
                          LocalDate creationDate,
                          LocalDate doneDate){
}