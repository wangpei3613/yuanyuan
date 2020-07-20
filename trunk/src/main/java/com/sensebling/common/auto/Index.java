package com.sensebling.common.auto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.sensebling.common.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Index {
	
	/**
	 * 配置参数
	 */
	private String tableName = "ARCH_ROOM_RACK_BOX";//表名，例：sen_user
	private String packageName = "com.sensebling.archive";//包名，例：com.sensebling.system
	private boolean isCover = false;//文件已存在是否覆盖
	private boolean isCut = false;//实体名是否截取掉第一个下划线前面的
	
	private String entityName;
	private String shortTableName;
	private String propName;
	private String rootPath;
	private String packagePath;
	private String sourcePath;
	private List<Column> cols = new ArrayList<Column>();
	
	public Index(){
		tableName = tableName.toLowerCase();
		if(isCut) {
			shortTableName = tableName.substring(tableName.indexOf("_")+1);
		}else {
			shortTableName = tableName;
		}
		entityName = Util.underline2Camel(shortTableName, false);
		propName = Util.underline2Camel(shortTableName, true);
		rootPath = System.getProperty("user.dir");
		packagePath = rootPath+"/src/main/java/"+packageName.replace(".", "/");
		sourcePath = rootPath+"/src/main/java/com/sensebling/common/auto/ftl";
	}

	public static void main(String[] args) throws Exception {
		new Index().start();
	}
	
	protected void start() throws Exception {
		getTableDetail();
		if(cols.size() == 0) {
			throw new Exception("未找到表相关信息");
		}
		createEntity();
		createService();
		createServiceImpl();
		createController();
		System.out.println("操作完成，请刷新查看代码.");  
	}
	

	protected void createEntity() {
		Set<String> $import = new HashSet<String>();
		for(Column col:cols) {
			if(col.getColumn().equalsIgnoreCase("id")) {
				continue;
			}
			$import.add("import "+col.getTypePackageName()+";");
		}
		
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("import", Util.collectionToStrings($import, "\n"));
		m.put("list", cols);
		generateFileByTemplate("entity.ftl", packagePath+"/entity/"+entityName+".java", m);
	}

	protected void createService() {
		generateFileByTemplate("svc.ftl", packagePath+"/service/"+entityName+"Svc.java", null);
	}
	
	protected void createServiceImpl() {
		generateFileByTemplate("impl.ftl", packagePath+"/service/impl/"+entityName+"SvcImpl.java", null);
	}
	
	protected void createController() {
		generateFileByTemplate("ctrl.ftl", packagePath+"/controller/"+entityName+"Ctrl.java", null);
	}
	
	protected void getTableDetail() throws Exception {
		Properties properties = new Properties();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(rootPath+"/src/main/resources/prop/dev/jdbc.properties"));
	    properties.load(bufferedReader);
	    String driverClassName = properties.getProperty("db.driverClassName");
	    String url = properties.getProperty("db.url");
	    String username = properties.getProperty("db.username");
	    String password = properties.getProperty("db.password");
	    String tabschema = "";
	    if(url.indexOf("currentSchema") > -1) {
	    		tabschema = url.substring(url.indexOf("currentSchema")+14, url.length()-1);
	    }
	    StringBuffer sql = new StringBuffer("select t.colno as num, lower(t.colname) as column, t.remarks as remark, lower(t.typename) as type, t.length as length from syscat.columns t where tabname=upper('"+tableName+"')");
	    if(StringUtil.notBlank(tabschema)) {
	    		sql.append(" and tabschema=upper('"+tabschema+"') ");
	    }
	    sql.append("order by num asc");
	    
	    Class.forName(driverClassName);
	    Connection con = DriverManager.getConnection(url,username,password);
	    Statement statement = con.createStatement();
	    ResultSet rs = statement.executeQuery(sql.toString());
	    while(rs.next()){
	    		Column col = new Column();
	    		col.setNum(rs.getInt("num"));
	    		col.setColumn(rs.getString("column"));
	    		col.setRemark(rs.getString("remark"));
	    		col.setType(rs.getString("type"));   
	    		cols.add(col);
	    }
	    rs.close();
	    con.close();
	}
	
	protected void generateFileByTemplate(String templateName, String target, Map<String, Object> data) {
		try {
			File file = new File(target);
			if(!file.exists() || isCover) {
				File folder = file.getParentFile();
				if(!folder.exists()) {
					folder.mkdirs();
				}
				
				if(data == null) {
					data = new HashMap<String, Object>();
				}
				data.put("package", packageName);
				data.put("entityName", entityName);
				data.put("propName", propName);
				data.put("mapping", "/"+shortTableName.replace("_", "/"));
				data.put("tableName", shortTableName);
				
				Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
				cfg.setDirectoryForTemplateLoading(new File(sourcePath));
				cfg.setDefaultEncoding("UTF-8");
				Template template = cfg.getTemplate(templateName);
				FileOutputStream fos = new FileOutputStream(target);
				Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
		        template.process(data,out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}