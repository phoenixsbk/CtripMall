package cn.lynx.ctripmall.db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-29T01:22:03.778+0800")
@StaticMetamodel(FlowInfo.class)
public class FlowInfo_ extends CtripEntity_ {
	public static volatile SingularAttribute<FlowInfo, String> flowCompanyName;
	public static volatile SingularAttribute<FlowInfo, String> flowTicketNumber;
	public static volatile SingularAttribute<FlowInfo, Integer> flowStatus;
	public static volatile SingularAttribute<FlowInfo, String> flowRemark;
}