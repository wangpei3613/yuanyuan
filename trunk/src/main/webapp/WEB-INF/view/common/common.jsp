<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- include config -->
<%@include file="../config/globalConfig.jsp" %>
<%@include file="../config/extjsConfig.jsp" %>

<style>
    .progress-box{
        text-align: center;
        width: 320px;
        border: 1px solid #999;
        padding: 1px;
        height: 8px;
        position: absolute;
        left:50%;
        top:50%;
        margin-left: -161px;
        margin-top:-6px;
    }
    .progress-box .progress-bar{
        width: 0;
        height: 100%;
        background-color: #248cd7;
        background-repeat: repeat-x;
        background-position: 0 0;
        background-size: 16px 8px;
        background-image: linear-gradient(315deg,transparent,transparent 33%,rgba(0,0,0,.12) 33%,rgba(0,0,0,.12) 66%,transparent 66%,transparent);
    }
    .progress-box .progress-text{
        position: absolute;
        top:15px;
        left: 50%;
        width:150px;
        margin-left:-75px;
        line-height: 16px;
        font-size: 12px;
        text-align: center;
        color:#666;
    }
</style>
<div id="progress-box" class="progress-box">
    <div id="progress-bar" class="progress-bar"></div>
    <div id="progress-text" class="progress-text">loading 0%</div>
</div>

<!-- load b bootstrap.js -->
<script type="text/javascript">
	var Ext = Ext || {};
	var isProduction = false;
    var loadCountTotal = 0;
    var curLoadCount = 0;
    var progressBox = document.getElementById('progress-box');
    var progressBar = document.getElementById('progress-bar');
    var progressText = document.getElementById('progress-text');
    var progressHasShowed = false;

	if (isProduction){
		Ext.manifest = "${path}/extjs/build/production/App/app.json";
    } else {
    	Ext.manifest = "${path}/extjs/bootstrap.json";
        progressBox.style.display = 'none';
    }

	Ext.beforeLoad = function (tags) {
		if (isProduction){
			Ext.Boot.baseUrl = '${path}/extjs/build/production/App/';
    	} else {
			Ext.Boot.baseUrl = '${path}/extjs/';
    	}

		Ext.Boot.Request.prototype.prependBaseUrl= true;

        Ext.Boot.Entry.prototype.notifyRequests = function() {
            var requests = this.requests,
                    len = requests.length,
                    i, request;
            for (i = 0; i < len; i++) {
                request = requests[i];
                request.processLoadedEntries();
            }
            loadProgress();
            if (this.done) {
                this.fireListeners();
            }
        };

        return function (manifest) {
            loadCountTotal = manifest.css.length + manifest.js.length;
            var code = '<%=BasicsFinal.getParamVal("extjs.version.code") %>';
            if(code == 'null'){
            		code = null;
            }
            manifest.content.loader = {"cache":code || +new Date()};
        };
	};

    function loadProgress() {

        if (progressHasShowed || !loadCountTotal) return;

        curLoadCount++;

        if (curLoadCount >= loadCountTotal){
            progressBox.style.display = 'none';
            progressHasShowed = true;
            return;
        }

        if (curLoadCount <= loadCountTotal) {
            var curPercent = parseInt((curLoadCount / loadCountTotal) * 100);

            setTimeout(function () {
                progressBar.style.width = curPercent + '%';

                progressText.innerHTML = 'loading '+ curPercent + '%';
            }, 0);
        } else {
            progressBox.style.display = 'none';
        }
    }
</script>

<c:choose>
      <c:when test="${isProduction}">
      	<jsp:include page="../../../extjs/build/production/App/bootstrap.jsp" />
      </c:when>
      <c:otherwise>
          <script id="microloader" data-app="93104f22-39dc-465a-817f-41da388320da" type="text/javascript" src="${path}/extjs/bootstrap.js"></script>
      </c:otherwise>
</c:choose>
