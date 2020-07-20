package ${package}.entity;

import java.io.Serializable;
${import}

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "${tableName}")
@SuppressWarnings("serial")
public class ${entityName} implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	<#list list as v>
	<#if v.column != 'id'>
	<#if (v.remark)??>
	/**${v.remark}**/
	</#if>
	private ${v.typeName} ${v.column};
	</#if>
	</#list>
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	<#list list as v>
	<#if v.column != 'id'>
	<#if (v.remark)??>
	/**get ${v.remark}**/
	</#if>
	public ${v.typeName} get${v.upperColumn}() {
		return ${v.column};
	}
	<#if (v.remark)??>
	/**set ${v.remark}**/
	</#if>
	public void set${v.upperColumn}(${v.typeName} ${v.column}) {
		this.${v.column} = ${v.column};
	}
	</#if>
	</#list>
}
