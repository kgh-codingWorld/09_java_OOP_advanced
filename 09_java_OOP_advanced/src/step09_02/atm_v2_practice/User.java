package step09_02.atm_v2_practice;

public class User {
	
	Account[] acc = new Account[UserManager.getInstance().ACC_MAX_CNT];
	int accCnt;
	String id;
	String pw;
}
