import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TicTacToeGui extends JFrame implements ActionListener {

    private int xScore, oScore, moveCounter;
    private boolean isPlayerOne;
    JDialog resultDialog;
    private JButton[][] board;
    private JLabel turnLabel, scoreLabel, resultLabel;
    public TicTacToeGui(){
        super("TicTacToe(Java)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(517,700);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CommonConstants.background_color);

        // init vars
        createResultDialog();
        board = new JButton[3][3];

        isPlayerOne = true;

        addGuiComponent();
    }

    private void addGuiComponent(){
        JLabel barLabel = new JLabel();
        barLabel.setOpaque(true);
        barLabel.setBackground(CommonConstants.bar_color);
        barLabel.setBounds(0,0,517,25);

        turnLabel = new JLabel(CommonConstants.X_Label);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Dialog",Font.PLAIN,40));
        turnLabel.setPreferredSize(new Dimension(100,turnLabel.getPreferredSize().height));
        turnLabel.setOpaque(true);
        turnLabel.setBackground(CommonConstants.X_color);
        turnLabel.setForeground(CommonConstants.board_color);
        turnLabel.setBounds(205,0,turnLabel.getPreferredSize().width,turnLabel.getPreferredSize().height);

        scoreLabel = new JLabel(CommonConstants.score_label);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Dialog",Font.PLAIN,40));
        scoreLabel.setForeground(CommonConstants.board_color);
        scoreLabel.setBounds(0, turnLabel.getY() + turnLabel.getPreferredSize().height + 25,500,
                scoreLabel.getPreferredSize().height);



        GridLayout gridLayout = new GridLayout(3,3);
        JPanel boardPanel = new JPanel(gridLayout);
        boardPanel.setBounds(0,
                scoreLabel.getY() + scoreLabel.getPreferredSize().height + 35,500,400);

        for (int i = 0;i<board.length;i++){
            for (int j = 0;j<board[i].length;j++){
                JButton button = new JButton();
                button.setFont(new Font("Dialog",Font.PLAIN,180));
//                button.setPreferredSize();
                button.setBackground(CommonConstants.background_color);
                button.addActionListener(this);
                button.setBorder(BorderFactory.createLineBorder(CommonConstants.board_color));

                // add button to board
                board[i][j] = button;
                boardPanel.add(board[i][j]);

            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Dialog",Font.PLAIN,24));
        resetButton.setBackground(CommonConstants.board_color);
        resetButton.addActionListener(this);
        resetButton.setBounds(215,600,
                resetButton.getPreferredSize().width,
                resetButton.getPreferredSize().height
        );
        resetButton.setFocusable(false);



        getContentPane().add(turnLabel);
        getContentPane().add(barLabel);
        getContentPane().add(scoreLabel);
        getContentPane().add(boardPanel);
        getContentPane().add(resetButton);
    }

    private void createResultDialog(){
        resultDialog = new JDialog();
        resultDialog.getContentPane().setBackground(CommonConstants.background_color);
        resultDialog.setResizable(false);
        resultDialog.setTitle("Result");
        resultDialog.setSize(200,150);
        resultDialog.setLocationRelativeTo(null);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(2,1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

        // result label.
        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Dialog",Font.PLAIN,18));
        resultLabel.setForeground(CommonConstants.board_color);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // restart button.
        JButton restartButton = new JButton("Play Again");
        restartButton.setBackground(CommonConstants.board_color);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(restartButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Reset") || command.equals("Play Again")){
            //reset the game.
            resetGame();

            // only reset the score when pressing reset.
            if (command.equals("Reset")){
                xScore = oScore = 0;
            }
            if (command.equals("Play Again")){
                resultDialog.setVisible(false);
            }
        }
        else{
            //player move
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("")){
                moveCounter++;

                if (isPlayerOne){
                    // player one(x player)
                    button.setText(CommonConstants.X_Label);
                    button.setFont(new Font("Dialog",Font.PLAIN,150));
                    button.setForeground(CommonConstants.X_color);

                    //update turn label.
                    turnLabel.setText(CommonConstants.O_Label);
                    turnLabel.setBackground(CommonConstants.O_color);
                    turnLabel.setFont(new Font("Dialog",Font.PLAIN,70));

                    // update turn.
                    isPlayerOne = false;
                }
                else {
                    // player one(x player)
                    button.setText(CommonConstants.O_Label);
                    button.setForeground(CommonConstants.O_color);
                    button.setFont(new Font("Dialog",Font.PLAIN,150));

                    //update turn label.
                    turnLabel.setText(CommonConstants.X_Label);
                    turnLabel.setBackground(CommonConstants.X_color);
                    turnLabel.setFont(new Font("Dialog",Font.PLAIN,70));

                    // update turn.
                    isPlayerOne = true;
                }

                // check win condition.
                if (isPlayerOne) {
                    // check to see if the winning move was by X.
                    checkOwin();
                }
                else{
                    // check to see if winning move was by O.
                    checkXwin();
                }

                // check for draw condition.
                checkDraw();

                // update score label.
                scoreLabel.setText("X: " + xScore + "| O: " + oScore);
            }

            repaint();
            revalidate();

        }
    }

    private void checkXwin(){
        String result = "X Wins";

        for (int row =0; row<board.length;row++){
            if (board[row][0].getText().equals("X") && board[row][1].getText().equals("X") && board[row][2].getText().equals("X")){
                resultLabel.setText(result);

                resultDialog.setVisible(true);

                // update score.
                xScore++;

            }
        }

        for (int col =0; col<board.length;col++){
            if (board[0][col].getText().equals("X") && board[1][col].getText().equals("X") && board[2][col].getText().equals("X")){
                resultLabel.setText(result);

                resultDialog.setVisible(true);

                // update score.
                xScore++;

            }
        }

        if (board[0][0].getText().equals("X") && board[1][1].getText().equals("X") && board[2][2].getText().equals("X")){
            resultLabel.setText(result);

            resultDialog.setVisible(true);

            // update score.
            xScore++;

        }

        if (board[2][0].getText().equals("X") && board[1][1].getText().equals("X") && board[0][2].getText().equals("X")){
            resultLabel.setText(result);

            resultDialog.setVisible(true);

            // update score.
            xScore++;

        }

    }

    private void checkOwin(){
        String result = "O Wins";

        for (int row =0; row<board.length;row++){
            if (board[row][0].getText().equals("O") && board[row][1].getText().equals("O") && board[row][2].getText().equals("O")){
                resultLabel.setText(result);

                resultDialog.setVisible(true);

                // update score.
                oScore++;

            }
        }

        for (int col =0; col<board.length;col++){
            if (board[0][col].getText().equals("O") && board[1][col].getText().equals("O") && board[2][col].getText().equals("O")){
                resultLabel.setText(result);

                resultDialog.setVisible(true);

                // update score.
                oScore++;

            }
        }

        if (board[0][0].getText().equals("O") && board[1][1].getText().equals("O") && board[2][2].getText().equals("O")){
            resultLabel.setText(result);

            resultDialog.setVisible(true);

            // update score.
            oScore++;

        }

        if (board[2][0].getText().equals("O") && board[1][1].getText().equals("O") && board[0][2].getText().equals("O")){
            resultLabel.setText(result);

            resultDialog.setVisible(true);

            // update score.
            oScore++;

        }


    }

    private void checkDraw(){
        if (moveCounter>=9){
            resultLabel.setText("Draw");
            resultDialog.setVisible(true);
        }

    }
    private void resetGame() {
        // reset player back to x_player
        isPlayerOne = true;
        turnLabel.setText(CommonConstants.X_Label);
        turnLabel.setBackground(CommonConstants.X_color);

        // reset score display
        scoreLabel.setText(CommonConstants.score_label);

        // rest move counter
        moveCounter = 0;

        // reset board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setText("");
            }
        }
    }
}
