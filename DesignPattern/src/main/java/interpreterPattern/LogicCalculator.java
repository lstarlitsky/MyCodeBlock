package interpreterPattern;

import java.util.Arrays;
import java.util.List;

/**
 * 伪代码解析计算
 *
 * @author mit 2022/8/24 0:24
 */
public class LogicCalculator {
    /**
     * 支持的逻辑运算符
     */
    final List<String> OPERAS = Arrays.asList("ANR", "OR", "NOT", "(", ")");

    public boolean isOpera(String element) {
        return OPERAS.contains(element);
    }
}
