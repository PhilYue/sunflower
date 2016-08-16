package com.metal.controller;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.metal.service.VideoTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metal.model.SubTask;
import com.metal.model.SubVideoTaskBean;
import com.metal.model.Task;
import com.metal.model.VideoTaskBean;
import com.metal.service.ConsoleService;

@Controller
//@RequestMapping("")
public class ConsoleController {

	private static Logger log = LoggerFactory.getLogger(ConsoleController.class);

	@Autowired
	private ConsoleService consoleService;

	@Autowired
	private VideoTaskService videoTaskService;

	@RequestMapping("/test")
	@ResponseBody
	String test() {
		return "ok";
	}

	@RequestMapping(value = {"/","index.htm"}, method=RequestMethod.GET)
	String _welcome() {
		return "index";
	}

	@RequestMapping("/index")
	String welcome() {
		return "index";
	}

	@RequestMapping("/videotasks")
	String getVideoTasks(Map<String, Object> model) {
		long start = System.currentTimeMillis();
		List<VideoTaskBean> videoTasks = consoleService.getVideoTasks();
		long end = System.currentTimeMillis();
		log.info("=======query task spend time :["+(end-start)+"]========");
		model.put("videotasks", videoTasks);
		return "videoTaskNew";
	}

	@RequestMapping("/subvideotasks/{vid}")
	String getSubVideoTasks(@PathVariable("vid") long vid,
			Map<String, Object> model) {
		List<SubVideoTaskBean> subVideoTasks = consoleService.getSubVideoTasks(vid);
		model.put("subvideotasks", subVideoTasks);
		return "subvideotasks";
	}

	@RequestMapping("/insertvideotask")
	String insertVideoTask() {
		return "insertvideotask";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/insert_video_task_post")
	@ResponseBody
	void postInsertVideoTask(@RequestParam("url") String url,
			@RequestParam("platform") int platform,
			@RequestParam("title") String title,
			@RequestParam("reset-hour") String resetHour,
			@RequestParam("reset-min") String resetMin, HttpServletResponse response) throws IOException {
		if(StringUtils.isEmpty(url) || StringUtils.isEmpty(title)) {
			response.sendRedirect("/videotasks");
		} else {
			VideoTaskBean videoTask = new VideoTaskBean();
			videoTask.setUrl(url);
			videoTask.setPlatform(platform);
			videoTask.setTitle(title);
			try {
				int hour = Integer.parseInt(resetHour);
				int min = Integer.parseInt(resetMin);
				if(hour < 0 || hour > 23 || min < 0 || min > 59) {
					videoTask.setReset_time(null);
				} else {
					videoTask.setReset_time(Time.valueOf(resetHour + ":" + resetMin + ":00"));
				}
			} catch (NumberFormatException e) {
				videoTask.setReset_time(null);
			}
			consoleService.createVideoTasks(videoTask);
			response.sendRedirect("/videotasks");
		}
	}

	@RequestMapping("/revertvideotask/{vid}")
	@ResponseBody
	void revert(@PathVariable("vid") long vid, HttpServletResponse response) throws IOException {
		consoleService.revertVideoTask(vid);
		response.sendRedirect("/videotasks");
	}

	@RequestMapping("/revertsubvideotask/{subvid}")
	@ResponseBody
	void revertSubVideoTask(@PathVariable("subvid") long subVid, HttpServletResponse response) throws IOException {
		consoleService.revertSubVideoTask(subVid);
		SubVideoTaskBean bean = consoleService.getSubVideoTaskById(subVid);
		response.sendRedirect("/subvideotasks/" + bean.getVid());
	}

	@RequestMapping("/stopvideotask/{vid}")
	@ResponseBody
	void stopVideoTask(@PathVariable("vid") long vid, HttpServletResponse response) throws IOException {
		consoleService.stopVideoTask(vid);
		response.sendRedirect("/videotasks");
	}

	@RequestMapping("/removevideotask/{vid}")
	@ResponseBody
	void removeVideoTask(@PathVariable("vid") long vid, HttpServletResponse response) throws IOException {
		consoleService.removeVideoTask(vid);
		response.sendRedirect("/videotasks");
	}
	
	@RequestMapping("/updatevideotask")
	String updateVideoTask(@RequestParam("vid") long vid, Map<String, Object> model) {
		VideoTaskBean bean = consoleService.getVideoTaskById(vid);
		model.put("videotask", bean);
		return "updatevideotask";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_video_task_post")
	@ResponseBody
	void postUpdateVideoTask(@RequestParam("vid") long vid,
			@RequestParam("title") String title, 
			@RequestParam("reset-hour") String resetHour, 
			@RequestParam("reset-min") String resetMin, HttpServletResponse response) throws IOException {
		VideoTaskBean video = new VideoTaskBean();
		video.setVid(vid);
		video.setTitle(title);
		try {
			int hour = Integer.parseInt(resetHour);
			int min = Integer.parseInt(resetMin);
			if(hour < 0 || hour > 23 || min < 0 || min > 59) {
				video.setReset_time(null);
			} else {
				video.setReset_time(Time.valueOf(resetHour + ":" + resetMin + ":00"));
			}
		} catch (NumberFormatException e) {
			video.setReset_time(null);
		}
		consoleService.updateVideoTask(video);
		response.sendRedirect("/videotasks");
	}
	
	//===========
	@RequestMapping("/tasks")
	String getTasks(Map<String, Object> model) {
		List<Task> tasks = consoleService.getTasks();
		model.put("tasks", tasks);
		return "tasks";
	}
	
	@RequestMapping("/subtasks/{task_id}")
	String getSubTasks(@PathVariable("task_id") long task_id,
			Map<String, Object> model) {
		List<SubTask> subTasks = consoleService.getSubTasks(task_id);
		model.put("subtasks", subTasks);
		return "subtasks";
	}
	
	@RequestMapping("/inserttask")
	String insertTask() {
		return "inserttask";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/insert_task_post")
	@ResponseBody
	void postInsertTask(@RequestParam("keyword") String keyword, HttpServletResponse response) throws IOException {
		if(StringUtils.isEmpty(keyword)) {
			response.sendRedirect("/tasks");
		} else {
			Task task = new Task();
			task.setKey_word(keyword);
			consoleService.createTask(task);
			response.sendRedirect("/tasks");
		}
	}
	
	@RequestMapping("/reverttask/{task_id}")
	@ResponseBody
	void revertTask(@PathVariable("task_id") long task_id, HttpServletResponse response) throws IOException {
		consoleService.revertTask(task_id);
		response.sendRedirect("/tasks");
	}
	
	@RequestMapping("/revertsubtask/{sub_task_id}")
	@ResponseBody
	void revertSubTask(@PathVariable("sub_task_id") long sub_task_id, HttpServletResponse response) throws IOException {
		consoleService.revertSubTask(sub_task_id);
		SubTask subTask = consoleService.getSubTaskById(sub_task_id);
		response.sendRedirect("/subtasks/" + subTask.getTask_id());
	}
	
	@RequestMapping("/stoptask/{task_id}")
	@ResponseBody
	void stopTask(@PathVariable("task_id") long task_id, HttpServletResponse response) throws IOException {
		consoleService.stopTask(task_id);
		response.sendRedirect("/tasks");
	}	
	
	@RequestMapping("/reset_hour")
	@ResponseBody
	void modifyResetHour(@RequestParam("sid") long subId, @RequestParam("hour") int hour, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("sub task id: " + subId);
		log.info("hour: " + hour);
		consoleService.resetHour(subId, hour);
	}
	
	@RequestMapping("/removetask/{task_id}")
	@ResponseBody
	void removeTask(@PathVariable("task_id") long task_id, HttpServletResponse response) throws IOException {
		consoleService.removeTask(task_id);
		response.sendRedirect("/tasks");
	}

	/***
	 * @Title 分析弹幕
	 * @return
     */
	@RequestMapping("/analyeBarrage.htm")
	public String analyeBarrage(){
//		VideoTaskBean videoTaskBean = videoTaskService.selectVideoTask();
		return "analyeBarrage";
	}

	@RequestMapping(value = {"/queryBarrageByGroup.do"}, method=RequestMethod.GET)
	@ResponseBody
	public String queryBarrageByGroup(@RequestParam("subVid")Integer subVid,@RequestParam("tag")Integer tag,@RequestParam("startShowTime")Integer startShowTime,@RequestParam("endShowTime")Integer endShowTime,@RequestParam("keyword")String params){
		subVid = (null == subVid?23:subVid);
		List<Map<String,Object>> lsmap = videoTaskService.queryBarrageByGroup(subVid,tag,startShowTime,endShowTime,params);
		String str = JSONObject.toJSONString(lsmap);
		return str;
	}

	/**
	 * @Title 跳转到主任务页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/mainTask.html")
	public String mainTask(Map<String, Object> model) {
		return "mainTask";
	}

	/***
	 * @Title 主任务表格加载
	 * @param model
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryMainTasks.json")
	@ResponseBody
	public Map<String,Object> queryMainTasks(Map<String, Object> model,@RequestParam("rows")int rows, @RequestParam("page")int page) {
		long start = System.currentTimeMillis();
		//查询分页信息
		Map<String,Object> list = videoTaskService.queryVideoTasksPage(rows,page);
		long end = System.currentTimeMillis();
		log.info("=======query task spend time :["+(end-start)+"]========");
		return list;
	}

	/**
	 * @Title 查询评论和弹幕数，根据vid
	 * @param taskId
	 * @return
     */
	@RequestMapping("/queryMainTasksCBCount.json")
	@ResponseBody
	public Map<String,Object> queryMainTasks(@RequestParam("taskId")int taskId) {
		long start = System.currentTimeMillis();
		//查询分页信息
		Map<String,Object> map = videoTaskService.queryMainTasksCBCount(taskId);
		long end = System.currentTimeMillis();
		log.info("=======query task spend time :["+(end-start)+"]========");
		return map;
	}

	/***
	 * @Title 系统首页
	 * @param model
	 * @return
     */
	@RequestMapping("/mainPage.html")
	public  String mainPage(Map<String, Object> model){
		return "mainPage";
	}

	/**
	 * @Title 修改任务配置
	 * @param vid
	 * @param title
	 * @param resetTime
     */
	@RequestMapping(value = "editTaskElement.do" ,method = RequestMethod.POST)
	@ResponseBody
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> postUpdateVideoTask(@RequestParam(name = "vid") String vid,
												  @RequestParam(name = "url") String url,
												  @RequestParam(name = "platform") String platform,
												  @RequestParam(name = "title") String title,
												  @RequestParam(name = "reset_time") String resetTime) {
		Map<String,Object> resultMap = new HashMap<String,Object>(2);

		VideoTaskBean video = new VideoTaskBean();
		video.setVid(Long.parseLong(vid));
		video.setTitle(title);
		video.setReset_time(Time.valueOf(resetTime));
		resultMap.put("code",200);
		resultMap.put("message",videoTaskService.editVideoTask(video));
		return resultMap;
	}

	/***
	 * @Title 添加新任务
	 * @param url
	 * @param platform
	 * @param title
	 * @param resetTime
     */
	@RequestMapping(value = "addVideoTask.do" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> postInsertVideoTask(@RequestParam(name = "url") String url,
							 @RequestParam(name = "platform") String platform,
							 @RequestParam(name = "title") String title,
							 @RequestParam(name = "reset_time") String resetTime) {
		//返回结果集
		Map<String,Object> resultMap = new HashMap<String,Object>(2);

		if(StringUtils.isEmpty(url) || StringUtils.isEmpty(title)) {
			resultMap.put("code",500);//参数为空
			resultMap.put("message","参数为空!");
			return resultMap;
		} else {
			VideoTaskBean videoTask = new VideoTaskBean();
			videoTask.setUrl(url);
			videoTask.setPlatform(Integer.parseInt(platform));
			videoTask.setTitle(title);
			videoTask.setReset_time(Time.valueOf(new StringBuilder(resetTime).append(":00").toString()));
			consoleService.createVideoTasks(videoTask);
			resultMap.put("code",200);//参数为空
			resultMap.put("message","任务成功添加");
		}
		return resultMap;
	}
	/***
	 * @Title 重置任務
	 * @param vid
	 * @return
     */
	@RequestMapping("/revertvideotask_new/{vid}")
	@ResponseBody
	Map<String,Object> revertVideoTask_new(@PathVariable("vid") long vid ) {
		//返回结果集
		Map<String,Object> resultMap = new HashMap<String,Object>(2);
		consoleService.revertVideoTask(vid);
		resultMap.put("code",200);//
		resultMap.put("message","操作成功！");
		return resultMap;
	}
	/***
	 * @Title 刪除任務
	 * @param vid
	 * @return
     */
	@RequestMapping("/removevideotask_new/{vid}")
	@ResponseBody
	Map<String,Object> removeVideoTask_new(@PathVariable("vid") long vid ) {
		//返回结果集
		Map<String,Object> resultMap = new HashMap<String,Object>(2);
		consoleService.removeVideoTask(vid);
		resultMap.put("code",200);//
		resultMap.put("message","操作成功！");
		return resultMap;
	}
	/***
	 * @Title 停止任務
	 * @param vid
	 * @return
     */
	@RequestMapping("/stopvideotask_new/{vid}")
	@ResponseBody
	Map<String,Object> stopVideoTask_new(@PathVariable("vid") long vid ) {
		//返回结果集
		Map<String,Object> resultMap = new HashMap<String,Object>(2);
		consoleService.stopVideoTask(vid);
		resultMap.put("code",200);//
		resultMap.put("message","操作成功！");
		return resultMap;
	}
}