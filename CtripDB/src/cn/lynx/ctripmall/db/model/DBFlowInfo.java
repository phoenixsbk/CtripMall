package cn.lynx.ctripmall.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.lynx.ctripmall.model.FlowInfo;

@Entity
public class DBFlowInfo extends FlowInfo implements CtripEntity {
	@Id
	private String uuid;

	@Override
	public String getUuid() {
		return this.uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
