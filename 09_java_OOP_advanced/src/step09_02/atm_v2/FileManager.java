package step09_02.atm_v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager { // 데이터베이스 역할
	
	private FileManager() {}
	private static FileManager instance = new FileManager();
	public static FileManager getInstance() { // 다른 클래스에서 FileManager 객체를 새로 생성하지 않고 여기 거 그대로 사용하기 위한 메서드
		return instance;
	}
	
	String fileName = "ATM.txt";
	String data = "";
	UserManager um = UserManager.getInstance();
	
	void setData() {
		
		data = "";
		int userCount = um.userCnt;
		data += userCount;
		data += "\n";
		
		for (int i = 0; i < userCount; i++) {
			data += um.userList[i].id;
			data += "\n";
			data += um.userList[i].pw;
			data += "\n";
			data += um.userList[i].accCnt;
			data += "\n";
			
			if (um.userList[i].accCnt == 0) {
				data += "0\n";
			}
			else {
				for (int j = 0; j < um.userList[i].accCnt; j++) {
					data += um.userList[i].acc[j].accNumber;
					data += "/";
					data += um.userList[i].acc[j].money;
					if (j != um.userList[i].accCnt - 1) {
						data += ",";
					}
				}
				data += "\n";
			}
		}
		
	}
	
	
	void save() {
		
		setData();
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(fileName);
			fw.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {try {fw.close();} catch (IOException e) {}}
		}
		
	}
	
	
	void load() {
		
		File file = new File(fileName); // ATM.txt
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			
			if (file.exists()) {
				
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				while (true) {
					String line = br.readLine();
					if (line == null) {
						break;
					}
					data += line;
					data += "\n";
				}
				
				String[] tmp = data.split("\n");
				um.userCnt = Integer.parseInt(tmp[0]);
				um.userList = new User[um.userCnt];
				for (int i = 0; i < um.userCnt; i++) {
					um.userList[i] = new User();
				}
				
				int j = 0;
				for (int i = 1; i < tmp.length; i += 4) {
					
					String id = tmp[i];
					String pw = tmp[i+1];
					int accCnt = Integer.parseInt(tmp[i+2]);
					
					um.userList[j].id = id;
					um.userList[j].pw = pw;
					um.userList[j].accCnt = accCnt;
					String accInfo = tmp[i+3];
					
					if (accCnt == 1) {
						String[] temp = accInfo.split("/");
						
						String acc = temp[0];
						int money = Integer.parseInt(temp[1]);
						
						um.userList[j].acc[0] = new Account();
						um.userList[j].acc[0].accNumber = acc;
						um.userList[j].acc[0].money = money;
					}
					if (accCnt > 1){
						
						String[] temp = accInfo.split(",");
						
						for (int k = 0; k < temp.length; k++) {
							String[] parse = temp[k].split("/");
							String acc = parse[0];
							int money = Integer.parseInt(parse[1]);
							
							um.userList[j].acc[k] = new Account();
							um.userList[j].acc[k].accNumber = acc;
							um.userList[j].acc[k].money = money;
						}
					}
					j++;
				}
			}
			else {
				//um.setDummy();
				setData();
				save();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {try {br.close();} catch (IOException e) {}}
			if (fr != null) {try {fr.close();} catch (IOException e) {}}
		}
	}
	
}
