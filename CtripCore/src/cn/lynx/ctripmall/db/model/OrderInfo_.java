package cn.lynx.ctripmall.db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-31T03:19:56.525+0800")
@StaticMetamodel(OrderInfo.class)
public class OrderInfo_ extends CtripEntity_ {
	public static volatile SingularAttribute<OrderInfo, Long> orderId;
	public static volatile SingularAttribute<OrderInfo, Long> timestamp;
	public static volatile SingularAttribute<OrderInfo, Long> bookingDate;
	public static volatile SingularAttribute<OrderInfo, Double> invoicePrice;
	public static volatile SingularAttribute<OrderInfo, String> invoiceContent;
	public static volatile SingularAttribute<OrderInfo, String> invoiceHead;
	public static volatile ListAttribute<OrderInfo, Product> productList;
	public static volatile SingularAttribute<OrderInfo, FlowInfo> flowInfo;
	public static volatile SingularAttribute<OrderInfo, Receiver> receiver;
}
