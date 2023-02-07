import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * flinkSQL env
 *
 * @author mit
 * @date 2023-02-03 11:38
 */
public class EnvTest {

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        // 1. 创建⼀个数据源（输⼊）表，这⾥的数据源是 flink ⾃带的⼀个随机 mock 数据的数据源。
        String sourceSql = "CREATE TABLE source_table (" +
                "   sku_id STRING," +
                "   price BIGINT" +
                ") WITH (" +
                "   'connector' = 'datagen'," +
                "   'rows-per-second' = '1'," +
                "   'fields.sku_id.length' = '1'," +
                "   'fields.price.min' = '1'," +
                "   'fields.price.max' = '1000000'" +
                ")";

        // 2. 创建⼀个数据汇（输出）表，输出到 kafka 中
        String sinkSql = "CREATE TABLE sink_table ("
                + " sku_id STRING,"
                + " count_result BIGINT,"
                + " sum_result BIGINT,"
                + " avg_result DOUBLE,"
                + " min_result BIGINT,"
                + " max_result BIGINT,"
                + " PRIMARY KEY (`sku_id`) NOT ENFORCED"
                + ") WITH ("
                + " 'connector' = 'upsert-kafka',"
                + " 'topic' = 'tuzisir',"
                + " 'properties.bootstrap.servers' = 'localhost:9092',"
                + " 'key.format' = 'json',"
                + " 'value.format' = 'json'"
                + ")";

        // 3. 执⾏⼀段 group by 的聚合 SQL 查询
        String selectWhereSql = "select sku_id,"
                + " count(*) as count_result,"
                + " sum(price) as sum_result,"
                + " avg(price) as avg_result,"
                + " min(price) as min_result,"
                + " max(price) as max_result"
                + " from source_table"
                + " group by sku_id";

        tEnv.executeSql(sourceSql);
        // tEnv.executeSql(sinkSql);
        TableResult tableResult = tEnv.executeSql(selectWhereSql);
        tableResult.print();

    }


}
