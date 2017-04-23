package fileutilities;

import java.io.File;
import java.util.LinkedList;

//Данный класс используется для реализации копирования и перемещения объектов
public class Mover {

    //Метод возвращает список удачно скопированных объектов. Если ничего скопировать не удалось - возвращает null
    public static File[] copy(File[] source, File target){

        //Проверяем тривиальные случаи
        if(source.length==0)return null;
        if(!target.exists())return null;

        //Список объектов, которые не удалось скопировать
        LinkedList<File> failedList=new LinkedList<>();

        //Список объектов, которые удалось скопировать (в этом списке содержатся объекты, после копирования уже находящиеся в target)
        LinkedList<File> successList=new LinkedList<>();

        //Список путей-источников
        LinkedList<File> s=new LinkedList<>();

        //Список путей-приемников
        LinkedList<File> t=new LinkedList<>();

        //Перебор путей в источнике
        for(File fTmp: source){

        }

        //Возвращаем скопированных объектов
        if(successList.isEmpty())return null;
        File[] tFile=new File[successList.size()];
        tFile=successList.toArray(tFile);
        return tFile;

    }


}
