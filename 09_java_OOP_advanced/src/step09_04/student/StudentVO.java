package step09_04.student;

public class StudentVO {
	
	private String id;
	private int num;
	private String name;
	
	public StudentVO(String id , int num , String name) {
		this.id = id;
		this.num = num;
		this.name = name;
	}	
	
	public void printOneInfo() {
		System.out.println("id: " + this.id + " / num: " + this.num + " / name: " + this.name);
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
