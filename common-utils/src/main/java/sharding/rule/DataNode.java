package sharding.rule;

import com.google.common.base.Splitter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by zhaoheng on 18/10/17.
 * 分片数据单元节点
 * 数据库名称,表名称
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class DataNode {


    private static final String DELIMITER = ".";

    private final String dataSourceName;

    private final String tableName;

    /**
     * 根据格式构造数据节点
     *
     * @param dataNode string of data node. use {@code .} to split data source name and table name.
     */
    public DataNode(final String dataNode) {
        List<String> segments = Splitter.on(DELIMITER).splitToList(dataNode);
        dataSourceName = segments.get(0);
        tableName = segments.get(1);
    }

    /**
     * 数据节点字符串的格式是否有效
     *
     * @param dataNodeStr string to be adjusted
     * @return format of data node string is valid or not
     */
    public static boolean isValidDataNode(final String dataNodeStr) {
        return dataNodeStr.contains(DELIMITER) && 2 == Splitter.on(DELIMITER).splitToList(dataNodeStr).size();
    }
}
