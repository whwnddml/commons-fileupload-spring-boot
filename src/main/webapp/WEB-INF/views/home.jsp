<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=1280" />
	<title>Home</title>
	
	
</head>
<script language="javascript">

</script>
<body>
여기는 home


<form action="/api/file/upload4" method="post" enctype="multipart/form-data">
          
              <ul>
                  <li>문자 <input type="text" name="sampleStringData"></li>
                  <li>첨부파일<input type="file" name="file"></li>
              </ul>
        
        
          
              <input type="submit"/>
        
        
          
          </form>

    
</body>
</html>
