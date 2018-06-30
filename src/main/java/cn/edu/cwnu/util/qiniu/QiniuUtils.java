package cn.edu.cwnu.util.qiniu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛上传工具
 */
public class QiniuUtils {

    private Logger logger = LoggerFactory.getLogger(QiniuUtils.class);

    public static final String DOMAINNAME = "http://p3nxz9y8f.bkt.clouddn.com";

    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "";
    String SECRET_KEY = "";
    //要上传的空间
    String bucketname = "";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    ///////////////////////指定上传的Zone的信息//////////////////
    //第一种方式: 指定具体的要上传的zone
    //注：该具体指定的方式和以下自动识别的方式选择其一即可
    //要上传的空间(bucket)的存储区域为华东时
    // Zone z = Zone.zone0();
    //要上传的空间(bucket)的存储区域为华北时
    // Zone z = Zone.zone1();
    //要上传的空间(bucket)的存储区域为华南时
    // Zone z = Zone.zone2();

    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
    Zone z = Zone.autoZone();
    Configuration c = new Configuration(z);

    //创建上传对象
    UploadManager uploadManager = new UploadManager(c);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    //js上传
    //scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
    public String getJSUpToken() {
        return auth.uploadToken(bucketname);
    }

    /**
     * 上传文件
     *
     * @param FilePath 上传文件的真实路径
     * @param key      上传到七牛后保存的文件名（可以包含路径），如 /test/test.png
     * @return
     */
    public boolean uploadFIle(String FilePath, String key) {
        boolean flag = false;
        try {
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            //上传成功
            if (res.statusCode == 200) {
                flag = true;
            } else {
                flag = false;
                logger.error("七牛上传文件失败（七牛原因或者七牛的配置原因）：" + res.bodyString());
            }

        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            logger.error("七牛上传文件失败（自己原因）：" + r.toString());
            try {
                //响应的文本信息
                logger.error(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }

        return flag;
    }
}

