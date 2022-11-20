package swap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;

public class gameMenuBar implements ActionListener{
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFile = new JMenu("File");
	private JMenuItem menuItemOptions = new JMenuItem("Options");
	private JMenuItem menuItemAbout = new JMenuItem("About");
	
	/* правильный вызов с конструктором:
	 * gameMenuBar MB = new gameMenuBar();
	 * MB.setMB(frame);
	 */
	gameMenuBar(){
		menuItemOptions.setActionCommand("Options");
		menuItemOptions.addActionListener((ActionListener) this);
		menuFile.add(menuItemOptions);
		menuItemAbout.setActionCommand("About");
		menuItemAbout.addActionListener((ActionListener) this);
		menuFile.add(menuItemAbout);
		menuBar.add(menuFile);
	}
	
	public void setMB(JFrame frame){
		frame.setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent a) {
		if(a.getActionCommand() == "Options"){
			String[] optionFields1 = {"2x2", "3x3", "4x4", "5x5", "Other"};
			JComboBox<String> cb1 = new JComboBox<String>(optionFields1);
			if(gameRule.gameW == gameRule.gameH && gameRule.gameW < 6 && gameRule.gameW > 1){cb1.setSelectedIndex(gameRule.gameW-2);}
			else{cb1.setSelectedIndex(4);}
			cb1.setBounds(50,50,90,20);
			NumberFormat format = NumberFormat.getIntegerInstance();
			NumberFormatter nf = new NumberFormatter(format);
			nf.setValueClass(Integer.class);
			nf.setAllowsInvalid(false);
			nf.setMinimum(1);
			nf.setMaximum(100);
			JFormattedTextField optionOwnField1 = new JFormattedTextField(nf);
			optionOwnField1.setValue(gameRule.gameW);
			JFormattedTextField optionOwnField2 = new JFormattedTextField(nf);
			optionOwnField2.setValue(gameRule.gameH);
			String[] optionMode = {"All Lights", "Random Lights", "3 States All Lights", "3 States Random Lights"};
			JComboBox<String> cb2 = new JComboBox<String>(optionMode);
			cb2.setSelectedItem(gameRule.mode);
			cb2.setBounds(50,50,90,20);
			final JComponent[] optionInputs = new JComponent[]{
					new JLabel("Side of the Field"),
					cb1,
					new JLabel("Mode"),
					cb2
			};
			int result = JOptionPane.showConfirmDialog(null,optionInputs, "option Dialog", JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.OK_OPTION){
				if(cb1.getSelectedItem() == "Other"){
					final JComponent[] otherInput = new JComponent[]{
							new JLabel("Other size of the Field (1 to 100)"),
							optionOwnField1,
							new JLabel("x"),
							optionOwnField2,
					};
					int otherResult = JOptionPane.showConfirmDialog(null,otherInput, "other", JOptionPane.PLAIN_MESSAGE);
					if(otherResult == JOptionPane.OK_OPTION){
						if(optionOwnField1.getText().isEmpty() || optionOwnField2.getText().isEmpty()){
							gameRule.gameW = 3; 
							gameRule.gameH = 3; 
						}else{
							gameRule.gameW = Integer.valueOf(optionOwnField1.getText()); 
							gameRule.gameH = Integer.valueOf(optionOwnField2.getText()); 
						}
					}
					//gameRule.gameW = Integer.valueOf(optionOwnField1.getText());
				}else{
					char k = cb1.getSelectedItem().toString().charAt(0);
					gameRule.gameW = Character.getNumericValue(k);
					gameRule.gameH = Character.getNumericValue(k);
				}
				gameRule.mode = cb2.getSelectedItem().toString();
				gameSave.writeSF();
				gameRule.updatebodyP();
				
			}
		}else if(a.getActionCommand() == "About"){
			JOptionPane.showMessageDialog(null, "Creater by TOXA", "About Window", JOptionPane.INFORMATION_MESSAGE);			
		}
	}
}