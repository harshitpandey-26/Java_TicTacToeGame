import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrontPage extends JFrame implements ActionListener {
    private JButton play;
    ImageIcon bgImage;
    public FrontPage(){
        super("TicTacToe(Java)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(555,700);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#4D4E54"));

        bgImage = new ImageIcon("TicTacToe.png");
        Image img = bgImage.getImage();
        Image new1 = img.getScaledInstance(517,300,Image.SCALE_SMOOTH);
        bgImage = new ImageIcon(new1);

        JLabel head = new JLabel("<html>Welcome to the <br> Tic Tac Toe Game</html>");
        head.setFont(new Font("Dialog",Font.BOLD,60));
        head.setForeground(Color.WHITE);
        head.setBounds(10,20,530,150);

        JLabel imgLabel = new JLabel();
        imgLabel.setIcon(bgImage);
        imgLabel.setBounds(10,200,540,300);

        play = new JButton("Lets Play");
        play.setFont(new Font("Dialog",Font.BOLD,40));
        play.setBounds(180,550,play.getPreferredSize().width,play.getPreferredSize().height);
        play.setBackground(Color.WHITE);
        play.setForeground((Color.decode("#4D4E54")));
        play.setFocusable(false);
        play.addActionListener(this);

        add(head);
        add(imgLabel);
        add(play);

        setVisible(true);

    }

    public static void main(String[] args) {SwingUtilities.invokeLater(FrontPage::new);}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==play){
            dispose();
            new TicTacToeGui().setVisible(true);
        }
    }
}
