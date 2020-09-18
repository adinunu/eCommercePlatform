package id.org.test.ms.shared.auth;

public enum LoginID {

	USERNAME(1), EMAIL(2), MOBILE(3); 
	
	private final int id;
	
	LoginID(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
