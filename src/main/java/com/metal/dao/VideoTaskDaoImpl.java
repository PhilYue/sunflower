package com.metal.dao;

import com.metal.common.ConnectionManager;
import com.metal.model.SubVideoTaskBean;
import com.metal.model.VideoTaskBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by phil on 2016/7/13.
 */
@Component
public class VideoTaskDaoImpl implements VideoTaskDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VideoTaskDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @title 查询视频子任务实体
     *
     * @param subVid
     */
    @Override
    public SubVideoTaskBean queryVideoTaskBySubVid(Integer subVid) {

        final String sql = "SELECT * FROM sub_video_task  WHERE sub_vid = %s";
        Connection conn = null;
        SubVideoTaskBean bean = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);
            QueryRunner qr = new QueryRunner();
            bean = qr.query(conn, String.format(sql,subVid), new BeanHandler<SubVideoTaskBean>(SubVideoTaskBean.class));
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(conn);
        }
        return bean;
    }

    /***
     * @Title 查询弹幕数量，根据关键字
     * @param subVid
     * @param startShowTime
     * @param endShowTime
     * @param params 关键字集合
     * @return
     */
    @Override
    public Map<String, Object> queryBarrageByGroup(Integer subVid,Integer startShowTime,Integer endShowTime,Object... params) {

        /** 分段查询弹幕数 */
//        final String sql = "SELECT COUNT(t.id) AS bCount FROM tv_barrage t WHERE t.v_sub_task_id = %s AND t.barrage_show_time >= %s AND t.barrage_show_time <= %s";
//        Map<String, Object> map = jdbcTemplate.queryForMap(String.format(sql,subVid,startShowTime,endShowTime));

        /** 分段查询弹幕包含关键字条数 */
        final String sql = "SELECT COUNT(t.id) AS bCount FROM tv_barrage t WHERE t.v_sub_task_id = %s AND t.barrage_show_time >= %s AND t.barrage_show_time <= %s";

        StringBuffer sb = new StringBuffer(String.format(sql,subVid,startShowTime,endShowTime));
        int flag = 0,plength = 0;
        if(null != params && params.length>0){
            Object param = params[0];
            plength = param.toString().split(",").length;
            sb.append(" AND (");
            for (Object str : param.toString().split(",")){
                flag++;
                sb.append("t.barrage_content LIKE '%").append(str).append("%'");
                if(flag < plength){
                    sb.append(" OR ");
                }
            }
            sb.append(" ) ");
        }
        Map<String, Object> map = jdbcTemplate.queryForMap(sb.toString());

        return map;
    }

    /**
     * 分页查询主任务信息
     *
     * @param rows
     * @param page
     */
    @Override
    public List<VideoTaskBean> queryVideoTasks(int rows, int page) {
        /**
         * 结论：组合sql效率最差（第二种），该应用场景（数据量不大，7条主线任务，每条数量集一万到5万不等）下比其他两种慢200ms ~ 400ms；
         *      第一种spring原生自带 jdbcTemplate 虽然不能控制事务，但是查询条件下，效率和第三种不分上下，相对c3p0 稳定，后者有轻微浮动，在200ms范围内浮动
         * 最后决定：
         *      查询用spring原装 jdbcTemplate
         * */

        /** 第一种方法 */
        long sa = System.currentTimeMillis();

        StringBuffer sb = new StringBuffer("select v.vid,v.url,v.platform,v.title,v.status,v.barrage_status,v.start_time,v.end_time,v.reset_time from video_task v order by v.start_time desc ");
        String cCountSql = "SELECT COUNT(c.comment_id) FROM video_comments c WHERE c.vid = %s";
        String bCountSql = "SELECT COUNT(t.id) FROM tv_barrage t WHERE t.v_task_id = %s";
        int start = 1;
        int pageSize = 15;
        if(rows != 0 && page != 0){
            start = rows*(page-1);
            pageSize = rows;
        }
        sb.append("LIMIT ").append(start).append(",").append(pageSize);
        List<VideoTaskBean> videoTaskBeanList = jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper<VideoTaskBean>(VideoTaskBean.class));

        List<VideoTaskBean> videoTaskBeanList_new = new ArrayList<VideoTaskBean>();
        for (VideoTaskBean v: videoTaskBeanList) {
            //这个地方尝试过 union ,查询效果并不明显，唯一的好处即只打开一次数据库连接
//            v.setCount(jdbcTemplate.queryForObject(String.format(cCountSql,v.getVid()), Integer.class, new Object[]{}));
//            v.setBarrageCount(jdbcTemplate.queryForObject(String.format(bCountSql,v.getVid()), Integer.class, new Object[]{}));
            videoTaskBeanList_new.add(v);
        }
        long ed = System.currentTimeMillis();
        System.out.println(videoTaskBeanList_new.size()+"拆分sql查询效率============="+(ed-sa));
        cCountSql = bCountSql = null;
        sb = null;
        return videoTaskBeanList_new;
        /** 第二种方法 */
        /*long sa1 = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer("SELECT sc.url,sc.vid,sc.platform,sc.title,sc.`STATUS`,sc.barrage_status,sc.start_time ,sc.end_time AS last_update_time,sc.reset_time,sc.count, COUNT(t.id) AS barrageCount FROM (select v.vid,v.url,v.platform,v.title,v.status,v.barrage_status,v.start_time,v.end_time,v.reset_time,count(c.comment_id) count from video_task v left join video_comments c on v.vid=c.vid group by v.vid ) AS sc LEFT JOIN tv_barrage t ON t.v_task_id = sc.vid GROUP BY sc.vid order by sc.start_time desc ");

        int start = 1;
        int pageSize = 15;
        if(rows != 0 && page != 0){
            start = rows*(page-1);
            pageSize = rows;
        }
        sb.append("LIMIT ").append(start).append(",").append(pageSize);
        List<VideoTaskBean> videoTaskBeanLista = jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper<VideoTaskBean>(VideoTaskBean.class));
        long ed1 = System.currentTimeMillis();
        System.out.println("组合sql查询效率============="+(ed1-sa1));*/

        /** 第三种 */
        /*long sa2 = System.currentTimeMillis();
        Connection conn = null;
        List<VideoTaskBean> videoTaskBeanList_newa = new ArrayList<VideoTaskBean>();
        try {
            conn = ConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);
            QueryRunner qr = new QueryRunner();
            List<VideoTaskBean> videoTaskBeanLi = qr.query(conn, sql, new BeanListHandler<VideoTaskBean>(VideoTaskBean.class));
            for (VideoTaskBean v: videoTaskBeanLi) {
                v.setCount(jdbcTemplate.queryForObject(String.format(sql2,v.getVid()), Integer.class, new Object[]{}));
                v.setBarrageCount(jdbcTemplate.queryForObject(String.format(sql3,v.getVid()), Integer.class, new Object[]{}));
                videoTaskBeanList_newa.add(v);
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(conn);
        }
        long ed2 = System.currentTimeMillis();
        System.out.println("第三种组合sql查询效率============="+(ed2-sa2));*/
//        return videoTaskBeanList;

    }

    @Override
    public Integer queryVideoTasksCount(int rows, int page) {
        StringBuffer sb = new StringBuffer("SELECT COUNT(t.vid) FROM video_task t where 1=1 ");

        Integer cCount = jdbcTemplate.queryForObject(sb.toString(), Integer.class, new Object[]{});

        sb = null;
        return cCount;
    }

    /**
     * 修改任务表
     *
     * @param video
     */
    @Override
    public int updateVideoTask(VideoTaskBean video) {
        String sql = "update video_task set title=?,reset_time=? where vid=?";
        int i = jdbcTemplate.update(sql, video.getTitle(), video.getReset_time(), video.getVid());
        sql = null;
        return i;
    }

    /**
     * 查询弹幕和评论数
     *
     * @param taskId
     */
    @Override
    public Map<String, Object> queryMainTasksCBCount(int taskId) {
        Map<String, Object> map = new HashMap<String,Object>(2);
        String cCountSql = "SELECT COUNT(c.comment_id) FROM video_comments c WHERE c.vid = %s";
        String bCountSql = "SELECT COUNT(t.id) FROM tv_barrage t WHERE t.v_task_id = %s";
        map.put("commentCount",jdbcTemplate.queryForObject(String.format(cCountSql,taskId), Integer.class, new Object[]{}));
        map.put("barrageCount",jdbcTemplate.queryForObject(String.format(bCountSql,taskId), Integer.class, new Object[]{}));
        return map;
    }
}
