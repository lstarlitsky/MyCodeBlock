package interpreterPattern;

/**
 * @author mit 2022/8/23 23:25
 */
public interface Stack {
    /**
     * 判断堆栈是否为空
     *
     * @return bool
     */
    public abstract boolean isEmpty();

    /**
     * 入栈
     *
     * @param value 元素
     */
    public abstract void push(String value);

    /**
     * 出栈，并得到该元素
     *
     * @return 算子代码
     */
    public abstract String pop();

    /**
     * 返回顶端元素
     *
     * @return 算子代码
     */
    public abstract String peek();
}
