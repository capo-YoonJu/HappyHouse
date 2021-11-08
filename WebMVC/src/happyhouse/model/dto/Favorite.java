package happyhouse.model.dto;

	public class Favorite {
		
		private int no;
		private int user_no;
		private String dongcode;
		private String gu;
		private String dong;

		public Favorite(int no, String dongcode, String gu, String dong) {
			super();
			this.no = no;
			this.dongcode = dongcode;
			this.gu = gu;
			this.dong = dong;
		}

		public Favorite(String dongcode, String gu, String dong) {
			super();
			this.dongcode = dongcode;
			this.gu = gu;
			this.dong = dong;
		}

		public int getNo() {
			return no;
		}

		public void setNo(int no) {
			this.no = no;
		}

		public int getUser_no() {
			return user_no;
		}

		public void setUser_no(int user_no) {
			this.user_no = user_no;
		}

		public String getDongcode() {
			return dongcode;
		}

		public void setDongcode(String dongcode) {
			this.dongcode = dongcode;
		}

		public String getGu() {
			return gu;
		}

		public void setGu(String gu) {
			this.gu = gu;
		}

		public String getDong() {
			return dong;
		}

		public void setDong(String dong) {
			this.dong = dong;
		}

		@Override
		public String toString() {
			return "Favorite [no=" + no + ", user_no=" + user_no + ", dongcode=" + dongcode + ", gu=" + gu + ", dong="
					+ dong + "]";
		}
		
	}