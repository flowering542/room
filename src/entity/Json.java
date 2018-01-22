package entity;

public class Json {

	   private String msg;
	   private boolean success;
	   private String url;
	   
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getMsg() {
				return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}

}
