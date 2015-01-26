package cn.lynx.ctripmall.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.lynx.ctripmall.model.Product;

@Entity
public class DBProduct extends Product implements CtripEntity {
	@Id
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
