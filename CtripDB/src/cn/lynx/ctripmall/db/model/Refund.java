package cn.lynx.ctripmall.db.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Refund extends CtripEntity {
	private long refundApplyId;
	private long timestamp;
	private int operateType;
	@OneToOne
	private Order order;
	private String remark;
	@OneToMany
	private List<Product> productList;

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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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
}
