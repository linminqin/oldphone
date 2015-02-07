package com.wxly.battledota.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmiky.jdp.database.dao.BaseDAO;
import com.lmiky.jdp.database.exception.DatabaseException;
import com.lmiky.jdp.service.exception.ServiceException;
import com.lmiky.jdp.service.impl.BaseServiceImpl;
import com.wxly.battledota.data.controller.DataImportController;
import com.wxly.battledota.data.dao.DataDAO;
import com.wxly.battledota.data.model.ItemData;
import com.wxly.battledota.data.service.DataService;

/**
 * 数据
 * @author lmiky
 * @date 2015年1月16日 下午2:14:41
 */
@Service("dataService")
public class DataServiceImpl extends BaseServiceImpl implements DataService {
	
	/* (non-Javadoc)
	 * @see com.wxly.battledota.data.service.DataService#importDatas(java.lang.Integer, java.lang.String, java.util.List, java.util.List)
	 */
	@Transactional(rollbackFor={Exception.class})
	public void importDatas(Integer type, String tableName, List<String> columnNames, List<List<Object>> datas) throws ServiceException {
		try {
			DataDAO dataDAO = (DataDAO)baseDAO;
			//删除旧数据
			if(type == DataImportController.IMPORT_TYPE_DELETE) {
				dataDAO.deleteDatas(tableName);
			}
			for(List<Object> d : datas) {	//循环插入
				dataDAO.importDatas(type, tableName, columnNames, d);
			}
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.wxly.battledota.data.service.DataService#importItemDatas(java.lang.Integer, java.lang.Integer, java.util.List)
	 */
	@Transactional(rollbackFor={Exception.class})
	public void importItemDatas(Integer type, Integer stageType, List<ItemData> datas) throws ServiceException {
		try {
			String tableName = stageType == DataImportController.STAGE_TYPE_NORMAL ? "t_stage_map_item" : "t_stage_map_elite_item"; // 关卡掉落表名
			//列名
			List<String> columnNames = new ArrayList<>();
			columnNames.add("minItemIdentifier");
			columnNames.add("maxItemIdentifier");
			columnNames.add("amount");
			columnNames.add("probability");
			columnNames.add("type");
			columnNames.add("stageMapId");
			List<List<Object>> improtDatas = new ArrayList<>();
			for(ItemData itemData : datas) {
				List<Object> item = new ArrayList<>();
				item.add(itemData.getMinItemIdentifier());
				item.add(itemData.getMaxItemIdentifier());
				item.add(itemData.getAmount());
				item.add(itemData.getProbability());
				item.add(itemData.getType());
				item.add(itemData.getStageIdentifier());
				improtDatas.add(item);
			}
			importDatas(type, tableName, columnNames, improtDatas);
		} catch (DatabaseException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.wxly.jdp.service.impl.BaseServiceImpl#setDAO(com.wxly.jdp.database.dao.BaseDAO)
	 */
	@Resource(name = "dataDAO")
	public void setDAO(BaseDAO dao) {
		super.setDAO(dao);
	}

}
