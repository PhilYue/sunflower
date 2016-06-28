<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 引入公共bootstrap css -->
<link rel="stylesheet" href="/static/bootstrap3.3.5/css/bootstrap.min.css"/>
<link rel="stylesheet" href="/static/bootstrap3.3.5/css/bootstrap-theme.min.css"/>
<link rel="stylesheet" href="/static/bootstrap3.3.5/css/bootstrap-datetimepicker.min.css"/>

<!-- 引入公共bootstrap js -->
<script type="text/javascript" src="/static/bootstrap3.3.5/js/jquery-1.12.0.js"></script>
<script type="text/javascript" src="/static/bootstrap3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/bootstrap3.3.5/js/bootstrap-datetimepicker.min.js"></script>

<!-- 头部 -->
<script type="text/javascript" src="/static/bootstrap3.3.5/js/jquery.bootstrap-autohidingnavbar.js"></script>

<!-- 自定义 -->
<script type="text/javascript" src="/static/js/daypicker.js" ></script>
<script type="text/javascript" src="/static/bootstrap3.3.5/js/confirm.js"></script>




<style>
.loader {
  position: relative;
  width: 2.5em;
  height: 2.5em;
  /* transform: rotate(165deg); */
}
.loader:before, .loader:after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  display: block;
  width: 0.5em;
  height: 0.5em;
  border-radius: 0.25em;
  transform: translate(-50%, -50%);
}
.loader:before {
  animation: before 2s infinite;
}
.loader:after {
  animation: after 2s infinite;
}

@keyframes before {
  0% {
    width: 0.5em;
    box-shadow: 1em -0.5em rgba(225, 20, 98, 0.75), -1em 0.5em rgba(111, 202, 220, 0.75);
  }
  35% {
    width: 2.5em;
    box-shadow: 0 -0.5em rgba(225, 20, 98, 0.75), 0 0.5em rgba(111, 202, 220, 0.75);
  }
  70% {
    width: 0.5em;
    box-shadow: -1em -0.5em rgba(225, 20, 98, 0.75), 1em 0.5em rgba(111, 202, 220, 0.75);
  }
  100% {
    box-shadow: 1em -0.5em rgba(225, 20, 98, 0.75), -1em 0.5em rgba(111, 202, 220, 0.75);
  }
}
@keyframes after {
  0% {
    height: 0.5em;
    box-shadow: 0.5em 1em rgba(61, 184, 143, 0.75), -0.5em -1em rgba(233, 169, 32, 0.75);
  }
  35% {
    height: 2.5em;
    box-shadow: 0.5em 0 rgba(61, 184, 143, 0.75), -0.5em 0 rgba(233, 169, 32, 0.75);
  }
  70% {
    height: 0.5em;
    box-shadow: 0.5em -1em rgba(61, 184, 143, 0.75), -0.5em 1em rgba(233, 169, 32, 0.75);
  }
  100% {
    box-shadow: 0.5em 1em rgba(61, 184, 143, 0.75), -0.5em -1em rgba(233, 169, 32, 0.75);
  }
}
/**
 * Attempt to center the whole thing!
 */

.loader {
  position: absolute;
  top: calc(50% - 1.25em);
  left: calc(50% - 1.25em);
}

#process_ing {
    z-index: 1000000;
    width: 100%;
    height: 100%;
    top: 0px;
    left: 0px;
    background-color: rgba(255, 255, 255, 0.61);
}
</style>
<div id="process_ing" class="loader" style="display: none;"></div>
<script>
function pageover(flag){
	if(flag){
		$("#process_ing").css({"display":"inline","height":(document.body.offsetHeight)});
		//$('body').css("overflow","hidden");
	}else{
		$("#process_ing").css({"display":"none"});
	}
}
</script>