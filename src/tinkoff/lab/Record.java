package tinkoff.lab;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record implements Serializable {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String name;
    private String content;
    Date date;

    String getName() {
        return name;
    }

    Record(String name, String content, Date date){
        this.name = name;
        this.content = content;
        this.date = date;
    }

    void edit(String newContent){
        this.content = newContent;
    }

    @Override
    public String toString() {
        return name + " - [Note № "+ (Repository.get().getRecords().indexOf(this) + 1) +"]" + "\n"
                + dateFormat.format(date)+"\n"
                + content + "\n";
    }
}
