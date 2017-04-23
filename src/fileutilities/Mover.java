package fileutilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;

//Данный класс используется для реализации копирования и перемещения объектов
public class Mover {

    public static final int COPY_OPT=1;
    public static final int MOVE_OPT=2;

    //Метод возвращает список удачно скопированных объектов, находящихся на момент завершения метода в папке target и в ее подпапках. Если ничего скопировать не удалось - возвращает null
    //Передача папки, в которой находятся объекты source необходима для упрощения кода метода
    //Параметр opt определяет тип операции: копирование (opt=COPY_OPT) или перемещение (opt=MOVE_OPT)
    public static File[] copy(File sourceFolder, File[] source, File targetFolder, int opt){

        //Проверяем тривиальные случаи
        if(source.length==0)return null;
        if(!targetFolder.exists())return null;
        if(!sourceFolder.exists())return null;

        //Список объектов, которые не удалось скопировать
        LinkedList<File> failedList=new LinkedList<>();

        //Список объектов, которые удалось скопировать (в этом списке содержатся объекты, после копирования уже находящиеся в target)
        LinkedList<File> successList=new LinkedList<>();

        //Список путей-источников
        LinkedList<File> s=new LinkedList<>();

        //Список путей-приемников
        LinkedList<File> t=new LinkedList<>();

        //Объект, необходимый для формирования списка объектов для копирования
        Walker walker=new Walker(){

            @Override
            public FileVisitResult visitFile(Path f, BasicFileAttributes atr) throws IOException{
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path f, IOException ex){
                return FileVisitResult.CONTINUE;
            }

        };

        //Перебор путей в источнике
        for(File fTmp: source){

        }

        //Возвращаем скопированных объектов
        if(successList.isEmpty())return null;
        File[] tFile=new File[successList.size()];
        tFile=successList.toArray(tFile);
        return tFile;

    }

    //Класс, предназначенный для обхода дерева каталогов.
    //В данном случае в качестве упражнения применено его представление в качестве абстрактного класса с последующей релизацией в теле метода copy
    private static abstract class Walker extends SimpleFileVisitor<Path>{}


}
