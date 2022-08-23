<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8" />
    <title>Home4</title>

<script src="http://code.jquery.com/jquery.min.js"></script>
<meta charset="UTF-8">
<style>
.progress-wrapper {
    width:100%;
}
.progress-wrapper .progress {
    background-color:green;
    width:0%;
    padding:5px 0px 5px 0px;
}
</style>
<script>
$(function(){
	
	 $('#file').bind('change',function(){
		 var fileSize = $('#file')[0].files[0].size;
		 $('#upload-fileSize').text(bytesToMegaBytes(fileSize,2)+"MB");
	 });
});
function bytesToMegaBytes(bytes, roundTo) {
	  var converted = bytes / (1024*1024);
	  return roundTo ? converted.toFixed(roundTo) : converted;
}
	
function postFile() {
    var formdata = new FormData();

    formdata.append('file', $('#file')[0].files[0]);
    int maxInt = 4000;//MB
    var maxSize = maxInt * 1024 * 1024; //MB
    var fileSize = $('#file')[0].files[0].size;
    
    if(fileSize > maxSize){
		alert("Attachment file size can be registered within "+maxInt+"MB");
		$(this).val('');
		return false;
	}
    var xhr = new XMLHttpRequest();
    xhr.upload.addEventListener('progress', function (e) {
    	
        var file1Size = $('#file')[0].files[0].size;
        
        if (e.loaded <= file1Size) {
            var percent = Math.round(e.loaded / file1Size * 100);
            $('#progress-bar-file').width(percent + '%').html(percent + '%');
        } 

        if(e.loaded == e.total){
            $('#progress-bar-file').width(100 + '%').html(100 + '%');
        }
    });   
   
    xhr.open('post', '/api/file/upload4');
    xhr.timeout = 45000;
    xhr.send(formdata);
    
    xhr.onload = function(){
    	if (xhr.status != 200) { // analyze HTTP status of the response
    	    alert('Error ${request.status}: ${request.statusText}'); // e.g. 404: Not Found
    	  } else { // show the result
    	    //alert('Done, got ${request.response.length} bytes'); // response is the server response
    	    //console.log(request.response);
    	    var obj = JSON.parse(xhr.response);
    	    console.log(obj);
    	    $('#upload-rslt-fileSize').text(bytesToMegaBytes(obj.fileSize,2)+"MB");
    	    $('#upload-rslt-contentType').text(obj.contentType);
    	    $('#upload-rslt-orgFileName').text(obj.orgFileName);
    	    $('#upload-rslt-repFileName').text(obj.repFileName);
    	    $('#upload-rslt-filePath').text(obj.filePath);
    	    
    	    //alert('Done, got ${request.response} '); // response is the server response
    	  }
    }
    
}
</script>
    
  </head>
  <body>
    <h1>File Upload Example</h1>
    <form id="form1" method="post" enctype="multipart/form-data" accept=".csv,*">
      <input type="file" name="file" id="file" />
      <div id="upload-fileSize"></div>
      <p>
      <div class="progress-wrapper" style="width:400px">
        <div id="progress-bar-file" class="progress"></div>
      </div>
      <button type="button" onclick="postFile()">Upload File</button>
      
      <div id="upload-rslt-fileSize"></div>
      <div id="upload-rslt-contentType"></div>
      <div id="upload-rslt-orgFileName"></div>
      <div id="upload-rslt-repFileName"></div>
      <div id="upload-rslt-filePath"></div>
      
    </form>
  </body>
</html>