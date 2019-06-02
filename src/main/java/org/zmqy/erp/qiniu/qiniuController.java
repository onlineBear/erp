package org.zmqy.erp.qiniu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zmqy.erp.model.mis.vo.Response;

@RestController
@RequestMapping("/test/qiniu/")
public class qiniuController {
    @Autowired
    QiniuService qiniuService;
    @PostMapping("uploadpic")
    public Response uploadpic(@RequestParam(value = "file",required = false)MultipartFile file){
        qiniuService.qiniuPic(file);
        return Response.ok();
    }
    @PostMapping("uploadpicList")
    public Response uploadpicList(@RequestParam(value = "file",required = false)MultipartFile[] file){
//        qiniuService.qiniuPic(file);
        System.out.println(file[1].getName());
        qiniuService.qiniuPicList(file);
        return Response.ok();
    }

}
