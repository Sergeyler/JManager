package fileutilities;

import java.io.File;
import java.util.LinkedList;
import javax.swing.*;

public class Renamer {

    //Метод влзвращает null, если переименование не произведено. Или возвращает список (не нулевой длины) объектов, переименование которых было выполнено
    public static File[] renameFile(File[] f){
        if(f.length==0)return null;                    //Если передан пустой массив
        if(f.length==1)return new File[]{R1(f[0])};    //Если передан массив из одного элемента
        if(f.length>1)return R2(f);                    //Если предполагается множественное переименование
        return null;
    }

    //Метод, предназначенный для переименования еденичных объектов
    private static File R1(File f){
        return null;
    }

    //Метод, предназначенный для переименования множества объектов
    private static File[] R2(File[] f){
        return null;
    }

    //Метод запрашивает имя папки или файла у пользователя. Возвращает null, если пользователь отказался от ввода
    private static String getName(String startName){
        String name="";
        boolean isFind;
        char[] disabledChars={'\\', '/', ':', '*', '?', '\"', '<', '>', '|'};
        while (true) {
            name=JOptionPane.showInputDialog(null, "Введите имя", startName);
            if(name==null)return null;
            name=name.trim();
            if(name.equals(""))return null;
            //Проверяем наличие недопустимых символов
            isFind=false;
            for(char c: name.toCharArray())
                for(char d: disabledChars){
                    if(c==d)isFind=true;
                }
            if(isFind){
                JOptionPane.showMessageDialog(null, "Введенное имя содержит недопустимые символы. Введите другое имя", "Ошибка", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            break;
        }
        return name;
    }

    //Метод возвращает расширение файла nameFile. Если расширения нет - возвращает пустую строку.
    //Расширением считается последовательность символов после последней точки.
    private static String getExtend(String nameFile){
        String extendFile="";
        int dotPos=nameFile.lastIndexOf(".");
        if((dotPos==(-1)) | (dotPos==0) | (dotPos==nameFile.length()))extendFile=""; else extendFile=nameFile.substring(dotPos+1);
        return extendFile.toLowerCase();
    }

}
