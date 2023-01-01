package com.stone.utils.io;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class FileUploadUtil {

public static String uploadFile(MultipartFile file, String path) throws Exception {
String uuid     = UUID.randomUUID().toString();
String fileName = file.getOriginalFilename();
String fileType = fileName.substring(fileName.lastIndexOf("."));
fileName = "/upload/" + uuid + fileType;
File targetFile = new File(path, fileName);
FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
return fileName;
}

/**
* 删除文件
*
* @param pic
*/
public static void deleteFile(String pic) {
File file = new File(pic);
if (file.exists()) { file.delete(); }
}
}
