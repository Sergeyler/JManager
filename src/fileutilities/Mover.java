package fileutilities;

import java.awt.Toolkit;
import java.io.*;
import java.nio.*;
import javax.swing.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import javax.swing.SwingWorker;

//Данный класс используется для реализации копирования и перемещения объектов
public class Mover extends SwingWorker<Void, Void>{

    //Перечень значений опций копирования
    public static final int COPY_OPT=1;
    public static final int MOVE_OPT=2;

    //Перечень основных параметров работы класса
    private final File sourceFolder;
    private final File[] source;
    private final File targetFolder;
    private final int copyOption;

    //Элементы интерфейса для отображения процесса копирования
    private static JFrame copyFrame=null;
    private static JLabel label=null;
    private static JProgressBar progressBar=null;
    private static String titleFrame="";
    private static String labelPrefix="";

    public Mover (File sourceFolder, File[] source, File targetFolder, int copyOption){
        this.sourceFolder=sourceFolder;
        this.source=source;
        this.targetFolder=targetFolder;
        this.copyOption=copyOption;

        //Создаем окно копирования
        if(copyOption==COPY_OPT){
            titleFrame="Копирование";
            labelPrefix="Копирую: ";
        }
        if(copyOption==MOVE_OPT){
            titleFrame="Перемещение";
            labelPrefix="Перемещаю: ";
        }
        if(copyFrame==null){
            int frameWidth=600;
            int frameHeight=200;
            copyFrame=new JFrame(titleFrame);
            copyFrame.setSize(frameWidth, frameHeight);
            copyFrame.setResizable(false);
            int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-frameWidth/2;
            int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-frameHeight/2;
            copyFrame.setLocation(xPos, yPos);
            
        }
        label.setText(labelPrefix);
        progressBar.setValue(0);
        copyFrame.setVisible(true);
    }

    @Override
    protected Void doInBackground() throws Exception {
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

        for(int i=0;i<101;i++){
            Thread.sleep(100);
        }

        return null;
    }

    //Класс необходим для реализации обхода и последовательного удаления объектов
    private static abstract class Walker extends SimpleFileVisitor<Path>{}

}

//Приемник = new File(targetFolder.getAbsolutePath()+File.separator+fTmp.getAbsolutePath().substring(pos));