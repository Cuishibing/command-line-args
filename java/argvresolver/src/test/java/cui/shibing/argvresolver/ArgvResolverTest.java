package cui.shibing.argvresolver;

import org.junit.Test;

import java.util.Arrays;

public class ArgvResolverTest {

    @Test
    public void test() {
        ArgvResolver argvResolver = new ArgvResolver();

        // 如果没有任何参数，则打印使用方法。
        argvResolver.registerParamListener("",(paramName,values)->{
            System.out.println("show usage!");
        });

        // 参数名称前缀为‘－’。
        argvResolver.registerParamListener("(^-\\w+)|", (paramName, values) -> {
            // 打印出参数名称和值。
            System.out.println(String.format("paramName:%s\tvalues:%s", paramName, values.toString()));
        });

        // 开始解析过程。
        argvResolver.resolve(Arrays.asList("-action", "on"));
    }

}