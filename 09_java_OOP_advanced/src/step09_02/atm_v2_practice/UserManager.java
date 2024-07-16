package step09_02.atm_v2_practice;

import java.util.Scanner;

public class UserManager {
	
	private UserManager() {}
	static private UserManager instance = new UserManager();
	static public UserManager getInstance() {
		return instance;
	}
	
	Scanner scan = new Scanner(System.in);
	
	final int ACC_MAX_CNT = 3;
	User[] userList = null;
	int userCnt = 0;
	
	void printAllUser() {
		
		for(int i=0; i<userCnt; i++) {
			System.out.println((i+1) + ".ID(" + userList[i].id + ")\tPW(" + userList[i].pw + ")\t");
			if(userList[i].accCnt != 0) {
				for (int j=0; j<userList[i].accCnt; j++) {
					System.out.println("(" + userList[i].acc[j].accNumber + ":" + userList[i].acc[j].money + ")");
				}
			}
		System.out.println();
		}
	}
	
	boolean getCheckAcc(String account) {
		
		boolean isDuple = false;
		for (int i=0; i<userCnt; i++) {
			for (int j=0; j<userList[i].accCnt; j++) {
				if (account.equals(userList[i].acc[j].accNumber)) {
					isDuple = true;
				}
			}
		}
		return isDuple; // isDuple = true
		
	}
	
	int logUser() {
		
		int identifier = -1;
		
		System.out.print("[로그인]아이디를 입력하세요: ");
		String id = scan.next();
		System.out.print("[로그인]패스워드를 입력하세요: ");
		String pw = scan.next();
		
		for (int i=0; i<UserManager.instance.userCnt; i++) {
			if(userList[i].id.equals(id) && userList[i].pw.equals(pw)) {
				identifier = i;
			}
		}
		
		return identifier;
	}
	
	boolean checkId(String id) {
		
		boolean isDuple = false;
		for (int i=0; i<userCnt; i++) {
			if(userList[i].id.equals(id)) {
				isDuple = true;
			}
		}
		return isDuple;
	}
	
	void joinMember() {
		
		System.out.print("[회원가입]아이디를 입력하세요: ");
		String id = scan.next();
		System.out.println("[회원가입]패스워드를 입력하세요: ");
		String pw = scan.next();
		
		boolean isResult = checkId(id);
		
		if(isResult) {
			System.out.println("[메시지]아이디가 중복됩니다.");
			return;
		}
		
		if(userCnt == 0) {
			userList = new User[userCnt + 1];
			userList[userCnt] = new User();
		}
		else {
			User[] tmp = userList;
			userList = new User[userCnt + 1];
			userList[userCnt] = new User();
			
			for (int i = 0; i < userCnt; i++) {
				userList[i] = tmp[i];
			}
			tmp = null;
		}
		userList[userCnt].id = id;
		userList[userCnt].pw = pw;
		
		userCnt++;
		System.out.println("[메시지]회원가입을 축하합니다.");
		
		FileManager.getInstance().save();
	}
	
	int deleteMember(int identifier) {
		
		User[] tmp = userList;
		userList = new User[userCnt - 1];
		
		int j=0;
		for(int i=0; i<userCnt; i++) {
			if(i != identifier) {
				userList[j++] = tmp[i];
			}
		}
		
		userCnt--;
		tmp = null;
		identifier = -1;
		
		System.out.println("[메시지]탈퇴되었습니다.");
		
		FileManager.getInstance().save();
		
		return identifier;
	}
	
	void setDummy() {
		
		String[] ids = {"user1", "user2", "user3", "user4", "user5"};
		String[] pws = {"1111", "2222", "3333", "4444", "5555"};
		String[] accs = {"1234567", "2345692", "1078912", "2489123", "7391234"};
		int[] moneys = {87000, 12000, 49000, 34000, 128000};
		
		userCnt = 5;
		userList = new User[userCnt];
		
		for (int i = 0; i < userCnt; i++) {
			userList[i] = new User();
			userList[i].id = ids[i];
			userList[i].pw = pws[i];
			userList[i].acc[0] = new Account();
			userList[i].acc[0].accNumber = accs[i];
			userList[i].acc[0].money = moneys[i];
			userList[i].accCnt++;
		}
	}
	
	

}
