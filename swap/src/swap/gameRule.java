package swap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;

public class gameRule{
	//размеры поля
	final public static boolean DEBUG = false;
	public static int gameW = 5;
	public static int gameH = 5;
	public static String mode = "All Lights";
	
	public static ArrayList<JButton> btnList = new ArrayList<JButton>();
	public static ArrayList<JButton> btnListTemp = new ArrayList<JButton>(); //saves random buttons
	public static ArrayList<JButton> btnListTemp1 = new ArrayList<JButton>(); //saves 3 random buttons
	
	public static int score=0;
	
	//размеры окна
	public static int windowSizeW;
	public static int windowSizeH;
	
	public static void setWindowSize(){
		if(120*gameW < 200){
			windowSizeW = 200;
		}else if(120*gameW > 1024 ){
			windowSizeW = 1024;
		}else{
			windowSizeW = 120*gameW;
		}
		if(120*gameH < 200){
			windowSizeH = 200;
		}else if(120*gameW > 1024 ){
			windowSizeH = 768;
		}else{
			windowSizeH = 100*gameH;
		}
	}	
	
	public static void win(){
		JButton button1 = new JButton();
		gameWindow.label3.setText("YOU WIN!");
		int k = 0;
		for(int i = 0; i < gameRule.gameW; i++){
			for (int j = 0; j < gameRule.gameH; j++){
				button1 = gameRule.btnList.get(k);
				button1.setEnabled(false);
				k = k+1;
			}
		}
	}
	
	//обнуление поля
	public static void clearField(){
		gameRule.score=0;
		gameWindow.label2.setText(Integer.toString(gameRule.score));
		gameWindow.label3.setText("        ");
		JButton button1 = new JButton();
		int k = 0;
		for(int i = 0; i < gameRule.gameW; i++){
			for (int j = 0; j < gameRule.gameH; j++){
				button1 = gameRule.btnList.get(k);
				button1.setBackground(null);
				button1.setEnabled(true);
				k = k+1;
			}
		}
	}
	
	public static void colorSet(int b){
		JButton button1 = new JButton();
		button1 = gameRule.btnList.get(b);
		if(gameRule.mode.equals("3 States All Lights") || gameRule.mode.equals("3 States Random Lights")){
			if(button1.getBackground() == Color.GREEN){
				button1.setBackground(Color.RED);
			}else if(button1.getBackground() == Color.RED){
				button1.setBackground(null);
			}else{
				button1.setBackground(Color.GREEN);
			}
		}else{
			if(button1.getBackground() == Color.RED){
				button1.setBackground(null);
			}else{
				button1.setBackground(Color.RED);
			}
		}
	}
	
	// меняем цвет кнопок
	public static void btnSet(int b){
		int i=b;
		//основная кнопка	
		gameRule.colorSet(i);
		//первое в ряду
		if(b%gameRule.gameW != 0){
			i=b-1;	
			gameRule.colorSet(i);
		}
		//последнее в ряду
		if(b%gameRule.gameW != gameRule.gameW-1){
			i=b+1;
			gameRule.colorSet(i);
		}
		//если выше таблицы
		if(b-gameRule.gameW >= 0){
			i=b-gameRule.gameW;	
			gameRule.colorSet(i);
		}
		//если ниже таблицы
		if(b+gameRule.gameW <= (gameRule.gameW*gameRule.gameH)-1){
			i=b+gameRule.gameW;	
			gameRule.colorSet(i);
		}		
	}
	
	public static void instawin(){
		JButton button1 = new JButton();
		int k = 0;
		for(int i = 0; i < gameRule.gameW; i++){
			for (int j = 0; j < gameRule.gameH; j++){
				button1 = gameRule.btnList.get(k);
				button1.setBackground(Color.RED);
				k = k+1;
			}
		}
	}
	
	//проверка на выигрыш
		public static void wincheck(){
			JButton button1 = new JButton();
			int k = 0;
			int a = 0; // считает закрашенные поля
			for(int i = 0; i < gameRule.gameW; i++){
				for (int j = 0; j < gameRule.gameH; j++){
					button1 = gameRule.btnList.get(k);
					if(button1.getBackground()==Color.RED){
						a=a+1;
					}
					k=k+1;
				}
			}
			if((gameRule.gameW*gameRule.gameH) == a){
				gameRule.win();
			}
		}
		
		public static void createButtons(){
			//создаем кнопки для игры 
	        int k = 0;
			for(int i = 0; i < gameRule.gameW; i++){
				for (int j = 0; j < gameRule.gameH; j++){
					JButton button = new JButton();
					button.setName(Integer.toString(k));
					button.setSize(new Dimension(10, 10));
					button.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(final ActionEvent a){
							JButton button = (JButton) a.getSource();
							int b = Integer.parseInt(button.getName());
							gameWindow.label2.setText(Integer.toString(b));
							gameRule.btnSet(b);
							gameRule.score=gameRule.score+1;
							gameWindow.label2.setText(Integer.toString(gameRule.score));
							gameRule.wincheck();
						}
					});
					gameRule.btnList.add(button);
					gameWindow.bodyP.add(button);
					k = k+1;
				}
			}
			gameRule.setMode();
		}
		
		public static void updatebodyP(){
			gameRule.score = 0;
			gameWindow.label2.setText(Integer.toString(gameRule.score));
			gameWindow.label3.setText("        ");
			gameWindow.bodyP.removeAll();
			gameWindow.bodyP.setLayout(new GridLayout(gameRule.gameH,gameRule.gameW, 0, 0));
			gameRule.btnList.clear();
			gameRule.createButtons();
			if(gameRule.mode.equals("Random Lights") || gameRule.mode.equals("3 States Random Lights")){
				gameWindow.rnd.setVisible(true);
			}else{
				gameWindow.rnd.setVisible(false);
			}
			gameWindow.bodyP.updateUI();
			gameRule.setWindowSize();
			gameWindow.frame.setSize(gameRule.windowSizeW, gameRule.windowSizeH);
			gameWindow.frame.repaint();
		}
		
		public static void setMode(){
			if(gameRule.mode.equals("All Lights")){
				//пока ничего не надо, это стандартный режим
				//System.out.println("all l mode");
			}else if(gameRule.mode.equals("Random Lights")){
				//добавить рандомное зажигание огней
				//System.out.println("rl mode");
				getRandomButtons();
			}else if(gameRule.mode.equals("3 States All Lights")){
				//System.out.println("3s all l mode");
			}else if(gameRule.mode.equals("3 States Random Lights")){
				//System.out.println("3s r l mode");
				getRandomButtons();
			}
		}
		
		public static void getRandomButtons(){
			Random rand = new Random();
			int numberOfElements1 = 0;
			int numberOfElements2 = 0;
			numberOfElements1 = rand.nextInt(btnList.size());
			numberOfElements2 = rand.nextInt(btnList.size()-numberOfElements1+1);
			//System.out.println(numberOfElements1);System.out.println(numberOfElements2);
			
			btnListTemp = new ArrayList<JButton>(btnList);
			Collections.shuffle(btnListTemp);
			if(gameRule.mode.equals("3 States Random Lights")){
				btnListTemp1 = new ArrayList<JButton>(btnListTemp);
				//temp1
				int i = 0;
				while(i != numberOfElements1){
					btnListTemp1.remove(0);
					i=i+1;
				}
				while(btnListTemp1.size() != numberOfElements2){
					btnListTemp1.remove(btnListTemp1.size()-1);
				}
			}
			//temp
			while(btnListTemp.size() != numberOfElements1){
				btnListTemp.remove(btnListTemp.size()-1);
			}
			clearField();
			setRandomButtons();
		}
		public static void setRandomButtons(){
			gameWindow.rnd.setVisible(true);
			JButton button1 = new JButton();
			for(int i=0;i<btnListTemp.size();i++){
				button1 = gameRule.btnListTemp.get(i);	
				button1.setBackground(Color.RED);
			}
			if(gameRule.mode.equals("3 States Random Lights")){
				for(int i=0;i<btnListTemp1.size();i++){
					button1 = gameRule.btnListTemp1.get(i);	
					button1.setBackground(Color.GREEN);
				}
			}
		}
	
}