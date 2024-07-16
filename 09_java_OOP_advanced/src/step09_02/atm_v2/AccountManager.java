package step09_02.atm_v2;

import java.util.Random;
import java.util.Scanner;

public class AccountManager { // 계좌 관련 기능(계좌 생성, 조회)

	//싱글톤 패턴
	private AccountManager() {} // 기본생성자
	private static AccountManager instance = new AccountManager(); // static을 이용한 자체 객체 생성(다른 클래스에서 동일한 주소를 가진 AccountManager 객체 사용 가능)
	public static AccountManager getInstance() { //getter
		return instance;
	}
	
	Scanner scan = new Scanner(System.in);
	Random ran = new Random();
	UserManager um = UserManager.getInstance();

	// 계좌 개설
	void createAcc(int identifier) {
		
		int accCntByUser = um.userList[identifier].accCnt; // 해당 유저가 가진 계좌 수
		
		if (accCntByUser == um.ACC_MAX_CNT) { // 해당 유저가 가진 계좌 수와 최대 개설 가능한 계좌 수가 일치할 때
			System.out.println("[메세지]계좌개설은 3개까지만 가능합니다.");
			return; // 반환 없이 종료
		}
		
		um.userList[identifier].acc[accCntByUser] = new Account(); // accCntByUser번째에 새로운 Account 객체 생성
		
		// 랜덤으로 계좌 번호 생성
		String makeAccount = "";
		while (true) {
			makeAccount = ran.nextInt(9000000) + 1000001 + "";		
			if (!um.getCheckAcc(makeAccount)){
				break;
			}
		}
		um.userList[identifier].acc[accCntByUser].accNumber = makeAccount;
		um.userList[identifier].accCnt++;
		System.out.println("[메세지]'" + makeAccount + "'계좌가 생성되었습니다.\n");
		
	}
	
	void deleteAcc(int identifier) {
		
		int accCntByUser = um.userList[identifier].accCnt; // 해당 유저의 계좌 수
		
		if (accCntByUser == 0) { // 가진 계좌가 없으면
			System.out.println("[메시지]보유하신 계좌가 존재하지 않습니다.");
			return; // 반환 없이 종료
		} else if (accCntByUser == 1) { // 가진 계좌가 1개면
			System.out.println("[메시지]"+um.userList[identifier].acc[0].accNumber+"");
			um.userList[identifier].acc = null;
		}
		
	}
	
	// 계좌 출력(아이디 기준)
	void printAcc(int identifier) {
		
		User temp = um.userList[identifier];
		System.out.println("====================");
		System.out.println("ID : " + temp.id);
		System.out.println("====================");
		for (int i = 0; i < temp.accCnt; i++) {
			System.out.println("accNumber:" +temp.acc[i].accNumber + " / money: " + temp.acc[i].money);
		}
		System.out.println("=============================\n");
		
	}
	
}
