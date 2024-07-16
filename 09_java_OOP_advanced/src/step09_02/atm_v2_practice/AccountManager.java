package step09_02.atm_v2_practice;

import java.util.Random;
import java.util.Scanner;

public class AccountManager {

	private AccountManager() {};
	private static AccountManager instance = new AccountManager();
	public static AccountManager getInstance() {
		return instance;
	}
	
	Scanner scan = new Scanner(System.in);
	Random ran = new Random();
	UserManager um = UserManager.getInstance();
	
	void createAcc(int identifier) {
		
		int accCntByUser = um.userList[identifier].accCnt;
		
		if(accCntByUser == um.ACC_MAX_CNT) {
			System.out.println("[메시지]계좌개설은 3개까지만 가능합니다.");
			return;
		}
		
		um.userList[identifier].acc[accCntByUser] = new Account();
		
		String makeAccount = "";
		while(true) {
			makeAccount = ran.nextInt(9000000) + 1000001 + "";
			if(!um.getCheckAcc(makeAccount)) {
				break;
			}
		}
		um.userList[identifier].acc[accCntByUser].accNumber = makeAccount;
		um.userList[identifier].accCnt++;
		System.out.println("[메시지]'" + makeAccount + "'계좌가 생성되었습니다.\n");
	}
	
	void deleteAcc(int identifier) {
		
		int accCntByUser = um.userList[identifier].accCnt;
		
		if(accCntByUser == 0) {
			System.out.println("[메시지]보유하신 계좌가 존재하지 않습니다.");
			return;
		}
		else if(accCntByUser == 1) {
			System.out.println("[메시지]" + um.userList[identifier].acc[0].accNumber);
			um.userList[identifier].acc = null;
		}
	}
	
	void printAcc(int identifier) {
		
		User temp = um.userList[identifier];
		System.out.println("================");
		System.out.println("ID : " + temp.id);
		System.out.println("================");
		for (int i = 0; i < temp.accCnt; i++) {
			System.out.println("accNumber: " + temp.acc[i].accNumber + "/money: " + temp.acc[i].money);
		}
		System.out.println("============================\n");
	}
}
