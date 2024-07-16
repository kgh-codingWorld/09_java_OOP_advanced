package step09_02.atm_v2;

public class User { // 사용자 정보
	
	Account[] acc = new Account[UserManager.getInstance().ACC_MAX_CNT];	
	int accCnt;	
	String id;											
	String pw;											
	
}


