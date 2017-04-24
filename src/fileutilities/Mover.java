package fileutilities;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import javax.swing.*;
import java.util.LinkedList;

//Данный класс используется для реализации копирования и перемещения объектов
public class Mover {

    public static final int COPY_OPT=1;
    public static final int MOVE_OPT=2;

    private static final LinkedList<File> s=new LinkedList<>();    //Список путей-источников
    private static final LinkedList<File> t=new LinkedList<>();    //Список путей-приемников
    private static final LinkedList<File> err=new LinkedList<>();  //Список объектов, которые не удалось копировать/переместить

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
            public FileVisitResult postVisitDirectory(Path f, IOException ex){
                s.add(f.toFile());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path f, BasicFileAttributes atr) throws IOException{
                s.add(f.toFile());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path f, IOException exc){
                err.add(f.toFile());
                return FileVisitResult.CONTINUE;
            }

        };

        //Формируем список источников
        for(File fTmp: source){
            if(!fTmp.exists())continue;
            if(fTmp.isFile())s.add(fTmp);
            if(fTmp.isDirectory()){
                try {
                    Files.walkFileTree(fTmp.toPath(), walker);
                } catch (IOException ex) {}
            }
        }

        //Формируем список приемников
        int pos=sourceFolder.getAbsolutePath().length();
        for(File fTmp: s){
            t.add(new File(targetFolder.getAbsolutePath()+File.separator+fTmp.getAbsolutePath().substring(pos)));
        }

        //Формирую интерфейс копирования
        int copyDialogWidth=500;
        int copyDialogHeight=100;
        JDialog copyDialog=new JDialog();
        copyDialog.setSize(copyDialogWidth, copyDialogHeight);
        copyDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-copyDialogWidth/2;
        int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-copyDialogHeight/2;
        copyDialog.setLocation(xPos, yPos);
        copyDialog.setModal(true);

        JPanel dialogPane=new JPanel();
        dialogPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 8, 5));
        dialogPane.setLayout(new BoxLayout(dialogPane, BoxLayout.Y_AXIS));
        JPanel lbPane=new JPanel();
        lbPane.setLayout(new BoxLayout(lbPane, BoxLayout.X_AXIS));

        JLabel lb=new JLabel("<html>Копирую: Z:\\8. Врач Ткаченко Наталья Леонидовна\\! ОТ СЕКРЕТАРЯ·٠•\\06.03.2017\\запрос по формам ВР-8доп ВР-5МЛУ\\бланки  форм 8-доп и 5МЛУ\\СПИСКИ больных2014-2015гг для ВР-8доп</html>");
        JProgressBar pb=new JProgressBar(0, s.size());
        lbPane.add(lb);
        lbPane.add(Box.createHorizontalGlue());
        pb.setPreferredSize(new Dimension(copyDialogWidth-40, 25));
        pb.setValue(0);

        dialogPane.add(lbPane);
        dialogPane.add(Box.createVerticalGlue());
        dialogPane.add(pb);

        copyDialog.setContentPane(dialogPane);
        copyDialog.setVisible(true);

        //Приступаю к копированию файлов

        //Возвращаем результат
        return null;

    }

    //Класс необходим для реализации обхода и последовательного удаления объектов
    private static abstract class Walker extends SimpleFileVisitor<Path>{}

}
