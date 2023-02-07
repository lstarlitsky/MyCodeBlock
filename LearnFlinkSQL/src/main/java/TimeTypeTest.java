import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * 时间类型的验证
 *
 * @author mit
 * @date 2023-02-07 16:29
 */
public class TimeTypeTest {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        String sinkTableSql = "CREATE TABLE sink_table (\n" +
                "result_interval_year TIMESTAMP(3),\n" +
                "result_interval_year_p TIMESTAMP(3),\n" +
                "result_interval_year_p_to_month TIMESTAMP(3),\n" +
                "result_interval_month TIMESTAMP(3)," +
                "result_interval_day TIMESTAMP(3),\n" +
                "result_interval_day_p1 TIMESTAMP(3),\n" +
                "result_interval_day_p1_to_hour TIMESTAMP(3),\n" +
                "result_interval_day_p1_to_minute TIMESTAMP(3),\n" +
                "result_interval_day_p1_to_second_p2 TIMESTAMP(3),\n" +
                "result_interval_hour TIMESTAMP(3),\n" +
                "result_interval_hour_to_minute TIMESTAMP(3),\n" +
                "result_interval_hour_to_second TIMESTAMP(3),\n" +
                "result_interval_minute TIMESTAMP(3),\n" +
                "result_interval_minute_to_second_p2 TIMESTAMP(3),\n" +
                "result_interval_second TIMESTAMP(3),\n" +
                "result_interval_second_p2 TIMESTAMP(3)\n" +
                ") WITH (\n" +
                "'connector' = 'print'\n" +
                ")";
        String insertSql = "INSERT INTO sink_table\n" +
                "SELECT\n" +
                "-- Flink SQL ⽀持的所有 INTERVAL ⼦句如下，总体可以分为 `年-⽉`、`⽇-⼩时-秒` 两种\n" +
                "-- 1. 年-⽉。取值范围为 [-9999-11, +9999-11]，其中 p 是指有效位数，取值范围 [1, 4]，默认\n" +
                "-- INTERVAL YEAR\n" +
                "f1 + INTERVAL '10' YEAR as result_interval_year\n" +
                "-- INTERVAL YEAR(p)\n" +
                ", f1 + INTERVAL '100' YEAR(3) as result_interval_year_p\n" +
                "-- INTERVAL YEAR(p) TO MONTH\n" +
                ", f1 + INTERVAL '10-03' YEAR(3) TO MONTH as result_interval_year_p_to_month\n" +
                "-- INTERVAL MONTH\n" +
                ", f1 + INTERVAL '13' MONTH as result_interval_month\n" +
                "-- 2. ⽇-⼩时-秒。取值范围为 [-999999 23:59:59.999999999, +999999 23:59:59.99999999\n" +
                "-- INTERVAL DAY\n" +
                ", f1 + INTERVAL '10' DAY as result_interval_day\n" +
                "-- INTERVAL DAY(p1)\n" +
                ", f1 + INTERVAL '100' DAY(3) as result_interval_day_p1\n" +
                "-- INTERVAL DAY(p1) TO HOUR\n" +
                ", f1 + INTERVAL '10 03' DAY(3) TO HOUR as result_interval_day_p1_to_hour\n" +
                "-- INTERVAL DAY(p1) TO MINUTE\n" +
                ", f1 + INTERVAL '10 03:12' DAY(3) TO MINUTE as result_interval_day_p1_to_minute\n" +
                "-- INTERVAL DAY(p1) TO SECOND(p2)\n" +
                ", f1 + INTERVAL '10 00:00:00.004' DAY TO SECOND(3) as result_interval_day_p1_to_\n" +
                "-- INTERVAL HOUR\n" +
                ", f1 + INTERVAL '10' HOUR as result_interval_hour\n" +
                "-- INTERVAL HOUR TO MINUTE\n" +
                ", f1 + INTERVAL '10:03' HOUR TO MINUTE as result_interval_hour_to_minute\n" +
                "-- INTERVAL HOUR TO SECOND(p2)\n" +
                ", f1 + INTERVAL '00:00:00.004' HOUR TO SECOND(3) as result_interval_hour_to_seco\n" +
                "-- INTERVAL MINUTE\n" +
                ", f1 + INTERVAL '10' MINUTE as result_interval_minute\n" +
                "-- INTERVAL MINUTE TO SECOND(p2)\n" +
                ", f1 + INTERVAL '05:05.006' MINUTE TO SECOND(3) as result_interval_minute_to_sec\n" +
                "-- INTERVAL SECOND\n" +
                ", f1 + INTERVAL '3' SECOND as result_interval_second\n" +
                "-- INTERVAL SECOND(p2)\n" +
                ", f1 + INTERVAL '300' SECOND(3) as result_interval_second_p2\n" +
                "FROM (SELECT TO_TIMESTAMP_LTZ(1640966476500, 3) as f1)";

        tEnv.executeSql(sinkTableSql);
        tEnv.executeSql(insertSql);
    }
}
