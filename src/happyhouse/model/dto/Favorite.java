package happyhouse.model.dto;

	public class Favorite {
		
		private int no;
		private int user_no;
		private String dongcode;
		private String dong;
		private String gu;

		public Favorite(int no, String dongcode, String dong, String gu) {
			super();
			this.no = no;
			this.dongcode = dongcode;
			this.dong = dong;
			this.gu = gu;
		}

		public Favorite(String dongcode, String dong, String gu) {
			super();
			this.dongcode = dongcode;
			this.dong = dong;
			this.gu = gu;
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

		public String getDong() {
				return dong;
			}

		public void setDong(String dong) {
				this.dong = dong;
			}

		public String getGu() {
				return gu;
			}

		public void setGu(String gu) {
				this.gu = gu;
			}

		@Override
			public String toString() {
				return "관심지역 정보 [ 동코드 = " + dongcode + ", 동 = " + dong + ", 구군 = " + gu + "]";
			}

	}