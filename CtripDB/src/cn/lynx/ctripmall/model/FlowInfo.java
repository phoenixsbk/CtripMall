package cn.lynx.ctripmall.model;

public abstract class FlowInfo {
	protected String flowCompanyName;
	protected String flowTicketNumber;
	protected int flowStatus;
	protected String flowRemark;

	public String getFlowCompanyName() {
		return flowCompanyName;
	}

	public void setFlowCompanyName(String flowCompanyName) {
		this.flowCompanyName = flowCompanyName;
	}

	public String getFlowTicketNumber() {
		return flowTicketNumber;
	}

	public void setFlowTicketNumber(String flowTicketNumber) {
		this.flowTicketNumber = flowTicketNumber;
	}

	public int getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFlowRemark() {
		return flowRemark;
	}

	public void setFlowRemark(String flowRemark) {
		this.flowRemark = flowRemark;
	}
}
