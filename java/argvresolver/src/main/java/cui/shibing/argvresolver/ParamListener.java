package cui.shibing.argvresolver;

import java.util.List;

@FunctionalInterface
public interface ParamListener {
    void onParamAppear(String paramName,List<String> values);
}