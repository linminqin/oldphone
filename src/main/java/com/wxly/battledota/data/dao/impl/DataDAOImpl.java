package com.wxly.battledota.data.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lmiky.jdp.database.dao.mybatis.BaseDAOImpl;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.wxly.battledota.data.dao.DataDAO;

/**
 * 数据
 * @author lmiky
 * @date 2015年1月16日 下午2:17:05
 */
@Repository("dataDAO")
public class DataDAOImpl extends BaseDAOImpl implements DataDAO  {

	/* (non-Javadoc)
	 * @see com.wxly.battledota.data.dao.DataDAO#importDatas(java.lang.Integer, java.lang.String, java.util.List, java.util.List)
	 */
	@Override
	public void importDatas(Integer type, String tableName, List<String> columnNames, List<Object> datas) throws DatabaseException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tableName", tableName);
			params.put("columns", columnNames);
			params.put("values", datas);
			sqlSessionTemplate.insert("common.add", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.wxly.battledota.data.dao.DataDAO#deleteDatas(java.lang.String)
	 */
	@Override
	public void deleteDatas(String tableName) throws DatabaseException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tableName", tableName);
			sqlSessionTemplate.delete("common.delete", params);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
