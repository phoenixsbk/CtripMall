package cn.lynx.ctripmall.db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-31T03:19:56.538+0800")
@StaticMetamodel(Refund.class)
public class Refund_ extends CtripEntity_ {
	public static volatile SingularAttribute<Refund, Long> refundApplyId;
	public static volatile SingularAttribute<Refund, Long> timestamp;
	public static volatile SingularAttribute<Refund, Integer> operateType;
	public static volatile SingularAttribute<Refund, OrderInfo> orderInfo;
	public static volatile SingularAttribute<Refund, String> remark;
	public static volatile SingularAttribute<Refund, FlowInfo> flowInfo;
	public static volatile ListAttribute<Refund, Product> productList;
}
