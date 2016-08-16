package com.metal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.metal.model.VideoTaskBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.metal.dao.ConsoleDao;
import com.metal.dao.VideoTaskDao;
import com.metal.model.SubVideoTaskBean;
import com.metal.service.VideoTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by phil on 2016/7/13.
 */
@Component
public class VideoTaskServiceImpl implements VideoTaskService{

    private static Logger logger = LoggerFactory.getLogger(VideoTaskServiceImpl.class);

    @Autowired
    private VideoTaskDao videoTaskDao;

    @Autowired
    private ConsoleDao consoleDao;



    @Override
    public List<Map<String, Object>> queryBarrageByGroup(Integer subVid,Integer tag, Integer startShowTime, Integer endShowTime,Object... params) {
        logger.info("========begin queryBarrageByGroup process=======");
        logger.info("=======param:[subVid:"+subVid+";startShowTime:"+startShowTime+";endShowTime:"+endShowTime+";keywords:"+params+"]======");
        if(null == subVid){
            logger.warn("======== subVid 参数为空========");
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        //查询subVideo实体，得到tv剧集播放时长，
//        SubVideoTaskBean subVideoTaskBean = consoleDao.getSubVideoTaskById(subVid);
        SubVideoTaskBean subVideoTaskBean = videoTaskDao.queryVideoTaskBySubVid(subVid);
        //默认间隔为五分钟
        Integer interval = 60*3;
        //如果前端指定区间，则返回前端指定的区间
        if(null != tag && 0 != tag){
            interval = tag;
        }
        Integer playTime = 2820;//subVideoTaskBean.getPlayTime();
        if(null != playTime && 0 != playTime){
            int i=0;
            if(null != tag){
                interval = tag;
            }
            for (i=1;i<=playTime;i=interval+i){
                if( interval > playTime - i ){
                    map = new HashMap<String,Object>(5);
                    map = videoTaskDao.queryBarrageByGroup(subVid,i,playTime,params);
                    map.put("startTime",i);
                    map.put("endTime",playTime);
                    mapList.add(map);
                    map = null;
                }else{
                    map = new HashMap<String,Object>(5);
                    map = videoTaskDao.queryBarrageByGroup(subVid,i,(interval+i),params);
                    map.put("startTime",i);
                    map.put("endTime",interval+i);
                    mapList.add(map);
                    map = null;
                }
            }
        }else{
            logger.warn("=======playTime is null=======");
        }
        logger.info("========end queryBarrageByGroup process=======");
        return mapList;
    }

    /**
     * @param rows
     * @param page
     * @return
     * @Title 分页查询主任务
     */
    @Override
    public Map<String, Object> queryVideoTasksPage(int rows, int page) {
        //返回结果集
        Map<String, Object> resultMap = new HashMap<String,Object>(2);
        //查询
        List<VideoTaskBean> subVideoTaskBeen = videoTaskDao.queryVideoTasks(rows,page);
        Integer count = videoTaskDao.queryVideoTasksCount(rows,page);
        //返回结果
        resultMap.put("data", JSONObject.toJSON(subVideoTaskBeen));
        resultMap.put("total",count);
        return resultMap;
    }

    /***
     * @param video
     * @return
     * @Title 修改任务
     */
    @Override
    public boolean editVideoTask(VideoTaskBean video) {
        int i = 0;
        try{
           i = videoTaskDao.updateVideoTask(video);
        }catch (Exception se){
            se.printStackTrace();
        }
        return (i >= 1);
    }

    /***
     * @param taskId
     * @return
     * @Title 查询弹幕和评论数根据taskId
     */
    @Override
    public Map<String, Object> queryMainTasksCBCount(int taskId) {
        return videoTaskDao.queryMainTasksCBCount(taskId);
    }
}
