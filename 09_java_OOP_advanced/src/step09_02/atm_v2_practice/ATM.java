package step09_02.atm_v2_practice;

// 24.05.30(THU)

import java.util.Scanner;

public class ATM { // ATM 기능
	
	Scanner scan = new Scanner(System.in);
	int identifier = -1; // 식별자
	UserManager um = UserManager.getInstance(); // UserManager 클래스에서 생성한 UserManager 객체 호출
	
	void play() {
		
		FileManager.getInstance().load();
		UserManager.getInstance().printAllUser();
	}

}
