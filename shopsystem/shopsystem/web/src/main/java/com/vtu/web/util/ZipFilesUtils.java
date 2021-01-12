package com.vtu.web.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * <p>Description: [文件压缩打包工具]</p >
 * Created on 2019年12月10日
 *
 * @author CJ
 * @version 1.0
 * Copyright (c) 2019
 */
public class ZipFilesUtils {

    private static final Logger log = LoggerFactory.getLogger(ZipFilesUtils.class);

    private static final byte[] BUF = new byte[1024];

    private ZipFilesUtils() {};

    /**
     * 多个文件压缩
     * @param files
     * @param zipFilePath
     */
    public static void zipFile(List<File> files, String zipFilePath){
        ZipOutputStream zipOutputStream = null;
        FileOutputStream fileOutputStream = null;
        File zipFile  = new File(zipFilePath);
        try {
            fileOutputStream = new FileOutputStream(zipFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (File file:files){
                zipFile(zipOutputStream, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if(fileOutputStream != null) {
                    fileOutputStream.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 将单个文件压缩到zip流
     * @param zipOutputStream
     * @param file
     * @throws IOException
     */
    private static void zipFile(ZipOutputStream zipOutputStream, File file) throws IOException {
        InputStream inputStream = null;
        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
        inputStream = new FileInputStream(file);
        int len;
        while ((len = inputStream.read(BUF))>0){
            zipOutputStream.write(BUF,0,len);
        }
        zipOutputStream.closeEntry();
        inputStream.close();
    }



    /**
     * 压缩（将文件夹打包）
     *
     * @param fileDir 文件夹目录路径
     * @param zipFilePath 目标压缩文件路径
     * @throws IOException
     */
    public static void zipByFolder(String fileDir, String zipFilePath) throws IOException {
        File folder = new File(fileDir);
        if(folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            List<File> filesList = Arrays.asList(files);
            zipFile(filesList, zipFilePath);
        }
    }

    /**
     * 解压
     *
     * @param zipFile 压缩文件
     * @param descDir 目标文件路径
     * @return
     * @throws IOException
     */
    public static List<File> unzip(File zipFile, String descDir) throws IOException {
        List<File> files = new ArrayList<>();
        if(StringUtils.isNotBlank(descDir)) {
            log.info("启用ZIP解压工具 >>>>>>>>>> ");
            if(zipFile.exists() && zipFile.getName().endsWith(".zip")) {
                OutputStream outputStream = null;
                InputStream inputStream = null;
                try {
                    ZipFile zf = new ZipFile(zipFile);
                    Enumeration entries = zf.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                        String zipEntryName = zipEntry.getName();
                        log.info("正在解压文件 -> " + zipEntryName);
                        inputStream = zf.getInputStream(zipEntry);
                        String descFilePath = descDir + File.separator + zipEntryName;
                        File descFile = createFile(descFilePath);
                        files.add(descFile);
                        outputStream = new FileOutputStream(descFilePath);
                        int len;
                        while((len = inputStream.read(BUF)) > 0) {
                            outputStream.write(BUF, 0, len);
                        }
                    }
                    log.info("解压完成 <<<<<<<<<< " + descDir);
                } finally {
                    if(null != inputStream) {
                        inputStream.close();
                    }
                    if(null != outputStream) {
                        outputStream.close();
                    }
                }
            }
        }
        return files;
    }

    private static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if(!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

}