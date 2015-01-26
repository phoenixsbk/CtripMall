package cn.lynx.ctripmall.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import cn.lynx.ctripmall.model.Receiver;

@Entity
public class DBReceiver extends Receiver implements CtripEntity {
	@Id
	private String uuid;
	@Lob
	private String address;
	@Lob
	private String remark;

	@Override
	public String getUuid() {
		return this.uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
