package sharding.parsing.parser.sql;

public interface SQLParser {

    /**
     * Parse SQL.
     *
     * @return SQL statement
     */
    SQLStatement parse();
}
