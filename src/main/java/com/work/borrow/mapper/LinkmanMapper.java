package com.work.borrow.mapper;

import com.work.borrow.po.LinkMan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * @ClassName LinkmanMapper
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2018/12/10- 12:00
 * @Version 1.0
 **/
@Mapper
@Component("linkmanMapper")
public interface LinkmanMapper {
    @Select("select * from linkman where infoId = #{infoId}")
    public List<LinkMan> getLinkman(@Param("infoId") int infoId);
}
