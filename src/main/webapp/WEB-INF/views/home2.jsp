<!DOCTYPE html>
<head>
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
function postFile() {
    var formdata = new FormData();

    formdata.append('file', $('#file')[0].files[0]);
    formdata.append('comment', $('#comment').val());

    var request = new XMLHttpRequest();

    request.upload.addEventListener('progress', function (e) {
        var file1Size = $('#file')[0].files[0].size;

        if (e.loaded <= file1Size) {
            var percent = Math.round(e.loaded / file1Size * 100);
            $('#progress-bar-file').width(percent + '%').html(percent + '%');
        } 

        if(e.loaded == e.total){
            $('#progress-bar-file').width(100 + '%').html(100 + '%');
        }
    });   

    request.open('post', '/api/file/upload4');
    request.timeout = 45000;
    request.send(formdata);
}
</script>
</head>
<form id="form1" method="post" enctype="multipart/form-data">
    <input id="file" type="file" />
    <lable>User Name</lable> : 
    <input id="comment" name="comment" type="text" />
    <div class="progress-wrapper" style="width:400px">
        <div id="progress-bar-file" class="progress"></div>
    </div>
    <button type="button" onclick="postFile()">Upload File</button>
</form>
</html>