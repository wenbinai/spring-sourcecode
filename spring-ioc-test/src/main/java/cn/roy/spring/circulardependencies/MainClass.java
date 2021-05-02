package cn.roy.spring.circulardependencies;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author smlz
 * @date 2019/5/29
 */
public class MainClass {

    public static void main(String[] args) {
        //创建IOC容器
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);

		InstanceA instanceA = (InstanceA) ctx.getBean("instanceA");
		InstanceB instanceB = (InstanceB) ctx.getBean("instanceB");

		System.out.println(instanceA.getInstanceB());
		System.out.println(instanceB.getInstanceA());



	}
}
