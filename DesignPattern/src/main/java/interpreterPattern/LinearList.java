package interpreterPattern;

/**
 * 线性表
 *
 * @author mit 2022/8/23 23:30
 */
public class LinearList {
    public int length = 0;
    private String[] arr;
    // 线性表的最小长度
    private static final int MIN_CAPACITY = 3;

    public LinearList(int length) {
        if (length >= MIN_CAPACITY) {
            arr = new String[length];
        } else {
            arr = new String[MIN_CAPACITY];
        }
    }

    public LinearList() {
        this(MIN_CAPACITY);
    }

    public LinearList(String[] arr) {
        if (arr.length <= MIN_CAPACITY) {
            this.arr = new String[MIN_CAPACITY];
        } else {
            this.arr = arr;
        }
        length = this.arr.length;
    }

    /**
     * 获取线性表容量
     *
     * @return 线性表大小
     */
    public int getCapacity() {
        return arr.length;
    }

    /**
     * 判断线性表为空
     *
     * @return bool
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * 判断线性表是否已满
     *
     * @return bool
     */
    public boolean isFull() {
        return length == arr.length;
    }

    /**
     * 获取线性表长度
     *
     * @return 线性表长度
     */
    public int size() {
        return length;
    }

    /**
     * 返回首个与key相等的元素的索引，查找不成功，返回null（这里是0）
     *
     * @param key 查找的元素
     * @return 首个与key相等的元素的索引
     */
    public int keySearch(String key) {
        if (isEmpty()) {
            throw new RuntimeException("线性表为空！");
        }
        for (int i = 0; i < length; i++) {
            if (key.equals(arr[i])) {
                return i;
            }
        }
        System.out.printf("线性表中没有该元素，key: %s", key);
        return 0;
    }

    /**
     * 删除首个与key相等的元素
     *
     * @param key 查找的元素
     */
    public void keyRemove(String key) {
        if (isEmpty()) {
            throw new RuntimeException("线性表为空！");
        }
        for (int i = 0; i < length; i++) {
            if (key.equals(arr[i])) {
                arr[i] = "";    // hits: 这里将对应的索引位置元素替换为""
                length--;
                return;
            }
        }
        System.out.printf("线性表中没有该元素，key: %s", key);
    }

    /**
     * 插入value，作为第n个元素
     *
     * @param value 插入的元素
     * @param n     插入的位置索引
     */
    public void insert(String value, int n) {
        if (isFull()) {
            System.out.println("线性表已满");
            return;
        }
        // 插入位置
        if (n >= 1 && n <= length + 1) {
            for (int i = length; i > n; i--) {
                arr[i] = arr[i - 1];
            }
            arr[n - 1] = value;
        } else if (n > length + 1) {
            System.out.println("插入位置过大，自动插入到尾部");
            this.insert(value, length + 1);
        } else if (n < 1) {
            System.out.println("插入位置过小，自动插入到头部");
            this.insert(value, 1);
        }
        length++;
    }

    /**
     * 在尾部插入元素
     *
     * @param value 插入的元素
     */
    public void tailInsert(String value) {
        this.insert(value, length + 1);
    }

    /**
     * 移除第n个元素并返回
     *
     * @param n 元素索引
     * @return 移除的元素
     */
    public String remove(int n) {
        if (isEmpty()) {
            throw new RuntimeException("线性表为空！");
        }
        if (n >= 1 && n <= length) {
            String temp = arr[n - 1];
            for (int i = n - 1; i <= length - 2; i++) {
                arr[i] = arr[i + 1];
            }
            arr[length - 1] = "";     // hits: 这里将补位的索引位置元素替换为""
            length--;
            return temp;
        } else {
            throw new RuntimeException("删除位置有误");
        }
    }

    /**
     * 顺序栈需求 --> 删除最后的元素（栈的顶部）并返回
     *
     * @return 移除的元素
     */
    public String tailPop() {
        return remove(length);
    }

    /**
     * 设置第n个元素为value
     *
     * @param value 设置的元素
     * @param n     索引位置
     */
    public void setValue(String value, int n) {
        if (isEmpty()) {
            throw new RuntimeException("线性表为空！");
        }
        if (n > length || n < 1) {
            System.out.println("访问越界");
        } else {
            arr[n - 1] = value;
        }
    }

    // 打印线性表数据
    public void showInfo() {
        for (int i = 0; i < length; i++) {
            System.out.println(arr[i] + "\t");
        }
        System.out.println("");
    }

    // 查看指定位置的元素
    public String getValue(int index) {
        return arr[index];
    }
}
