package interpreterPattern;

/**
 * 顺序栈，即用顺序表存储元素
 *
 * @author mit 2022/8/24 0:16
 */
public class SeqStack implements Stack {
    private LinearList linearList;

    public SeqStack(LinearList linearList) {
        this.linearList = linearList;
    }

    @Override
    public boolean isEmpty() {
        return linearList.isEmpty();
    }

    @Override
    public void push(String value) {
        linearList.tailInsert(value);
    }

    @Override
    public String pop() {
        return linearList.tailPop();
    }

    @Override
    public String peek() {
        return linearList.getValue(linearList.length - 1);
    }

    public int length() {
        return linearList.size();
    }
}
