/******************************************************************/
/*****************************基础数据*****************************/
/******************************************************************/

/*英雄*/
ALTER TABLE t_hero DROP FOREIGN KEY fk_hero_soulitemid;
UPDATE t_hero SET soulItemId = (SELECT identifier FROM t_item WHERE id = soulItemId) WHERE soulItemId IS NOT NULL;
ALTER TABLE t_hero CHANGE COLUMN soulItemId   soulItemId   INT COMMENT '灵魂石物品ID';


/*物品*/
ALTER TABLE t_item DROP FOREIGN KEY fk_item_syntheticformulaid;
UPDATE t_item SET syntheticFormulaId = (SELECT identifier FROM t_synthetic_formula WHERE id = syntheticFormulaId) WHERE syntheticFormulaId IS NOT NULL;
ALTER TABLE t_item CHANGE COLUMN syntheticFormulaId  syntheticFormulaId INT COMMENT '合成配方';

ALTER TABLE t_item ADD COLUMN type INT NOT NULL COMMENT '物品类型分类';
UPDATE t_item i SET i.type = (SELECT t.identifier FROM t_item_subtype s, t_item_type t WHERE s.typeId = t.id AND s.id = i.subType);
ALTER TABLE t_item DROP FOREIGN KEY fk_item_subtype;
UPDATE t_item t SET t.subType = (SELECT identifier FROM t_item_subtype WHERE id = t.subType) WHERE subType IS NOT NULL;
ALTER TABLE t_item CHANGE COLUMN subType subType INT NOT NULL COMMENT '物品类型子分类';

/*物品子分类*/
ALTER TABLE t_item_subtype DROP FOREIGN KEY fk_itemsubtype_typeid;
UPDATE t_item_subtype t SET t.typeId = (SELECT identifier FROM t_item_type WHERE id = t.typeId) WHERE typeId IS NOT NULL;
ALTER TABLE t_item_subtype CHANGE COLUMN typeId typeId INT NOT NULL COMMENT '所属类别';

/*英雄成长信息*/
ALTER TABLE t_hero_grow_info DROP FOREIGN KEY fk_herogrowinfo_heroid;
UPDATE t_hero_grow_info t SET t.heroId = (SELECT identifier FROM t_hero WHERE id = t.heroId) WHERE heroId IS NOT NULL;
ALTER TABLE t_hero_grow_info CHANGE COLUMN heroId heroId INT NOT NULL COMMENT '英雄ID';

/*物品合成配方*/
ALTER TABLE t_synthetic_formula DROP FOREIGN KEY fk_syntheticformula_type;
UPDATE t_synthetic_formula t SET t.type = (SELECT identifier FROM t_synthetic_formula_type WHERE id = t.type) WHERE type IS NOT NULL;
ALTER TABLE t_synthetic_formula CHANGE COLUMN TYPE  type INT NOT NULL COMMENT '类别：1=碎片合成，3=装备成品合成';
DROP TABLE t_synthetic_formula_type;

/*英雄装备组*/
ALTER TABLE t_hero_equip DROP FOREIGN KEY fk_heroequip_hero;
UPDATE t_hero_equip t SET t.heroId = (SELECT identifier FROM t_hero WHERE id = t.heroId) WHERE heroId IS NOT NULL;
ALTER TABLE t_hero_equip CHANGE COLUMN heroId heroId INT NOT NULL COMMENT '英雄ID';

/*关卡地图*/
ALTER TABLE t_stage_map DROP FOREIGN KEY fk_stagemap_stageid;
UPDATE t_stage_map t SET t.stageId = (SELECT identifier FROM t_stage WHERE id = t.stageId) WHERE stageId IS NOT NULL;
ALTER TABLE t_stage_map CHANGE COLUMN stageId stageId INT NOT NULL COMMENT '所属关卡';

/*关卡物品掉落表*/
ALTER TABLE t_stage_map_item DROP FOREIGN KEY fk_stagemapitem_stagemapid;
UPDATE t_stage_map_item t SET t.stageMapId = (SELECT identifier FROM t_stage_map WHERE id = t.stageMapId) WHERE stageMapId IS NOT NULL;
ALTER TABLE t_stage_map_item CHANGE COLUMN stageMapId stageMapId INT NOT NULL COMMENT '关卡地图ID';

/*关卡精英地图*/
ALTER TABLE t_stage_map_elite DROP FOREIGN KEY fk_stagemapelite_stageid;
UPDATE t_stage_map_elite t SET t.stageId = (SELECT identifier FROM t_stage WHERE id = t.stageId) WHERE stageId IS NOT NULL;
ALTER TABLE t_stage_map_elite CHANGE COLUMN stageId stageId INT NOT NULL COMMENT '所属关卡';
ALTER TABLE t_stage_map_elite DROP FOREIGN KEY fk_stagemapelite_normalmapid;
UPDATE t_stage_map_elite t SET t.normalMapId = (SELECT identifier FROM t_stage_map WHERE id = t.normalMapId) WHERE normalMapId IS NOT NULL;
ALTER TABLE t_stage_map_elite CHANGE COLUMN normalMapId normalMapId INT NOT NULL COMMENT '对应普通关卡ID';

/*精英关卡物品掉落表*/
ALTER TABLE t_stage_map_elite_item DROP FOREIGN KEY fk_stagemapeliteitem_stagemapid;
UPDATE t_stage_map_elite_item t SET t.stageMapId = (SELECT identifier FROM t_stage_map_elite WHERE id = t.stageMapId) WHERE stageMapId IS NOT NULL;
ALTER TABLE t_stage_map_elite_item CHANGE COLUMN stageMapId stageMapId INT NOT NULL COMMENT '关卡地图ID';

/*英雄试炼关卡*/
ALTER TABLE t_trials_stage DROP FOREIGN KEY fk_trialsstage_type;
UPDATE t_trials_stage t SET t.type = (SELECT identifier FROM t_trials_stage_type WHERE id = t.type) WHERE type IS NOT NULL;
ALTER TABLE t_trials_stage CHANGE COLUMN TYPE type INT NOT NULL COMMENT '所属类别';

/*英雄试炼关卡掉落配置表*/
ALTER TABLE t_trials_stage_item DROP FOREIGN KEY fk_trialsstageitem_stage;
UPDATE t_trials_stage_item t SET t.stageId = (SELECT identifier FROM t_trials_stage WHERE id = t.stageId) WHERE stageId IS NOT NULL;
ALTER TABLE t_trials_stage_item CHANGE COLUMN stageId stageId INT NOT NULL COMMENT '关卡ID';
ALTER TABLE t_trials_stage_item DROP FOREIGN KEY fk_trialsstageitem_item;
UPDATE t_trials_stage_item t SET t.itemId = (SELECT identifier FROM t_item WHERE id = t.itemId) WHERE itemId IS NOT NULL;
ALTER TABLE t_trials_stage_item CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品';

/*关卡*/
ALTER TABLE t_stage CHANGE COLUMN identifier identifier INT NOT NULL COMMENT '编号';

/*关卡地图*/
ALTER TABLE t_stage_map CHANGE COLUMN identifier identifier INT NOT NULL COMMENT '编号';

/*关卡精英地图*/
ALTER TABLE t_stage_map_elite CHANGE COLUMN identifier identifier INT NOT NULL COMMENT '编号';

/******************************************************************/
/*****************************玩家数据*****************************/
/******************************************************************/

/*玩家*/
ALTER TABLE t_player CHANGE COLUMN portrait portrait INT NOT NULL COMMENT '头像', CHANGE COLUMN portraitBackground portraitBackground INT COMMENT '头像背景框';

/*玩家信息*/
ALTER TABLE t_player_info DROP COLUMN sweepTicket;

/*物品背包*/
ALTER TABLE t_item_bag CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';

/*玩家已获得头像框*/
ALTER TABLE t_player_portrait_background CHANGE COLUMN identifier identifier INT NOT NULL COMMENT '头像框ID', CHANGE COLUMN unlockSourceIdentifier unlockSourceIdentifier INT NOT NULL COMMENT '解锁来源编号';

/*玩家已获得头像*/
ALTER TABLE t_player_portrait CHANGE COLUMN identifier identifier INT NOT NULL COMMENT '头像ID', CHANGE COLUMN unlockSourceIdentifier unlockSourceIdentifier INT NOT NULL COMMENT '解锁来源编号';

/*玩家战队*/
ALTER TABLE t_player_team CHANGE COLUMN hero1 hero1 INT COMMENT '英雄1',
CHANGE COLUMN hero2 hero2 INT COMMENT '英雄2',
CHANGE COLUMN hero3 hero3 INT COMMENT '英雄3',
CHANGE COLUMN hero4 hero4 INT COMMENT '英雄4',
CHANGE COLUMN hero5 hero5 INT COMMENT '英雄5';

/*玩家英雄*/
ALTER TABLE t_player_hero CHANGE COLUMN heroId heroId INT NOT NULL COMMENT '英雄',
CHANGE COLUMN equip1 equip1 INT COMMENT '装备1',
CHANGE COLUMN equip2 equip2 INT COMMENT '装备2',
CHANGE COLUMN equip3 equip3 INT COMMENT '装备3',
CHANGE COLUMN equip4 equip4 INT COMMENT '装备4',
CHANGE COLUMN equip5 equip5 INT COMMENT '装备5',
CHANGE COLUMN equip6 equip6 INT COMMENT '装备6';



/*玩家闯关最优记录*/
ALTER TABLE t_stage_through_best CHANGE COLUMN stage stage INT NOT NULL COMMENT '关卡',
CHANGE COLUMN stageMap stageMap INT NOT NULL COMMENT '关卡地图';

/*玩家闯关记录*/
ALTER TABLE t_stage_through CHANGE COLUMN stage stage INT COMMENT '关卡',
CHANGE COLUMN stageMap stageMap INT NOT NULL COMMENT '关卡地图',
CHANGE COLUMN hero1 hero1 INT COMMENT '英雄1',
CHANGE COLUMN hero2 hero2 INT COMMENT '英雄2',
CHANGE COLUMN hero3 hero3 INT COMMENT '英雄3',
CHANGE COLUMN hero4 hero4 INT COMMENT '英雄4',
CHANGE COLUMN hero5 hero5 INT COMMENT '英雄5';

/*闯关掉落物品表*/
ALTER TABLE t_stage_through_item CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';


/*玩家精英关闯关最优记录*/
ALTER TABLE t_elitestage_through_best CHANGE COLUMN stage stage INT NOT NULL COMMENT '关卡',
CHANGE COLUMN stageMap stageMap INT NOT NULL COMMENT '关卡地图';

/*玩家精英关闯关记录*/
ALTER TABLE t_elitestage_through CHANGE COLUMN stage stage INT COMMENT '关卡',
CHANGE COLUMN stageMap stageMap INT NOT NULL COMMENT '关卡地图',
CHANGE COLUMN hero1 hero1 INT COMMENT '英雄1',
CHANGE COLUMN hero2 hero2 INT COMMENT '英雄2',
CHANGE COLUMN hero3 hero3 INT COMMENT '英雄3',
CHANGE COLUMN hero4 hero4 INT COMMENT '英雄4',
CHANGE COLUMN hero5 hero5 INT COMMENT '英雄5';

/*精英关闯关掉落物品表*/
ALTER TABLE t_elitestage_through_item CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';

/*精英关挑战次数购买记录*/
ALTER TABLE t_purchase_elitestage_list CHANGE COLUMN eliteMapId eliteMapId INT NOT NULL COMMENT '地图ID';


/*MC玩家当前闯关信息*/
ALTER TABLE t_mc_stage_through_info CHANGE COLUMN stage stage INT NOT NULL COMMENT '关卡编号',
CHANGE COLUMN stageMap stageMap INT NOT NULL COMMENT '关卡地图编号';

/*MC玩家闯关记录*/
ALTER TABLE t_mc_stage_through CHANGE COLUMN stage stage INT COMMENT '关卡',
CHANGE COLUMN stageMap stageMap INT NOT NULL COMMENT '关卡地图',
CHANGE COLUMN hero1 hero1 INT COMMENT '英雄1',
CHANGE COLUMN hero2 hero2 INT COMMENT '英雄2',
CHANGE COLUMN hero3 hero3 INT COMMENT '英雄3',
CHANGE COLUMN hero4 hero4 INT COMMENT '英雄4',
CHANGE COLUMN hero5 hero5 INT COMMENT '英雄5';

/*MC闯关掉落物品表*/
ALTER TABLE t_mc_stage_through_item CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';

/*英雄试炼挑战掉落物品*/
ALTER TABLE t_trials_fight_item CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';

/*英雄试炼玩家挑战记录*/
ALTER TABLE t_trials_fight_list CHANGE COLUMN stageId stageId INT NOT NULL COMMENT '挑战关卡ID',
CHANGE COLUMN stageTypeId stageTypeId INT NOT NULL COMMENT '挑战关卡类别',
CHANGE COLUMN hero1 hero1 INT COMMENT '英雄1',
CHANGE COLUMN hero2 hero2 INT COMMENT '英雄2',
CHANGE COLUMN hero3 hero3 INT COMMENT '英雄3',
CHANGE COLUMN hero4 hero4 INT COMMENT '英雄4',
CHANGE COLUMN hero5 hero5 INT COMMENT '英雄5';

/*英雄试炼挑战队伍*/
ALTER TABLE t_trials_team CHANGE COLUMN stageTypeId stageTypeId INT NOT NULL COMMENT '试炼类别',
CHANGE COLUMN hero1 hero1 INT COMMENT '英雄1',
CHANGE COLUMN hero2 hero2 INT COMMENT '英雄2',
CHANGE COLUMN hero3 hero3 INT COMMENT '英雄3',
CHANGE COLUMN hero4 hero4 INT COMMENT '英雄4',
CHANGE COLUMN hero5 hero5 INT COMMENT '英雄5';

/*矿山出战战队*/
ALTER TABLE t_mine_team CHANGE COLUMN hero1 hero1 INT COMMENT '英雄1',
CHANGE COLUMN hero2 hero2 INT COMMENT '英雄2',
CHANGE COLUMN hero3 hero3 INT COMMENT '英雄3',
CHANGE COLUMN hero4 hero4 INT COMMENT '英雄4',
CHANGE COLUMN hero5 hero5 INT COMMENT '英雄5';

/*矿山守卫队伍*/
ALTER TABLE t_mine_defend_team CHANGE COLUMN heroId heroId INT NOT NULL COMMENT '英雄',
CHANGE COLUMN equip1 equip1 INT COMMENT '装备1',
CHANGE COLUMN equip2 equip2 INT COMMENT '装备2',
CHANGE COLUMN equip3 equip3 INT COMMENT '装备3',
CHANGE COLUMN equip4 equip4 INT COMMENT '装备4',
CHANGE COLUMN equip5 equip5 INT COMMENT '装备5',
CHANGE COLUMN equip6 equip6 INT COMMENT '装备6';


/*矿山已出战英雄状态*/
ALTER TABLE t_mine_hero CHANGE COLUMN heroId heroId INT NOT NULL COMMENT '英雄ID';


/*矿山出征对手英雄表*/
ALTER TABLE t_mine_battle_rival_hero CHANGE COLUMN heroId heroId INT NOT NULL COMMENT '英雄',
CHANGE COLUMN equip1 equip1 INT COMMENT '装备1',
CHANGE COLUMN equip2 equip2 INT COMMENT '装备2',
CHANGE COLUMN equip3 equip3 INT COMMENT '装备3',
CHANGE COLUMN equip4 equip4 INT COMMENT '装备4',
CHANGE COLUMN equip5 equip5 INT COMMENT '装备5',
CHANGE COLUMN equip6 equip6 INT COMMENT '装备6';


/*矿山战斗胜利奖励物品列表*/
ALTER TABLE t_mine_battle_reward_item CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';

/*矿山战斗胜利奖励英雄列表*/
ALTER TABLE t_mine_battle_reward_hero CHANGE COLUMN heroId heroId INT NOT NULL COMMENT '英雄ID';

/*矿山战斗胜利奖励物品列表记录*/
ALTER TABLE t_mine_battle_reward_item_list CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';

/*矿山战斗胜利奖励英雄列表记录*/
ALTER TABLE t_mine_battle_reward_hero_list CHANGE COLUMN heroId heroId INT NOT NULL COMMENT '英雄ID';


/*矿山商店物品列表*/
ALTER TABLE t_mine_shop_item CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';

/*矿山商店购买记录*/
ALTER TABLE t_mine_shop_purchase_list CHANGE COLUMN itemId itemId INT NOT NULL COMMENT '物品ID';


