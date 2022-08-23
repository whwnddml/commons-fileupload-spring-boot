<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<style>
	*{margin:0;padding:0}
	.progressContainer{position:relative;width: 450px;padding:20px 10px;border: 1px solid #eee;margin-top: 15px;background:#000;height:20px;}
	.progress{position:absolute;width: calc(100% - 20px);height: 20px;}
	.progressTotal{background: #5D5D5D;border-radius: 10px;}
	.progressNow{width: calc(0% - 20px);background: #FFF;border-radius: 10px;}
	.progressPer{background: transparent; text-align:center;color:#A6A6A6;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<body>
<h1>
	File Upload Test
</h1>

<P>  The time on the server is ${serverTime}. </P>
	<div style="width: 100%;padding: 25px;">
		<form id="fileForm" action="/api/file/upload4" method="post" enctype="multipart/form-data">
			<input type="file" name="uploadFile" multiple>
			<button type="button" id="btn">전송</button>
		</form>
		<div class="progressContainer">
			<div class="progress progressTotal"></div>
			<div class="progress progressNow"></div>
			<div class="progress progressPer">0 %</div>
		</div>
	</div>
</body>
<script>
	$(function(){	
		$("#btn").on("click", function(){
			console.log("click Time : " + new Date);
			
			var form = $("#fileForm")[0];
			var formData = new FormData(form);
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/api/file/upload4",
				data: formData,
				processData: false,
				contentType: false,
				cache: false,
				xhr: function(){
					var xhr = $.ajaxSettings.xhr();
					xhr.upload.onprogress = function(e){
						var per = e.loaded * 100 / e.total;
						progressBar(per);
					};
					return xhr;
				},
				success: function (data) {
					console.log("SUCCESS : ", data);
				},
				error: function (e) {
					console.log("ERROR : ", e);
				}
			});
		});
	});
	
	function progressBar(per){
		if(per > 55){
			$(".progressPer").css("color", "#000");
		}
		per = per.toFixed(1);
		$(".progressPer").text(per+" %");
		$(".progressNow").css("width", "calc(" + per + "% - 20px)");
	}
</script>
</html>