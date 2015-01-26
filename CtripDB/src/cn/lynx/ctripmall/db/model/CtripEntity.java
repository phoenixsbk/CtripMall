package cn.lynx.ctripmall.db.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface CtripEntity {
	public String getUuid();

	public void setUuid(String uuid);
}
