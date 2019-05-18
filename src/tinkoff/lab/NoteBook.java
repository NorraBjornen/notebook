package tinkoff.lab;

public class NoteBook {

    public static void main(String[] args){
        try{
            String cmd = args[0];
            CommandHandler.handleCommand(cmd);
        } catch (Exception e){
            showHelp();
        }
    }

    private static void showHelp(){
        String helpText = "Укажите в качестве ключа одну их следующих команд:\n\n"
                            + "\t-new\t - добавление новой записи\n"
                            + "\t-show\t - отображение всех записей\n"
                            + "\t-del\t - удаление записи\n"
                            + "\t-edit\t - редактирование записи\n"
                            + "\t-filter\t - вывод отфильтрованных по дате записей\n\n"
                            + "Подробную информацию о работе программы Вы найдёте в readme.txt";
        System.out.println(helpText);
    }
}
