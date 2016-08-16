<%@ page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <title>主任务</title>

    <link href="/static/css/plugins/clockpicker/clockpicker.css" rel="stylesheet">
    <link href="/static/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">

</head>
<body>
<%@ include file="common/commonHead.jsp" %>
<div id="modalBody" class="panel-body" style="padding-bottom: 0px;margin-top: 50px;">
    <div class="panel panel-default">
        <div class="panel-body">
            <div id="taskGrid" class="dg"></div>
        </div>
    </div>
</div>
<!-- 添加任务 -->
<div class="modal inmodal" id="addTaskModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated flipInY">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">添加新任务</h4>
                <small class="font-bold"></small>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">URL</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="url">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">平台</label>
                    <div class="col-sm-10">
                        <select class="form-control m-b" name="platform">
                            <option value="2">爱奇艺</option>
                            <option value="1">优酷</option>
                            <option value="0">腾讯</option>
                            <option value="3">乐视</option>
                            <option value="4">搜狐</option>
                            <option value="5">B站</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="title">
                    </div>
                </div>
                <div class="form-group ">
                    <label class="col-sm-2 control-label">定时重抓</label>
                    <div class="col-sm-10 clockpicker" data-autoclose="true">
                        <input type="text" class="form-control" value="" name="reset_time">
                            <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                            </span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary btn-add-task">添加</button>
            </div>
        </div>
    </div>
</div>
<!-- 修改任务 -->
<div class="modal fade" id="editTaskModal" tabindex="-1" role="dialog" aria-labelledby="editTaskModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="editTaskModalLabel">修改任务参数</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">URL</label>
                    <div class="col-sm-10">
                        <input type="hidden" name="vid">
                        <input type="text" class="form-control" name="url">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">平台</label>
                    <div class="col-sm-10">
                        <select class="form-control m-b" name="platform">
                            <option value="2">爱奇艺</option>
                            <option value="1">优酷</option>
                            <option value="0">腾讯</option>
                            <option value="3">乐视</option>
                            <option value="4">搜狐</option>
                            <option value="5">B站</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="title">
                    </div>
                </div>
                <div class="form-group ">
                    <label class="col-sm-2 control-label">定时重抓</label>
                    <div class="col-sm-10 clockpicker" data-autoclose="true">
                        <input type="text" class="form-control" name="reset_time" value="">
                            <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                            </span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-edit-task">OK!</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</body>
<!-- Clock picker -->
<script type="text/javascript" src="/static/js/plugins/clockpicker/clockpicker.js"></script>
<!-- GITTER -->
<script src="/static/js/plugins/gritter/jquery.gritter.min.js"></script>
<!-- Selef -->
<script type="text/javascript" src="/static/pageJs/mainTaskList.js"></script>
</html>