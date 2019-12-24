package com.meethere.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.UUID;

public class FileUtil {

    /**
     * 保存上传的文件
     *
     * @param picture
     * @return 文件下载的url
     * @throws Exception
     */
    public static String saveVenueFile(MultipartFile picture) throws Exception {

        // 获取文件名
        String fileName = picture.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传后的路径
        String filePath = "E:\\SoftwareTesting-MeetHere\\file\\venue";
        // 解决中文问题，liunx下中文路径，图片显示问题
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath, fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        picture.transferTo(dest);
        return filePath + fileName;
    }
}
