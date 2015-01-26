package cn.lynx.ctripmall.db.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import cn.lynx.ctripmall.model.FlowInfo;
import cn.lynx.ctripmall.model.OrderInfo;
import cn.lynx.ctripmall.model.Product;
import cn.lynx.ctripmall.model.Receiver;

@Entity
public class DBOrderInfo extends OrderInfo implements CtripEntity {
	@Id
	private String uuid;
	@OneToMany
	private List<Product> productList;
	@OneToOne
	private Receiver receiver;
	@OneToOne
	private FlowInfo flowInfo;
	
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public String getUuid() {
		return this.uuid;
	}
}
