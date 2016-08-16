package com.metal.service;


import com.metal.model.VideoTaskBean;

import java.util.List;
import java.util.Map;

/**
 * Created by phil on 2016/7/13.
 */
public interface VideoTaskService {

    /***
     * @Titile
     * @param subVid
     * @param tag 间隔时间（秒s）
     * @param startShowTime
     * @param endShowTime
     * @return
     */
    List<Map<String,Object>> queryBarrageByGroup(Integer subVid,Integer tag,Integer startShowTime,Integer endShowTime,Object... params);

    /**
     * @Title 分页查询主任务
     * @param rows
     * @param page
     * @return
     */
    Map<String,Object> queryVideoTasksPage(int rows, int page);

    /***
     * @Title 修改任务
     * @param video
     * @return
     */
    boolean editVideoTask(VideoTaskBean video);

    /***
     * @Title 查询弹幕和评论数根据taskId
     * @param taskId
     * @return
     */
    Map<String,Object> queryMainTasksCBCount(int taskId);
}
