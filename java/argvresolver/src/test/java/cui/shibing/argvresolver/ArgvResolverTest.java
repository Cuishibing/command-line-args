package cui.shibing.argvresolver;

import org.junit.Test;

import java.util.Arrays;

public class ArgvResolverTest {

    @Test
    public void test() {
        ArgvResolver argvResolver = new ArgvResolver();
        argvResolver.registerParamListener("(^-\\w+)|(^--\\w+)|(^---\\w+)", (paramName, values) -> {
            System.out.println(String.format("paramName:%s\tvalues:%s", paramName, values.toString()));
        });
        argvResolver.resolve(Arrays.asList("-action", "on",  "--test" ,"-startTime", "2019-3-23",
                "-name","cuishibing","-sex","man","---address","beijing","-school","xi'an keji daxue"));

        argvResolver.registerParamListener("",(paramName,values)->{
            System.out.println("this is usage!");
        });
        argvResolver.resolve((String[]) null);
    }

}