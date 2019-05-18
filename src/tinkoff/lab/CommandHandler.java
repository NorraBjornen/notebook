package tinkoff.lab;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

class CommandHandler {
    private static final String COMMAND_SHOW = "-show";
    private static final String COMMAND_FILTER = "-filter";
    private static final String COMMAND_WRITE_NEW = "-new";
    private static final String COMMAND_EDIT = "-edit";
    private static final String COMMAND_DELETE = "-del";

    private static final String COMMAND_BY_NUMBER = "-number";
    private static final String COMMAND_BY_NAME = "-name";

    static void handleCommand(String cmd){
        switch (cmd){
            case COMMAND_WRITE_NEW:
                writeNewRecord();
                break;
            case COMMAND_SHOW:
                System.out.println();
                showAllRecords();
                break;
            case COMMAND_DELETE:
                deleteRecord();
                break;
            case COMMAND_EDIT:
                editRecord();
                break;
            case COMMAND_FILTER:
                showFiltered();
                break;
        }
    }

    private static void showAllRecords(){
        ArrayList<Record> records = Repository.get().getRecords();

        if(records.size() == 0)
            System.out.println("Нет записей");
        else
            for(Record rec : records)
                System.out.println(rec);
    }

    private static void showFiltered(){
        String hint = "Введите дату в формате гггг/мм/дд чч:мм:сс\n"
                + "чтобы не отображать записи, сделанные ранее этой даты";
        System.out.println(hint);

        Scanner sc = new Scanner(System.in);
        String strDate = sc.nextLine();

        try{
            Date date = Record.dateFormat.parse(strDate);

            ArrayList<Record> records = Repository.get().getFilteredRecords(date);
            for(Record rec : records)
                System.out.print("\n"+rec);

        } catch (ParseException e){
            System.out.println("\nНекорректно введена дата");
        }
    }

    private static void editRecord(){
        String hint = "Введите '-number номер_записи'\n"
                +"Либо '-name название_записи', которую хотите отредактировать\n";
        System.out.println(hint);

        Scanner sc = new Scanner(System.in);
        String[] command = sc.nextLine().split(" ");

        try{
            String editCase = command[0];
            Record record;

            if(editCase.equals(COMMAND_BY_NUMBER)){

                int number = Integer.parseInt(command[1]);
                record = Repository.get().getRecord(number);

            } else if (editCase.equals(COMMAND_BY_NAME)){

                String name = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                record = Repository.get().getRecord(name);
                if(record == null)
                    throw new NullPointerException();

            } else {
                throw new Exception();
            }

            System.out.println("Введите новое содержание");
            String content = sc.nextLine();

            Repository.get().editRecord(record, content);

        } catch (IndexOutOfBoundsException npe) {
            System.out.println("Отсутсвует запись с таким номером");
        } catch (NullPointerException npe) {
            System.out.println("Отсутсвует запись с таким названием");
        } catch (Exception e){
            System.out.println("Некорректная команда");
        }
    }

    private static void deleteRecord(){
        String hint = "Введите '-number номер_записи'\n"
                +"Либо '-name название_записи', которую хотите удалить\n";
        System.out.println(hint);

        Scanner sc = new Scanner(System.in);

        String[] command = sc.nextLine().split(" ");

        try{
            String deleteCase = command[0];

            if(deleteCase.equals(COMMAND_BY_NUMBER)){

                Repository.get().deleteRecordByNumber(Integer.parseInt(command[1]));

            } else if (deleteCase.equals(COMMAND_BY_NAME)){

                String name = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                Repository.get().deleteRecordByName(name);
            }
        } catch (Exception e){
            System.out.println("Некорректная команда");
        }
    }

    private static void writeNewRecord(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите название записи");
        String name = sc.nextLine();

        System.out.println("Введите текст записи");
        String content = sc.nextLine();

        Date date = new Date();

        Record record = new Record(name, content, date);

        Repository.get().writeNewRecord(record);
    }
}
