package swap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class gameWindow implements ActionListener {
	public static JFrame frame = new JFrame("Game title");
	
	private static JPanel headerP = new JPanel();
	public static JPanel bodyP = new JPanel(new GridLayout(gameRule.gameH,gameRule.gameW, 0, 0));
	private static JPanel footerP = new JPanel();
	
	private static JLabel label1 = new JLabel("Score: ");
	public static JLabel label2 = new JLabel("0");
	public static JLabel label3 = new JLabel("        ");
	
	public static JButton rnd = new JButton("Random");
	
	public gameWindow(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(createMainPanel());
		gameRule.setWindowSize();
		frame.setSize(gameRule.windowSizeW, gameRule.windowSizeH);
		frame.setResizable(false);
		frame.setVisible(true);
		
		//собираем свой меню бар
		gameMenuBar MB = new gameMenuBar();
		MB.setMB(frame);
	}
	
	private JPanel createMainPanel(){
		JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // score
        headerP.add(label1);
        headerP.add(label2);
        
        headerP.add(Box.createRigidArea(new Dimension(gameRule.windowSizeW/4,0)));
        // you win
        headerP.add(label3);       
        
        gameSave.createSF();
        //gameRule.createButtons();
		
		// кнопка reset
		JButton rst = new JButton("Reset");
		rst.addActionListener((ActionListener) this);
		footerP.add(rst);
		
		// кнопка random
		rnd.addActionListener((ActionListener) this);
		footerP.add(rnd);
		
		// кнопка вин
		if (gameRule.DEBUG){
			//instawin
			JButton ww = new JButton("win");
			ww.addActionListener((ActionListener) this);
			footerP.add(ww);
		}
		
		
		//обавляем панели
		panel.add(headerP, BorderLayout.NORTH);
		panel.add(bodyP, BorderLayout.CENTER);
		panel.add(footerP, BorderLayout.SOUTH);

        return panel;
	}
	
	public static void main(String args[]) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new gameWindow();
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

	public void actionPerformed(ActionEvent a) {
		JButton button = (JButton) a.getSource();
		if (button.getText() == "Reset"){
			gameRule.clearField();
			if(gameRule.mode.equals("Random Lights") || gameRule.mode.equals("3 States Random Lights")){
				gameRule.setRandomButtons();
			}
		}else if (button.getText() == "win"){
			gameRule.instawin();
		}else if (button.getText() == "Random"){
			gameRule.getRandomButtons();
		}
		gameRule.wincheck();
	}
}