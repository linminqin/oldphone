package com.wxly.battledota.data.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.lmiky.jdp.controller.BaseController;
import com.lmiky.jdp.filemanager.util.FileUtils;
import com.lmiky.jdp.json.util.JsonUtils;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.util.DateUtils;
import com.lmiky.jdp.util.PropertiesUtils;
import com.lmiky.jdp.util.ResponseUtils;
import com.wxly.battledota.data.model.ItemData;
import com.wxly.battledota.data.service.DataService;

/**
 * 数据导入
 * @author lmiky
 * @date 2015年1月16日 上午10:50:02
 */
@Controller
@RequestMapping("/data")
public class DataImportController extends BaseController {
	// 导入类别
	public static final int IMPORT_TYPE_DELETE = 1; // 清空数据重导
	public static final int IMPORT_TYPE_APPEND = 2; // 在原有数据上追加

	// 关卡类别
	public static final int STAGE_TYPE_NORMAL = 1; // 普通关卡
	public static final int STAGE_TYPE_ELITE = 2; // 精英关卡

	/**
	 * 导入数据
	 * @author lmiky
	 * @date 2015年1月16日 上午11:35:39
	 * @param modelMap
	 * @param request
	 * @param type 类别：1=清空数据重导，2=在原有数据上追加
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/import.shtml")
	public void importData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", required = true) Integer type) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String savePath = PropertiesUtils.getStringContextValue("system.file.path");
			String filePath = FileUtils.upload(modelMap, request, response, "file", savePath); // 上传文件
			String realPath = WebUtils.getRealPath(request.getSession().getServletContext(), filePath);
			File importFile = new File(realPath);
			Workbook book = Workbook.getWorkbook(importFile);
			String tableName = null; // 表名
			int columnCount = 0; // 列数
			int rowCount = 0; // 行数
			List<String> tableColumns = new ArrayList<>();
			List<String> tableColumnTypes = new ArrayList<>();
			List<List<Object>> tableRows = new ArrayList<>();
			for (Sheet sheet : book.getSheets()) {
				tableName = sheet.getName().trim();
				columnCount = sheet.getColumns();
				rowCount = sheet.getRows();
				tableColumns.clear();
				tableColumnTypes.clear();
				// 获取列名
				for (int i = 0; i < columnCount; i++) {
					String columnName = sheet.getCell(i, 0).getContents().trim();
					String columnType = columnName.substring(columnName.length() - 1);
					columnName = columnName.substring(0, columnName.length() - 1);
					tableColumns.add(columnName);
					tableColumnTypes.add(columnType);
				}
				tableRows.clear();
				List<Object> rowValues = null;
				for (int i = 1; i < rowCount; i++) {
					rowValues = new ArrayList<>();
					for (int j = 0; j < columnCount; j++) {
						String rowValue = sheet.getCell(j, i).getContents().trim(); // 获取每行的数据
						String columnType = tableColumnTypes.get(j);
						Object data = rowValue;
						switch (columnType) { // 数据类型
						case "S":
							data = rowValue;
							break;
						case "I":
							data = Integer.parseInt(rowValue);
							break;
						case "L":
							data = Long.parseLong(rowValue);
							break;
						case "D":
							data = Double.parseDouble(rowValue);
							break;
						case "F":
							data = Float.parseFloat(rowValue);
							break;
						case "E":
							data = DateUtils.parseDate(rowValue);
							break;
						case "T":
							data = DateUtils.parseTime(rowValue);
							break;
						case "N":
							data = DateUtils.parseDateTime(rowValue);
							break;
						}
						rowValues.add(data);
					}
					tableRows.add(rowValues);
				}
				((DataService) service).importDatas(type, tableName, tableColumns, tableRows);
			}
			result.put("code", 200);
		} catch (Exception e) {
			transactException(e, modelMap, request, response);
			result.put("code", 500);
			result.put("msg", e.getMessage());
		}
		ResponseUtils.write(response, JsonUtils.toJson(result));
	}

	/**
	 * 导入关卡掉落物品数据
	 * @author lmiky
	 * @date 2015年1月16日 下午9:44:53
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param stageType 关卡类别：1=普通关卡，2=精英关卡
	 * @throws Exception
	 */
	@RequestMapping("/importStageItem.shtml")
	public void importStageItem(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "stageType", required = true) Integer stageType, @RequestParam(value = "type", required = true) Integer type) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String savePath = PropertiesUtils.getStringContextValue("system.file.path");
			String filePath = FileUtils.upload(modelMap, request, response, "file", savePath); // 上传文件
			String realPath = WebUtils.getRealPath(request.getSession().getServletContext(), filePath);
			File importFile = new File(realPath);
			Workbook book = Workbook.getWorkbook(importFile);
			Sheet sheet = book.getSheet(0);	//设定为第一个单元格
			List<ItemData> datas = new ArrayList<>();
			for(int i=0; i<sheet.getRows(); i++) {
				int stageIdentifier = Integer.parseInt(sheet.getCell(0, i).getContents().trim());
				//固定获得物品1
				int amount = Integer.parseInt(sheet.getCell(3, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);//关卡ID
					itemData.setItemIdentifier(Integer.parseInt(sheet.getCell(2, i).getContents().trim()));//固定获得物品ID
					itemData.setAmount(amount);	//固定获得物品数量
					itemData.setProbability(new Double(Double.parseDouble(sheet.getCell(4, i).getContents().trim()) * 100).intValue());	//固定获得物品概率
					itemData.setType(ItemData.TYPE_FIXED);
					datas.add(itemData);
				}
				//固定获得物品2
				amount = Integer.parseInt(sheet.getCell(6, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setItemIdentifier(Integer.parseInt(sheet.getCell(5, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(new Double(Double.parseDouble(sheet.getCell(7, i).getContents().trim()) * 100).intValue());
					itemData.setType(ItemData.TYPE_FIXED);
					datas.add(itemData);
				}
				//固定获得物品3
				amount = Integer.parseInt(sheet.getCell(9, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setItemIdentifier(Integer.parseInt(sheet.getCell(8, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(new Double(Double.parseDouble(sheet.getCell(10, i).getContents().trim()) * 100).intValue());
					itemData.setType(ItemData.TYPE_FIXED);
					datas.add(itemData);
				}
				//固定获得物品4
				amount = Integer.parseInt(sheet.getCell(12, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setItemIdentifier(Integer.parseInt(sheet.getCell(11, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(new Double(Double.parseDouble(sheet.getCell(13, i).getContents().trim()) * 100).intValue());
					itemData.setType(ItemData.TYPE_FIXED);
					datas.add(itemData);
				}
				//固定获得物品5
				amount = Integer.parseInt(sheet.getCell(15, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setItemIdentifier(Integer.parseInt(sheet.getCell(14, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(new Double(Double.parseDouble(sheet.getCell(16, i).getContents().trim()) * 100).intValue());
					itemData.setType(ItemData.TYPE_FIXED);
					datas.add(itemData);
				}
				//随机获得物品1
				amount = Integer.parseInt(sheet.getCell(19, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setMinItemIdentifier(Integer.parseInt(sheet.getCell(17, i).getContents().trim()));
					itemData.setMaxItemIdentifier(Integer.parseInt(sheet.getCell(18, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(new Double(Double.parseDouble(sheet.getCell(20, i).getContents().trim()) * 100).intValue());
					itemData.setType(ItemData.TYPE_RANDOM);
					datas.add(itemData);
				}
				//随机获得物品2
				amount = Integer.parseInt(sheet.getCell(23, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setMinItemIdentifier(Integer.parseInt(sheet.getCell(21, i).getContents().trim()));
					itemData.setMaxItemIdentifier(Integer.parseInt(sheet.getCell(22, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(new Double(Double.parseDouble(sheet.getCell(24, i).getContents().trim()) * 100).intValue());
					itemData.setType(ItemData.TYPE_RANDOM);
					datas.add(itemData);
				}
				//其他奖励获得物品
				amount = Integer.parseInt(sheet.getCell(26, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setItemIdentifier(Integer.parseInt(sheet.getCell(25, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(new Double(Double.parseDouble(sheet.getCell(27, i).getContents().trim()) * 100).intValue());
					itemData.setType(ItemData.TYPE_OTHER);
					datas.add(itemData);
				}
				//扫荡获得物品1
				amount = Integer.parseInt(sheet.getCell(29, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setItemIdentifier(Integer.parseInt(sheet.getCell(28, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(100);	//扫荡百分百获得
					itemData.setType(ItemData.TYPE_SWEEP);
					datas.add(itemData);
				}
				//扫荡获得物品2
				amount = Integer.parseInt(sheet.getCell(31, i).getContents().trim());	//数量
				if(amount > 0) {
					ItemData itemData = new ItemData();
					itemData.setStageIdentifier(stageIdentifier);
					itemData.setItemIdentifier(Integer.parseInt(sheet.getCell(30, i).getContents().trim()));
					itemData.setAmount(amount);
					itemData.setProbability(100);
					itemData.setType(ItemData.TYPE_SWEEP);
					datas.add(itemData);
				}
			}
			((DataService) service).importItemDatas(type, stageType, datas);
			result.put("code", 200);
		} catch (Exception e) {
			transactException(e, modelMap, request, response);
			result.put("code", 500);
			result.put("msg", e.getMessage());
		}
		ResponseUtils.write(response, JsonUtils.toJson(result));
	}

	/*
	 * (non-Javadoc)
	 * @see com.wxly.jdp.base.controller.BaseController#setService(com.wxly.jdp.service.BaseService)
	 */
	@Resource(name = "dataService")
	public void setService(BaseService service) {
		super.setService(service);
	}
}
