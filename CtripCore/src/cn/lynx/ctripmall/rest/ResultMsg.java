package cn.lynx.ctripmall.rest;

public class ResultMsg {
	private int result;
	private String resultmessage;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getResultmessage() {
		return resultmessage;
	}

	public void setResultmessage(String resultmessage) {
		this.resultmessage = resultmessage;
	}
	
	@Override
	public String toString() {
		return "Status code [" + result + "] | message [" + resultmessage + "]";
	}
}
