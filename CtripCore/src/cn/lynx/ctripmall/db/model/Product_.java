package cn.lynx.ctripmall.db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-31T03:19:56.529+0800")
@StaticMetamodel(Product.class)
public class Product_ extends CtripEntity_ {
	public static volatile SingularAttribute<Product, String> exProductId;
	public static volatile SingularAttribute<Product, String> exSubProductId;
	public static volatile SingularAttribute<Product, String> productName;
	public static volatile SingularAttribute<Product, Long> quantity;
	public static volatile SingularAttribute<Product, Double> price;
	public static volatile SingularAttribute<Product, Integer> experience;
	public static volatile SingularAttribute<Product, Double> settlePrice;
	public static volatile SingularAttribute<Product, String> color;
	public static volatile SingularAttribute<Product, String> size;
}
