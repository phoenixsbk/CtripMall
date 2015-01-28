package cn.lynx.ctripmall.db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-29T01:22:03.782+0800")
@StaticMetamodel(Receiver.class)
public class Receiver_ extends CtripEntity_ {
	public static volatile SingularAttribute<Receiver, String> contactName;
	public static volatile SingularAttribute<Receiver, String> mobilePhone;
	public static volatile SingularAttribute<Receiver, String> email;
	public static volatile SingularAttribute<Receiver, String> provinceName;
	public static volatile SingularAttribute<Receiver, String> cityName;
	public static volatile SingularAttribute<Receiver, String> zoneName;
	public static volatile SingularAttribute<Receiver, String> address;
	public static volatile SingularAttribute<Receiver, String> remark;
}
