package com.hp.mapper;

import com.hp.dto.DeptDto;
import com.hp.pojo.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 严敏
 * @since 2023-01-02
 */
public interface DeptMapper extends BaseMapper<Dept> {
    List<Dept> selectAllDepts();

    List<DeptDto> findDeptDtoByParentDeptId(@Param("parrentId") Integer parentId);
}
