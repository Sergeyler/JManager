package fileutilities;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

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

        Walker walker=new Walker() {

            @Override
            public FileVisitResult preVisitDirectory(Path f, BasicFileAttributes atr) throws IOException{
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path f, IOException ex) throws IOException{
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path f, BasicFileAttributes atr) throws IOException{
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path f, IOException exc) throws IOException{
                return FileVisitResult.CONTINUE;
            }

        };

        return null;

    }

    //Класс необходим для реализации обхода и последовательного удаления объектов
    private static abstract class Walker extends SimpleFileVisitor<Path>{}

}

//Приемник = new File(targetFolder.getAbsolutePath()+File.separator+fTmp.getAbsolutePath().substring(pos));