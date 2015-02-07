package com.wxly.battledota.data.dao;

import java.util.List;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.exception.DatabaseException;

/**
 * @author lmiky
 * @date 2015年1月16日 下午2:14:32
 */
public interface DataDAO extends BaseDAO {

	/**
	 * 数据导入
	 * @author lmiky
	 * @date 2015年1月16日 下午2:15:24
	 * @param type 类别：1=清空数据重导，2=在原有数据上追加
	 * @param tableName
	 * @param columnNames
	 * @param datas
	 * @throws DatabaseException
	 */
	public void importDatas(Integer type, String tableName, List<String> columnNames, List<Object> datas) throws DatabaseException;
	
	/**
	 * 删除数据
	 * @author lmiky
	 * @date 2015年1月16日 下午3:03:15
	 * @param tableName
	 * @throws DatabaseException
	 */
	public void deleteDatas(String tableName) throws DatabaseException;
}
