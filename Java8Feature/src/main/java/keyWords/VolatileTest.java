package keyWords;

/**
 * Volatile关键字
 * Volatile关键字的作用主要有如下两个：
 * 1.线程的可见性：当一个线程修改一个共享变量时，另外一个线程能读到这个修改的值。
 * 2. 顺序一致性：禁止指令重排序。
 *
 * @author mit
 * @date 2022-11-22 22:23:70
 * @see <a>rel="https://blog.csdn.net/xinghui_liu/article/details/124379221"</a>
 */
public class VolatileTest {
    /**
     * 验证volatile关键字的作用
     */
    boolean flag = true;
    // volatile boolean flag = true;

    public void updateFlag() {
        this.flag = false;
        System.out.println("修改flag值为：" + this.flag);
    }

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        new Thread(() -> {
            while (test.flag) {
            }
            System.out.println(Thread.currentThread().getName() + "结束");
        }, "Thread1").start();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                test.updateFlag();
            } catch (InterruptedException e) {
            }
        }, "Thread2").start();

    }
}
