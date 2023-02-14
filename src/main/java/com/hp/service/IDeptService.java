package com.hp.service;

import com.hp.dto.DeptDto;
import com.hp.pojo.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-02
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 查询所有的部门
     * @return
     */
    public Map<String,Object> deptList();

    public void saveDept(Dept dept);

    void updateDept(Dept dept);

    void deleteDept(Integer id);

    List<DeptDto> findAllDept();
}
