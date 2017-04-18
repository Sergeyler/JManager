package fileutilities;

import java.io.File;
import java.util.LinkedList;
import javax.swing.*;

public class Renamer {

    //Метод возвращает null, если переименование не произведено. Или возвращает список (не нулевой длины) объектов, переименование которых было выполнено
    public static File[] renameFile(File[] f){
        if(f.length==0)return null;
        if(f.length==1){
            File fTmp=R1(f[0]);
            if(fTmp==null) return null;
            return new File[]{fTmp};
        }
        if(f.length>1)return R2(f);
        return null;
    }

    //Метод, предназначенный для переименования еденичных объектов
    private static File R1(File f){
        //Проверяем доступность элемента
        if(!f.exists()){
            JOptionPane.showMessageDialog(null, "<html>Объект "+f.getAbsolutePath()+" недоступен.<br>Возможно он был переимещен или переименован.", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        //Объявляем вспомогательные переменные
        File fTmp=null;
        String name=null;

        //Запрашиваем новое имя для объекта
        if(f.isFile())name=getName(f.getName(), "<html>Введите имя.<br>Обратите внимание, что в случае смены расширения файл может оказаться недоступным");
        if(f.isDirectory())name=getName(f.getName(), "Введите имя папки");
        if(name==null)return null;

        //Пробуем переименовать
        fTmp=new File((f.getParent()==null)?(f.toPath().getRoot().toString()):(f.getParent())+File.separator+name);
        boolean successfullRename=f.renameTo(fTmp);
        if(!successfullRename){
            JOptionPane.showMessageDialog(null, "<html>Не удалось переименовать папку.<br>Возможно она была удалена, либо перемещена. Либо у Вас нет прав на ее переименование", "Ошибка переименования", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        //Возвращаем результат - ссылку на переименованный объект
        return fTmp;

    }

    //Метод, предназначенный для переименования множества объектов
    private static File[] R2(File[] f){
        System.out.println("Данная функция еще не реализована");
        return null;
    }

    //Метод запрашивает имя папки или файла у пользователя. Возвращает null, если пользователь отказался от ввода
    private static String getName(String startName, String msg){
        String name="";
        boolean isFind;
        char[] disabledChars={'\\', '/', ':', '*', '?', '\"', '<', '>', '|'};
        while (true) {
            name=JOptionPane.showInputDialog(null, msg, startName);
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
    private static String getExtendFile(String nameFile){
        String extendFile="";
        int dotPos=nameFile.lastIndexOf(".");
        if((dotPos==(-1)) | (dotPos==0) | (dotPos==nameFile.length()))extendFile=""; else extendFile=nameFile.substring(dotPos+1);
        return extendFile.toLowerCase();
    }

    //Метод возвращает имя файла
    private static String getNameFile(String nameFile){
        String result=nameFile;
        int dotPos=nameFile.lastIndexOf(".");
        if(dotPos!=(-1) & dotPos!=0 & dotPos!=nameFile.length())result=nameFile.substring(0, dotPos);
        return result;
    }

}
