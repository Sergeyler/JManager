package jmanager;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MainClass {

    private final JFrame frame;

    private final int W=1400;
    private final int H=850;

    private final JPanel northPanel=new JPanel();

    private final JButton properties_left=new JButton(new ImageIcon("icons\\properties_small.png"));
    private final JButton allot_left=new JButton(new ImageIcon("icons\\allot_small.png"));
    private final JButton rename_left=new JButton(new ImageIcon("icons\\rename_small.png"));
    private final JButton create_left=new JButton(new ImageIcon("icons\\create_small.png"));
    private final JButton del_left=new JButton(new ImageIcon("icons\\delete_small.png"));
    private final JButton move_left=new JButton(new ImageIcon("icons\\move_small.png"));
    private final JButton copy_left=new JButton(new ImageIcon("icons\\copy_small.png"));

    private final JButton properties_right=new JButton(new ImageIcon("icons\\properties_small.png"));
    private final JButton allot_right=new JButton(new ImageIcon("icons\\allot_small.png"));
    private final JButton rename_right=new JButton(new ImageIcon("icons\\rename_small.png"));
    private final JButton create_right=new JButton(new ImageIcon("icons\\create_small.png"));
    private final JButton del_right=new JButton(new ImageIcon("icons\\delete_small.png"));
    private final JButton move_right=new JButton(new ImageIcon("icons\\move_small.png"));
    private final JButton copy_right=new JButton(new ImageIcon("icons\\copy_small.png"));

    private final JPopupMenu pm=new JPopupMenu();
    private final JMenuItem propertiesItem=new JMenuItem("Свойства");
    private final JMenuItem allotItem=new JMenuItem("Выделить все");
    private final JMenuItem renameItem=new JMenuItem("Переименовать");
    private final JMenuItem createItem=new JMenuItem("Создать папку");
    private final JMenuItem delItem=new JMenuItem("Удалить");
    private final JMenuItem moveItem=new JMenuItem("Переместить");
    private final JMenuItem copyItem=new JMenuItem("Копировать");

    private final JPanel centerPanel=new JPanel();
    private final FilePane leftFilePane;
    private final FilePane rightFileJPane;

    public MainClass() {
        //Предварительные действия по формированию окна программы
//        String laf=UIManager.getSystemLookAndFeelClassName();
//        try {
//            UIManager.setLookAndFeel(laf);
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
//            JOptionPane.showMessageDialog(null, "Возникла ошибка при попытке переключить стиль интерфейса. Работа программы будет прекращена", "Ошибка", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
//        }
        frame=new JFrame("JManager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(W, H);
        frame.setIconImage(new ImageIcon("icons\\logo.png").getImage());
        frame.setResizable(false);
        frame.setLayout(new BorderLayout(10, 10));
        int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-W/2;
        int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-H/2;
        frame.setLocation(xPos, yPos);

        //Формируем северную панель
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //Формируем левую последовательность кнопок
        northPanel.add(properties_left);
        properties_left.setActionCommand("properties_left");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(allot_left);
        allot_left.setActionCommand("allot_left");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(rename_left);
        rename_left.setActionCommand("rename_left");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(create_left);
        create_left.setActionCommand("create_left");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(del_left);
        del_left.setActionCommand("del_left");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(move_left);
        move_left.setActionCommand("move_left");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(copy_left);
        copy_left.setActionCommand("copy_left");

        //Добавляем заполнитель
        northPanel.add(Box.createHorizontalGlue());

        //Формируем правую последовательность кнопок
        northPanel.add(properties_right);
        properties_right.setActionCommand("properties_right");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(allot_right);
        allot_right.setActionCommand("allot_right");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(rename_right);
        rename_right.setActionCommand("rename_right");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(create_right);
        create_right.setActionCommand("create_right");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(del_right);
        del_right.setActionCommand("del_right");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(move_right);
        move_right.setActionCommand("move_right");
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(copy_right);
        copy_right.setActionCommand("copy_right");

        //Формируем поведение кнопок основных действий при наведении на них курсора. Для левой панели...
        properties_left.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                properties_left.setText("Свойства");
            }

            @Override
            public void mouseExited(MouseEvent e){
                properties_left.setText("");
            }
        });
        allot_left.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                allot_left.setText("Выделить все");
            }

            @Override
            public void mouseExited(MouseEvent e){
                allot_left.setText("");
            }
        });
        rename_left.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                rename_left.setText("Переименовать");
            }

            @Override
            public void mouseExited(MouseEvent e){
                rename_left.setText("");
            }
        });
        create_left.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                create_left.setText("Создать папку");
            }

            @Override
            public void mouseExited(MouseEvent e){
                create_left.setText("");
            }
        });
        del_left.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                del_left.setText("Удалить");
            }

            @Override
            public void mouseExited(MouseEvent e){
                del_left.setText("");
            }
        });
        move_left.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                move_left.setText("Переместить вправо");
            }

            @Override
            public void mouseExited(MouseEvent e){
                move_left.setText("");
            }
        });
        copy_left.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                copy_left.setText("Копировать вправо");
            }

            @Override
            public void mouseExited(MouseEvent e){
                copy_left.setText("");
            }
        });

        //...и для правой
        properties_right.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                properties_right.setText("Свойства");
            }

            @Override
            public void mouseExited(MouseEvent e){
                properties_right.setText("");
            }
        });
        allot_right.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                allot_right.setText("Выделить все");
            }

            @Override
            public void mouseExited(MouseEvent e){
                allot_right.setText("");
            }
        });
        rename_right.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                rename_right.setText("Переименовать");
            }

            @Override
            public void mouseExited(MouseEvent e){
                rename_right.setText("");
            }
        });
        create_right.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                create_right.setText("Создать папку");
            }

            @Override
            public void mouseExited(MouseEvent e){
                create_right.setText("");
            }
        });
        del_right.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                del_right.setText("Удалить");
            }

            @Override
            public void mouseExited(MouseEvent e){
                del_right.setText("");
            }
        });
        move_right.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                move_right.setText("Переместить влево");
            }

            @Override
            public void mouseExited(MouseEvent e){
                move_right.setText("");
            }
        });
        copy_right.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                copy_right.setText("Копировать влево");
            }

            @Override
            public void mouseExited(MouseEvent e){
                copy_right.setText("");
            }
        });

        //Создаем всплывающее меню для панелей
        pm.add(copyItem);
        copyItem.setActionCommand("copy_");
        pm.add(moveItem);
        moveItem.setActionCommand("move_");
        pm.add(delItem);
        delItem.setActionCommand("del_");
        pm.addSeparator();
        pm.add(allotItem);
        allotItem.setActionCommand("allot_");
        pm.add(renameItem);
        renameItem.setActionCommand("rename_");
        pm.add(createItem);
        createItem.setActionCommand("create_");
        pm.addSeparator();
        pm.add(propertiesItem);
        propertiesItem.setActionCommand("properties_");

        //Формируем центральную панель
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        leftFilePane=new FilePane((int)(W/2), H);
        rightFileJPane=new FilePane((int)(W/2), H);
        leftFilePane.setPopupMenu(pm, "left");
        rightFileJPane.setPopupMenu(pm, "right");
        centerPanel.add(leftFilePane.getContentPanel());
        centerPanel.add(rightFileJPane.getContentPanel());
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        //Прописываем обработчик для компонентов
        copyItem.addActionListener(a);
        moveItem.addActionListener(a);
        delItem.addActionListener(a);
        allotItem.addActionListener(a);
        renameItem.addActionListener(a);
        createItem.addActionListener(a);
        propertiesItem.addActionListener(a);

        properties_left.addActionListener(a);
        allot_left.addActionListener(a);
        rename_left.addActionListener(a);
        create_left.addActionListener(a);
        del_left.addActionListener(a);
        move_left.addActionListener(a);
        copy_left.addActionListener(a);

        properties_right.addActionListener(a);
        allot_right.addActionListener(a);
        rename_right.addActionListener(a);
        create_right.addActionListener(a);
        del_right.addActionListener(a);
        move_right.addActionListener(a);
        copy_right.addActionListener(a);

        //Выводим главное окно на экран
        frame.setVisible(true);
        
    }

    //Обработчик событий
    private final ActionListener a=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }

    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{MainClass m=new MainClass();});
    }

}
