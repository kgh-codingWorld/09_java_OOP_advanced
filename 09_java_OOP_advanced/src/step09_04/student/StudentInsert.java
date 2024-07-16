package step09_04.student;
public class StudentInsert {
	
	private StudentDAO studentDAO;
	
	public StudentInsert(StudentDAO stDAO) {
		this.studentDAO = stDAO;
	}	
	
	public void insert(StudentVO studentVO) {
		String id = studentVO.getId();
		if (checkId(id)) {
			studentDAO.insert(studentVO);
		}
		else {
			System.out.println("중복아이디 입니다");
		}
	}
	
	
	public boolean checkId(String id) {
		StudentVO studentVO = studentDAO.select(id);
		return studentVO == null? true:false;
	}
	
}
