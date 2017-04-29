package fileutilities;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.*;
import javax.swing.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.LinkedList;

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

    //Список полей, необходимых для построения окна копирования
    private static JFrame copyFrame=null;
    private static final JLabel nameCopyFile=new JLabel("");
    private static final JProgressBar progressBar=new JProgressBar();
    private static String titleFrame="";
    private static String labelPrefix="";

    //Список полей, необходимых для отображения диалога конфликта файлов
    private static JDialog conflictDialog=null;
    private static JLabel nameConflictFile=new JLabel("Файл: ");
    private static JComboBox<String> optForAction=new JComboBox<>(new String[]{"Не копировать этот файл", "Заменить файл в папке назначения", "Скопировать, но сохранить оба файла"});
    private static JCheckBox defaultActBox=new JCheckBox("Всегда выполнять выбранное действие", true);
    private static JButton okBtn=new JButton("OK");

    //поля, необходимые непосредственно для копирования файлов и папок
    private LinkedList<File> s=new LinkedList<>();    //Список источников
    private LinkedList<File> t=new LinkedList<>();    //Список приемников

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

        //Создаем диалог копирования
        if(copyFrame==null){
            int frameWidth=600;
            int frameHeight=100;
            copyFrame=new JFrame(titleFrame);
            copyFrame.setSize(frameWidth, frameHeight);
            copyFrame.setResizable(false);
            copyFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-frameWidth/2;
            int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-frameHeight/2;
            copyFrame.setLocation(xPos, yPos);
            Box p0=Box.createVerticalBox();
            Box p1=Box.createHorizontalBox();
            p0.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            p1.add(nameCopyFile);
            p1.add(Box.createHorizontalGlue());
            p0.add(p1);
            p0.add(Box.createVerticalGlue());
            p0.add(progressBar);
            progressBar.setPreferredSize(new Dimension(frameWidth-20, 25));
            copyFrame.add(p0);
        }
        copyFrame.setTitle(titleFrame);
        nameCopyFile.setText(labelPrefix);        //Предельная длина выводимого пути - 139 символов
        progressBar.setMinimum(0);
        progressBar.setValue(0);
        copyFrame.setVisible(true);

        //Создаем диалог, который будет отображаться в случае конфликта имен файлов
        if(conflictDialog==null){
            int dialogWidth=300;
            int dialogHeight=200;
            conflictDialog=new JDialog(copyFrame, true);
            conflictDialog.setTitle("Внимание");
            conflictDialog.setSize(new Dimension(dialogWidth, dialogHeight));
            conflictDialog.setResizable(false);
            conflictDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-dialogWidth/2;
            int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-dialogHeight/2;
            conflictDialog.setLocation(xPos, yPos);

            Box p0=Box.createVerticalBox();
            p0.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            Box p1=Box.createHorizontalBox();
            p1.add(nameConflictFile);
            p1.add(Box.createHorizontalGlue());
            p0.add(p1);

            p0.add(Box.createVerticalGlue());

            Box p2=Box.createHorizontalBox();
            p2.add(new JLabel("Выберите действие:"));
            p2.add(Box.createHorizontalGlue());
            p0.add(p2);

            Box p3=Box.createHorizontalBox();
            optForAction.setMaximumSize(new Dimension(dialogWidth-20, 20));
            p3.add(optForAction);
            p0.add(p3);

            p0.add(Box.createVerticalStrut(10));

            Box p4=Box.createHorizontalBox();
            p4.add(defaultActBox);
            p4.add(Box.createHorizontalGlue());
            p0.add(p4);

            Box p5=Box.createHorizontalBox();
            p5.add(Box.createHorizontalGlue());
            p5.add(okBtn);
            p0.add(p5);

            conflictDialog.setContentPane(p0);
            okBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    conflictDialog.setVisible(false);
                }
            });
        }

    }

    @Override
    protected Void doInBackground() throws Exception {

        //Первый этап - формироваиние текущего списка файлов из target. Это необходимо, чтобы после работы процедуры копирования корректно отобразить в панели-приемнике список изменений
        LinkedList<File> al0=new LinkedList<>();
        al0.addAll(Arrays.asList(targetFolder.listFiles()));
        


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