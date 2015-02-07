package com.wxly.battledota.data.service;

import java.util.List;

import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.service.exception.ServiceException;
import com.wxly.battledota.data.model.ItemData;

/**
 * 数据
 * @author lmiky
 * @date 2015年1月16日 下午12:34:55
 */
public interface DataService extends BaseService {

	/**
	 * 导入数据
	 * @author lmiky
	 * @date 2015年1月16日 下午2:07:27
	 * @param type 类别：1=清空数据重导，2=在原有数据上追加
	 * @param tableName
	 * @param columnNames
	 * @param datas
	 * @throws ServiceException
	 */
	public void importDatas(Integer type, String tableName, List<String> columnNames, List<List<Object>> datas) throws ServiceException;
	
	/**
	 * 导入物品数据
	 * @author lmiky
	 * @date 2015年1月17日 下午3:43:09
	 * @param type
	 * @param stageType
	 * @param datas
	 * @throws ServiceException
	 */
	public void importItemDatas(Integer type, Integer stageType, List<ItemData> datas) throws ServiceException;
}
