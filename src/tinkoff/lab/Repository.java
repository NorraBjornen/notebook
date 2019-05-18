package tinkoff.lab;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

class Repository {
    private static final String FILE_NAME = "output.txt";
    private static Repository repo;

    private ArrayList<Record> records;

    ArrayList<Record> getRecords() {
        return records;
    }

    ArrayList<Record> getFilteredRecords(Date date){
        ArrayList<Record> filtered = new ArrayList<>(records);
        filtered.removeIf(it -> it.date.compareTo(date) < 0);
        return filtered;
    }

    private Repository(){
        records = loadAllRecords();
    }

    static Repository get(){
        if(repo == null)
            repo = new Repository();
        return repo;
    }

    Record getRecord(int number) throws IndexOutOfBoundsException{
        return records.get(number - 1);
    }

    Record getRecord(String name){
        for (Record record : records)
            if(record.getName().equals(name))
                return record;
        return null;
    }

    void editRecord(Record record, String newContent) throws Exception{
        record.edit(newContent);
        writeRecordsToStorage();
    }

    void deleteRecordByName(String name){
        records.removeIf(it -> it.getName().equals(name));
        writeRecordsToStorage();
    }

    void deleteRecordByNumber(int number){
        records.remove(number - 1);
        writeRecordsToStorage();
    }

    void writeNewRecord(Record record){
        records.add(record);
        writeRecordsToStorage();
    }

    private ArrayList<Record> loadAllRecords(){
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fis);
            ArrayList<Record> records = (ArrayList<Record>) in.readObject();
            in.close();
            return records;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    private void writeRecordsToStorage(){
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(records);
            oos.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
