package com.hp.mapper;

import com.hp.pojo.TitleCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 严敏
 * @since 2023-01-03
 */
public interface TitleCategoryMapper extends BaseMapper<TitleCategory> {

    List<TitleCategory> selectAllTitleCateggory();
}
