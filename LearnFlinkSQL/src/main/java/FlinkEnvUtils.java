import lombok.Builder;
import lombok.Data;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * 处理Flink env的工具类
 *
 * @author mit
 * @date 2023-02-15 11:16
 */
public class FlinkEnvUtils {

    /**
     * 是否开启增量checkpoint机制
     * 增量checkpoint机制只支持RocksDB StateBackend
     */
    private static final boolean ENABLE_INCREMENTAL_CHECKPOINT = true;
    /**
     * Sets the number of threads used to transfer files while snapshotting/restoring.
     */
    private static final int NUMBER_OF_TRANSFER_THREADS = 3;

    /**
     * 创建 streamTableEnv
     *
     * @param args 参数
     * @return FlinkEnv
     */
    public static FlinkEnv getStreamTableEnv(String[] args) {
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        Configuration configuration = Configuration.fromMap(parameterTool.toMap());
        configuration.setString("rest.flamegraph.enabled", "true");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        env.setParallelism(1);

        String stateBackend = parameterTool.get("state.backend", "rocksdb");
        switch (stateBackend) {
        }

    }

    @Builder
    @Data
    public static class FlinkEnv {
        private StreamExecutionEnvironment streamExecutionEnvironment;
        private StreamTableEnvironment streamTableEnvironment;
        private TableEnvironment tableEnvironment;

        public StreamExecutionEnvironment env() {
            return this.streamExecutionEnvironment;
        }

        public StreamTableEnvironment streamTableEnv() {
            return this.streamTableEnvironment;
        }

        public TableEnvironment tableEnv() {
            return this.tableEnvironment;
        }
    }
}
