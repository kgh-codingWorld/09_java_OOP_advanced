package step09_01.atm_v1;

public class Account {
	
	String number; // 계좌번호
	int money; // 금액
	
	
	void printOwnAccount() {
		// 필드의 데이터 출력
		System.out.println(this.number +  " : " + this.money);
		
	}
	
}
