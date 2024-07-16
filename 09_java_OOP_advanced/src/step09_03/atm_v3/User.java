package step09_03.atm_v3;

// User 클래스 선언
public class User {

    // 사용자의 ID
    String id;
    
    // 사용자의 비밀번호
    String password;
    
    // 사용자가 소유한 계좌들의 목록
    Account[] accList;
    
    // 사용자가 소유한 계좌의 개수
    int accCount;

    // 기본 생성자
    User() {}
    
    // 사용자 ID와 비밀번호를 매개변수로 받는 생성자
    User(String id, String password) {
        this.id = id;  // 인스턴스 변수 id에 매개변수 id를 대입
        this.password = password;  // 인스턴스 변수 password에 매개변수 password를 대입
    }
    
    // 사용자 ID, 비밀번호, 계좌 목록, 계좌 개수를 매개변수로 받는 생성자
    User(String id, String password, Account[] accList, int accCount) {
        this.id = id;  // 인스턴스 변수 id에 매개변수 id를 대입
        this.password = password;  // 인스턴스 변수 password에 매개변수 password를 대입
        this.accList = accList;  // 인스턴스 변수 accList에 매개변수 accList를 대입
        this.accCount = accCount;  // 인스턴스 변수 accCount에 매개변수 accCount를 대입
    }
    
    // 사용자의 모든 계좌 정보를 출력하는 메서드
    void printOneUserAllAccounts() {
        // 사용자가 계좌를 하나도 가지고 있지 않은 경우
        if (accCount == 0) {
            System.out.println(id + "\t" + password + "\t계좌를 개설해주세요.");
        }
        // 사용자가 한 개 이상의 계좌를 가지고 있는 경우
        else if (accCount > 0) {
            System.out.print(id + "\t" + password + "\t");
            // 모든 계좌 정보를 출력
            for (int i = 0; i < accCount; i++) {
                System.out.print(accList[i].number + "/" + accList[i].money + "원;");
            }
            System.out.println();  // 줄 바꿈
        }
    }
}
