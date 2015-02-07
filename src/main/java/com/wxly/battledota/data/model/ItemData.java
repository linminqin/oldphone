package com.wxly.battledota.data.model;

/**
 * 物品数据
 * @author lmiky
 * @date 2015年1月17日 下午2:55:43
 */
public class ItemData {
	//类别
	public static final int TYPE_FIXED = 1;	//固定获得
	public static final int TYPE_RANDOM = 2;	//随机获得
	public static final int TYPE_SWEEP = 3;	//扫荡获得
	public static final int TYPE_OTHER = 4;	//其他
	
	private Integer stageIdentifier;
	private Integer maxItemIdentifier;
	private Integer minItemIdentifier;
	private Integer amount;
	private Integer probability;
	private Integer type;
	
	/**
	 * @return the stageIdentifier
	 */
	public Integer getStageIdentifier() {
		return stageIdentifier;
	}

	/**
	 * @param stageIdentifier the stageIdentifier to set
	 */
	public void setStageIdentifier(Integer stageIdentifier) {
		this.stageIdentifier = stageIdentifier;
	}

	/**
	 * 设置获得物品
	 * @author lmiky
	 * @date 2015年1月17日 下午2:58:38
	 * @param itemIdentifier
	 */
	public void setItemIdentifier(Integer itemIdentifier) {
		this.maxItemIdentifier = this.minItemIdentifier = itemIdentifier;	//固定获得一个物品
	}
	
	/**
	 * @return the maxItemIdentifier
	 */
	public Integer getMaxItemIdentifier() {
		return maxItemIdentifier;
	}
	/**
	 * @param maxItemIdentifier the maxItemIdentifier to set
	 */
	public void setMaxItemIdentifier(Integer maxItemIdentifier) {
		this.maxItemIdentifier = maxItemIdentifier;
	}
	/**
	 * @return the minItemIdentifier
	 */
	public Integer getMinItemIdentifier() {
		return minItemIdentifier;
	}
	/**
	 * @param minItemIdentifier the minItemIdentifier to set
	 */
	public void setMinItemIdentifier(Integer minItemIdentifier) {
		this.minItemIdentifier = minItemIdentifier;
	}
	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	/**
	 * @return the probability
	 */
	public Integer getProbability() {
		return probability;
	}
	/**
	 * @param probability the probability to set
	 */
	public void setProbability(Integer probability) {
		this.probability = probability;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
}
