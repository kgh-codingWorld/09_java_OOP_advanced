package step09_01.atm_v1;

public class UserManager {
	
	User[] user = null; // User 타입의 배열 생성(아이디/계좌 개수/계좌번호/금액)
	int userCount = 0;
	
	void printAllUser() { // 어디에 쓰이는 거임?
		for(int i = 0; i < userCount; i++) {
			user[i].printAccount(); // i번째 user의 계좌 조회
		}
	}
	
	
	void addUser() {
		
		if (userCount == 0) { // 사용자가 없으면
			
			user = new User[1]; // 길이가 1인 사용자 배열 생성
			
			System.out.print("[가입] 아이디를 입력하세요 : ");
			String id = ATM.scan.next();
			
			user[userCount] = new User(); // 0번째 user index에 새로운 User 객체 생성
			user[userCount].id = id; // 0번째 user index의 id 필드에 입력받은 아이디 저장
			System.out.println("[메시지] ID : '" + id+ "' 가입 되었습니다.\n");
			userCount++; // userCount 1 증가
			
			
		}
		else { // 사용자가 있으면
			
			System.out.print("[가입] 아이디를 입력하세요 : ");
			String id = ATM.scan.next();
			
			boolean isDuple = false; // 중복 여부 확인: 중복 아님
			
			for (int i = 0; i < userCount; i++) { // 0부터 사용자 수만큼 i를 돌렸을 때
				if (user[i].id.equals(id)) { // i번째에 저장되어있는 id와 입력받은 아이디가 일치하면
					isDuple = true; // 중복
				}
			}
			
			System.out.println("isDuple: "+isDuple); // isDuple: true
			
			if (!isDuple) { // 중복이 아니면
				
				User[] temp = user;
				user = new User[userCount + 1];
				for(int i = 0; i < userCount; i++) {
					user[i] = temp[i];
				}
				temp = null;
				
				user[userCount] = new User();
				user[userCount].id = id;
				System.out.println("[메시지] ID : '" + id+ "' 가입 되었습니다.\n");
				userCount++;
				
			}
			else {
				System.out.println("[메시지] '"+ id + "'은 이미 가입된 아이디 입니다.\n");
			}
			
		}
		
	}
	
	
	User getUser(int idx) { // user index 반환(계좌생성 시 사용)
		return user[idx];
	}
	
	
	
	int logUser() {
		
		int identifier = -1;
		System.out.print("[입력] 아이디를 입력하세요 : ");
		String name = ATM.scan.next();
		
		for (int i = 0; i < userCount; i++) {
			if (name.equals(user[i].id)) {
				identifier = i;
				System.out.println("[" + user[identifier].id + "] 님 로그인.\n");
			}
		}
		
		return identifier;
		
	}
	
	
	void leave() {
		
		System.out.print("[입력] 탈퇴할 아이디를 입력하세요 : ");
		String name = ATM.scan.next();
		int identifier = -1;
		for (int i = 0; i < userCount; i++) { // 0 ~ 사용자수만큼 반복
			if (name.equals(user[i].id)) { // 돌려봤을 때 입력받은 아이디와 i번째 사용자의 아이디가 일치할 때
				identifier = i;	// 일치하는 i(숫자)를 identifier에 저장(identifier = 탈퇴할 아이디가 담긴 사용자의 index)
			}
		}
		
		if(identifier == -1) { // identifier가 -1이라면
			System.out.println("[메시지] 아이디를 다시 확인하세요.");
			return; // 값을 반환하지 않고 종료
		}
		// identifier가 -1이 아닌 다른 수일 때
		System.out.println("ID : '" + user[identifier].id + "' 가 탈퇴되었습니다.");
		
		User[] temp = user; // 기존 User 배열을 임시로 저장
		user = new User[userCount - 1]; // 기존 User 배열에 사용자수에서 -1한 수만큼의 길이인 새로운 배열 생성
		
		for(int i = 0; i < identifier; i++) { // 탈퇴할 사용자 이전의 데이터를 복사, 0 ~ identifier - 1
			user[i] = temp[i]; // 탈퇴할 사용자 이전(identifier - 1)의 모든 데이터를 새로 만든 배열에 복사
		}
		for(int i =identifier; i < userCount-1; i++) { // 탈퇴할 사용자 이후의 데이터를 복사, identifier ~ userCount - 2(끝까지)
			user[i] =temp[i + 1]; // 탈퇴할 사용자 이후의 데이터(temp[i + 1]를 복사해서 새로운 배열에 저장
		}
		
		userCount--;
		
	}
	
}
