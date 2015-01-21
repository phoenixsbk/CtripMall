package cn.lynx.ctripmall.db;

public class CtripDBMgr {
	private static CtripDBMgr instance = new CtripDBMgr();
	
	private CtripDBMgr() {
	}
	
	public static CtripDBMgr getInstance() {
		return instance;
	}

}
