package co.yixiang.yshop.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 * @author yshop
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${yshop.info.base-package}
@SpringBootApplication(scanBasePackages = {"${yshop.info.base-package}.server", "${yshop.info.base-package}.module"})
public class YshopServerApplication {

    public static void main(String[] args){

        SpringApplication.run(YshopServerApplication.class, args);


    }

}
