package com.santrong.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.santrong.demo.entry.DemoForm;
import com.santrong.demo.entry.DemoView;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午10:33:09
 */
public interface DemoMapper {
	
    @Select("select * from demo where id=#{id}")
    DemoView getDemoById(@Param("id") String s);
    
    @Insert("insert into demo values(#{id}, #{field1})")
    int insert(DemoForm form);
    
}
