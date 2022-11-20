package swap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class gameSave{
	//private static String filename = ".\\src\\swap\\savefile.txt";
	private static String filename = ".\\savefile.txt";
	
	public static void createSF(){
		try{
			File file = new File(filename);
			if(file.createNewFile()){
				//file created
				writeSF();
				gameSave.readSF();
			}else{
				//file exists
				gameSave.readSF();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void writeSF(){
		try{
			FileWriter file = new FileWriter(filename);
			file.write(gameRule.gameW+"\n"+gameRule.gameH+"\n"+gameRule.mode);
			file.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void readSF(){
		try{
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			int line = 0;
			while(reader.hasNextLine()){
				line = line+1;
				String data = reader.nextLine();
				if(line == 1){
					gameRule.gameW = Integer.valueOf(data);
				}else if(line == 2){
					gameRule.gameH = Integer.valueOf(data);
				}else if(line == 3){
					gameRule.mode = data;
				}
			}
			reader.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		gameRule.updatebodyP();
	}
		
	public static void deleteSF(){
		File file = new File(filename);
		if(file.delete()){
			//file deleted
		}
	}
}