package cn.lynx.ctripmall.model;

import java.util.List;

public abstract class Refund {
	
	protected long refundApplyId;
	protected long timestamp;
	protected int operateType;
	protected OrderInfo orderInfo;
	protected String remark;
	protected FlowInfo flowInfo;
	protected List<Product> productList;

	public long getRefundApplyId() {
		return refundApplyId;
	}

	public void setRefundApplyId(long refundApplyId) {
		this.refundApplyId = refundApplyId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public FlowInfo getFlowInfo() {
		return flowInfo;
	}

	public void setFlowInfo(FlowInfo flowInfo) {
		this.flowInfo = flowInfo;
	}
}
