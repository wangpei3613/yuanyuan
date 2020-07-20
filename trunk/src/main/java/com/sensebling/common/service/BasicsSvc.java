package com.sensebling.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;

/**
 * 系统基本业务层接口
 * @date 2014-9-23
 * @param <T>
 */
public interface BasicsSvc<T extends Serializable> {

	/**
	 * 删除一个实体
	 * 
	 * @param id 对象id
	 */
	public boolean delete(String id);

	/**
	 * 批量删除
	 * @param ids id数组
	 * @param cascade 是否应用级联关系(删除)
	 * @return
	 */
	public int deleteByIds(String[] ids,boolean cascade);
	/**
	 * 批量删除
	 * @param ids 多个或单个id,以逗号分隔
	 * @param cascade 是否应用级联关系(删除)
	 * @return
	 */
	
	public int deleteByIds(String ids,boolean cascade);
	/**
	 * 删除对象
	 * 
	 * @param t 对象
	 */
	
	public void delete(T t);

	/**
	 * 保存对象
	 * 
	 * @param t 对象
	 * @throws Exception
	 */
	public Serializable save(T t);
	/**
	 * 保存对象
	 * 
	 * @param t 对象
	 * @throws Exception
	 */
	public void save(List<T> t);

	/**
	 * 保存或者修改对象
	 * 
	 * @param t  对象
	 */
	public void saveOrUpdate(T t);
	
	public void saveOrUpdate(List<T> list);

	/**
	 * 更新对象数据
	 * @param t 对象
	 */
	public void update(T t);
	/**
	 * 更新对象数据
	 * @param t 对象
	 */
	public void merge(T t);

	/**
	 * 使用get方法得到一个实体
	 * @param id
	 * @return 实体
	 * @throws Exception
	 */
	public T get(Serializable id);
	public T getBean(Serializable id);

	/**
	 * 使用get取得数据，并加锁
	 * @param id
	 * @return 实体
	 */
	public T getLock(Serializable id);

	/**
	 * 使用load方法得到实体
	 * @param id 
	 * @return 实体
	 * @throws Exception
	 */
	public T load(Serializable id);

	/**
	 * 得到所有实体集合
	 * @return 实体数据集合
	 * @throws Exception
	 */
	public List<T> findAll();

	/**
	 * 得到所有满足查询条件的对象集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<T> findAll(QueryParameter qp);

	/**
	 * 查询总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public int findAllCount();
	/**
	 * 统计查询
	 * @param qp 查询参数对象
	 * @return 数据条数
	 */
	public int findAllCount(QueryParameter qp);

	/**
	 * 传入实体对象，查找对象的列表信息。 如果实体对象中的属性值为 null ，不参与查询条件
	 * 
	 * @param t 查询的实体
	 * @return
	 */
	public List<T> findByCriteria(T t);

	/**
	 * 分页查询，如果传入对象属性值为null，不参与查询条件
	 * 
	 * @param t 实体对象
	 * @param pageSize 每页显示条数
	 * @param pageIndex 页码，第几页
	 * @return pager 查询后分页对象
	 */
	public Pager findPage(T t, int pageSize, int pageIndex);

	/**
	 * 使用Criteria进行分页查询，可以传参以及参数配比条件
	 * 
	 * @param map
	 * key:需要查询的列名
	 * value :Object[] 第一个参数是 连接符 ：=，<>,<,>,like 等等；
	 * 如果第一个参数 是BaseFinal.IN ,第二个参数是 数组
	 * 如果第一个参数 是BaseFinal.NOTBETWEEN  需要有第二个参数和第三个参数，第二个是最小值，第三个是最大值
	 * 另外：如果key =“orderstr” 那么List中第一个参数是 desc或者 asc,第二个参数是列名
	 * @param page 分页对象
	 * @return 查询后的分页对象
	 */
	public Pager findByCriteria(Map<String, Object[]> map, Pager page);

	/**
	 * 快速分页查询
	 * @param qp 查询参数
	 * @return
	 */
	public Pager findPage(QueryParameter qp);
	public Pager findPageHql(QueryParameter qp);
	
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
	 * @param tbNm 表名
	 * @param params 要修改的字段
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
	 * 判断表中某字段值是否已存在
	 * @param columnName 字段名
	 * @param columnValue 字段值
	 * @param ids 排除的ids
	 * @return 返回true代表不存在
	 */
	public boolean checkColumn(String columnName, Object columnValue, Object... ids);
	/**
	 * 判断表中某些字段值是否已存在
	 * @param map key为字段名，value为字段值
	 * @param ids 排除的ids
	 * @return 返回true代表不存在
	 */
	public boolean checkColumns(Map<String, Object> map, Object... ids);
	/**
	 * 根据字段查询
	 * @param keys columnname、columnvalue交替传输
	 * @return
	 */
	public List<T> getByColumnsList(Object... keys);
	/**
	 * 根据字段查询
	 * @param keys columnname、columnvalue交替传输
	 * @return
	 */
	public T getByColumns(Object... keys);
}
