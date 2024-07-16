package step09_04.student;

public class Controller {
	
	private StudentDAO stDAO;
	private StudentInsert insert;
	private StudentSelect select;	
	private StudentSelectAll selectAll;
	
	public Controller() {
		stDAO = new StudentDAO();	
		insert = new StudentInsert(stDAO);
		select = new StudentSelect(stDAO);
		selectAll = new StudentSelectAll(stDAO);
	}
	
	public StudentDAO getStDAO() {
		return stDAO;
	}
	
	public void setStDAO(StudentDAO stDAO) {
		this.stDAO = stDAO;
	}
	
	public StudentInsert getInsert() {
		return insert;
	}
	
	public void setInsert(StudentInsert insert) {
		this.insert = insert;
	}
	
	public StudentSelect getSelect() {
		return select;
	}
	
	public void setSelect(StudentSelect select) {
		this.select = select;
	}
	
	public StudentSelectAll getSelectAll() {
		return selectAll;
	}
	
	public void setSelectAll(StudentSelectAll selectAll) {
		this.selectAll = selectAll;
	}
	
}
