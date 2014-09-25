package com.companyname.projectname.webpage.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.companyname.projectname.webpage.home.entry.MenuItem;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午10:33:09
 */
public interface MenuMapper {
	
    @Select("select * from web_menu where parentId=#{parentId} order by priority asc")
    List<MenuItem> selectByParentId(@Param("parentId") String parentId);
    
}
