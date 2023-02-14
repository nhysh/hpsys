package com.hp.service;

import com.hp.pojo.TitleCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-03
 */
public interface ITitleCategoryService extends IService<TitleCategory> {
    /**
     * 查询所有的职位分类
     * @return
     */
    public Map<String,Object> titleCategoryList();

    void saveTitleCategory(TitleCategory titleCategory);

    void updateTitleCategory(TitleCategory titleCategory);

    void deleteDept(Integer id);
}
