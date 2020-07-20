package com.sensebling.common.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.sql.JoinType;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.sensebling.common.annotation.SenTable;
import com.sensebling.common.dao.BasicsDao;
import com.sensebling.common.util.BeanTransformerAdapter;
import com.sensebling.common.util.ModelUtil;
import com.sensebling.common.util.MyServiceException;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.common.util.UUIDGenerator;

/**
 * 数据库操作实现类
 * 
 * @author 
 * @date 2014-10-12
 * @param <T>
 */
@Repository("baseDao")
@Lazy(true)
@Scope("prototype")
public class BasicsDaoImpl<T extends Serializable> implements BasicsDao<T> {
	// 当前泛型类
	private Class<T> entityClass;
	// 根据id删除数据hql
	private String HQL_DELETE_BYIDS;
	// 查询所有
	private String HQL_LIST_ALL;

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
		HQL_DELETE_BYIDS = "delete " + this.entityClass.getSimpleName()+ " t where t.id in ";
		HQL_LIST_ALL = "from " + this.entityClass.getSimpleName() +" t";
		
		int sessionFactoryOrder = 0;
		if(entityClass != null) {
			SenTable annotation = entityClass.getAnnotation(SenTable.class);
			if(annotation != null) {
				sessionFactoryOrder = annotation.sessionFactoryOrder();
			}
		}
		if(sessionFactoryOrder == 1) {
			this.sessionFactory = sf2;
		}else {
			this.sessionFactory = sf1;
		}
	}
	private SessionFactory sessionFactory;
	@Resource(name="sessionFactory") 
	private SessionFactory sf1;
	@Resource(name="sessionFactory")
	private SessionFactory sf2;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		
		Session session=sessionFactory.getCurrentSession();
//		session.setCacheMode(CacheMode.REFRESH);
		return session;
	}
	private Query getHqlQuery(String hql,boolean cacheable)
	{
		Query q = this.getSession().createQuery(hql);
		q.setCacheable(cacheable);
		return q;
	}
	private SQLQuery getSqlQuery(String sql,boolean cacheable)
	{
		SQLQuery q = this.getSession().createSQLQuery(sql);
//		q.setCacheable(true);
		return q;
	}
	/**
	 * 创建sql，封装成对象
	 * @param sql
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SQLQuery getSqlQueryToEntity(String sql)
	{
		SQLQuery q = this.getSession().createSQLQuery(sql);
//		q.setResultTransformer(Transformers.aliasToBean(this.entityClass));
		q.setResultTransformer(new BeanTransformerAdapter(entityClass));
		return q;
	}
	/**
	 * 创建sql，封装成指定对象
	 * @param sql
	 * @param entityName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SQLQuery getSqlQueryToEntity(String sql,String entityName)
	{
		SQLQuery q = this.getSession().createSQLQuery(sql);
//		q.setResultTransformer(Transformers.aliasToBean(ModelUtil.getClassByName(entityName)));
		q.setResultTransformer(new BeanTransformerAdapter(ModelUtil.getClassByName(entityName)));
		return q;
	}
	
	
	/** ============公共方法================ */
	/**
	 * 执行hql，传参 Object[]
	 * 
	 * @param hql
	 * @param params
	 * @return integer
	 */
	public Integer executeHQL(String hql, Object... params) {
		Query query = getHqlQuery(hql,false);
		setQueryParams(query, params);
		return query.executeUpdate();
	}

	/**
	 * 执行hql,传参List
	 * 
	 * @param hql
	 * @param params
	 * @return integer
	 */
	public Integer executeHQL(String hql, List<Object> params) {
		Query query = getHqlQuery(hql,false);
		setQueryParams(query, params);
		return query.executeUpdate();
	}

	/**
	 * 执行简单hql
	 * 
	 * @param hql
	 * @return Integer
	 */
	public Integer executeHQL(String hql) {
		return getHqlQuery(hql,false).executeUpdate();
	}

	/**
	 * 执行sql，传参 Object[]
	 * 
	 * @param sql
	 * @param params
	 * @return integer
	 */
	public Integer executeSQL(String sql, Object... params) {
		SQLQuery query = getSqlQuery(sql,false);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.executeUpdate();
	}

	/**
	 * 执行sql，传参 List
	 * 
	 * @param sql
	 * @param params
	 * @return integer
	 */
	public Integer executeSQL(String sql, List<Object> params) {
		SQLQuery query = getSqlQuery(sql,false);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		return query.executeUpdate();
	}

	/**
	 * 执行简单sql
	 * 
	 * @param sql
	 * @return Integer
	 */
	public Integer executeSQL(String sql) {
		return getSqlQuery(sql,false).executeUpdate();
	}

	/**
	 * 设置参数
	 * 
	 * @param params
	 */
	private void setQueryParams(Query query, List<Object> params) {
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
	}

	/**
	 * 设置参数
	 * 
	 * @param params
	 */
	private void setQueryParams(Query query, Object... params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}

	/**
	 * 添加查询条件
	 * YF
	 * 2017-11-28 下午03:07:42
	 * @param sql
	 * @param params
	 */
	private String  addCondition(String sql,Map<String,Object> params){
		if(null!=params&&!params.isEmpty()){
		    Set<String> sets = params.keySet();
		    StringBuffer sqls = new StringBuffer();
		    sqls.append(" where ");
		    for (String string : sets) {
		    	if(string.contains("like_anywhere")){
		    		String str = string.split("like_anywhere")[0];
		    		sqls.append(str+" like :"+string+" and");
		    	}else if(string.contains("like_start")){
		    		String str = string.split("like_start")[0];
		    		sqls.append(str+" like  :"+string+" and");
		    	}else if(string.contains("like_end")){
		    		String str = string.split("like_end")[0];
		    		sqls.append(str+" like :"+string+" and");
		    	}else if(string.contains("in")){
		    		String str = string.split("in")[0];
		    		sqls.append(str+" in (:"+string+") and");
		    	}else if(string.contains("not_in")){
		    		String str = string.split("not_in")[0];
		    		sqls.append(str+" not_in (:"+string+") and");
		    	}else if(string.contains("equal")){
		    		String str = string.split("equal")[0];
		    		sqls.append(str+" = :"+string+" and");
		    	}else if(string.contains("not_equal")){
		    		String str = string.split("not_equal")[0];
		    		sqls.append(str+" != :"+string+" and");
		    	}else if(string.contains("large")){
		    		String str = string.split("large")[0];
		    		sqls.append(str+" > :"+string+" and");
		    	}else if(string.contains("large_equal")){
		    		String str = string.split("large_equal")[0];
		    		sqls.append(str+" >= :"+string+" and");
		    	}else if(string.contains("small_equal")){
		    		String str = string.split("small_equal")[0];
		    		sqls.append(str+" <= :"+string+" and");
		    	}else if(string.contains("small")){
		    		String str = string.split("small")[0];
		    		sqls.append(str+" < :"+string+" and");
		    	}
		    	
			}
		    sql =sql +	sqls.substring(0, sqls.lastIndexOf("and"));
		}
		System.out.println(sql);
		return sql;
	}
	
	
	/**
	 * 设置参数
	 * YF
	 * 2017-11-28 下午02:57:18
	 * @param query
	 * @param params
	 */
	private void setQueryParams(Query query, Map<String,Object> params) {
		if (params != null && !params.isEmpty()) {
//			for (int i = 0; i < params.size(); i++) {
//				query.setParameter(i, params.get(i));
//			}
			  Set<String> sets = params.keySet();
			  for (String string : sets) {
				  System.out.println(string+" params.get(string)="+params.get(string));
				  if(string.contains("like_anywhere")){
//			    		String str = string.split("like_anywhere")[0];
//			    		sqls.append(str+" like :"+string+" and");
			    		query.setParameter(string, "%"+params.get(string)+"%");
			    	}else if(string.contains("like_start")){
			    		query.setParameter(string, "%"+params.get(string) );
			    	}else if(string.contains("like_end")){
			    		query.setParameter(string,  params.get(string)+"%" );
			    	}else{
			    		query.setParameter(string, params.get(string));
			    	}
				   
			}
		}
	}
	
	/**
	 * 设置修改时 set后的参数
	 * YF
	 * 2017-11-29 上午09:43:06
	 * @param sql
	 * @param params
	 * @throws MyServiceException 
	 */
	private String setUpdateParams(String sql,Map<String,Object> params) throws MyServiceException{
		if(null!=params&&!params.isEmpty()){
			Set<String> sets = params.keySet();
			StringBuffer sqls = new StringBuffer();
			for (String string : sets) {
				if(!string.contains("equal")){
					throw new MyServiceException("字段的操作符必须是equal");
//					continue;
				}
				String str = string.split("equal")[0];
	    		sqls.append(str+" = :"+string+" ,");
			}
			 sql =sql +	sqls.substring(0, sqls.lastIndexOf(","));
		}
		return sql;
	}
	
	/** ===================基本操作开始====================* */
	/**
	 * 保存对象信息
	 * 
	 * @param t
	 *            对象信息
	 * @return Serializable
	 */
	public Serializable save(T t) {
		return getSession().save(t);
	}
	public void save(List<T> list) {
		if(list!=null && list.size()>0){
			Session session = getSession();
			for(int i=0;i<list.size();i++){
				session.save(list.get(i));
			}
		}
	}

	/**
	 * 修改实体
	 * 
	 * @param t
	 */
	public void update(T t) {
		getSession().update(t);
	}
	/**
	 * 修改实体
	 * 
	 * @param t
	 */
	public void merge(T t) {
		getSession().merge(t);
	}

	/**
	 * 使用 HQL修改对象信息
	 * 
	 * @param hql
	 * @param Object[]
	 *            objects
	 * @return Integer
	 */
	public Integer updateByHQL(String hql, Object[] objects) {
		return executeHQL(hql, objects);
	}

	/**
	 * 使用hql修改
	 * 
	 * @param hql
	 * @param List
	 * @return Integer
	 */
	public Integer updateByHQL(String hql, List<Object> params) {
		return executeHQL(hql, params);
	}

	/**
	 * 使用hql修改 无参
	 * 
	 * @param hql
	 * @return Integer
	 */
	public Integer updateByHQL(String hql) {
		return executeHQL(hql);
	}

	/**
	 * 使用SQL修改
	 * 
	 * @param sql
	 * @param object[]
	 *            objects
	 * @return Integer
	 */
	public Integer updateBySQL(String sql, Object... objects) {
		return executeSQL(sql, objects);

	}

	/**
	 * 使用SQL修改
	 * 
	 * @param sql
	 * @param List
	 *            <Object> params
	 * @return Integer
	 */
	public Integer updateBySQL(String sql, List<Object> params) {
		return executeSQL(sql, params);

	}

	/**
	 * 使用sql修改 无参
	 * 
	 * @param sql
	 * @return Integer
	 */
	public Integer updateBySQL(String sql) {
		return executeSQL(sql);

	}

	/**
	 * 删除对象信息
	 * 
	 * @param t
	 *            对象
	 */
	public void delete(T t) {
		getSession().delete(t);
	}

	/**
	 * 删除对象信息
	 * 
	 * @param id
	 *            对象id
	 * @return int
	 */
	public int delete(String id) {
		T t=get(id);//先加载对象再删除才能级联删除
		if(t==null)
			return 0;
		delete(t);
		return 1;
	}
	/**
	 * 级联删除,由于直接使用hql删除不会级联删除,此方法先持久化查询出要删除的对象然后再调用session的delete方法删除
	 * 这样可以级联删除会有效
	 * @param ids 多个或单个id
	 * @return
	 */
	private int deleteByCascade(String idOrids)
	{
		if(StringUtil.isBlank(idOrids))
			return 0;
		QueryParameter qp=new QueryParameter();
		qp.addParamter("id", idOrids, QueryCondition.in);
		List<T> list=findAll(qp);
		for(T t:list)
			delete(t);
		return list.size();
	}
	private int deleteByCascade(String[] idOrids)
	{
		if(idOrids.length==0)
			return 0;
		QueryParameter qp=new QueryParameter();
		qp.addParamter("id", StringUtil.arrayIDToString(idOrids), QueryCondition.in);
		List<T> list=findAll(qp);
		for(T t:list)
			delete(t);
		return list.size();
	}
	/**
	 * 批量删除对象
	 * 
	 * @param String[] ids 传入id数据
	 * @param boolean cascade 是否需要级联删除（true：表示是）          
	 * @return int
	 */
	public int deleteByIds(String[] ids,boolean cascade) {
		if (ids.length > 0) 
		{
			if(cascade){
				return deleteByCascade(ids);
			}
			String strIds = StringUtil.arrayIDToString(ids);
			return getHqlQuery(HQL_DELETE_BYIDS + "(" + strIds + ")",false).executeUpdate();
		}
		return -1;

	}
	/**
	 * 批量删除对象
	 * 
	 * @param String ids 传入的id字符串
	 * @param boolean cascade 是否需要级联删除（true：表示是）
	 * @return int
	 */
	public int deleteByIds(String ids,boolean cascade) {
		if (StringUtil.notBlank(ids)) 
		{
			
			if(cascade){
				return deleteByCascade(ids);
			}	
			String strIds = StringUtil.change_in(ids);
			return getHqlQuery(HQL_DELETE_BYIDS + "(" + strIds + ") ",false).executeUpdate();
		}
		return 0;
	}
	
	/**
	 * 根据sql语句删除对象
	 * @param sql
	 * @param params
	 * @return 返回-1 表示删除失败
	 */
	public int deleteBySql(String sql,Object[] params){
		if (StringUtil.notBlank(sql)) {
			SQLQuery query=getSqlQuery(sql, false);
			this.setQueryParams(query, params);
			return query.executeUpdate();		
		}
		return -1;
		
	}

	/**
	 * 保存或者修改
	 * 
	 * @param entity
	 *            对象
	 */
	public void saveOrUpdate(T t) {
		this.getSession().saveOrUpdate(t);
	}
	
	public void saveOrUpdate(List<T> list) {
		if(list!=null && list.size()>0){
			Session session = getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	/**
	 * 获得对象信息，不用锁定
	 * 
	 * @param id
	 *            对象id
	 * @return entity
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSession().get(this.getEntityClass(), id);
	}
	@SuppressWarnings("unchecked")
	public T getBean(Serializable id) {
		try {
			T t1 = (T) getSession().get(this.getEntityClass(), id);
			Class<T> cls = this.getEntityClass();
			T t2 = cls.newInstance();
			BeanUtils.copyProperties(t1, t2);
			return t2;
		} catch (InstantiationException e) {
			e.printStackTrace();  
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public T get(Class<T> cl,Serializable id)
	{
		return (T)getSession().get(cl, id);
	}
	/**
	 * 获得实体信息，lock=true 表示锁定
	 * 
	 * @param id
	 *            对象id
	 * @param lock
	 *            是否锁定
	 * @return entity
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id, boolean lock) {
		if (lock) {
			return (T) getSession().get(getEntityClass(), id, LockOptions.UPGRADE);
		} else {
			return (T) getSession().get(getEntityClass(), id);
		}
	}

	/**
	 * 使用hql查询，得到一条记录
	 * 
	 * @param hql
	 * @param Object[] hql所需的参数，必须按照顺序
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T getByHQL(String hql, Object[] params) {
		Query q =  getHqlQuery(hql, false);
		setQueryParams(q, params);
		return (T)q.uniqueResult();
		/*List<T> l = this.find(hql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}*/
	}
	

	/**
	 * 使用hql查询，得到一条记录
	 * 
	 * @param hql
	 * @param list<Object> hql所需的参数，必须按照顺序
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T getByHQL(String hql, List<Object> params) {
		Query q =  getHqlQuery(hql, false);
		setQueryParams(q, params);
		return (T)q.uniqueResult();
		/*List<T> l = this.find(hql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}*/
	}

	/**
	 * 使用sql查询，得到一条数据
	 * 
	 * @param sql
	 * @param List
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T getBySQL(String sql, List<Object> params) {
		SQLQuery q =  getSqlQuery(sql, false);
		setQueryParams(q, params);
		return (T)q.uniqueResult();
		/*List<T> l = this.findBySQL(sql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}*/
	}
	/**
	 * sql查询，得到一个实体对象
	 * @param sql 需要执行的sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getBySQLEntity(String sql) {
		SQLQuery query = getSqlQueryToEntity(sql);
		return (T)query.uniqueResult();
	}
	/**
	 * sql查询，得到一个实体对象
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getBySQLEntity(String sql, List<Object> params) {
		SQLQuery query = getSqlQueryToEntity(sql);
		this.setQueryParams(query, params);
		return (T)query.uniqueResult();
	}
	
	/**
	 * sql查询，得到一个实体对象
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @param entityName 需要转换成的对象名称（类名）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getBySQLEntity(String sql, List<Object> params,String entityName) {
		SQLQuery query = getSqlQueryToEntity(sql,entityName);
		this.setQueryParams(query, params);
		return (T)query.uniqueResult();
		/*List<T> l = this.findBySQL(sql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}*/
	}
	
	public Object getBySQLEntity(String sql,String entityName, Object... params) {
		List<T> list = findBySQLEntity(sql, entityName, params);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	
	/**
	 * 使用sql查询，得到一条数据
	 * 
	 * @param sql
	 * @param Object[]
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T getBySQL(String sql, Object[] params) {
		
		SQLQuery query =  getSqlQuery(sql, false);
		this.setQueryParams(query, params);
		return (T)query.uniqueResult();
		/*List<T> l = this.findBySQL(sql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}*/
	}
	/**
	 *  使用sql查询，得到一条数据，封装成指定对象
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @param entityName 需要转换成的对象名（类名）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getBySQLEntity(String sql, Object[] params,String entityName) {
		SQLQuery query = getSqlQueryToEntity(sql,entityName);
		this.setQueryParams(query, params);
		return (T)query.uniqueResult();
		/*List<T> l = this.findBySQL(sql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}*/
	}
	@SuppressWarnings("unchecked")
	@Override
	public <S>S getBySQLEntity(String sql, Class<S> cls,Object... params){
		SQLQuery query = getSqlQueryToEntity(sql,cls.getName());
		this.setQueryParams(query, params);
		return (S)query.uniqueResult();
	}
	/**
	 * 使用sql查询，得到一条数据，封装成对象,默认
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public T getBySQLEntity(String sql, Object[] params) {
		SQLQuery query = getSqlQueryToEntity(sql);
		this.setQueryParams(query, params);
		return (T)query.uniqueResult();
		/*List<T> l = this.findBySQL(sql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}*/
	}
	/**
	 * 查询列表
	 * 
	 * @param hql
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hql) {
		Query query = getHqlQuery(hql, false);
		return query.list();
	}

	/**
	 * 查询列表
	 * 
	 * @param hql
	 * @param Object[]
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object[] param) {
		Query q =  getHqlQuery(hql, false);
		setQueryParams(q, param);
		List<T> list=q.list();
		return list;
	}

	/**
	 * 查询列表
	 * 
	 * @param hql
	 * @param List
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, List<Object> param) {
		Query q =  getHqlQuery(hql, false);
		setQueryParams(q, param);
		return q.list();
	}

	/**
	 * 使用sql查询，返回列表
	 * 
	 * @param sql
	 * @param List
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySQL(String sql, List<Object> param) {
		SQLQuery q =  getSqlQuery(sql, false);
		setQueryParams(q, param);
		return q.list();
	}
	//-------------------------------------------------------------------------------------------------------------
	/**
	 * sql查询，把列表中的信息转换成对象
	 * @param sql 需要执行的sql
	 * @param param 参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySQLEntity(String sql, List<Object> param) {
		SQLQuery query = getSqlQueryToEntity(sql);
		setQueryParams(query, param);
		return query.list();
	}
	/**
	 * sql查询，把列表中的信息转换成指定的对象
	 * @param sql 需要执行的sql
	 * @param param 参数
	 * @param entityName 需要转换成的对象名称（类名）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySQLEntity(String sql, List<Object> param,String entityName) {
		SQLQuery query = getSqlQueryToEntity(sql,entityName);
		setQueryParams(query, param);
		return query.list();
	}
	

	/**
	 * 使用sql查询，返回列表
	 * 
	 * @param sql
	 * @param Object[]
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySQL(String sql, Object[] param) {
		SQLQuery q = getSqlQuery(sql, false);
		setQueryParams(q, param);
		return q.list();
	}
	/**
	 * 使用sql查询，返回列表,并且把list中的信息封装成对象
	 * @param sql 需要执行的sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySQLEntity(String sql) {
		SQLQuery query = getSqlQueryToEntity(sql);
		return query.list();
	}
	/**
	 * 使用sql查询，返回列表,并且把list中的信息封装成对象
	 * @param sql 需要执行的sql
	 * @param param  参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySQLEntity(String sql, Object[] param) {
		SQLQuery query = getSqlQueryToEntity(sql);
		setQueryParams(query, param);
		return query.list();
	}
	/**
	 * 使用sql查询，返回列表,并且把list中的信息封装成对象
	 * @param sql 需要执行的sql
	 * @param param  参数
	 * @param entityName 需要转换的对象名称（类名）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySQLEntity(String sql, Object[] param,String entityName) {
		SQLQuery query = getSqlQueryToEntity(sql,entityName);
		setQueryParams(query, param);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findBySQLEntity(String sql,String entityName, Object... param) {
		SQLQuery query = getSqlQueryToEntity(sql,entityName);
		setQueryParams(query, param);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <S> List<S> findBySQLEntity(String sql,Class<S> cls, Object... param) {
		SQLQuery query = getSqlQueryToEntity(sql,cls.getName());
		setQueryParams(query, param);
		return query.list();
	}
	/**
	 * load方法得到对象信息
	 * 
	 * @param id
	 *            对象id
	 * @return entity
	 */
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(this.getEntityClass(), id);
	}

	/**
	 * 根据实体查询
	 * 
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(T t) {
		Example example = Example.create(t);
		Criteria criteria = this.getSession().createCriteria(getEntityClass());
		criteria.add(example);
		return criteria.list();
	}

	/**
	 * 获得总数
	 * 
	 * @param hql
	 * @return Long
	 */
	public Long count(String hql) {
		return ((Number) getHqlQuery(hql, false).uniqueResult()).longValue();
	}

	/**
	 * 获得总数
	 * 
	 * @param hql
	 * @param Object[]
	 * @return Long
	 */
	public Long count(String hql, Object[] params) {
		Query q = getHqlQuery(hql, false);
		setQueryParams(q, params);
		return ((Number) q.uniqueResult()).longValue();
	}

	/**
	 * 获得总数
	 * 
	 * @param hql
	 * @param List
	 * @return Long
	 */
	public Long count(String hql, List<Object> params) {
		Query q = getHqlQuery(hql, false);
		setQueryParams(q, params);
		return ((Number) q.uniqueResult()).longValue();
	}

	/**
	 * 使用hql查询
	 * 
	 * @param hql
	 * @param object[]
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryByHql(String hql, Object... objects) {
		Query query = getHqlQuery(hql, false);
		setQueryParams(query, objects);
		return query.list();
	}

	/**
	 * 使用hql查询
	 * 
	 * @param hql
	 * @param List
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryByHql(String hql, List<Object> params) {
		Query query = getHqlQuery(hql, false);
		setQueryParams(query, params);
		return query.list();
	}

	/**
	 * 使用sql查询
	 * 
	 * @param sql
	 * @param object[]
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryBySql(String sql, Object... objects) {
		Query query = getSqlQuery(sql, false);
		setQueryParams(query, objects);
		return query.list();
	}

	/**
	 * 使用sql查询
	 * 
	 * @param sql
	 * @param List
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryBySql(String sql, List<Object> params) {
		Query query = getSqlQuery(sql, false);
		setQueryParams(query, params);
		return query.list();
	}
	/**
	 * 使用sql 查询返回List<Map>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryBySql_listMap(String sql, Object... params) {
		Query query = getSqlQuery(sql, false);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		setQueryParams(query,params);
		return query.list();
	}
	
	/**
	 * 使用sql 查询返回List<Map>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryBySql_listMap(String sql, List<Object> params) {
		Query query = getSqlQuery(sql, false);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		setQueryParams(query,params);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryBySql_listMap(String sql) {
		Query query = getSqlQuery(sql, false);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	/**
	 * 得到总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer findAllCount() {
		Query query = getHqlQuery("select count(*) "+HQL_LIST_ALL, true);
		return ((Number) query.uniqueResult()).intValue();
	}
	/**
	 * 得到总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer findAllCount(QueryParameter qp) {
		Query query = getHqlQuery("select count(*) "+HQL_LIST_ALL+" where "+qp.transformationCondition("t"), true);
		return ((Number) query.uniqueResult()).intValue();
	}
	
	/**
	 * 得到总数
	 */
	public Integer findSqlCount(String sql,List<Object> params) {
		sql = "select count(1) from ("+sql+")";
		SQLQuery query = getSqlQuery(sql, false);
		setQueryParams(query,params);
		return ((Number) query.uniqueResult()).intValue();
	}
	public Integer findSqlCount(String sql,Object... params) {
		sql = "select count(1) from ("+sql+")";
		SQLQuery query = getSqlQuery(sql, false);
		setQueryParams(query,params);
		return ((Number) query.uniqueResult()).intValue();
	}
	
	/**
	 * 得到实体列表集合（所有）
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
//		Query query = getHqlQuery(HQL_LIST_ALL,false);
//		List<T> list = query.list();
//		return list;
		Criteria criteria=getSession().createCriteria(getEntityClass());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	/**
	 * 得到实体列表集合（所有）
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(QueryParameter qp) {
//		Query query =getHqlQuery(HQL_LIST_ALL+" where "+qp.transformationCondition("t")+qp.getOrderStr("t"),false);
//		List<T> list = query.list();
//		return list;
		Criteria criteria=getSession().createCriteria(getEntityClass());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		setCriteriaValue(qp, criteria);
		return criteria.list();
	}
	/** =====================基本数据库操作方法实现结束==========================* */

	/** =====================分页方法开始================* */
	// 首先是获得总数
	/**
	 * 通过hql 传入参数，得到分页总数量
	 * 
	 * @param originHql
	 * @param page
	 * @param Object[]
	 *            params
	 */
	private void generatePageTotalCountByHQL(String originHql, Pager page,
			Object... params) {
		String generatedCountHql = "select count(*) " + originHql;
		Query countQuery = getHqlQuery(generatedCountHql,false);
		setQueryParams(countQuery, params);
		int totalCount = ((Number) countQuery.uniqueResult()).intValue();
		page.setRecords(totalCount);
	}

	/**
	 * 通过hql 传入参数，得到分页总数量
	 * 
	 * @param originHql
	 * @param page
	 * @param List
	 *            <Object> params
	 */
	private void generatePageTotalCountByHQL(String originHql, Pager page,
			List<Object> params) {
		String generatedCountHql = "select count(*) " + originHql;
		Query countQuery = getHqlQuery(generatedCountHql,false);
		setQueryParams(countQuery, params);
		int totalCount = ((Number) countQuery.uniqueResult()).intValue();
		page.setRecords(totalCount);
	}

	/**
	 * 无参，获得总数
	 * 
	 * @param originHql
	 * @param page
	 */
	private void generatePageTotalCountByHQL(String originHql, Pager page) {
		String generatedCountHql = "select count(*) " + originHql;
		Query countQuery = getHqlQuery(generatedCountHql,false);
		int totalCount = ((Number) countQuery.uniqueResult()).intValue();
		page.setRecords(totalCount);
	}

	/**
	 * 通过sql查询,传入参数，查询总数量
	 * 
	 * @param sql
	 * @param page
	 * @param Object[]
	 *            obj
	 */
	@SuppressWarnings("unused")
	private void generatePageTotalCountBySQL(String sql, Pager page,
			Object... obj) {
		SQLQuery sqlQuery = getSqlQuery(sql,false);
		for (int i = 0; i < obj.length; i++) {
			sqlQuery.setParameter(i, obj[i]);
		}
		page.setRecords(sqlQuery.list().size());
	}

	/**
	 * 通过sql查询,传入参数，查询总数量
	 * 
	 * @param sql
	 * @param page
	 * @param List
	 *            <Object> params
	 */
	@SuppressWarnings("unused")
	private void generatePageTotalCountBySQL(String sql, Pager page,
			List<Object> params) {
		SQLQuery sqlQuery = getSqlQuery(sql,false);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				sqlQuery.setParameter(i, params.get(i));
			}
		}
		page.setRecords(sqlQuery.list().size());
	}

	/**
	 * 通过sql查询,传入参数，查询总数量 无参
	 * 
	 * @param sql
	 * @param page
	 */
	@SuppressWarnings("unused")
	private void generatePageTotalCountBySQL(String sql, Pager page) {
		SQLQuery sqlQuery = getSqlQuery(sql,false);
		page.setRecords(sqlQuery.list().size());
	}

	/**
	 * 使用Criteria获得分页总数
	 * 
	 * @param t
	 * @param page
	 */
	private void generatePageTotalCountByCriteria(T t, Pager page) {
		Example example = Example.create(t);
		Criteria criteria = this.getSession().createCriteria(t.getClass());
		criteria.add(example);
		page.setRecords(criteria.list().size());

	}

	// 其次是获得分页具体信息

	/**
	 * 
	 * 分页查询数据，存入到pager的list属性中，无参
	 * 
	 * @param hql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void generatePageListByHQL(String hql, int pageSize, int pageIndex,
			Pager page) {
		List<T> list = getHqlQuery(hql,false).setFirstResult(
				(pageIndex - 1) * pageSize).setMaxResults(pageSize).list();
		page.setRow(list);

	}

	/**
	 * 执行hql语句分页，并按顺序传入参数new Object[]{a,b}，存入到pager 的list属性中
	 * 
	 * @param hql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码
	 * @param Object[]
	 *            obj
	 */
	private void generatePageListByHQL(String hql, int pageSize, int pageIndex,
			Pager page, Object... obj) {
		Query query = getHqlQuery(hql,false).setFirstResult(
				(pageIndex - 1) * pageSize).setMaxResults(pageSize);
		setQueryParams(query, obj);
		page.setRow(query.list());
	}

	/**
	 * 执行hql语句分页，将数据保存到pager的list中
	 * 
	 * @param hql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param page
	 *            分页实体类
	 * @param List
	 *            <Object> params
	 */
	private void generatePageListByHQL(String hql, int pageSize, int pageIndex,
			Pager page, List<Object> params) {
		Query query = getHqlQuery(hql,false).setFirstResult(
				(pageIndex - 1) * pageSize).setMaxResults(pageSize);
		setQueryParams(query, params);
		page.setRow(query.list());
	}

	/**
	 * 
	 * 通过sql 查询数据 , 分页 并传入参数 new Object[]{a,b},如果不想分页 可把pageSize和 pageIndex 设为0
	 * 
	 * @param sql
	 * @param objs
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> querySQLParam(String sql, int pageSize, int pageIndex,
			Object... obj) {
		SQLQuery sqlQuery = getSqlQuery(sql,false);
		for (int i = 0; i < obj.length; i++) {
			sqlQuery.setParameter(i, obj[i]);
		}
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		return sqlQuery.list();

	}
	
	/**
	 * sql分页查询，如果有分页信息那么进行分页；如果没有，不分页
	 * 把信息封装成对象
	 * @param sql 
	 * @param pageSize 每页显示多少条
	 * @param pageIndex 当前页
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,
			Object... obj) {
		SQLQuery sqlQuery = getSqlQueryToEntity(sql);
		for (int i = 0; i < obj.length; i++) {
			sqlQuery.setParameter(i, obj[i]);
		}
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		return sqlQuery.list();

	}
	/**
	 * sql分页查询，如果有分页信息那么进行分页；如果没有，不分页
	 * 把信息封装成指定对象 
	 * @param sql
	 * @param pageSize 每页显示多少条
	 * @param pageIndex 当前页
	 * @param entityName 需要封装的对象名称
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,String entityName,
			Object... obj) {
		SQLQuery sqlQuery = getSqlQueryToEntity(sql,entityName);
		for (int i = 0; i < obj.length; i++) {
			sqlQuery.setParameter(i, obj[i]);
		}
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		return sqlQuery.list();

	}

	/**
	 * 
	 * 通过sql 查询数据 , 分页 并传入参数 List,如果不想分页 可把pageSize和 pageIndex 设为0
	 * 
	 * @param sql
	 * @param objs
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<T> querySQLParam(String sql, int pageSize, int pageIndex,
			List<Object> params) {
		SQLQuery sqlQuery = getSqlQuery(sql,false);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				sqlQuery.setParameter(i, params.get(i));
			}
		}
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		return sqlQuery.list();

	}
	/**
	 * 通过sql 查询数据 , 分页 并传入参数 List,如果不想分页 可把pageSize和 pageIndex 设为0
	 * 把返回list中的信息封装成指定对象
	 * @param sql
	 * @param pageSize
	 * @param pageIndex
	 * @param entityName 需要转换成的对象名（类名）
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,String entityName,
			List<Object> params) {
		SQLQuery sqlQuery = getSqlQueryToEntity(sql,entityName);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				sqlQuery.setParameter(i, params.get(i));
			}
		}
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		return sqlQuery.list();

	}
	/**
	 * 通过sql 查询数据 , 分页 并传入参数 List,如果不想分页 可把pageSize和 pageIndex 设为0
	 * 把返回list中的信息封装成指定对象
	 * @param sql
	 * @param pageSize
	 * @param pageIndex
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,
			List<Object> params) {
		SQLQuery sqlQuery = getSqlQueryToEntity(sql);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				sqlQuery.setParameter(i, params.get(i));
			}
		}
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		return sqlQuery.list();

	}

	/**
	 * 通过sql 查询数据 , 分页 如果不想分页 可把pageSize和 pageIndex 设为0
	 * 
	 * @param sql
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> querySQLParam(String sql, int pageSize, int pageIndex) {
		SQLQuery sqlQuery = getSqlQuery(sql,false);
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		
		return sqlQuery.list();

	}
	@SuppressWarnings("unchecked")
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex) {
		SQLQuery sqlQuery = getSqlQueryToEntity(sql);
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		
		return sqlQuery.list();

	}
	@SuppressWarnings("unchecked")
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,String entityName) {
		SQLQuery sqlQuery = getSqlQueryToEntity(sql,entityName);
		if (pageSize != 0) {
			sqlQuery.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
		}
		
		return sqlQuery.list();

	}
	/**
	 * 使用Criteria 执行分页
	 * 
	 * @param t
	 *            需要传入的实体对象
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param page
	 *            Pager
	 */
	private void generatePageListByCriteria(T t, int pageSize, int pageIndex,
			Pager page) {
		Example example = Example.create(t);
		Criteria criteria = this.getSession().createCriteria(t.getClass());
		criteria.setCacheable(true);
		criteria.add(example);
		criteria.setFirstResult((pageIndex - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		page.setRow(criteria.list());

	}

	// 最后执行分页，把数据存到pager中
	/**
	 * 执行hql语句分页，并按顺序传入参数new Object[]{a,b} 分页数据获取 pager.getList()
	 * 
	 * @param hql
	 * @param pageSize
	 *            每页显示的条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param Object[]
	 *            obj
	 * @return Pager
	 */
	public Pager queryHQLPage(String hql, int pageSize, int pageIndex,
			Object... obj) {
		Pager pagination = new Pager();
		// 获得总数
		generatePageTotalCountByHQL(hql, pagination, obj);
		// 获得查询数据，存入list
		generatePageListByHQL(hql, pageSize, pageIndex, pagination, obj);
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		return pagination;
	}
	public Pager queryHQLPageDB2(String queryHql,String orderHql, int pageSize, int pageIndex,
			List<Object> params) {
		Pager pagination = new Pager();
		// 获得总数
		generatePageTotalCountByHQL(queryHql, pagination, params);
		if(StringUtil.notBlank(orderHql)){
			queryHql += orderHql;
		}
		// 获得查询数据，存入list
		generatePageListByHQL(queryHql, pageSize, pageIndex, pagination, params);
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		return pagination;
	}
	/**
	 * 执行hql语句分页，并按顺序传入参数List 分页数据获取 pager.getList()
	 * 
	 * @param hql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param List
	 *            <Object> params
	 * @return Pager
	 */
	public Pager queryHQLPage(String hql, int pageSize, int pageIndex,
			List<Object> params) {
		Pager pagination = new Pager();
		// 获得总数
		generatePageTotalCountByHQL(hql, pagination, params);
		// 获得查询数据，存入list
		generatePageListByHQL(hql, pageSize, pageIndex, pagination, params);
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	/**
	 * 无参分页，分页数据获取 pager.getList()
	 * 
	 * @param hql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @return Pager 返回信息
	 */
	public Pager queryHQLPage(String hql, int pageSize, int pageIndex) {

		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		// 获得总数
		generatePageTotalCountByHQL(hql, pagination);
		// 获得查询数据，存入list
		generatePageListByHQL(hql, pageSize, pageIndex, pagination);
		return pagination;
	}

	/**
	 * SQL查询数据并分页,如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 
	 * @param sql
	 * 
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param obj
	 *            参数 Object[]
	 * @return Pager
	 */
	public Pager querySQLPage(String sql, int pageSize, int pageIndex,
			Object... obj) {

		List<T> list = querySQLParam(sql, pageSize, pageIndex, obj);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		List<Object> paramsList=null;
		if(obj!=null)
			paramsList=Arrays.asList(obj);
		generatePageTotalCountSql(sql, paramsList,pagination);
		pagination.setRow(list);
		return pagination;
	}
	/**
	 * SQL查询数据并分页,如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 *  把返回值封装到对象里
	 * @param sql
	 * 
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param obj
	 *            参数 Object[]
	 * @return Pager
	 */
	public Pager querySQLPageEntity(String sql, int pageSize, int pageIndex,
			Object... obj) {

		List<T> list = querySQLParamEntity(sql, pageSize, pageIndex, obj);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		List<Object> paramsList=null;
		if(obj!=null)
			paramsList=Arrays.asList(obj);
		generatePageTotalCountSql(sql, paramsList,pagination);
		pagination.setRow(list);
		return pagination;
	}
	/**
	 * SQL查询数据并分页,如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 把返回值封装到对象里
	 * @param sql
	 * 
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param obj
	 *            参数 Object[]
	 * @entityName 需要转换成的对象名称（类名）
	 * @return Pager
	 */
	public Pager querySQLPageEntity(String sql, int pageSize, int pageIndex,String entityName,
			Object... obj) {

		List<T> list = querySQLParamEntity(sql, pageSize, pageIndex,entityName, obj);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		List<Object> paramsList=null;
		if(obj!=null)
			paramsList=Arrays.asList(obj);
		generatePageTotalCountSql(sql, paramsList,pagination);
		pagination.setRow(list);
		return pagination;
	}

	/**
	 * SQL查询数据并分页，如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param obj
	 *            参数 List<Object>
	 * @return Pager
	 */
	public Pager querySQLPage(String sql, int pageSize, int pageIndex,
			List<Object> params) {

		List<T> list = querySQLParam(sql, pageSize, pageIndex, params);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		generatePageTotalCountSql(sql, params,pagination);
		pagination.setRow(list);
		return pagination;
	}
	/**
	 * SQL查询数据并分页，如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 查询的数据用map保存
	 * @param sql
	 * @param pageSize 每页显示条数
	 * @param pageIndex 页码，第几页
	 * @param params 参数 List<Object>
	 * @return Pager
	 */
	public Pager querySQLPageUseMap(String sql, int pageSize, int pageIndex,
			List<Object> params) {

		Query query = getSqlQuery(sql, false);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		setQueryParams(query, params);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		generatePageTotalCountSql(sql, params,pagination);
		if (pageSize != 0) {
			query.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			query.setFirstResult((pageIndex - 1) * pageSize);
		}
		pagination.setRow(query.list());
		return pagination;
	}
	/**
	 * SQL查询数据并分页，如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 查询的数据用map保存
	 * @param sql
	 * @param pageSize 每页显示条数
	 * @param pageIndex 页码，第几页
	 * @param params 参数
	 * @return Pager
	 */
	public Pager querySQLPageUseMap(String sql, int pageSize, int pageIndex,
			Object... params) {

		Query query = getSqlQuery(sql, false);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		setQueryParams(query, params);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		List<Object> paramsList=null;
		if(params!=null)
			paramsList=Arrays.asList(params);
		generatePageTotalCountSql(sql, paramsList,pagination);
		if (pageSize != 0) {
			query.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			query.setFirstResult((pageIndex - 1) * pageSize);
		}
		pagination.setRow(query.list());
		return pagination;
	}
	/**
	 * SQL查询数据并分页，如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 并且把返回信息封装在对象中
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @entityName 需要转换成的对象名称（类名）
	 * @param obj
	 *            参数 List<Object>
	 * @return Pager
	 */
	public Pager querySQLPageEntity(String sql, int pageSize, int pageIndex,String entityName,
			List<Object> params) {

		List<T> list = querySQLParamEntity(sql, pageSize, pageIndex,entityName, params);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		generatePageTotalCountSql(sql, params,pagination);
		pagination.setRow(list);
		return pagination;
	}

	/**
	 * SQL查询数据并分页，如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 并且把返回信息封装在对象中
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param obj
	 *            参数 List<Object>
	 * @return Pager
	 */
	public Pager querySQLPageEntity(String sql, int pageSize, int pageIndex,
			List<Object> params) {

		List<T> list = querySQLParamEntity(sql, pageSize, pageIndex, params);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		generatePageTotalCountSql(sql, params,pagination);
		pagination.setRow(list);
		return pagination;
	}

	/**
	 * SQL查询数据并分页，如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @return Pager
	 */
	public Pager querySQLPage(String sql, int pageSize, int pageIndex) {

		List<T> list = querySQLParam(sql, pageSize, pageIndex);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		generatePageTotalCountSql(sql, null,pagination);
		pagination.setRow(list);
		return pagination;
	}
	/**
	 * SQL查询数据并分页，如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 并且把返回信息转换成对象
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @return Pager
	 */
	public Pager querySQLPageEntity(String sql, int pageSize, int pageIndex) {

		List<T> list = querySQLParamEntity(sql, pageSize, pageIndex);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		generatePageTotalCountSql(sql, null,pagination);
		pagination.setRow(list);
		return pagination;
	}
	/**
	 * SQL查询数据并分页，如果不需要分页，可以传参数pageSize = 0，pageIndex = 0
	 * 并且把返回信息转换成指定对象
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @entityName 需要转换成的对象名称（类名）
	 * @return Pager
	 */
	public Pager querySQLPageEntity(String sql, int pageSize, int pageIndex,String entityName) {

		List<T> list = querySQLParamEntity(sql, pageSize, pageIndex,entityName);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		generatePageTotalCountSql(sql, null,pagination);
		pagination.setRow(list);
		return pagination;
	}
	
	
	public Pager queryCriteriaPage(T t, int pageSize, int pageIndex) {
		Pager pagination = new Pager();
		// 获得总数
		generatePageTotalCountByCriteria(t, pagination);
		// 获得查询数据，存入list
		generatePageListByCriteria(t, pageSize, pageIndex, pagination);
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		return pagination;
	}
	/**
	 * 分页，传入map，page
	 * @param map
	 * @param page
	 * @return
	 */
	public Pager queryCriteriaPage(Map<String,Object[]> map,Pager page){
		
		// 获得总数
		generatePageCountByCriteria(map, page);
		// 获得查询数据，存入list
		generatePageListByCriteria(map, page);
		
		return page;
	}
	
	public Pager paginationHql(String hql, List<Object> params, Pager pager) {

		Query q = getHqlQuery(hql,false);
		setQueryParams(q, params);
		if(pager!=null)
		{
			generatePageTotalCountHql(hql, params, pager);
			q.setFirstResult(pager.getFirstIndex());
			q.setMaxResults(pager.getPageSize());
		}
		else
			pager=new Pager();
		
		pager.setRow(q.list());
		return pager;
	}
	public Pager paginationSql(String sql, List<Object> params, Pager pager) {

		Query q = getSqlQuery(sql,false);
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		setQueryParams(q, params);
		if(pager!=null)
		{
			generatePageTotalCountSql(sql, params, pager);
			q.setFirstResult(pager.getFirstIndex());
			q.setMaxResults(pager.getPageSize());
		}
		else
			pager=new Pager();
		
		pager.setRow(q.list());
		return pager;
	}
	public Pager paginationHql(QueryParameter qp) {

		String hql=HQL_LIST_ALL+" where "+qp.transformationCondition("t") ;
		Query q =getHqlQuery(hql+qp.getOrderStr("t"),false);
		Pager pager=qp.getPager();
		if(pager!=null)
		{
			generatePageTotalCountHql(hql, null, pager);
			q.setFirstResult(pager.getFirstIndex());
			q.setMaxResults(pager.getPageSize());
		}
		else
			pager=new Pager();
		
		pager.setRow(q.list());
		return pager;
	}
	public <S> Pager findPageSql(String sql, QueryParameter qp, Class<S> cls){
		sql += qp.transformationCondition(null)+qp.getOrderStr(null);
		return this.querySQLPageEntity(sql.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), cls.getName());
	}
	public <S> List<S> findListSql(String sql, QueryParameter qp, Class<S> cls){
		sql += qp.transformationCondition(null)+qp.getOrderStr(null);
		return this.findBySQLEntity(sql, cls);
	}
	public Pager pagination(QueryParameter qp) {

		Criteria criteria=getSession().createCriteria(getEntityClass());
		setCriteriaValue(qp, criteria);
		Pager pager=qp.getPager();
		if(pager!=null)
		{
			criteria.setFirstResult(pager.getFirstIndex());
			criteria.setMaxResults(pager.getPageSize());
		}
		else
			pager=new Pager();
		
		pager.setRow(criteria.list());//查询数据
		//查询总条数
		criteria.setFirstResult(0);
		if(qp.getPager()!=null)
			pager.setRecords(((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
		else
			pager.setRecords(pager.getRows().size());
		
		return pager;
	}
	private void generatePageTotalCountHql(String originHql, List<Object> params,Pager page) {  
        String generatedCountHql = "select count(*) " + originHql;  
        Query countQuery = getHqlQuery(generatedCountHql,false);  
        setQueryParams(countQuery, params);  
        int totalCount = ((Number) countQuery.uniqueResult()).intValue();
        page.setRecords(totalCount);  
    }
	private void generatePageTotalCountSql(String originSql, List<Object> params,Pager page) {  
        String generatedCountHql = "select count(*) from (" + originSql+") t";  
        Query countQuery = getSqlQuery(generatedCountHql,false);  
        setQueryParams(countQuery, params);  
        int totalCount = ((Number) countQuery.uniqueResult()).intValue();  
        page.setRecords(totalCount);   
    }
	
	/**
	 * 
	 * YF
	 * 2017-11-28 下午04:09:13
	 * @param originSql
	 * @param params
	 * @param page
	 */
	private void generatePageTotalCountSqlM(String originSql, Map<String,Object> params,Pager page) {  
        String generatedCountHql = "select count(*) from (" + originSql+") t";  
        Query countQuery = getSqlQuery(generatedCountHql,false);  
        setQueryParams(countQuery, params);  
        int totalCount = ((Number) countQuery.uniqueResult()).intValue();  
        page.setRecords(totalCount);   
    }
	
	private void setCriteriaValue(Map<String,Object[]> map,Criteria criteria){
		if(map != null){
			Set<String> keyset = map.keySet();
			for(String key : keyset){
				//当为null的时候，过滤掉
				  if (key == null || map.get(key) == null ) {   
                        
                  }  
				  //排序
				  if("orderstr".equals(key)) {
					  	Object[] objs = map.get(key);
					  	if( objs[0] != null && (!"".equals(objs[0]))){
					  		String orderstr = objs[0]+" "+objs[1];
							String[] orderstrs = orderstr.split(",");
							for(int i=0;i<orderstrs.length;i++) {
								if(orderstrs[i].endsWith("asc")) {
									criteria.addOrder(Order.asc(orderstrs[i].substring(0,orderstrs[i].indexOf("asc")).trim()));
								} else if(orderstrs[i].endsWith("desc")) {
									criteria.addOrder(Order.desc(orderstrs[i].substring(0,orderstrs[i].indexOf("desc")).trim()));
								}
							}
					  	}
					  	
				  }

				 
				  
				  // 拼装条件  
				  Object[] objs = map.get(key);
				 
				  //等于
				  if("=".equals(objs[0].toString()) && objs[1] != null && (!"".equals(objs[1]))){
					  criteria.add(Restrictions.eq(key, objs[1]));   
					 }
				  //不等于
				  if(("!=".equals(objs[0].toString()) || "<>".equals(objs[0].toString()))&& objs[1] != null && (!"".equals(objs[1]))){
					  criteria.add(Restrictions.not(Restrictions.eq(key, objs[1])));
				  }
				  //大于> gt() 大于
				  if(">".equals(objs[0].toString()) && objs[1] != null && (!"".equals(objs[1]))){
					  criteria.add(Restrictions.gt(key, objs[1]));
				  }
				  //大于等于
				  if(">=".equals(objs[0].toString()) && objs[1] != null && (!"".equals(objs[1]))){
					  criteria.add(Restrictions.ge(key, objs[1]));
				  }
				  //小于
				  if("<".equals(objs[0].toString()) && objs[1] != null && (!"".equals(objs[1]))){
					  criteria.add(Restrictions.lt(key, objs[1]));
				  }
				  //小于等于
				  if("<=".equals(objs[0].toString()) && objs[1] != null && (!"".equals(objs[1]))){
					  criteria.add(Restrictions.le(key, objs[1]));
				  }
				  //等于空值
				  if("is null".equals(objs[0].toString()) ){
					  criteria.add(Restrictions.isNull(key));
				  }
				  //非空值
				  if("is not null".equals(objs[0].toString())){
					  criteria.add(Restrictions.isNotNull(key));
				  }
				  
				  //like
				  if("like".equals(objs[0].toString()) && objs[1] != null &&  (!"".equals(objs[1]))){
					  
					  criteria.add(Restrictions.like(key,"%"+objs[1]+"%"));
				  }
				  //like 开始于
				  if("begin like".equals(objs[0].toString()) && objs[1] != null &&  (!"".equals(objs[1]))){
					criteria.add(Restrictions.like(key,"%"+objs[1]));
				  }
				//like 结束于
				  if("end like".equals(objs[0].toString()) && objs[1] != null &&  (!"".equals(objs[1]))){
						  criteria.add(Restrictions.like(key,objs[1]+"%"));
				  }
				  //in
				  if("in".equals(objs[0].toString()) && objs[1] != null && (!"".equals(objs[1]))){
					  Object[] obj= new Object[objs.length-1];
					  for(int i =0;i<objs.length;i++){
						  obj[i]=objs[i+1];
					  }
					  criteria.add(Restrictions.in(key, obj));
					  obj=null;
				  }
				  //not in
				  if("not in".equals(objs[0].toString()) && objs[1] != null && (!"".equals(objs[1]))){
					  criteria.add(Restrictions.not(Restrictions.in(key, new Object[]{objs[1]})));
				  }
				  //between x and y
				  if("between".equals(objs[0].toString()) ){
					  if(objs[1] != null && (!"".equals(objs[1])) && objs[2] != null && (!"".equals(objs[2]))){
						  criteria.add(Restrictions.between(key, objs[1], objs[2]));
					  }
					  
				  }
				//not between x and y
				  if("not between".equals(objs[0].toString())){
					  if(objs[1] != null && (!"".equals(objs[1])) && objs[2] != null && (!"".equals(objs[2]))){
						  criteria.add(Restrictions.not(Restrictions.between(key, objs[1], objs[2])));
					  }
					  
				  }
			}
		}
		
		
	}
	@SuppressWarnings("rawtypes")
	private void setCriteriaValue(QueryParameter qp,Criteria criteria)
	{
		if(qp==null)
			return;
		Map<String, Criteria> criterias=null;
		/**加入排序处理*/
		if(StringUtil.notBlank(qp.getSortField()))
		{
			String[] orders = (qp.getSortField() + " " + qp.getSortOrder()).split(",");
			List<String[]> queryOrder=new ArrayList<String[]>();
			List<String> orderFields=new ArrayList<String>();
			for (String order:orders) 
			{
				order=order.trim();
				String orderField="";
				String[] temp=new String[2];
				if (order.endsWith("asc"))
				{
					orderField=order.substring(0,order.indexOf("asc")).trim();
					temp[0]=orderField;
					temp[1]="asc";
					queryOrder.add(temp);
					orderFields.add(orderField);
				}
				else if (order.endsWith("desc"))
				{
					orderField=order.substring(0,order.indexOf("desc")).trim();
					temp[0]=orderField;
					temp[1]="desc";
					queryOrder.add(temp);
					orderFields.add(orderField);
				}
				else
					continue;
			}
			criterias=initCriteria(criteria, orderFields,null);
			for(String[] order:queryOrder)
			{
				Criteria c_criteria=criterias.get(order[0]);
				if(order[1].equals("asc"))
					c_criteria.addOrder(Order.asc(order[0].substring(order[0].lastIndexOf(".")+1)));
				else if(order[1].equals("desc"))
					c_criteria.addOrder(Order.desc(order[0].substring(order[0].lastIndexOf(".")+1)));
			}
		}
		if(qp.getParams()==null||qp.getParams().size()==0)
			return;
		//处理查询的属性名和Criteria对象,得到每个属性名对应的Criteria集合
		criterias=initCriteria(criteria, qp.getParamNames(),criterias);
		for (String param : qp.getParamNames())//循环参数属性
		{
			QueryCondition qc = qp.getCondition(param);
			if (StringUtil.isBlank(qp.getParam(param)))
				continue;
			Criteria c_criteria=criterias.get(param);//获取此参数对应的Criteria对象
			String fieldName=param.substring(param.lastIndexOf(".")+1);//得到参数的属性名称(如:xxx.aa处理后是aa)
			if(qc==null)
			{
				c_criteria.add(Restrictions.eq(fieldName, qp.getParam(param).toString()));
				continue;
			}
			switch (qc) {
			case equal:
				c_criteria.add(Restrictions.eq(fieldName, qp.getParam(param).toString()));
				break;
			case not_equal:
				c_criteria.add(Restrictions.not(Restrictions.eq(fieldName, qp.getParam(param).toString())));
				break;
			case in:
				if(qp.getParam(param).getClass().isArray())
					c_criteria.add(Restrictions.in(fieldName, (Object[])qp.getParam(param)));
				else if(qp.getParam(param) instanceof Collection)
					c_criteria.add(Restrictions.in(fieldName, ((Collection)qp.getParam(param)).toArray()));
				else
					c_criteria.add(Restrictions.in(fieldName, qp.getParam(param).toString().trim().split(",")));
				break;
			case not_in:
				if(qp.getParam(param).getClass().isArray())
					c_criteria.add(Restrictions.not(Restrictions.in(fieldName, (Object[])qp.getParam(param))));
				else if(qp.getParam(param) instanceof Collection)
					c_criteria.add(Restrictions.not(Restrictions.in(fieldName, ((Collection)qp.getParam(param)).toArray())));
				else
					c_criteria.add(Restrictions.not(Restrictions.in(fieldName, qp.getParam(param).toString().trim().split(","))));
				break;
			case if_null:
				if(qp.getParam(param).equals("1"))
					c_criteria.add(Restrictions.or(Restrictions.eq(fieldName, ""),Restrictions.isNull(fieldName)));
				else if(qp.getParam(param).equals("0"))
					c_criteria.add(Restrictions.isNotNull(fieldName));
				else if(qp.getParam(param).equals("2"))
					c_criteria.add(Restrictions.isNull(fieldName));
				break;
			case large:
				c_criteria.add(Restrictions.gt(fieldName, qp.getParam(param)));
				break;
			case large_equal:
				c_criteria.add(Restrictions.ge(fieldName, qp.getParam(param)));
				break;
			case small:
				c_criteria.add(Restrictions.lt(fieldName, qp.getParam(param)));
				break;
			case small_equal:
				c_criteria.add(Restrictions.le(fieldName, qp.getParam(param)));
				break;
			case like_anywhere:
				c_criteria.add(Restrictions.like(fieldName, qp.getParam(param).toString(),MatchMode.ANYWHERE));
				break;
			case like_start:
				c_criteria.add(Restrictions.like(fieldName, qp.getParam(param).toString(),MatchMode.START));
				break;
			case like_end:
				c_criteria.add(Restrictions.like(fieldName, qp.getParam(param).toString(),MatchMode.END));
				break;
			case between:
				Object[] b_obj1=new Object[2];
				if(qp.getParam(param) instanceof Object[])
					b_obj1=(Object[])qp.getParam(param);
				if(qp.getParam(param) instanceof Collection)
					b_obj1=((Collection)qp.getParam(param)).toArray();
				c_criteria.add(Restrictions.between(fieldName, b_obj1[0],b_obj1[1]));
				break;
			case not_between:
				Object[] b_obj2=new Object[2];
				if(qp.getParam(param) instanceof Object[])
					b_obj2=(Object[])qp.getParam(param);
				if(qp.getParam(param) instanceof Collection)
					b_obj2=((Collection)qp.getParam(param)).toArray();
				c_criteria.add(Restrictions.not(Restrictions.between(fieldName, b_obj2[0],b_obj2[1])));
				break;
			case large_small:
				Object[] b_obj3=new Object[2];
				if(qp.getParam(param) instanceof Object[])
					b_obj3=(Object[])qp.getParam(param);
				if(qp.getParam(param) instanceof Collection)
					b_obj3=((Collection)qp.getParam(param)).toArray();
				if(StringUtil.notBlank(b_obj3[0]))
					c_criteria.add(Restrictions.ge(fieldName, b_obj3[0]));
				if(b_obj3.length==2&&StringUtil.notBlank(b_obj3[1]))
					c_criteria.add(Restrictions.le(fieldName, b_obj3[1]));
				break;
			case small_or:
				c_criteria.add(Restrictions.or(Restrictions.lt(fieldName, qp.getParam(param)),Restrictions.isNull(fieldName)));
				break;
			default:
				c_criteria.add(Restrictions.eq(fieldName, qp.getParam(param)));
			}
		}
	}
	/**
	 * 处理查询条件的映射,使查询条件的属性名称支持xxx.xx.xx等形式,
	 * Criteria在使用表连接查询时需要在主表的Criteria中创建关联表的Criteria,而且同一个关联表只能创建一个,
	 * 此方法可以无限制的递用关联表的关联表中的列作为查询条件
	 * @param rootCriteria 根Criteria对象
	 * @param paramNames 查询的属性名集合(支持名称为:xxx或者sss.ss或者aaa.aa.a)
	 * @param criterias 已有的参照集合,若不传则new一个,否则将在此基础上进行操作
	 * @return Map<String, Criteria> 所有查询的属性名对应的Criteria集合
	 */
	private Map<String, Criteria> initCriteria(Criteria rootCriteria,Collection<String> paramNames,Map<String, Criteria> criterias)
	{
		if(criterias==null)
			criterias=new HashMap<String, Criteria>();
		if(paramNames==null)
			return criterias;
		Iterator<String> iterator=paramNames.iterator();
		while(iterator.hasNext())
		{
			String param=iterator.next();
			//判断查询属性名是否包含.分割符号,若没有则为主表的字段直接返回主表Criteria对象
			if(!param.contains("."))
			{
				criterias.put(param, rootCriteria);
				continue;
			}
			//截取关联表的属性名,列:xxx.xx.x  截取出xxx.xx 这作为关联的表映射
			String param_key=param.substring(0,param.lastIndexOf("."));
			//判断criterias中是否存在了
			if(criterias.get(param)!=null)
				continue;
			String[] params=param_key.split("[.]");
			String temp_param="";
			Criteria temp_c=rootCriteria;
			for(int i=0;i<params.length;i++)
			{
				if(i==0)
					temp_param=params[i];
				else
					temp_param=temp_param+"."+params[i];
				if(criterias.get(temp_param)==null)
				{
					temp_c=temp_c.createCriteria(params[i],JoinType.LEFT_OUTER_JOIN);
					criterias.put(temp_param, temp_c);
				}
				else
					temp_c=criterias.get(temp_param);
			}
			criterias.put(param, temp_c);
		}
		return criterias;
	}
	/**
	 * Criteria 查询，分页获得条数
	 * @param map
	 * @param page
	 */
	private void generatePageListByCriteria(Map<String,Object[]> map,Pager page){
		Criteria criteria = this.getSession().createCriteria(getEntityClass());
		setCriteriaValue(map,criteria);
		criteria.setFirstResult(page.getFirstIndex());
		criteria.setMaxResults(page.getPageSize());
		page.setRow(criteria.list());
		
	}
	/**
	 * Criteria 查询，获得总条数
	 * @param map
	 * @param page
	 */
	private void generatePageCountByCriteria(Map<String,Object[]> map,Pager page){
		Criteria criteria = this.getSession().createCriteria(getEntityClass());
		setCriteriaValue(map,criteria);
		page.setRecords(criteria.list().size());
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<T> findAllOpen() {
		Query query = getSession().createQuery(HQL_LIST_ALL);
		List<T> list = query.list();
		return list;
	}

	/**
	 * 根据表名查询单表信息()
	 * YF
	 * 2017-11-27
	 * @param tbNm 表名
	 * @param params 查询条件可以为空
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findListByTableName(String tableName,Map<String, Object> params) throws Exception {
		Query query = getSession().createSQLQuery("select * from "+tableName);
		if(null!=params&&!params.isEmpty()){
			setQueryParams(query, params);
		}
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = query.list();
		return list;
	}

	/**
	 * 查询
	 * 根据表名查询单表信息 
	 * YF
	 * 2017-11-28 上午09:53:20
	 * @param tableName 表名
	 * @param pageSize 每页显示条数
	 * @param pageIndex 页码，第几页
	 * @param params 查询条件可以为空
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pager findPageByTableName(String tableName, int pageSize, int pageIndex,
			Map<String,Object> params) throws Exception {
		String sql = "select * from "+tableName;
		sql = addCondition(sql, params);
		Query query = getSqlQuery(sql, false);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		setQueryParams(query, params);
		Pager pagination = new Pager();
		pagination.setPageIndex(pageIndex);
		pagination.setPageSize(pageSize);
		generatePageTotalCountSqlM(sql, params,pagination);
		if (pageSize != 0) {
			query.setMaxResults(pageSize);
		}
		if (pageIndex != 0) {
			query.setFirstResult((pageIndex - 1) * pageSize);
		}
		pagination.setRow(query.list());
		return pagination;
	}

	/**
	 * 根据条件删除表中信息
	 * YF
	 * 2017-11-28 下午01:50:24
	 * @param tableName 表名
	 * @param params
	 * @throws Exception
	 */
	@Override
	public int delInfoByTableName(String tableName, Map<String, Object> params) throws Exception {
		String sql = "delete from "+tableName;
		sql = addCondition(sql, params);
		Query query = getSession().createSQLQuery(sql);
		if(null!=params&&!params.isEmpty()){
			setQueryParams(query, params);
		}
		return query.executeUpdate();
		
	}

	/**
	 * 根据一定条件修改单表中信息
	 * YF
	 * 2017-11-28 下午09:30:34
	 * @param tableName 表名
	 * @param params 参数
	 * @param params 要修改的字段
	 * @param cond 条件
	 * @throws Exception
	 */
	@Override
	public int updateByTableName(String tableName, Map<String, Object> params,Map<String, Object> cond)
			throws Exception {
		String sql = "update "+tableName +" set ";
		sql = setUpdateParams(sql, params);
		Map<String, Object> cond2 = null;
		if(null!=cond&&!cond.isEmpty()){
			 cond2 = new HashMap<String, Object>();
			 for (Map.Entry<String,Object> entry : cond.entrySet()) {
				 cond2.put(entry.getKey()+"_up", entry.getValue());
           }
		   sql = addCondition(sql, cond2);
		}
		
		Query query = getSession().createSQLQuery(sql);
		if(null!=params&&!params.isEmpty()){
			
			if(null!=cond&&!cond.isEmpty()){
				 for (Map.Entry<String,Object> entry : cond.entrySet()) {
					 params.put(entry.getKey()+"_up", entry.getValue());
	           }
			}
			
			setQueryParams(query, params);
		}
		return query.executeUpdate();
	}
	
	/**
	 * 设置参数
	 * YF
	 * 2017-11-28 下午09:39:50
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unused")
	private String addFiledCon(String sql,Map<String, Object> params,Map<String, Object> cond){
		StringBuffer sqls = new StringBuffer();
		if(null!=params&&!params.isEmpty()){
			
			Set<String> sets = params.keySet();
			for (String string : sets) {
				sqls.append(string+"=:"+string+",");
			}
			sql =sql +	sqls.substring(0, sqls.lastIndexOf(","));
		}
		
		if(null!=cond&&!cond.isEmpty()){
			sqls.delete(0, sql.length());
			sqls.append(" where ");
			Set<String> sets = cond.keySet();
			for (String string : sets) {
				sqls.append(string+" =:"+string+" and");
			}
			sql =sql +	sqls.substring(0, sqls.lastIndexOf("and"));
		}
		
		return sql;
	}

	/**
	 * 添加
	 * YF
	 * 2017-11-29 上午10:56:39
	 * @param tbNm 表名
	 * @param params 字段参数
	 * @throws Exception
	 */
	@Override
	public void addByTableName(String tbNm, Map<String, Object> params)
			throws Exception {
		String sql ="insert into "+tbNm +"(";
		if(null!=params&&!params.isEmpty()){
			UUIDGenerator uu = new UUIDGenerator();  
	        String id = uu.generate().toString();  
		    params.put("ID", id); 
			StringBuffer sqls = new StringBuffer();
			Set<String> sets = params.keySet();
			for (String string : sets) {
				sqls.append(string+",");
			}
			sql =sql +	sqls.substring(0, sqls.lastIndexOf(","))+") values (";
			sqls.delete(0, sqls.length());
			for (String string : sets) {
				sqls.append(":"+string+",");
			}
			sql =sql +	sqls.substring(0, sqls.lastIndexOf(","))+") ";
			Query query = getSession().createSQLQuery(sql);
			
			setQueryParams(query, params);
			
			query.executeUpdate();
		}
	}

	@Override
	public String callProcedure(final String sql, final Object... obj) {
		Session session = getSession();
        return session.doReturningWork(new ReturningWork<String>() {
            @Override
            public String execute(Connection connection) throws SQLException {
                CallableStatement cs = connection.prepareCall(sql);
                int inParametersLength = obj.length;
                for(int i=0;i<inParametersLength;i++){
                    cs.setObject(i+1, obj[i]);
                }
                cs.registerOutParameter(inParametersLength+1,Types.VARCHAR);
                cs.executeUpdate();
                String str =cs.getString(inParametersLength+1);
                cs.close();
                return str;
            }
        });
	}
	private String getTableName() {
		Class<?> cls;
		String name = null;
		try {
			cls = Class.forName(entityClass.getName());
			name = cls.getAnnotation(Table.class).name();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return name;
	}
	@Override
	public boolean checkColumn(String columnName, Object columnValue, Object[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(columnName, columnValue);
		return checkColumns(map, ids);
	}

	@Override
	public boolean checkColumns(Map<String, Object> map, Object[] ids) {
		StringBuffer sql = new StringBuffer("select count(1) num from "+getTableName()+" where 1=1 ");
		List<Object> param = new ArrayList<Object>();
		for(String key:map.keySet()) {
			sql.append(" and "+key+"=? ");
			param.add(map.get(key));
		}
		if(ids!=null && ids.length>0) {
			for(Object id:ids) {
				if(StringUtil.notBlank(id)) {
					sql.append(" and id!=?");
					param.add(id);
				}
			}
		}
		List<Map<String, Object>> list = queryBySql_listMap(sql.toString(), param);
		if(list!=null && !list.isEmpty()) {
			return ((int)list.get(0).get("NUM")) == 0;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <S> List<S> findBySQLEntity(String sql, Object[] param,Class<S> cls){
		SQLQuery query = getSqlQueryToEntity(sql,cls.getName());
		setQueryParams(query, param);
		return query.list();
	}

	
}
