package fileutilities;

import java.io.*;
import javax.swing.JOptionPane;

//Класс, необходимый для создания папок
public class Creater {

    //В метод передается текущий каталог той панели, в которой будем создавать папку
    public static void createFolder(File f){
        //Папка f должна существовать и быть доступной для записи
        if(!f.exists()){
            JOptionPane.showMessageDialog(null, "<html>Папка:<br>"+f.getAbsolutePath()+" не доступна. Проверьте располажение папки и права доступа к ней", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showInputDialog(null, "Введине имя для новой папки", "Введите имя", JOptionPane.QUESTION_MESSAGE);
    }

}
