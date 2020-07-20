package com.sensebling.common.auto;

import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("unused")
public class Column {
	private int num;
	private String column;
	private String remark;
	private String type;
	private String typeName;
	private String typePackageName;
	private String upperColumn;
	
	public static Map<String, String[]> typeData;
	
	static {
		typeData = new HashMap<String, String[]>();
		typeData.put("bigint", new String[] {"Long","java.lang.Long"});
		typeData.put("char", new String[] {"String","java.lang.String"});
		typeData.put("graphic", new String[] {"String","java.lang.String"});
		typeData.put("clob", new String[] {"String","java.lang.String"});
		typeData.put("dbclob", new String[] {"String","java.lang.String"});
		typeData.put("date", new String[] {"Date","java.sql.Date"});
		typeData.put("decimal", new String[] {"BigDecimal","java.math.BigDecimal"});
		typeData.put("double", new String[] {"Double","java.lang.Double"});
		typeData.put("float", new String[] {"Double","java.lang.Double"});
		typeData.put("integer", new String[] {"Integer","java.lang.Integer"});
		typeData.put("long varchar", new String[] {"String","java.lang.String"});
		typeData.put("long vargraphic", new String[] {"String","java.lang.String"});
		typeData.put("numeric", new String[] {"BigDecimal","java.math.BigDecimal"});
		typeData.put("real", new String[] {"Float","java.lang.Float"});
		typeData.put("smallint", new String[] {"Integer","java.lang.Integer"});
		typeData.put("time", new String[] {"Time","java.sql.Time"});
		typeData.put("timestamp", new String[] {"Timestamp","java.sql.Timestamp"});
		typeData.put("varchar", new String[] {"String","java.lang.String"});
		typeData.put("vargraphic", new String[] {"String","java.lang.String"});
	}
	public String getTypeName() {
		if(typeData.containsKey(type)) {
			return typeData.get(type)[0];
		}
		return "String";
	}
	public String getTypePackageName() {
		if(typeData.containsKey(type)) {
			return typeData.get(type)[1];
		}
		return "java.lang.String";
	}
	public String getUpperColumn() {
		return column.substring(0, 1).toUpperCase() + column.substring(1);
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setTypePackageName(String typePackageName) {
		this.typePackageName = typePackageName;
	}
	public void setUpperColumn(String upperColumn) {
		this.upperColumn = upperColumn;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
