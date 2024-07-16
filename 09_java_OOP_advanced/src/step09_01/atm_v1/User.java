package step09_01.atm_v1;

public class User {
	
	String id;
	int accCount;
	Account[] acc; // Account 타입의 배열 선언(계좌번호/금액)
	
	void printAccount() {
		
		for (int i = 0; i < accCount; i++) {
			acc[i].printOwnAccount(); // 내가 가진 모든 계좌 정보 조회
		}	
		
	}
	
}
