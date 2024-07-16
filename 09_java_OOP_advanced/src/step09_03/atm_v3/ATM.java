package step09_03.atm_v3;

import java.util.Random;
import java.util.Scanner;

// ATM 클래스 선언
public class ATM {
    
    // 전역적으로 사용할 Scanner 인스턴스
    static Scanner scan = new Scanner(System.in);
    
    // 전역적으로 사용할 Random 인스턴스
    static Random ran = new Random();
    
    // ATM 클래스 생성자
    ATM() {
        // 파일에서 데이터를 로드
        boolean isLoad = FileManager.getInstance().loadData();
        
        // 데이터를 로드하지 못한 경우 더미 데이터를 생성
        if (!isLoad) {
            UserManager.getInstance().setDummy();
            FileManager.getInstance().saveData();
        }
    }
    
    // 메뉴를 보여주는 메서드
    void showMenu() {
        while(true) {
            // 모든 사용자 데이터를 출력
            printAllDataByAllUser();
            
            // 메뉴 출력
            System.out.println("[MEGA ATM]");
            System.out.println("[1]회원가입\n[2]로그인\n[0]종료");
            System.out.print("메뉴를 선택하세요 : ");
            
            // 사용자 입력 받기
            int choice = scan.nextInt();
            
            // 선택된 메뉴에 따라 다른 메서드 호출
            if (choice == 1) join(); 
            else if (choice == 2) login(); 
            else if (choice == 0) break;
        }
    }
    
    // 모든 사용자 정보를 출력하는 메서드
    void printAllDataByAllUser() {
        UserManager.getInstance().printAllUserInfo();
    }
    
    // 로그인 메서드
    void login() { 
        UserManager.getInstance().loginUser();
    }
    
    // 회원가입 메서드
    void join() { 
        UserManager.getInstance().joinUser(); 
    }
}
