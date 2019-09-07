package vip.wush.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: HystrixController
 * @Description: Hystrix限流熔断
 * @Author: wush
 * @Date: 2019/7/18 14:17
 */

@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    private static final Logger LOG = LoggerFactory.getLogger(HystrixController.class);


    @GetMapping(value = "/ok")
    @HystrixCommand(fallbackMethod = "okFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "50")

    })
    public String ok() throws Exception {
        int l = new Random().nextInt(200);
        LOG.info(String.format("l=%s", l));
        TimeUnit.MILLISECONDS.sleep(l);
        return "ok";
    }


    public String okFallback(Throwable e) {
        System.out.println("execute okFallback!" + e.getMessage());
        LOG.error("error", e);
        return "fallback";
    }


    public String okFallback() {
        return "fallbackssssss";
    }


}
