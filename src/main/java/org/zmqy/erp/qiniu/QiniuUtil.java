package org.zmqy.erp.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


public class QiniuUtil {
    private static String accesskey;
    private static String secretkey;
    private static String bucket;

    public static void main(String[] args) {
//        Test1 test = new Test1();
//        String[] del = {"12fd2368c28c4f25bda65cdcd504a1f5","7ea0d61bbaa84f3088f661e0772aeae9"};
//        List<String> del = new ArrayList<>();
//        del.add("b1f807dd68e5460bbc4fdd25d5ed5cd3");
//        del.add("f22de4eca205498e8fa6e66239c31559");
//        Test1.del(del);

        File file = new File("D:\\qiniu\\test7.mp4");
        try {
            FileInputStream in_file = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), in_file);
            QiniuUtil.uploadVideo(multipartFile);
            in_file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*File file = new File("D:\\qiniu\\test6.mp4");
        File file1 = new File("D:\\qiniu\\test7.mp4");
        try {
            FileInputStream in_file = new FileInputStream(file);
            FileInputStream in_file1 = new FileInputStream(file1);
            MultipartFile filem = new MockMultipartFile(file.getName(), in_file);
            MultipartFile filem1 = new MockMultipartFile(file1.getName(), in_file1);
            MultipartFile[] multipartFile = new MultipartFile[2];
            multipartFile[0] = filem;
            multipartFile[1] = filem1;
            QiniuUtil.uploadVideo(multipartFile);
            in_file.close();
            in_file1.close();
            multipartFile.clone();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 取秘钥和空间
     */
    private static void init(){
        Properties pro = QiniuUtil.fileUtil("qiniu.properties");
        if (pro == null || pro.equals("")) {
            throw new RuntimeException("配置文件不存在");
        }
         accesskey = pro.getProperty("accesskey");
         secretkey = pro.getProperty("secretkey");
         bucket = pro.getProperty("bucket");
        if (accesskey == null || accesskey.equals("")) {
            throw new RuntimeException("accesskey为空");
        }
        if (secretkey == null || secretkey.equals("")) {
            throw new RuntimeException("secretkey为空");
        }
        if (bucket == null || bucket.equals("")) {
            throw new RuntimeException("bucket为空");
        }
    }

/*
    private QiniuUtil(){
        Properties pro = QiniuUtil.fileUtil("qiniu.properties");
        if (pro == null || pro.equals("")) {
            throw new RuntimeException("配置文件不存在");
        }

        secretkey = pro.getProperty("secretkey");
        bucket = pro.getProperty("bucket");
        if (accesskey == null || accesskey.equals("")) {
            throw new RuntimeException("accesskey为空");
        }
        if (secretkey == null || secretkey.equals("")) {
            throw new RuntimeException("secretkey为空");
        }
        if (bucket == null || bucket.equals("")) {
            throw new RuntimeException("bucket为空");
        }

    }

*/

    /**
     * 单图片上传
     *
     * @param file
     */
    public static void uploadPic(MultipartFile file) {
        Properties pro = QiniuUtil.fileUtil("pic.properties");
        init();



        Zone zone = Zone.autoZone();

        //Zone.zone2()
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //配置文件上传策略
        StringMap putPolicy = new StringMap();
        Integer fsizeMin = Integer.valueOf(pro.getProperty("fsizeMin"));
        Integer fsizeLimit = Integer.valueOf(pro.getProperty("fsizeLimit"));

        putPolicy.put("fsizeMin", fsizeMin);//配置最小图片大小
        putPolicy.put("fsizeLimit", fsizeLimit);//配置最大图片大小
        putPolicy.put("mimeLimit", "image/*");//配置只允许上传图片格式


//...生成上传凭证，然后准备上传


        //指定uuid为文件名
        String key = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Auth auth = Auth.create(accesskey, secretkey);
            String upToken = auth.uploadToken(bucket, key, 3600, putPolicy);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果

                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 多图片上传
     *
     * @param fileArray
     */
    public static void uploadPic(MultipartFile[] fileArray) {
        if (fileArray != null && fileArray.length > 0) {
            Properties pro = QiniuUtil.fileUtil("pic.properties");
            init();

            Zone zone = Zone.autoZone();
            //Zone.zone2()
            //构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(zone);
//...其他参数参考类注释

            UploadManager uploadManager = new UploadManager(cfg);
            //配置文件上传策略
            StringMap putPolicy = new StringMap();
            Integer fsizeMin = Integer.valueOf(pro.getProperty("fsizeMin"));
            Integer fsizeLimit = Integer.valueOf(pro.getProperty("fsizeLimit"));

            putPolicy.put("fsizeMin", fsizeMin);//配置最小图片大小
            putPolicy.put("fsizeLimit", fsizeLimit);//配置最大图片大小
            putPolicy.put("mimeLimit", "image/*");


//...生成上传凭证，然后准备上传
            for (MultipartFile multipartFile : fileArray) {
                if (multipartFile != null && !multipartFile.isEmpty()) {


                    //指定uuid为文件名
                    String key = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    InputStream inputStream = null;
                    try {
                        inputStream = multipartFile.getInputStream();
                        Auth auth = Auth.create(accesskey, secretkey);
                        String upToken = auth.uploadToken(bucket, key, 3600, putPolicy);

                        try {
                            Response response = uploadManager.put(inputStream, key, upToken, null, null);
                            //解析上传成功的结果

                            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                            System.out.println(putRet.key);
                            System.out.println(putRet.hash);
                        } catch (QiniuException ex) {
                            Response r = ex.response;
                            System.err.println(r.toString());
                            try {
                                System.err.println(r.bodyString());
                            } catch (QiniuException ex2) {
                                //ignore
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

    }

    /**
     * 单视频上传
     *
     * @param file
     */
    public static void uploadVideo(MultipartFile file) {
        Properties pro = QiniuUtil.fileUtil("video.properties");
        init();


        Zone zone = Zone.autoZone();

        //Zone.zone2()
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //配置文件上传策略
        StringMap putPolicy = new StringMap();
        Integer fsizeMin = Integer.valueOf(pro.getProperty("fsizeMin"));
        Integer fsizeLimit = Integer.valueOf(pro.getProperty("fsizeLimit"));

        putPolicy.put("fsizeMin", fsizeMin);//配置最小图片大小
        putPolicy.put("fsizeLimit", fsizeLimit);//配置最大图片大小
        putPolicy.put("mimeLimit", "video/*");//配置只允许上传视频格式


//...生成上传凭证，然后准备上传


        //指定uuid为文件名
        String key = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Auth auth = Auth.create(accesskey, secretkey);
            String upToken = auth.uploadToken(bucket, key, 3600, putPolicy);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果

                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 多视频上传
     *
     * @param fileArray
     */
    public static void uploadVideo(MultipartFile[] fileArray) {
        if (fileArray != null && fileArray.length > 0) {
            Properties pro = QiniuUtil.fileUtil("video.properties");
            init();

            Zone zone = Zone.autoZone();
            //Zone.zone2()
            //构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(zone);
//...其他参数参考类注释
            for (MultipartFile multipartFile : fileArray) {
                if (multipartFile != null && !multipartFile.isEmpty()) {
                    UploadManager uploadManager = new UploadManager(cfg);
                    //配置文件上传策略
                    StringMap putPolicy = new StringMap();
                    Integer fsizeMin = Integer.valueOf(pro.getProperty("fsizeMin"));
                    Integer fsizeLimit = Integer.valueOf(pro.getProperty("fsizeLimit"));

                    putPolicy.put("fsizeMin", fsizeMin);//配置最小图片大小
                    putPolicy.put("fsizeLimit", fsizeLimit);//配置最大图片大小
                    putPolicy.put("mimeLimit", "video/*");//配置只允许上传图片格式


//...生成上传凭证，然后准备上传


                    //指定uuid为文件名
                    String key = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    InputStream inputStream = null;
                    try {
                        inputStream = multipartFile.getInputStream();
                        Auth auth = Auth.create(accesskey, secretkey);
                        String upToken = auth.uploadToken(bucket, key, 3600, putPolicy);

                        try {
                            Response response = uploadManager.put(inputStream, key, upToken, null, null);
                            //解析上传成功的结果

                            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                            System.out.println(putRet.key);
                            System.out.println(putRet.hash);
                        } catch (QiniuException ex) {
                            Response r = ex.response;
                            System.err.println(r.toString());
                            try {
                                System.err.println(r.bodyString());
                            } catch (QiniuException ex2) {
                                //ignore
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

    }


    /**
     * 单文件删除
     *
     * @param fileName
     */
    public static void del(String fileName) {

        init();
        Zone zone = Zone.autoZone();

        //Zone.zone2()
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
//...其他参数参考类注释

        Auth auth = Auth.create(accesskey, secretkey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }

    /**
     * 多文件删除
     *
     * @param fileNameArray
     */
    public static void del(String[] fileNameArray) {
        if (fileNameArray == null || fileNameArray.equals("")) {
            return;
        }

        init();
        Zone zone = Zone.autoZone();

        //Zone.zone2()
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        Auth auth = Auth.create(accesskey, secretkey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, fileNameArray);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusesList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < fileNameArray.length; i++) {
                BatchStatus status = batchStatusesList[i];
                String key = fileNameArray[i];
                System.out.println(key + "\t");
                if (status.code == 200) {
                    System.out.println("delete success");
                } else {
                    System.out.println(status.data.error);
                }

            }

        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
    }

    /**
     * 多文件删除
     *
     * @param fileNameList
     */
    public static void del(List<String> fileNameList) {
        if (fileNameList == null || fileNameList.equals("")) {
            return;
        }
        String[] fileNameArray = fileNameList.toArray(new String[fileNameList.size()]);

        init();

        Zone zone = Zone.autoZone();
        //Zone.zone2()
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        Auth auth = Auth.create(accesskey, secretkey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, fileNameArray);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusesList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < fileNameArray.length; i++) {
                BatchStatus status = batchStatusesList[i];
                String key = fileNameArray[i];
                System.out.println(key + "\t");
                if (status.code == 200) {
                    System.out.println("delete success");
                } else {
                    System.out.println(status.data.error);
                }

            }

        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }

    }

    /**
     * 图片替换
     *
     * @param newFile
     * @param oldFileName
     */
    public void replacePic(MultipartFile[] newFile, String[] oldFileName) {
        this.del(oldFileName);
        this.uploadPic(newFile);
    }

    /**
     * 视频替换
     *
     * @param newFile
     * @param oldFileName
     */
    public void replaceVideo(MultipartFile[] newFile, String[] oldFileName) {
        this.del(oldFileName);
        this.uploadVideo(newFile);
    }

    /**
     * 读取配置文件
     *
     * @param fileName
     * @return
     */
    public static Properties fileUtil(String fileName) {
        if (fileName == null || fileName.equals("")) {
            throw new RuntimeException("配置文件名为空");
        }
        Properties prop = new Properties();
        InputStream is = null;
        //读取文件   通过类加载读取
        try {
            is = new FileInputStream("src/main/resources/config/" + fileName);
            prop.load(is);
            return prop;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

}
