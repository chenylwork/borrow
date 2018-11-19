package com.work.borrow.mapper;

import com.work.borrow.po.AppVersion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("appVersionMapper")
public interface AppVersionMapper {

    /**
     * 录入版本信息
     * @param version
     * @return
     */
    @Insert("insert into app_version(version,content,`url`,`type`) " +
            "value(#{version},#{content},#{url},#{type});")
    boolean inputVersion(AppVersion version);

    /**
     * 获取全部版本信息
     * @return
     */
    @Select("select * from app_version where type = #{type};")
    List<AppVersion> queryVersion(@Param("type") String type);

    /**
     * 获取最高版本号
     * @param type
     * @return
     */
    @Select("select * from app_version where type = #{type} order by id DESC LIMIT 0,1;")
    AppVersion queryMaxVersion(@Param("type") String type);

}
