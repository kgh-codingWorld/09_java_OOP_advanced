package step09_02.atm_v2;

// 24.05.31(FRI)
/*
 * # 삭제
 * 1. temp에 기존 배열 임시 저장
 * 
 * 2. 기존 배열에 [기존 cnt -1]한 새로운 배열 생성
 * 
 * 3. 복사
 *  1) int j=0; 기존 배열을 복사할 새 배열의 index 자동 증가 역할
 *  2) for문
 *  - 기존 cnt -1만큼 반복
 *  - i가 식별자(-1)가 아닐 때 기존 배열을 새 배열에 복사(j를 index에서 사용)
 *  3) 삭제(null 사용)
 */

import java.util.Scanner;

public class UserManager { // 회원가입, 로그인 기능
	
	//싱글톤 패턴
	private UserManager() {} // 기본 생성자
	private static UserManager instance = new UserManager(); // static으로 자체 객체 생성
	public  static UserManager getInstance() { // 다른 클래스에서 UserManager 객체를 새로 생성하지 않고 여기 거 그대로 사용하기 위한 메서드
		return instance;
	}
	
	Scanner scan = new Scanner(System.in);
	
	final int ACC_MAX_CNT = 3;			// 최대 개설 가능한 계좌 수
	User[] userList = null;				// 전체 회원정보
	int userCnt = 0;					// 전체 회원 수
	
	// 모든 사용자 출력(ATM.play() 출력 시)
	void printAllUser() {
		
		for (int i = 0; i < userCnt; i++) {
			System.out.print((i+1) + ".ID(" + userList[i].id + ")\tPW(" + userList[i].pw + ")\t");
			if (userList[i].accCnt != 0) {
				for (int j = 0; j < userList[i].accCnt; j++) {
					System.out.print("(" + userList[i].acc[j].accNumber + ":" + userList[i].acc[j].money + ")");
				}
			}
			System.out.println();
		}
	}
	
	// 계좌 중복 확인
	boolean getCheckAcc(String account) {
		
		boolean isDuple = false;
		for (int i = 0; i < userCnt; i++) {
			for (int j = 0; j < userList[i].accCnt; j++) {
				if (account.equals(userList[i].acc[j].accNumber)) {
					isDuple = true;
				}
			}
		}
		return isDuple; // isDuple = true
		
	}
	
	// 로그인 기능
	int logUser() { // 굳이 login()과 기능을 나눈 이유가 무엇인가 -> ATM 기능과 사용자 관련 기능을 분리하기 위함
		
		int identifier = -1;
		
		System.out.print("[로그인]아이디를 입력하세요 : ");
		String id = scan.next();
		System.out.print("[로그인]패스워드를 입력하세요 : ");
		String pw = scan.next();
		
		for (int i = 0; i < UserManager.instance.userCnt; i++) {
			if (userList[i].id.equals(id) && userList[i].pw.equals(pw)) {
				identifier = i;
			}
		}
		
		// UserManager.instance.userCnt = instance.userCnt
//		System.out.println("UserManager.instance.userCnt!!!"+UserManager.instance.userCnt);
//		System.out.println("instance.userCnt!!!"+instance.userCnt);
		
//		for (int i=0; i<instance.userCnt; i++) {
//			if (userList[i].id.equals(id) && userList[i].pw.equals(pw)) {
//				identifier = i;
//			}
//		}
		
		return identifier;

	}
	
	// 아이디 중복 확인
	boolean checkId(String id) {
		
		boolean isDuple = false;
		for (int i = 0; i < userCnt; i++) {
			if (userList[i].id.equals(id)) {
				isDuple = true;
			}
		}
		// equals 미성립 시 아무것도 반환하지 않으므로 isDuple은 무조건 true로 반환됨
		return isDuple;
		
	}
	
	// 회원가입 기능
	void joinMember() {
		
		System.out.print("[회원가입]아이디를 입력하세요 : ");
		String id = scan.next();
		System.out.print("[회원가입]패스워드를 입력하세요 : ");
		String pw = scan.next();
		
		boolean isResult = checkId(id);
		
		if (isResult) {
			System.out.println("[메세지]아이디가 중복됩니다.");
			return;
		}
		
		if (userCnt == 0) {
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
		System.out.println("[메세지]회원가입을 축하합니다.");
		
		FileManager.getInstance().save();

	}

	// 계정 삭제
	int deleteMember(int identifier) { // 언제 정확히 삭제 동작이 실행되는가????
		
		User[] tmp = userList; // 새 배열에 기존 배열 임시저장
		userList = new User[userCnt - 1]; // 기존 배열에 사용자 수에서 -1한 새로운 배열 생성
		
		int j = 0;
		for (int i = 0; i < userCnt; i++) { // 사용자 수 -1만큼 반복
			if (i != identifier) {
				userList[j++] = tmp[i]; // index 자동증가시켜서 기존 배열을 새 배열에 복사
			}
		}
		
		userCnt--;
		tmp = null;
		identifier = -1; // 초기화
		
		System.out.println("[메세지]탈퇴되었습니다.");

		FileManager.getInstance().save();
		
		return identifier;
		
	}
	
	// (테스트생성용 메서드)  : 테스트 데이타 > 더미
	void setDummy() {
		
		String[] ids  = {"user1"  ,  "user2",     "user3",    "user4",    "user5"};
		String[] pws  = {"1111"   ,   "2222",      "3333",     "4444",    "5555"};
		String[] accs = {"1234567",  "2345692",  "1078912",   "2489123",  "7391234"};
		int[] moneys  = { 87000   ,     12000,    49000,        34000,     128000};
		
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
