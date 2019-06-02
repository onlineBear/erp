package org.zmqy.erp.tool.util.file;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;


/**
 * @Description: 文件操作工具类
 * @Author: lly
 * @Date: 2019-02-21 10:28
 */
public class FileUtil {
    public static void setLogPath(String logPath) {
        FileUtil.logPath = logPath;
    }

    //    private static Logger logger = Logger.getLogger(FileOperateUtil.class);
    private static String logPath = "";

    public static String getLogPath() {
        return logPath;
    }

    /**
     * 文件解压 .zip文件
     *
     * @param zipFilePath  要解压的文件路径
     * @param fileSavePath 解压到的文件路径
     * @param isDelete     是否删除源文件
     */
    public static void unZipFiles(String zipFilePath, String fileSavePath, boolean isDelete) {
        FileUtil fileOperateUtil = new FileUtil();
        boolean isUnZipSuccess = true;
        try {
            (new File(fileSavePath)).mkdirs();
            File f = new File(zipFilePath);
            if ((!f.exists()) && (f.length() <= 0)) {
                throw new RuntimeException("not find " + zipFilePath + "!");
            }
            //一定要加上编码，之前解压另外一个文件，没有加上编码导致不能解压
            ZipFile zipFile = new ZipFile(f, "gbk");
            String gbkPath, strtemp;
            Enumeration<ZipEntry> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                org.apache.tools.zip.ZipEntry zipEnt = e.nextElement();
                gbkPath = zipEnt.getName();
                strtemp = fileSavePath + File.separator + gbkPath;
                if (zipEnt.isDirectory()) { //目录
                    File dir = new File(strtemp);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    continue;
                } else {
                    // 读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    // 建目录
                    String strsubdir = gbkPath;
                    for (int i = 0; i < strsubdir.length(); i++) {
                        if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                            String temp = fileSavePath + File.separator
                                    + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if (!subdir.exists())
                                subdir.mkdir();
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int len;
                    byte[] buff = new byte[5120];
                    while ((len = bis.read(buff)) != -1) {
                        bos.write(buff, 0, len);
                    }
                    is.close();
                    bis.close();
                    bos.close();
                    fos.close();
                }
            }
            zipFile.close();
        } catch (Exception e) {
            throw new RuntimeException("解压文件异常");
        }
        /**
         * 文件不能删除的原因：
         * 1.看看是否被别的进程引用，手工删除试试(删除不了就是被别的进程占用)
         2.file是文件夹 并且不为空，有别的文件夹或文件，
         3.极有可能有可能自己前面没有关闭此文件的流(我遇到的情况)
         */
        if (isDelete && isUnZipSuccess) {
            boolean flag = new File(zipFilePath).delete();
        }
    }

    /**
     * 删除某路径下的目录及目录里面的所有文件
     *
     * @param filePath
     * @return
     */
    public static Boolean delDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.delDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static void main(String[] args) throws Exception {
        unZipFiles("D:\\java\\workspace\\erp\\target\\classes\\webapps\\reportTemplate\\1\\test.zip", "D:\\java\\workspace\\erp\\target\\classes\\webapps\\reportTemplate\\1\\", false);
        //delDirectory("D:\\java\\workspace\\erp\\target\\classes\\webapps\\reportTemplate\\1\\proOrder\\");
        //deleteFile("D:\\java\\workspace\\erp\\target\\classes\\webapps\\reportTemplate\\1\\proOrder.zip");
    }

}
