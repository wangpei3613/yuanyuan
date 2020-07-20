package com.sensebling.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;


/**
 * 数据库操作接口
 * 
 * @author 
 * @date 2014-9-23
 * @param <T>
 */
public interface BasicsDao<T extends Serializable> {

	public void setEntityClass(Class<T> entityClass);
	/**
	 * 执行hql，传参 Object[]
	 * 
	 * @param hql
	 * @param params
	 * @return integer
	 */
	public Integer executeHQL(String hql, Object... params);

	/**
	 * 执行hql,传参List
	 * 
	 * @param hql
	 * @param params
	 * @return integer
	 */
	public Integer executeHQL(String hql, List<Object> params);

	/**
	 * 执行简单hql
	 * 
	 * @param hql
	 * @return Integer
	 */
	public Integer executeHQL(String hql);

	/**
	 * 执行sql，传参 Object[]
	 * 
	 * @param sql
	 * @param params
	 * @return integer
	 */
	public Integer executeSQL(String sql, Object... params);

	/**
	 * 执行sql，传参 List
	 * 
	 * @param sql
	 * @param params
	 * @return integer
	 */
	public Integer executeSQL(String sql, List<Object> params);

	/**
	 * 执行简单sql
	 * 
	 * @param sql
	 * @return Integer
	 */
	public Integer executeSQL(String sql);

	/**
	 * 保存对象信息
	 * 
	 * @param t
	 *            对象信息
	 * @return Serializable
	 */
	public Serializable save(T t);
	/**
	 * 保存对象信息
	 * 
	 * @param t
	 *            对象信息
	 * @return Serializable
	 */
	public void save(List<T> t);

	/**
	 * 修改实体
	 * 
	 * @param t
	 */
	public void update(T t);
	/**
	 * 修改实体
	 * 
	 * @param t
	 */
	public void merge(T t);

	/**
	 * 使用 HQL修改对象信息
	 * 
	 * @param hql
	 * @param Object[]
	 *            objects
	 * @return Integer
	 */
	public Integer updateByHQL(String hql, Object[] objects);

	/**
	 * 使用hql修改
	 * 
	 * @param hql
	 * @param List
	 * @return Integer
	 */
	public Integer updateByHQL(String hql, List<Object> params);

	/**
	 * 使用hql修改 无参
	 * 
	 * @param hql
	 * @return Integer
	 */
	public Integer updateByHQL(String hql);

	/**
	 * 使用SQL修改
	 * 
	 * @param sql
	 * @param object[]
	 *            objects
	 * @return Integer
	 */
	public Integer updateBySQL(String sql, Object... objects);

	/**
	 * 使用SQL修改
	 * 
	 * @param sql
	 * @param List
	 *            <Object> params
	 * @return Integer
	 */
	public Integer updateBySQL(String sql, List<Object> params);

	/**
	 * 使用sql修改 无参
	 * 
	 * @param sql
	 * @return Integer
	 */
	public Integer updateBySQL(String sql);

	/**
	 * 删除对象信息
	 * 
	 * @param t
	 *            对象
	 */
	public void delete(T t);

	/**
	 * 删除对象信息
	 * 
	 * @param id
	 *            对象id
	 * @return int
	 */
	public int delete(String id);
	/**
	 * 批量删除
	 * @param ids 多个或单个id,以逗号分隔
	 * @param cascade 是否应用级联关系(删除)
	 * @return
	 */
	public int deleteByIds(String ids,boolean cascade);
	/**
	 * 批量删除
	 * @param ids id数组
	 * @param cascade 是否应用级联关系(删除)
	 * @return
	 */
	public int deleteByIds(String[] ids,boolean cascade);
	
	/**
	 * 根据sql语句删除对象
	 * @param sql
	 * @param params
	 * @return
	 */
	public int deleteBySql(String sql,Object[] params);


	/**
	 * 保存或者修改
	 * 
	 * @param entity
	 *            对象
	 */
	public void saveOrUpdate(T t);
	
	public void saveOrUpdate(List<T> list);

	/**
	 * 获得对象信息，不用锁定
	 * 
	 * @param id
	 *            对象id
	 * @return entity
	 */
	public T get(Serializable id);
	/**
	 * 获得普通javabean对象
	 * 
	 * @param id
	 *            对象id
	 * @return entity
	 */
	public T getBean(Serializable id);
	/**
	 * 获得对象信息，不用锁定
	 * 
	 * @param id
	 *            对象id
	 * @return entity
	 */
	public T get(Class<T> cl,Serializable id);

	/**
	 * 获得实体信息，lock=true 表示锁定
	 * 
	 * @param id
	 *            对象id
	 * @param lock
	 *            是否锁定
	 * @return entity
	 */
	public T get(Serializable id, boolean lock);

	/**
	 * 使用hql查询，得到一条记录
	 * 
	 * @param hql
	 * @param Object[]
	 * @return T
	 */
	public T getByHQL(String hql, Object[] params);

	/**
	 * 使用hql查询，得到一条记录
	 * 
	 * @param hql
	 * @param params
	 * @return T
	 */
	public T getByHQL(String hql, List<Object> params);
	/**
	 * sql查询，得到一个默认的实体对象
	 * @param sql 需要执行的sql
	 * @return
	 */
	public T getBySQLEntity(String sql);
	/**
	 * sql查询，得到一个默认的实体对象
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @return
	 */
	public T getBySQLEntity(String sql, List<Object> params); 
	/**
	 * sql查询，得到一个指定的实体对象
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @param entityName 需要转换成的对象名称（类名）
	 * @return
	 */
	public T getBySQLEntity(String sql, List<Object> params,String entityName);
	/**
	 * sql查询，得到一个指定的实体对象
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @param entityName 需要转换成的对象名称（类名）
	 * @return
	 */
	public Object getBySQLEntity(String sql,String entityName, Object... params);
	public <S>S getBySQLEntity(String sql, Class<S> cls,Object... params);
	/**
	 * 使用sql查询，得到一条数据
	 * 
	 * @param sql
	 * @param List
	 * @return T
	 */
	public T getBySQL(String sql, List<Object> params);

	/**
	 *  使用sql查询，得到一条数据，封装成指定对象
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @param entityName 需要转换成的对象名（类名）
	 * @return
	 */
	public T getBySQLEntity(String sql, Object[] params,String entityName);
	/**
	 * 使用sql查询，得到一条数据，封装成对象,默认
	 * @param sql 需要执行的sql
	 * @param params 参数
	 * @return 
	 */
	public T getBySQLEntity(String sql, Object[] params);
	/**
	 * 使用sql查询，得到一条数据
	 * 
	 * @param sql
	 * @param Object[]
	 * @return T
	 */
	public T getBySQL(String sql, Object[] params);

	/**
	 * 查询列表
	 * 
	 * @param hql
	 * @return List
	 */
	public List<T> find(String hql);

	/**
	 * 查询列表
	 * 
	 * @param hql
	 * @param Object[]
	 * @return List
	 */
	public List<T> find(String hql, Object[] param);

	/**
	 * 查询列表
	 * 
	 * @param hql
	 * @param List
	 * @return List
	 */
	public List<T> find(String hql, List<Object> param);

	/**
	 * 使用sql查询，返回列表
	 * 
	 * @param sql
	 * @param List
	 * @return List
	 */
	public List<T> findBySQL(String sql, List<Object> param);
	/**
	 * sql查询，把列表中的信息转换成默认的对象
	 * @param sql 需要执行的sql
	 * @return
	 */
	public List<T> findBySQLEntity(String sql);
	/**
	 * sql查询，把列表中的信息转换成默认的对象
	 * @param sql 需要执行的sql
	 * @param param 参数
	 * @return
	 */
	
	public List<T> findBySQLEntity(String sql, List<Object> param);
	/**
	 * sql查询，把列表中的信息转换成指定的对象
	 * @param sql 需要执行的sql
	 * @param param 参数
	 * @param entityName 需要转换成的对象名称（类名）
	 * @return
	 */
	public List<T> findBySQLEntity(String sql, List<Object> param,String entityName); 

	/**
	 * 使用sql查询，返回列表
	 * 
	 * @param sql
	 * @param Object[]
	 * @return List
	 */
	public List<T> findBySQL(String sql, Object[] param);
	
	/**
	 * 使用sql查询，返回列表,并且把list中的信息封装成默认的对象
	 * @param sql 需要执行的sql
	 * @param param  参数
	 * @return
	 */
	public List<T> findBySQLEntity(String sql, Object[] param);
	/**
	 * 使用sql查询，返回列表,并且把list中的信息封装成指定的对象
	 * @param sql 需要执行的sql
	 * @param param  参数
	 * @param entityName 需要转换的对象名称（类名）
	 * @return
	 */
	public List<T> findBySQLEntity(String sql, Object[] param,String entityName);
	public <S> List<S> findBySQLEntity(String sql, Object[] param,Class<S> cls);
	/**
	 * 使用sql查询，返回列表,并且把list中的信息封装成指定的对象
	 * @param sql 需要执行的sql
	 * @param entityName 需要转换的对象名称（类名）
	 * @param param  参数
	 * @return
	 */
	public List<T> findBySQLEntity(String sql,String entityName, Object... param);
	public <S> List<S> findBySQLEntity(String sql,Class<S> cls, Object... param);
	/**
	 * load方法得到对象信息
	 * 
	 * @param id
	 *            对象id
	 * @return entity
	 */
	public T load(Serializable id);

	/**
	 * 根据实体查询
	 * 
	 * @param t
	 * @return
	 */
	public List<T> findByCriteria(T t);

	/**
	 * 获得总数
	 * 
	 * @param hql
	 * @return Long
	 */
	public Long count(String hql);

	/**
	 * 获得总数
	 * 
	 * @param hql
	 * @param Object[]
	 * @return Long
	 */
	public Long count(String hql, Object[] params);

	/**
	 * 获得总数
	 * 
	 * @param hql
	 * @param List
	 * @return Long
	 */
	public Long count(String hql, List<Object> params);

	/**
	 * 使用hql查询
	 * 
	 * @param hql
	 * @param object[]
	 * @return List<Object[]>
	 */
	public List<Object[]> queryByHql(String hql, Object... objects);

	/**
	 * 使用hql查询
	 * 
	 * @param hql
	 * @param List
	 * @return List<Object[]>
	 */
	public List<Object[]> queryByHql(String hql, List<Object> params);

	/**
	 * 使用sql查询
	 * 
	 * @param sql
	 * @param object[]
	 * @return List<Object[]>
	 */
	public List<Object[]> queryBySql(String sql, Object... objects);

	/**
	 * 使用sql查询
	 * 
	 * @param sql
	 * @param List
	 * @return List<Object[]>
	 */
	public List<Object[]> queryBySql(String sql, List<Object> params);
	/**
	 * 使用sql 查询 返回List map
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryBySql_listMap(String sql, Object... params);
	public List<Map<String, Object>> queryBySql_listMap(String sql);

	/**
	 * 得到总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer findAllCount();
	public Integer findAllCount(QueryParameter qp);
	public Integer findSqlCount(String sql,List<Object> params);
	public Integer findSqlCount(String sql,Object... params);
	/**
	 * 得到实体列表集合（所有）
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<T> findAll();
	/**
	 * 得到实体列表集合（所有）
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<T> findAll(QueryParameter qp);

	/**
	 * 
	 * 通过sql 查询数据 , 分页 并传入参数 new Object[]{a,b},如果不想分页 可把pageSize和 pageIndex 设为0
	 * 
	 * @param sql
	 * @param objs
	 * @return List<T>
	 */
	public List<T> querySQLParam(String sql, int pageSize, int pageIndex,
			Object... obj);
	/**
	 * 通过sql 查询数据 , 分页 并传入参数 new Object[]{a,b},如果不想分页 可把pageSize和 pageIndex 设为0 <br>
	 * 并且把返回值信息封装成默认的对象
	 * @param sql 
	 * @param pageSize 每页显示多少条
	 * @param pageIndex 当前页
	 * @param obj
	 * @return
	 */
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,
			Object... obj);

	/**
	 * 通过sql 查询数据 , 分页 并传入参数 new Object[]{a,b},如果不想分页 可把pageSize和 pageIndex 设为0 <br>
	 * 并且把返回的信息封装成指定的对象 
	 * @param sql
	 * @param pageSize 每页显示多少条
	 * @param pageIndex 当前页
	 * @param entityName 需要封装的对象名称（类名）
	 * @param obj
	 * @return
	 */
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,String entityName,
			Object... obj);
	
	/**
	 * 
	 * 通过sql 查询数据 , 分页 并传入参数 List,如果不想分页 可把pageSize和 pageIndex 设为0
	 * 
	 * @param sql
	 * @param objs
	 * @return List
	 */
	public List<T> querySQLParam(String sql, int pageSize, int pageIndex,
			List<Object> params);
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
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,String entityName,
			List<Object> params);
	/**
	 * 通过sql 查询数据 , 分页 并传入参数 List,如果不想分页 可把pageSize和 pageIndex 设为0<br>
	 * 把返回list中的信息封装成默认的对象
	 * @param sql
	 * @param pageSize
	 * @param pageIndex
	 * @param params
	 * @return
	 */
	
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,
			List<Object> params);

	/**
	 * 通过sql 查询数据 , 分页 如果不想分页 可把pageSize和 pageIndex 设为0
	 * 
	 * @param sql 需要执行的sql
	 * @param pageSize 每页显示条数
	 * @param pageIndex 当前页
	 * @return
	 */
	public List<T> querySQLParam(String sql, int pageSize, int pageIndex);
	/**
	 * 通过sql 查询数据 , 分页 如果不想分页 可把pageSize和 pageIndex 设为0<br>
	 * 把返回list中的信息封装成默认的对象
	 * @param sql 需要执行的sql
	 * @param pageSize 每页显示条数
	 * @param pageIndex 当前页
	 * @return
	 */
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex);
	/**
	 * 通过sql 查询数据 , 分页 如果不想分页 可把pageSize和 pageIndex 设为0<br>
	 * 把返回list中的信息封装成指定的对象
	 * @param sql 需要执行的sql
	 * @param pageSize 每页显示条数
	 * @param pageIndex 当前页
	 * @param entityName 需要转换成的对象名称（类名）
	 * @return
	 */
	public List<T> querySQLParamEntity(String sql, int pageSize, int pageIndex,String entityName);
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
			Object... obj);
	/**
	 * 执行hql语句分页，并按顺序传入参数new Object[]{a,b} 分页数据获取 pager.getList()
	 * 本方法适用于db2数据库
	 * @param queryHql 查询hql
	 * @param orderHql 排序hql
	 * @param pageSize
	 *            每页显示的条数
	 * @param pageIndex
	 *            页码，第几页
	 * @param List
	 *            params
	 * @return Pager
	 */
	public Pager queryHQLPageDB2(String queryHql,String orderHql, int pageSize, int pageIndex,
			List<Object> params);

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
			List<Object> params);

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
	public Pager queryHQLPage(String hql, int pageSize, int pageIndex);

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
			Object... obj);

	/**
	 * SQL查询数据并分页<br>
	 *  把返回值封装到默认的对象里
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
			Object... obj);
	/**
	 * SQL查询数据并分页<br>
	 * 把返回值封装到指定的对象里
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
			Object... obj);
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
			List<Object> params);
	/**
	 * SQL查询数据并分页，<br>
	 * 并且把返回信息封装在指定的对象中
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
			List<Object> params);
	/**
	 * SQL查询数据并分页，<br>
	 * 并且把返回信息封装在默认的对象中
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
			List<Object> params);

	/**
	 * SQL查询数据并分页，<br>
	 * 
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @return Pager
	 */
	public Pager querySQLPage(String sql, int pageSize, int pageIndex);
	/**
	 * SQL查询数据并分页<br>
	 * 并且把返回信息转换成默认的对象
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @return Pager
	 */
	public Pager querySQLPageEntity(String sql, int pageSize, int pageIndex);
	/**
	 * SQL查询数据并分页，<br>
	 * 并且把返回信息转换成指定对象
	 * @param sql
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @entityName 需要转换成的对象名称（类名）
	 * @return Pager
	 */
	public Pager querySQLPageEntity(String sql, int pageSize, int pageIndex,String entityName);
	
	/**
	 * 使用Criteria做分页查询，传入实体对象
	 * 
	 * @param t
	 *            实体对象
	 * @param pageSize
	 *            每页显示条数
	 * @param pageIndex
	 *            页码，第几页
	 * @return pager
	 */
	public Pager queryCriteriaPage(T t, int pageSize, int pageIndex);
	public Pager paginationHql(String hql, List<Object> params, Pager pager);
	public Pager paginationSql(String sql, List<Object> params, Pager pager);
	public Pager pagination(QueryParameter qp);
	/**
	 * 分页查询，传入map参数以及查询条件限制
	 * @param map
	 * @param page
	 * @return
	 */
	public Pager queryCriteriaPage(Map<String,Object[]> map,Pager page);
	/**
	 * openSession 查询 仅供系统启动时，初始化数据的加载使用
	 * 
	 * @return
	 */
	public List<T> findAllOpen();
	/**
	 * 获得SessionFactory
	 * @return
	 */
	public SessionFactory getSessionFactory();
	/**
	 * 自动分页查询,使用hql方式查询分页
	 * @param qp
	 * @return
	 */
	public Pager paginationHql(QueryParameter qp);
	public <S> Pager findPageSql(String sql, QueryParameter qp, Class<S> cls);
	public <S> List<S> findListSql(String sql, QueryParameter qp, Class<S> cls);
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
			List<Object> params);
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
			Object... params);
	
	/**
	 * 查询
	 * 根据表名查询单表信息 
	 * YF
	 * 2017-11-27
	 * @param tableName 表名
	 * @param params 查询条件可以为空
	 * @return
	 */
	public List<Map<String, Object>> findListByTableName(String tableName,Map<String, Object> params)throws Exception;
	
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
	public Pager findPageByTableName(String tableName,int pageSize, int pageIndex,Map<String,Object> params)throws Exception;
	
	/**
	 * 根据条件删除表中信息
	 * YF
	 * 2017-11-28 下午01:50:24
	 * @param tableName 表名
	 * @param params
	 * @throws Exception
	 */
	public int delInfoByTableName(String tableName,Map<String, Object> params )throws Exception;
	
	/**
	 * 根据一定条件修改单表中信息
	 * YF
	 * 2017-11-28 下午09:30:34
	 * @param tableName 表名
	 * @param params 要修改的字段 操作符必须是equal
	 * @param cond 条件
	 * @throws Exception
	 */
	public int updateByTableName(String tableName,Map<String, Object> params,Map<String, Object> cond)throws Exception;
	
	/**
	 * 添加
	 * YF
	 * 2017-11-29 上午10:56:39
	 * @param tableName 表名
	 * @param params 字段参数
	 * @throws Exception
	 */
	public void addByTableName(String tableName,Map<String, Object> params)throws Exception;
	/**
	 * 执行存储过程 返回字符串
	 * @param sql call语句
	 * @param obj 参数
	 * @return
	 */
	public String callProcedure(String sql, Object... obj);
	/**
	 * 判断表中某字段值是否已存在
	 * @param columnName 字段名
	 * @param columnValue 字段值
	 * @param ids 排除的ids
	 * @return 返回true代表不存在
	 */
	public boolean checkColumn(String columnName, Object columnValue, Object[] ids);
	/**
	 * 判断表中某些字段值是否已存在
	 * @param map key为字段名，value为字段值
	 * @param ids 排除的ids
	 * @return 返回true代表不存在
	 */
	public boolean checkColumns(Map<String, Object> map, Object[] ids);
}
