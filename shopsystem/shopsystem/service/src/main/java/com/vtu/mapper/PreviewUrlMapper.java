package com.vtu.mapper;

import com.vtu.model.PreviewUrl;
import com.vtu.model.Works;
import org.apache.ibatis.annotations.Param;

public interface PreviewUrlMapper {
    int deleteByPrimaryKey(Long previewUrlId);

    /**
     * 插入作品预览
     * @param works
     * @return
     */
    int insertPreviewUrl(@Param("works") Works works);

    int insertSelective(PreviewUrl record);

    PreviewUrl selectByPrimaryKey(Long previewUrlId);

    int updateByPrimaryKeySelective(PreviewUrl record);

    int updateByPrimaryKey(PreviewUrl record);
}