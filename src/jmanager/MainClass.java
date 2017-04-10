package jmanager;

import java.awt.BorderLayout;
import java.awt.Toolkit;
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

    private final JPanel centerPanel=new JPanel();
    private final JPanel left;
    private final JPanel right;

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

        northPanel.add(properties_left);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(allot_left);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(rename_left);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(create_left);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(del_left);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(move_left);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(copy_left);

        northPanel.add(Box.createHorizontalGlue());

        northPanel.add(properties_right);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(allot_right);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(rename_right);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(create_right);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(del_right);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(move_right);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(copy_right);

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
                allot_left.setText("Выделить все/Снять выделение");
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
                allot_right.setText("Выделить все/Снять выделение");
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

        //Формируем центральную панель
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        left=new FilePane((int)(W/2), H).getContentPanel();
        right=new FilePane((int)(W/2), H).getContentPanel();
        centerPanel.add(left);
        centerPanel.add(right);

        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{MainClass m=new MainClass();});
    }

}
