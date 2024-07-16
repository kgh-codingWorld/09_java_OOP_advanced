package step09_03.atm_v3;

// AccountManager 클래스 선언
public class AccountManager {

    // 생성자를 private으로 선언하여 외부에서 인스턴스화하지 못하게 함
    private AccountManager() {}
    
    // AccountManager의 단일 인스턴스를 생성(싱글톤 패턴)
    private static AccountManager instance = new AccountManager();
    
    // 단일 인스턴스를 반환하는 메서드
    public static AccountManager getInstance() {
        return instance;
    }
    
    // UserManager의 단일 인스턴스를 참조
    UserManager userManager = UserManager.getInstance();
    
    // 새로운 계좌를 생성하는 메서드
    void createAccount() {
        // 로그인한 사용자를 가져옴
        User loginUser = userManager.userList[userManager.identifier];
        
        // 사용자가 이미 최대 계좌 수(3개)를 가지고 있는지 확인
        if (loginUser.accCount == 3) {
            System.out.println("[메세지]더 이상 계좌를 생성할 수 없습니다.\n");
            return;
        }
        
        // 사용자가 계좌가 하나도 없는 경우 계좌 리스트 배열을 초기화
        if (loginUser.accCount == 0) {
            loginUser.accList = new Account[loginUser.accCount + 1]; // 새 배열 생성
        } 
        // 사용자가 기존에 계좌가 있는 경우, 계좌 리스트 배열을 확장하여 새로운 계좌를 추가
        else if (loginUser.accCount > 0) {
            Account[] temp = loginUser.accList; // 기존 계좌 리스트 임시 저장
            loginUser.accList = new Account[loginUser.accCount + 1]; // 새 배열 생성
            for(int i = 0; i < loginUser.accCount; i++) {
                loginUser.accList[i] = temp[i]; // 기존 계좌를 새 배열로 복사
            }
            temp = null; // 임시 배열 제거
        }
        
        // 새 계좌를 생성하여 계좌 리스트에 추가
        loginUser.accList[loginUser.accCount] = new Account();
        
        // 새 계좌의 번호를 생성 (임의의 8자리 숫자로 구성)
        String makeAccount = ATM.ran.nextInt(90000000) + 10000001 + "";
        loginUser.accList[loginUser.accCount].number = makeAccount;
        loginUser.accList[loginUser.accCount].money = 0; // 초기 잔액은 0으로 설정
        
        // 계좌 개수를 증가
        loginUser.accCount++;
        System.out.println("[메세지]계좌가 생성되었습니다.\n");
        
        // 데이터를 파일에 저장
        FileManager.getInstance().saveData();
    }
    
    // 사용자 계좌 목록을 보여주는 메서드
    int showAccList(String msg) {
        int loginAccIndex = -1;
        User loginUser = userManager.userList[userManager.identifier];
        
        // 사용자가 가지고 있는 계좌 목록을 출력
        if (loginUser.accCount > 0) {
            for (int i = 0; i < loginUser.accCount; i++) {
                System.out.println("[" + (i + 1) + "]" + loginUser.accList[i].number);
            }
            System.out.print("[" + msg + "]내 계좌를 메뉴에서 선택하세요 : ");
            loginAccIndex = ATM.scan.nextInt(); // 사용자로부터 계좌 선택 입력 받기
            loginAccIndex--; // 배열 인덱스는 0부터 시작하므로 1 감소
        }
        
        return loginAccIndex; // 선택된 계좌의 인덱스를 반환
    }
    
    // 입금 메서드
    void income() {
        int loginAccIndex = showAccList("입금");
        if (loginAccIndex == -1) {
            System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
            return;
        }
        
        System.out.print("[입금]금액을 입력하세요 : ");
        int money = ATM.scan.nextInt();
        
        // 선택된 계좌에 입금 금액을 더함
        userManager.userList[userManager.identifier].accList[loginAccIndex].money += money;
        System.out.println("[메세지]입금을 완료하였습니다.\n");
        
        FileManager.getInstance().saveData(); // 데이터를 파일에 저장
    }
    
    // 출금 메서드
    void outcome() {
        int loginAccIndex = showAccList("출금");
        if (loginAccIndex == -1) {
            System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
            return;
        }
        
        System.out.print("[출금]금액을 입력하세요 : ");
        int money = ATM.scan.nextInt();
        
        // 선택된 계좌의 잔액이 출금 금액보다 적으면 오류 메시지 출력
        if (userManager.userList[userManager.identifier].accList[loginAccIndex].money < money) {
            System.out.println("[메세지]계좌잔액이 부족합니다.\n");
            return;
        }
        
        // 선택된 계좌에서 출금 금액을 뺌
        userManager.userList[userManager.identifier].accList[loginAccIndex].money -= money;
        System.out.println("[메세지]출금을 완료하였습니다.\n");
        
        FileManager.getInstance().saveData(); // 데이터를 파일에 저장
    }
    
    // 계좌 번호로 계좌를 확인하는 메서드
    int checkAcc(String transAccount) {
        int check = -1;
        for (int i = 0; i < userManager.userList.length; i++) {
            if (userManager.userList[i].accList != null) {
                for (int j = 0; j < userManager.userList[i].accCount; j++) {
                    if (transAccount.equals(userManager.userList[i].accList[j].number)) {
                        check = i; // 계좌 번호가 일치하는 사용자의 인덱스를 반환
                    }
                }
            }
        }
        
        return check; // 계좌가 없는 경우 -1 반환
    }
    
    // 사용자 인덱스와 계좌 번호로 계좌 인덱스를 가져오는 메서드
    int getAccIndex(int transUserIndex, String transAccount) {
        int accIndex = 0;
        
        for (int i = 0; i < userManager.userList[transUserIndex].accCount; i++) {
            if (transAccount.equals(userManager.userList[transUserIndex].accList[i].number)) {
                accIndex = i; // 계좌 번호가 일치하는 계좌의 인덱스를 반환
            }
        }
        
        return accIndex; // 계좌 인덱스를 반환
    }
    
    // 계좌 이체 메서드
    void transfer() {
        int loginAccIndex = showAccList("이체");
        if (loginAccIndex == -1) {
            System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
            return;
        }        
        
        System.out.print("[이체]이체할 '계좌번호'를 입력하세요 : ");
        String transAccount = ATM.scan.next();
        
        int transUserIndex = checkAcc(transAccount); // 이체할 계좌의 사용자 인덱스를 확인
        if (transUserIndex == -1) {
            System.out.println("[메세지]'계좌번호'를 확인해주세요.\n");
            return;
        }
        
        int transAccIndex = getAccIndex(transUserIndex, transAccount); // 이체할 계좌의 인덱스 확인
        
        System.out.print("[이체]금액을 입력하세요 : ");
        int money = ATM.scan.nextInt();
        
        // 출금하려는 계좌의 잔액이 부족하면 오류 메시지 출력
        if (money > userManager.userList[userManager.identifier].accList[loginAccIndex].money) {
            System.out.println("[메세지]계좌잔액이 부족합니다.\n");
            return;
        }
        
        // 로그인한 사용자의 계좌에서 이체 금액을 뺌
        userManager.userList[userManager.identifier].accList[loginAccIndex].money -= money;
        // 이체 대상 사용자의 계좌에 이체 금액을 더함
        userManager.userList[transUserIndex].accList[transAccIndex].money += money;
        System.out.println("[메세지]이체를 완료하였습니다.\n");
        
        FileManager.getInstance().saveData(); // 데이터를 파일에 저장
    }
    
    // 계좌 조회 메서드
    void lookupAcc() {
        // 로그인한 사용자의 모든 계좌 정보를 출력
        userManager.userList[userManager.identifier].printOneUserAllAccounts();
    }

    // 계좌 삭제 메서드
    void deleteAcc() {
        int loginAccIndex = showAccList("삭제");
        if (loginAccIndex == -1) {
            System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
            return;
        }
        
        User user = userManager.userList[userManager.identifier];
        
        // 사용자가 계좌가 하나만 있는 경우 계좌 리스트를 null로 설정
        if (user.accCount == 1) {
            user.accList = null;
        }
        // 사용자가 여러 개의 계좌를 가지고 있는 경우
        else if (user.accCount > 1) {
            Account[] acc = user.accList; // 기존 계좌 리스트 임시 저장
            user.accList = new Account[user.accCount - 1]; // 새 배열 생성
            int j = 0;
            for (int i = 0; i < user.accCount; i++) {
                if (i != loginAccIndex) {
                    user.accList[j] = acc[i]; // 선택된 계좌를 제외하고 복사
                    j = j + 1;
                }
            }
            acc = null; // 임시 배열 제거
        }
        user.accCount--; // 계좌 개수를 감소
        
        System.out.println("[메세지]계좌삭제를 완료하였습니다.\n");
        
        FileManager.getInstance().saveData(); // 데이터를 파일에 저장
    }
}
