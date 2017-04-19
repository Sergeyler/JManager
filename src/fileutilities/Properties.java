package fileutilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//Класс, необходимый для получения свойств объектов
public class Properties {

    public static void showProperties(File[] f){

        //Действие, выполняемое когда массив объектов пуст
        if(f.length==0)return;

        //Действие, выполняемое, когда массив содержит один элемент
        if(f.length==1){
            Object a=null;
            try {
                a=Files.getAttribute(f[0].toPath(), "creationTime");
            } catch (IOException ex) {
                System.out.println("Ошибка при получении атрибутов файла");
            }
            System.out.println(a);

        }

        //Действие, выполняемое, когда массив содержит несколько элементов
        if(f.length>1){
            System.out.println("Данное действие еще не реализовано");
        }

    }

    //Метод возвращает расширение файла nameFile. Если расширения нет - возвращает пустую строку.
    //Расширением считается последовательность символов после последней точки.
    private static String getExtendFile(String nameFile){
        String extendFile="";
        int dotPos=nameFile.lastIndexOf(".");
        if((dotPos==(-1)) | (dotPos==0) | (dotPos==nameFile.length()))extendFile=""; else extendFile=nameFile.substring(dotPos+1);
        return extendFile.toLowerCase();
    }

}
