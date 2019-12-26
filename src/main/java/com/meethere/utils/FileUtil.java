package com.meethere.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
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

        if (picture.isEmpty())
            return "";
        String fileDirPath = new String("src/main/resources/" + "static/file/venue");

        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }

        String filename = picture.getOriginalFilename();

        System.out.println(fileDir.getAbsolutePath());
        String suffixName = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID() + suffixName;
        File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
        System.out.println(newFile.getAbsolutePath());
            // 上传图片到 -》 “绝对路径”
            picture.transferTo(newFile);
            return "file/venue/"+filename;
    }

    public static String saveUserFile(MultipartFile picture) throws Exception{
        if (picture.isEmpty())
            return "";
        String fileDirPath = new String("src/main/resources/" + "static/file/user");

        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }

        String filename = picture.getOriginalFilename();

        System.out.println(fileDir.getAbsolutePath());
        String suffixName = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID() + suffixName;
        File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
        System.out.println(newFile.getAbsolutePath());
        // 上传图片到 -》 “绝对路径”
        picture.transferTo(newFile);
        return "file/user/"+filename;
    }


}
