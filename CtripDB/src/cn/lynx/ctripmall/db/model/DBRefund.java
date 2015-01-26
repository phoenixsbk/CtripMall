package cn.lynx.ctripmall.db.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import cn.lynx.ctripmall.model.FlowInfo;
import cn.lynx.ctripmall.model.OrderInfo;
import cn.lynx.ctripmall.model.Product;
import cn.lynx.ctripmall.model.Refund;

@Entity
public class DBRefund extends Refund implements CtripEntity {
	@Id
	private String uuid;
	@OneToOne
	private OrderInfo orderInfo;
	@OneToMany
	private List<Product> productList;
	@OneToOne
	private FlowInfo flowInfo;

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return this.uuid;
	}
}
