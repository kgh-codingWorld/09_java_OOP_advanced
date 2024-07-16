package step09_03.atm_v3;

// UserManager 클래스 선언
public class UserManager {

    // 생성자를 private으로 선언하여 외부에서 인스턴스화하지 못하게 함 (싱글턴 패턴)
    private UserManager() {}

    // UserManager의 단일 인스턴스를 생성
    private static UserManager instance = new UserManager();

    // 단일 인스턴스를 반환하는 메서드
    public static UserManager getInstance() {
        return instance;
    }

    // 사용자 리스트 배열
    User[] userList;

    // 사용자 수
    int userCount;

    // 현재 로그인한 사용자의 인덱스, 초기값은 -1 (로그인되지 않은 상태)
    int identifier = -1;

    // 모든 사용자 정보를 출력하는 메서드
    void printAllUserInfo() {
        System.out.println("아이디\t패스워드\t계좌정보");
        for (int i = 0; i < userCount; i++) {
            userList[i].printOneUserAllAccounts();
        }
        System.out.println("--------------------------");
    }

    // 더미 사용자 데이터를 설정하는 메서드
    void setDummy() {
        // 사용자 수를 5명으로 설정
        userCount = 5;
        
        // 사용자 리스트 배열을 초기화
        userList = new User[userCount];
        for (int i = 0; i < userCount; i++) {
            userList[i] = new User();
        }

        // 임의의 아이디를 생성하기 위한 문자열 배열
        String[] a = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
        String[] b = {"l", "b", "c", "m", "e", "f", "g", "n", "i", "p", "k"};
        String[] c = {"s", "t", "u", "w", "v", "o", "x", "n", "q", "p", "r"};
        
        // 사용자 각각에 대해 임의의 아이디를 생성
        for (int i = 0; i < userCount; i++) {
            String id = "";
            int rNum = ATM.ran.nextInt(a.length); // a 배열에서 임의의 인덱스를 선택
            id += a[rNum]; // 선택된 문자를 id에 추가
            rNum = ATM.ran.nextInt(b.length); // b 배열에서 임의의 인덱스를 선택
            id += b[rNum]; // 선택된 문자를 id에 추가
            rNum = ATM.ran.nextInt(c.length); // c 배열에서 임의의 인덱스를 선택
            id += c[rNum]; // 선택된 문자를 id에 추가
            userList[i].id = id; // 완성된 id를 사용자에게 설정
        }

        // 임의의 패스워드를 생성하기 위한 문자열 배열
        String[] d = {"1", "8", "9", "4"};
        String[] e = {"2", "7", "0", "6"};
        String[] f = {"5", "3", "2", "7"};
        
        // 사용자 각각에 대해 임의의 패스워드를 생성
        for (int i = 0; i < userCount; i++) {
            String pw = "";
            int rNum = ATM.ran.nextInt(d.length); // d 배열에서 임의의 인덱스를 선택
            pw += d[rNum]; // 선택된 문자를 pw에 추가
            rNum = ATM.ran.nextInt(e.length); // e 배열에서 임의의 인덱스를 선택
            pw += e[rNum]; // 선택된 문자를 pw에 추가
            rNum = ATM.ran.nextInt(f.length); // f 배열에서 임의의 인덱스를 선택
            pw += f[rNum]; // 선택된 문자를 pw에 추가
            userList[i].password = pw; // 완성된 pw를 사용자에게 설정
        }

        // 더미 데이터가 추가되었음을 알리는 메시지 출력
        System.out.println("[메세지]더미 파일이 추가되었습니다.\n");
    }

    // 아이디 중복 확인 메서드
    int checkId(String id) {
        int check = -1; // 아이디가 없는 경우 -1을 반환
        for (int i = 0; i < userCount; i++) {
            if (userList[i].id.equals(id)) { // 아이디가 이미 존재하면
                check = i; // 해당 인덱스를 반환
                break; // 중복된 아이디가 있으면 루프를 종료
            }
        }
        return check;
    }

    // 사용자 가입 메서드
    void joinUser() {
        System.out.print("[가입]아이디를 입력하세요 : ");
        String id = ATM.scan.next(); // 사용자로부터 아이디 입력 받기

        // 아이디 중복 확인
        int checkedId = checkId(id);
        if (checkedId != -1) {
            System.out.println("[메세지]아이디가 중복됩니다.\n");
            return; // 중복된 아이디가 있으면 가입 절차 중단
        }

        System.out.print("[가입]패스워드를 입력하세요 : ");
        String password = ATM.scan.next(); // 사용자로부터 패스워드 입력 받기

        // 사용자가 처음 가입하는 경우
        if (userCount == 0) {
            userList = new User[1]; // 새로운 배열 생성
            userList[0] = new User(id, password); // 첫 번째 사용자 추가
        }
        // 이미 사용자가 있는 경우
        else if (userCount > 0) {
            User[] temp = userList; // 기존 사용자 목록을 임시 배열에 저장
            userList = new User[userCount + 1]; // 기존 사용자 수보다 하나 더 큰 배열 생성
            for (int i = 0; i < userCount; i++) {
                userList[i] = temp[i]; // 기존 사용자를 새로운 배열로 복사
            }
            userList[userCount] = new User(id, password); // 새로운 사용자 추가
            temp = null; // 임시 배열 제거
        }

        userCount++; // 사용자 수 증가
        System.out.println("[메세지]회원가입을 완료하였습니다.\n");

        // 데이터 파일에 저장
        FileManager.getInstance().saveData();

        // 모든 사용자 정보 출력
        printAllUserInfo();
    }

    // 사용자 탈퇴 메서드
    void leaveUser() {
        if (userCount == 1) {
            userList = null; // 남은 사용자가 없을 경우 null로 설정
        } else if (userCount > 1) {
            User[] temp = userList; // 기존 사용자 목록을 임시 배열에 저장
            userList = new User[userCount - 1]; // 기존 사용자 수보다 하나 더 작은 배열 생성
            int j = 0;
            for (int i = 0; i < userCount; i++) {
                if (i != identifier) { // 탈퇴할 사용자가 아니면
                    userList[j++] = temp[i]; // 새로운 배열에 사용자 추가
                }
            }
        }
        userCount--; // 사용자 수 감소

        System.out.println("[메세지]탈퇴되었습니다.\n");
        logoutUser(); // 로그아웃
        FileManager.getInstance().saveData(); // 데이터 파일에 저장
    }

    // 사용자 로그인 메서드
    void loginUser() {
        System.out.print("[로그인]아이디를 입력하세요 : ");
        String id = ATM.scan.next(); // 사용자로부터 아이디 입력 받기

        System.out.print("[로그인]패스워드를 입력하세요 : ");
        String password = ATM.scan.next(); // 사용자로부터 패스워드 입력 받기

        for (int i = 0; i < userCount; i++) {
            if (userList[i].id.equals(id) && userList[i].password.equals(password)) {
                identifier = i; // 아이디와 비밀번호가 일치하는 사용자의 인덱스 저장
                break; // 로그인 성공하면 루프 종료
            }
        }

        if (identifier == -1) {
            System.out.println("[메세지]아이디와 패스워드가 틀렸습니다.\n");
        } else {
            System.out.println("[메세지]" + userList[identifier].id + "님, 환영합니다.\n");
            afterloginMenu(); // 로그인 후 메뉴로 이동
        }
    }

    // 사용자 로그아웃 메서드
    void logoutUser() {
        identifier = -1; // 현재 로그인한 사용자 초기화
        System.out.println("[메세지]로그아웃되었습니다.\n");
    }

    // 로그인 후 메뉴 표시 메서드
    void afterloginMenu() {
        while (true) {
            System.out.println("[" + userList[identifier].id + "님, 로그인]");
            System.out.println("[1]계좌생성 [2]입금하기 [3]출금하기 [4]이체하기 [5]계좌조회 "
                    + "[6]계좌삭제 [7]회원탈퇴 [0]뒤로가기");
            System.out.print("메뉴를 선택하세요 : ");
            int choice = ATM.scan.nextInt(); // 사용자로부터 메뉴 선택 입력 받기

            if (choice == 1) {
                AccountManager.getInstance().createAccount(); // 계좌 생성
            } else if (choice == 2) {
                AccountManager.getInstance().income(); // 입금
            } else if (choice == 3) {
                AccountManager.getInstance().outcome(); // 출금
            } else if (choice == 4) {
                AccountManager.getInstance().transfer(); // 이체
            } else if (choice == 5) {
                AccountManager.getInstance().lookupAcc(); // 계좌 조회
            } else if (choice == 6) {
                AccountManager.getInstance().deleteAcc(); // 계좌 삭제
            } else if (choice == 7) {
                leaveUser(); // 회원 탈퇴
                break; // 메뉴 종료
            } else if (choice == 0) {
                logoutUser(); // 로그아웃
                break; // 메뉴 종료
            }
        }
    }
}
