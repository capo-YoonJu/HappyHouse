package happyhouse.model.dto;

public class Market {

	private String commName;
	private String commBranch;
	private String commCategory;
	private String commGuguncode;
	private String commAddress;
	private String commLng;
	private String commLat;
	
	public Market(String commName, String commBranch, String commCategory, String commGuguncode, String commAddress,
			String commLng, String commLat) {
		super();
		this.commName = commName;
		this.commBranch = commBranch;
		this.commCategory = commCategory;
		this.commGuguncode = commGuguncode;
		this.commAddress = commAddress;
		this.commLng = commLng;
		this.commLat = commLat;
	}
	
	public String getCommName() {
		return commName;
	}
	
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public String getCommBranch() {
		return commBranch;
	}
	public void setCommBranch(String commBranch) {
		this.commBranch = commBranch;
	}
	public String getCommCategory() {
		return commCategory;
	}
	public void setCommCategory(String commCategory) {
		this.commCategory = commCategory;
	}
	public String getCommGuguncode() {
		return commGuguncode;
	}
	public void setCommGuguncode(String commGuguncode) {
		this.commGuguncode = commGuguncode;
	}
	public String getCommAddress() {
		return commAddress;
	}
	public void setCommAddress(String commAddress) {
		this.commAddress = commAddress;
	}
	public String getCommLng() {
		return commLng;
	}
	public void setCommLng(String commLng) {
		this.commLng = commLng;
	}
	public String getCommLat() {
		return commLat;
	}
	public void setCommLat(String commLat) {
		this.commLat = commLat;
	}

	@Override
	public String toString() {
		return "상권 정보 [상호명 = " + commName + ", 지점명 = " + commBranch + ", 업종 = " + commCategory
				+ ", 동코드 = " + commGuguncode + ", 주소=" + commAddress + ", 경도 = " + commLng
				+ ", 위도 = " + commLat + "]";
	}

}
