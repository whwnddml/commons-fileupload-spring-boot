package com.fileupload.demo;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileUploadAPIController {
	
	
	
	/**
	 * @param multipartHttpServletRequest
	 * @throws Exception
	 * 
	 * apach commons fileupload를 이용한 대용량 파일 업로드 테스트
	 */
	@PostMapping("/upload4")
	@ResponseBody
	public Map<String, Object> commonUpload(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		
		log.debug("--- in controller method ---");
		
		Map<String, Object> rsltMap = new HashMap<String, Object>();
		Map<String, Object> fileMap = new HashMap<String, Object>();
		rsltMap.put("rslt", "file upload fail!");
		
		log.debug("===>>> comment1 : {}",multipartHttpServletRequest.getParameter("comment"));
		
		
		if (ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
	        Iterator<String> filenameIterator = multipartHttpServletRequest.getFileNames();
	        String name;
	        
	        String replaceFileName, originalFileExtension, contentType;
	        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
	        ZonedDateTime currentTime = ZonedDateTime.now();
	        String path = "/up/" + currentTime.format(dateFormat);
	        File file = new File(path);
	        if (!file.exists()) {
	            file.mkdirs();
	        }
	        	        
	        while (filenameIterator.hasNext()) {
	        	
	            name = filenameIterator.next();
	            log.debug("File name tag : " + name);
	            List<MultipartFile> fileList = multipartHttpServletRequest.getFiles(name);
	            
	            for (MultipartFile multipartFile : fileList) {
	                log.debug("--- start file ---");
	                log.debug("File name : " + multipartFile.getOriginalFilename());
	                log.debug("File size : " + multipartFile.getSize());
	                log.debug("File content-type : " + multipartFile.getContentType());
	                log.debug("--- end file ---");
	                
	                if (!multipartFile.isEmpty()) {
	                	log.debug("multipartFile.isNotEmpty");
	                    contentType = multipartFile.getContentType();
	                    
	                    if (ObjectUtils.isEmpty(contentType)) {
	                        break;
	                    } else {
	                    	log.debug("contentType {}", contentType);
	                    	/*
	                        if (contentType.contains("image/jpeg")) {
	                            originalFileExtension = ".jpg";
	                        } else if (contentType.contains("image/png")) {
	                            originalFileExtension = ".png";
	                        } else if (contentType.contains("image/gif")) {
	                            originalFileExtension = ".gif";
	                        } else if (contentType.contains("application/x-zip-compressed")) {
	                            originalFileExtension = ".zip";
	                        } else if (contentType.contains("text/csv")) {
	                            originalFileExtension = ".csv";
	                        } else {
	                            break;
	                        }
	                    	 */
	                    }
	                    replaceFileName = System.nanoTime() + "_" +multipartFile.getOriginalFilename();
	                    log.debug("replaceFileName {}", replaceFileName);
	                    
	                    //System.out.println("replaceFileName : " + replaceFileName);
//	                    
//	                    BoardFileDto boardFile = new BoardFileDto();
//	                    boardFile.setBoardIdx(boardIdx);
//	                    boardFile.setFileSize(multipartFile.getSize());
//	                    boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
//	                    boardFile.setStoredFilePath(path + "/" + replaceFileName);
//	                    dtoFileList.add(boardFile);

	                    log.debug("file_path : {}/{}", path, replaceFileName);
	                    file = new File(path + "/" + replaceFileName);
	                    multipartFile.transferTo(file);
	                    
	                    fileMap.put("fileSize", multipartFile.getSize());
	                    fileMap.put("contentType", multipartFile.getContentType());
	                    fileMap.put("orgFileName", multipartFile.getOriginalFilename());
	                    fileMap.put("repFileName", replaceFileName);
	                    fileMap.put("filePath", path + "/" + replaceFileName);
	                    
	                    //rsltMap.put("rslt", fileMap);
	                    
	                }else {
	                	log.debug("multipartFile.isEmpty");
	                }

	            }
	        }
	    }
		
		return fileMap;
	}
	
	
}
