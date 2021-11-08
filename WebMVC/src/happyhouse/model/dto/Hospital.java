package happyhouse.model.dto;
// DTO : Data Transfer Object
// VO : Value Object

public class Hospital {

	private int no;
	
	private String sido;
	private String gungu;
	private String name;
	private String address;
	private String type;
	private String tel;
	private String guguncode;
	
	public Hospital(String sido, String gungu, String name, String address, String type, String tel,
			String guguncode) {
		super();
		this.sido = sido;
		this.gungu = gungu;
		this.name = name;
		this.address = address;
		this.type = type;
		this.tel = tel;
		this.guguncode = guguncode;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getGungu() {
		return gungu;
	}

	public void setGungu(String gungu) {
		this.gungu = gungu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getGuguncode() {
		return guguncode;
	}

	public void setGuguncode(String guguncode) {
		this.guguncode = guguncode;
	}

	@Override
	public String toString() {
		return "의료기관 정보 [ 시=" + sido + ", 구군=" + gungu + ", 이름=" + name + ", 주소="
				+ address + ", 전화번호 = " + tel + ", 동코드 = " + guguncode + "]";
	}

}
