package step09_01.atm_v1_practice;

import java.util.Random;
import java.util.Scanner;

public class ATM {

	static Scanner scan = new Scanner(System.in);
	Random ran = new Random();
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
			}
			else if (sel == 3) {
			}
			else if (sel == 4) {
			}
			else if (sel == 0) {
				System.out.println("[메시지] 프로그램을 종료합니다.");
				break;
			}
			
		}
		
		
	}
	
	void join() {
		
	}
}
