package cui.shibing.argvresolver;

import java.util.List;

public interface ParamListener {
    void onParamAppear(String paramName,List<String> values);
}