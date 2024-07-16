package step09_01.atm_v1;
import java.util.Random;
import java.util.Scanner;

public class ATM {
	
	static Scanner scan = new Scanner(System.in);
	Random ran   = new Random();
	UserManager userManager = new UserManager();
	int identifier = -1;
	
	void printMainMenu() {
				
		while (true) {
			
			System.out.println("\n[ MEGA ATM ]");
			System.out.print("[1.회원가입] [2.로그인] [3.로그아웃] [4.회원탈퇴] [0.종료] : ");
			int sel = scan.nextInt();
			
			if (sel == 1) {
				join();
			}
			else if (sel == 2) {
				login();
			}
			else if (sel == 3) {
				logout();
			}
			else if (sel == 4) {
				leave();
			}
			else if (sel == 0) {
				System.out.println("[메시지] 프로그램을 종료합니다.");
				break;
			}
			
		}
		
		
	}

	
	void join() {	
		userManager.addUser();
	}
	
	
	void login() {
		
		identifier = userManager.logUser();
		
		if (identifier != -1) { 
			printAccountMenu(); // 로그인 시 계좌 메뉴 호출
		}
		else {
			System.out.println("[메세지] 로그인실패.");
		}
		
	}
	
	
	void logout() {
		
		if (identifier == -1) {
			System.out.println("[메시지] 로그인을 하신 후 이용하실 수 있습니다.");
		}
		else {
			identifier = -1;
			System.out.println("[메시지] 로그아웃 되었습니다.");
		}
		
	}
	
	
	void leave() {
		userManager.leave();
	}
	
	//--------------계좌 메뉴----------------
	void printAccountMenu() {
		
		while (true) {
			
			System.out.print("[1.계좌생성] [2.계좌삭제] [3.조회] [0.로그아웃] : ");
			int sel = scan.nextInt();
			
			String makeAccount = Integer.toString(ran.nextInt(90001) + 10000);
			 
			// 계좌생성
			if (sel == 1) {
				
				if (userManager.user[identifier].accCount == 0) { // 해당 user의 계좌 개수가 0이면
					
					userManager.user[identifier].acc = new Account[1]; // 새로운 Account 배열 생성(길이 1)
					
					userManager.user[identifier].acc[0] = new Account(); // 0번째 배열에 Account 객체 생성
					userManager.user[identifier].acc[0].number = makeAccount; // 계좌번호 생성
				
				}
				else { // 해당 user가 다른 계좌도 가지고 있으면
					
					Account[] temp = userManager.getUser(identifier).acc; // 해당 user의 모든 계좌를 가져와 임시 저장
					int tempAccCount = userManager.getUser(identifier).accCount; // user의 index로 모든 계좌 개수를 가져와 임시 저장
					userManager.user[identifier].acc = new Account[tempAccCount+1]; // 계좌 개수 + 1만큼으로 늘린 새로운 배열 생성
					
					for (int i = 0; i < tempAccCount; i++) {
						userManager.user[identifier].acc[i] = temp[i]; // 기존 계좌 정보를 새로운 배열에 저장
					}
					userManager.user[identifier].acc[tempAccCount] = new Account(); // 새로운 계좌 객체를 배열에 추가
					userManager.user[identifier].acc[tempAccCount].number = makeAccount; // 새로운 계좌 객체를 추가한 배열에 계좌생성
					
				}
				userManager.user[identifier].accCount++; // 계좌 수 증가
				System.out.println("[메시지]'"+makeAccount +"'계좌가 생성되었습니다.\n");
				
			} 	
			// 계좌삭제
			else if (sel == 2) {
				
				if (userManager.user[identifier].accCount == 0) { // 계좌가 존재하지 않을 때
					System.out.println("[메시지] 더 이상 삭제할 수 없습니다.\n");
					continue; // 동작 계속
				}
				
				if (userManager.user[identifier].accCount == 1) { // 계좌가 하나만 존재할 때
					System.out.println("[메시지] 계좌번호 :'"+ userManager.user[identifier].acc[0].number+"' 삭제 되었습니다.\n");
					userManager.user[identifier].acc = null;
				}
				else { // 계좌가 여러 개 존재할 때
					
					System.out.print("삭제 하고 싶은 계좌 번호를 입력하세요 : ");
					String deleteAccount = scan.next();
					int tempAccCount = userManager.user[identifier].accCount; // 기존의 계좌 수를 가져와 임시 저장
					int delIdx = -1;
					for (int i = 0; i <tempAccCount; i++) {
						if (deleteAccount.equals(userManager.user[identifier].acc[i].number)) { // i번째 계좌 정보의 계좌번호와 삭제하고 싶은 계좌번호와 일치 시
							delIdx = i; // delIdx에 계좌 정보 index숫자 저장
						}
					}
					
					if (delIdx == -1) {
						System.out.println("[메시지] 계좌번호를 확인하세요.\n");
						continue;
					}
					else {
						System.out.println("[메시지] 계좌번호 :'"+ userManager.user[identifier].acc[delIdx].number+"' 삭제 되었습니다.\n");
						
						Account[] temp = userManager.user[identifier].acc; // 기존의 모든 계좌 정보를 가져와 임시 저장
						userManager.user[identifier].acc = new Account[tempAccCount-1]; // 기존 계좌 수에서 하나 뺀 길이로 새로운 배열 생성
						
						
						for (int i = 0; i < delIdx; i++) { // 0 ~ 삭제하고자 하는 index 번호
							userManager.user[identifier].acc[i] = temp[i]; // i번째의 기존 배열을 새로운 i번째 배열에 저장
						}
						for (int i = delIdx; i < tempAccCount - 1; i++) { // 삭제하고자 하는 index 번호 ~ 기존 계좌 수에서 하나 뺀 값
							userManager.user[identifier].acc[i] = temp[i+1]; // **삭제하고자 하는 index 번호보다 하나더 큰 기존 배열부터 새로운 i번째 배열에 저장
						}
					}
					
				}
				userManager.user[identifier].accCount--;
				
			}
			
			else if (sel == 3) {
				
				if (userManager.user[identifier].accCount == 0) {
					System.out.println("[메시지] 생성된 계좌가 없습니다.\n");
				}
				else {
					userManager.user[identifier].printAccount();
				}
				
			}   
			else if (sel == 0) {
				
				logout();
				break;
				
			}
			
		}
		
	}	
}
