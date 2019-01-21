package sharding.parsing.parser.context.table;

import com.google.common.base.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class Table {

    private final String name;

    /**
     * 别名
     */
    private final Optional<String> alias;

}
