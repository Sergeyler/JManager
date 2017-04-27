package fileutilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

    //Список объектов из source, которые после выполнения процедуры копирования/перемещения надо сделать выделенными в папке target
    private File[] allocateList;

    //Список элементов пользовательского интерфейса, необходимых для работы процедуры

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

        //Создаем элементы интерфейса окна копирования
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
            int frameHeight=150;
            copyFrame=new JFrame(titleFrame);
            copyFrame.setLayout(new BorderLayout());
            copyFrame.setSize(frameWidth, frameHeight);
            copyFrame.setResizable(false);
            copyFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-frameWidth/2;
            int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-frameHeight/2;
            copyFrame.setLocation(xPos, yPos);

            JPanel p1=new JPanel();
            p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
            p1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            label=new JLabel("");
            p1.add(label);
            p1.add(Box.createHorizontalGlue());

            JPanel p0=new JPanel();
            p0.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));
            p0.add(p1);
            p0.add(Box.createVerticalStrut(5));
            progressBar=new JProgressBar();
            progressBar.setPreferredSize(new Dimension(frameWidth-200, 50));
            p0.add(progressBar);
            copyFrame.add(p0, SwingConstants.CENTER);
        }
        copyFrame.setTitle(titleFrame);
        label.setText(labelPrefix);
        progressBar.setMinimum(0);
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

        progressBar.setMaximum(100);

        for(int i=0;i<101;i++){
            progressBar.setValue(i);
            Thread.sleep(100);
        }

        copyFrame.setVisible(false);
        firePropertyChange("endMover", null, null);

        return null;
    }

    //Метод возвращает ссылку на изначально переданный список объектов
    public File[] getSourceAllocateList(){
        return source;
    }

    //Метод возвращает список элементов, которые должны быть выделены в папке target после завершения работы процедуры копирования/перемещения
    public File[] getTargetAllocateList(){
        return allocateList;
    }

    //Класс необходим для реализации обхода дерева папок
    private static abstract class Walker extends SimpleFileVisitor<Path>{}

}

//Приемник = new File(targetFolder.getAbsolutePath()+File.separator+fTmp.getAbsolutePath().substring(pos));