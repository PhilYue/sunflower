package com.metal.dao;

import com.metal.model.SubVideoTaskBean;
import com.metal.model.VideoTaskBean;

import java.util.List;
import java.util.Map;

/**
 * Created by phil on 2016/7/13.
 */
public interface VideoTaskDao {
    /** 查询视频子任务实体 */
    SubVideoTaskBean queryVideoTaskBySubVid(Integer subVid);
    /** 查询弹幕数量根据区间 */
    public Map<String,Object> queryBarrageByGroup(Integer subVid,Integer startShowTime,Integer endShowTime,Object... params);
    /** 分页查询主任务信息 */
    List<VideoTaskBean> queryVideoTasks(int rows, int page);
    Integer queryVideoTasksCount(int rows, int page);
    /** 修改任务表 */
    int updateVideoTask(VideoTaskBean video);
    /** 查询弹幕和评论数 */
    Map<String,Object> queryMainTasksCBCount(int taskId);
}
