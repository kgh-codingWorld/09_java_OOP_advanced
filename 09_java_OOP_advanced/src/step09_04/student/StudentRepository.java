package step09_04.student;

import java.util.HashMap;

public class StudentRepository {

	private static HashMap<String, StudentVO> stDB = new HashMap<String , StudentVO>();

	public static HashMap<String, StudentVO> getStDB() {
		return stDB;
	}

	public static void setStDB(HashMap<String, StudentVO> stDB) {
		StudentRepository.stDB = stDB;
	}	
	
}
