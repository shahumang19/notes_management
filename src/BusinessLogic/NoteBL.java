/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author admin1
 */
public class NoteBL {
    int id,priority;
    String cat,title,textData;
    boolean lock ;
    Date creationDate;
    Time creationTime;

    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationTime(Time creationTime) {
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public String getCat() {
        return cat;
    }

    public String getTitle() {
        return title;
    }

    public String getTextData() {
        return textData;
    }

    public boolean isLock() {
        return lock;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Time getCreationTime() {
        return creationTime;
    }
    
    
}
