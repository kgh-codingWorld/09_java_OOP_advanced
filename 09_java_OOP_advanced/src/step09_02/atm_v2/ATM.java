package step09_02.atm_v2;

// 24.05.31(FRI)
/*
 * 1. UserManager
 * 2. AccountManager
 * 순으로 보았다.
 * (FileManager는 DB 역할이라 굳이 보지 않아도 될 듯)
 * 
 */

import java.util.Scanner;

public class ATM { // ATM 기능
	
	Scanner scan = new Scanner(System.in);
	int identifier = -1;
	UserManager um = UserManager.getInstance(); // UserManager 클래스에서 생성한 UserManager 객체 호출(static)
	
	// 메뉴
	void play() {
		
		FileManager.getInstance().load(); // DB 가져옴
		UserManager.getInstance().printAllUser(); // 전체 사용자 정보
		
		while (true) {
			
			System.out.println("[ATM]");
			System.out.println("[1.회원가입]\n[2.로그인]\n[0.종료]");
			System.out.print("메뉴 선택 : ");
			int sel = scan.nextInt();
			
			if		(sel == 1)  join();  // UserManager.getInstance.joinMember()
			else if (sel == 2)  login(); // UserManager.getInstance.logUser() 
			else if (sel == 0)  break;	 // 메뉴 종료
			
		}
		
	}
	
	// 로그인 - UserMangager의 메서드 사용
	void login() {
		
		identifier = um.logUser(); // UserManager의 logUser()
		if (identifier != -1) loginMenu(); // 로그인 성공 시
		else 				  System.out.println("[메세지]아이디와 패스워드를 확인해주세요.");

	}
	
	// 로그인 성공 후 계좌 메뉴
	void loginMenu() {
		
		while (true) {
			
			System.out.println("[" + um.userList[identifier].id + "님, 환영합니다.]");
			System.out.println("[1.계좌생성]\n[2.계좌삭제]\n[3.조    회]\n[4.회원탈퇴]\n[0.로그아웃]");
			System.out.println("메뉴 선택 : ");
			int selectMenu = scan.nextInt();

			if (selectMenu == 1) { // 계좌생성
				AccountManager.getInstance().createAcc(identifier);
				FileManager.getInstance().save();
			}
			else if (selectMenu == 2) { // 계좌삭제
				// 구현해보시오.
				AccountManager.getInstance().deleteAcc(identifier);
			}
			else if (selectMenu == 3) { // 조회
				AccountManager.getInstance().printAcc(identifier);
			}
			else if (selectMenu == 4) { // 회원탈퇴
				identifier = um.deleteMember(identifier);
				break;
			}
			else if (selectMenu == 0) {
				identifier = -1;
				System.out.println("로그아웃 되었습니다.");
				break;
			}
			
		}
		
	}
	
	// 회원가입
	void join() {
		UserManager.getInstance().joinMember();
	}

	
}
