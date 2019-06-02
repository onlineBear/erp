package org.zmqy.erp.qiniu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class QiniuService {
    @Transactional(rollbackFor = Exception.class)
    public void qiniuPic(MultipartFile file){
        QiniuUtil.uploadPic(file);
    }

    @Transactional(rollbackFor = Exception.class)
    public void qiniuPicList(MultipartFile[] file){
        QiniuUtil.uploadPic(file);
    }
}
