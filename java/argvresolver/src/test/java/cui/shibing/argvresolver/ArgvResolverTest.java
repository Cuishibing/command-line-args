package cui.shibing.argvresolver;

import cui.shibing.argvresolver.exception.ResolveException;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

public class ArgvResolverTest {

    @Test
    public void test() {
//        ArgvResolver argvResolver = new ArgvResolver();
//
//        // 如果没有任何参数，则打印使用方法。
//        argvResolver.registerParamListener("",(paramName,values)->{
//            System.out.println("show usage!");
//            return null;
//        });
//
//        // 参数名称前缀为‘－’。
//        argvResolver.registerParamListener("(^-\\w+)", (paramName, values) -> {
//            // 打印出参数名称和值。
//            System.out.println(String.format("paramName:%s\tvalues:%s", paramName, values.toString()));
//            return (v)-> System.out.println(String.format("param:%s processed!",paramName));
//        });
//
//        // 开始解析过程。
//        argvResolver.resolve(Arrays.asList("-action", "on"));
    }

    @Test
    public void testOption() throws ResolveException {
        Consumer<Option> optionConsumer = option -> {
            System.out.println(option.getOptionName() + " " + option.getOptionValues());
        };
        OptionDefinition defAction = new OptionDefinition("(-a)|(--action)"
              , optionConsumer);
        OptionDefinition defTarget = new OptionDefinition("(-t)|(--target)"
                , optionConsumer);

        ArgvResolver resolver = new ArgvResolver();
        resolver.addOptionDefinition(defAction);
        resolver.addOptionDefinition(defTarget);
        List<String> remainValues = resolver.resolve("-t 1 2 -a add 2".split("\\s+"));
        System.out.println("remaining " + remainValues);
    }

}