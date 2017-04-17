package fileutilities;

import java.io.File;
import java.util.LinkedList;
import javax.swing.JOptionPane;

//Класс предназначен для переименования объектов и групп объектов
public class Renamer {

    //Важно! Метод возвращает null, если не удалось переименовать ни один объект из списка f
    //Если переименование было выполнено, пусть даже частично, то метод возвращает массив переименованных объектов.
    public static File[] renameFile(File[] f){
        //Пул переименованных объектов
        LinkedList<File> pool=new LinkedList<>();

        //Если передан массив нулевой длины
        if(f.length==0)return null;

        //Сперва обрабатываем случай, когда пользователь пытается переименовать еденичный объект
        //Вспомогательные переменные, учитываемые при переименовании единичных объектов
        String startName="";
        String startExtend="";
        if(f.length==1){
            if(!f[0].exists()){
                JOptionPane.showMessageDialog(null, "<html>Объект:"+f[0].getAbsolutePath()+" недоступен.<br>Проверьте расположение объекта.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            startName=f[0].getName();
            if(f[0].isFile()){
                startExtend=getExtend(startName);
            }
        }

        //Запрашиваем новое имя
        //Сперва создаем вспомогаиельные переменные
        String name=null;
        char[] disabledChars={'\\', '/', ':', '*', '?', '\"', '<', '>', '|'};
        boolean isFind=false;
        while (true) {
            name=JOptionPane.showInputDialog(null, "Введите новое имя", startName);

            

            System.out.println(name);

            break;
        }

        File[] poolArray=new File[pool.size()];
        poolArray=pool.toArray(poolArray);
        return (poolArray.length==0?null:poolArray);
    }

    private String showInputDialog(String startText){
        return null;
    }

    //Метод возвращает расширение файла nameFile. Если расширения нет - возвращает пустую строку.
    //Расширением считается последовательность символов после последней точки.
    private static String getExtend(String nameFile){
        String extendFile="";
        int dotPos=nameFile.lastIndexOf(".");
        if((dotPos==(-1)) | (dotPos==0) | (dotPos==nameFile.length()))extendFile=""; else extendFile=nameFile.substring(dotPos+1);
        return extendFile;
    }

}
