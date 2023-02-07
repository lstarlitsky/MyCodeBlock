import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

/**
 * SQL 与 DataStreamAPI 的转换
 *
 * @author mit
 * @date 2023-02-07 15:46
 */
public class SQLToDataStreamApi {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        // 1、模拟补贴券流水数据
        String createTableSql = "CREATE TABLE source_table ("
                + " id BIGINT," // 补贴券的流⽔ id
                + " money BIGINT," // 补贴券的⾦额
                + " row_time AS cast(CURRENT_TIMESTAMP as timestamp_LTZ(3)),"
                + " WATERMARK FOR row_time AS row_time - INTERVAL '5' SECOND"
                + ") WITH ("
                + " 'connector' = 'datagen',"
                + " 'rows-per-second' = '1',"
                + " 'fields.id.min' = '1',"
                + " 'fields.id.max' = '100000',"
                + " 'fields.money.min' = '1',"
                + " 'fields.money.max' = '100000'"
                + ")";
        String querySql = "SELECT UNIX_TIMESTAMP(CAST(window_end AS STRING)) * 1000 AS window_end," +
                "   window_start," +
                "   sum(money) AS sum_money," +
                "   count(distinct id) as count_distinct_id" +
                " FROM TABLE(CUMULATE(" +
                "       TABLE source_table," +
                "       DESCRIPTOR(row_time)," +
                "       INTERVAL '5' SECOND," +
                "       INTERVAL '1' DAY))" +
                " GROUP BY window_start, window_end";

        tEnv.executeSql(createTableSql);
        Table resultTable = tEnv.sqlQuery(querySql);

        // 3. 将⾦额结果转为 DataStream，然后⾃定义超过 1w 的报警逻辑
        tEnv.toDataStream(resultTable, Row.class)
                .flatMap(
                        new FlatMapFunction<Row, Object>() {
                            @Override
                            public void flatMap(Row value, Collector<Object> out) throws Exception {
                                long sum_money = Long.parseLong(String.valueOf(value.getField("sum_money")));
                                if (sum_money > 10000L) {
                                    System.out.println("报警，超过 1W，sum_money: " + sum_money);
                                }
                            }
                        }
                );

        env.execute();
    }
}
