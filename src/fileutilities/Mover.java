package fileutilities;

import java.io.File;
import java.util.LinkedList;

//Данный класс используется для реализации копирования и перемещения объектов
public class Mover {

    public static void copy(File[] source, File target){

        //Проверяем тривиальные случаи
        if(source.length==0)return;
        if(!target.exists())return;

        //Список объектов, которые не удалось скопировать
        LinkedList<File> failedCopy=new LinkedList<>();

        //Список путей-источников
        LinkedList<File> s=new LinkedList<>();

        //Список путей-приемников
        LinkedList<File> t=new LinkedList<>();

        //Перебор путей в источнике
        for(File fTmp: source){
            
        }

    }


}
