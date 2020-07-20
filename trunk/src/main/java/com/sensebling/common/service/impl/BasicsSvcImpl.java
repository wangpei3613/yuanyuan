package com.sensebling.common.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensebling.common.dao.BasicsDao;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;


/**
 * 系统基本业务层实现
 * @date 2014-9-23
 * @param <T>
 */
@Service("basicsSvc")
@Lazy(true)
@Transactional
public class BasicsSvcImpl<T extends Serializable> implements BasicsSvc<T> {
	@SuppressWarnings("all")
	private Class<T> entityClass;

	@Resource(name="baseDao")
	protected BasicsDao<T> baseDao;
	@SuppressWarnings("unchecked")
	public BasicsSvcImpl() {
		Type type = this.getClass().getGenericSuperclass();
       if(type instanceof ParameterizedType){  
           //参数化类型  
           ParameterizedType type1= (ParameterizedType) type;  
           //返回表示此类型实际类型参数的 Type 对象的数组  
           Type[] actualTypeArguments = type1.getActualTypeArguments();  
           setEntityClass((Class<T>) actualTypeArguments[0]);
       }else{  
           this.entityClass= (Class<T>)type;  
       }  
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}

	@PostConstruct
	public void init() throws Exception {
		baseDao.setEntityClass(entityClass);
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public boolean delete(String id) {
		return this.baseDao.delete(id)>0?true:false;
	}

	public int deleteByIds(String[] ids,boolean cascade) {
		return this.baseDao.deleteByIds(ids,cascade);
	}
	public int deleteByIds(String ids,boolean cascade)
	{
		return this.baseDao.deleteByIds(ids,cascade);
	}
	public void delete(T t) {
		this.baseDao.delete(t);
	}

	public List<T> findAll() {
		return this.baseDao.findAll();
	}
	public List<T> findAll(QueryParameter qp) {

		return this.baseDao.findAll(qp);
	}
	public int findAllCount() {
		return this.baseDao.findAllCount();
	}
	public int findAllCount(QueryParameter qp)
	{
		return this.baseDao.findAllCount(qp);
	}
	public T get(Serializable id) {
		return this.baseDao.get(id);
	}
	public T getBean(Serializable id) {
		return this.baseDao.getBean(id);
	}
	public T getLock(Serializable id) {
		return this.baseDao.get(id, true);
	}

	public T load(Serializable id) {
		return this.baseDao.load(id);
	}

	public Serializable save(T t) {
		return this.baseDao.save(t);
	}
	public void save(List<T> t) {
		this.baseDao.save(t);
	}

	public void saveOrUpdate(T t) {
		this.baseDao.saveOrUpdate(t);
	}
	
	public void saveOrUpdate(List<T> list) {
		this.baseDao.saveOrUpdate(list);
	}

	public void update(T t) {
		this.baseDao.update(t);
	}
	
	public void merge(T t) {
		this.baseDao.merge(t);
	}

	public Pager findPage(T t, int pageSize, int pageIndex) {
		
		return this.baseDao.queryCriteriaPage(t, pageSize, pageIndex);
	}

	public List<T> findByCriteria(T t) {
		
		return this.baseDao.findByCriteria(t);
	}
	public Pager findPage(QueryParameter qp)
	{
		return this.baseDao.paginationHql(qp);
	}
	public Pager findPageHql(QueryParameter qp)
	{
		return this.baseDao.paginationHql(qp);
	}
	public Pager findByCriteria(Map<String, Object[]> map, Pager page) {
		
		return this.baseDao.queryCriteriaPage(map, page);
	}
	
	/**
	 * 根据表名查询单表信息()
	 * YF
	 * 2017-11-27
	 * @param tableName 表名
	 * @param params 查询条件可以为空
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findListByTableName(String tableName,Map<String, Object> params) throws Exception {
		return  (List<Map<String, Object>>) this.baseDao.findListByTableName(tableName, params);
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
		
		return this.baseDao.findPageByTableName(tableName, pageSize, pageIndex, params);
	}
	
	
	
	/**
	 * 根据条件删除表中信息
	 * YF
	 * 2017-11-28 下午01:50:24
	 * @param tableName 表名
	 * @param params
	 * @throws Exception
	 */
	public int delInfoByTableName(String tableName,Map<String, Object> params )throws Exception{
		return this.baseDao.delInfoByTableName(tableName,params);
	}
	
	/**
	 * 根据一定条件修改单表中信息
	 * YF
	 * 2017-11-28 下午09:30:34
	 * @param tableName 表名
	 * @param params 要修改的字段
	 * @param cond 条件
	 * @throws Exception
	 */
	public int updateByTableName(String tableName,Map<String, Object> params,Map<String, Object> cond)throws Exception{
		return this.baseDao.updateByTableName(tableName, params, cond);
	}

	/**
	 * 添加
	 * YF
	 * 2017-11-29 上午10:56:39
	 * @param tableName 表名
	 * @param params 字段参数
	 * @throws Exception
	 */
	@Override
	public void addByTableName(String tableName, Map<String, Object> params)
			throws Exception {
		this.baseDao.addByTableName(tableName, params);
	}

	@Override
	public synchronized boolean checkColumn(String columnName, Object columnValue, Object... ids) {
		return baseDao.checkColumn(columnName, columnValue, ids);
	}

	@Override
	public synchronized boolean checkColumns(Map<String, Object> map, Object... ids) {
		return baseDao.checkColumns(map, ids);
	}

	@Override
	public List<T> getByColumnsList(Object... keys) {
		QueryParameter qp = new QueryParameter();
		if(keys!=null && keys.length>0) {
			for(int i=0;i<keys.length;i+=2) {
				if(keys.length > i+1) {
					if(StringUtil.isBlank(keys[i+1]) || StringUtil.isBlank(keys[i])) {//若出现字段名或字段值为空时直接返回空
						return null;
					}
					qp.addParamter(StringUtil.ValueOf(keys[i]), keys[i+1]);
				}
			}
		}
		return findAll(qp);  
	}

	@Override
	public T getByColumns(Object... keys) {
		List<T> list = getByColumnsList(keys);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	
}
