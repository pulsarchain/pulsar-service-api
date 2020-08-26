package com.bosha.user.server.mapper;

import java.util.List;


import com.bosha.user.api.entity.Post;
import org.apache.ibatis.annotations.Param;

public interface PostMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Post record);

    List<Post> listAll();

    int countByName(@Param("name") String name);

    Post getByName(@Param("name") String name);
}