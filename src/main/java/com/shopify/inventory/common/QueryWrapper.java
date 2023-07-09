package com.shopify.inventory.common;

import lombok.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sing-fung
 * @since 1/10/2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QueryWrapper
{
    /**
     * size of each page
     */
    private Integer limit;
    /**
     * current page number
     */
    private Integer offset;
    /**
     * sort wrapper
     */
    private Map<String, String> sortWrapper;
    /**
     * equal wrapper
     */
    private Map<String, Object> eqWrapper;
    /**
     * not equal wrapper
     */
    private Map<String, Object> neqWrapper;
    /**
     * in wrapper
     */
    private Map<String, List<?>> inWrapper;
    /**
     * not in wrapper
     */
    private Map<String, List<?>> ninWrapper;
    /**
     * key wrapper
     */
    private Map<String, List<String>> keyWrapper;
    /**
     * set wrapper
     */
    private Map<String, Object> setWrapper;

    public static QueryWrapperBuilder newBuilder() {
        return new QueryWrapperBuilder();
    }

    /**
     * QueryWrapperBuilder
     */
    public static class QueryWrapperBuilder
    {
        private Integer limit;
        private Integer offset;
        private final Map<String, String> sortWrapper;
        private final Map<String, Object> eqWrapper;
        private final Map<String, Object> neqWrapper;
        private final Map<String, List<?>> inWrapper;
        private final Map<String, List<?>> ninWrapper;
        private final Map<String, List<String>> keyWrapper;
        private final Map<String, Object> setWrapper;

        public QueryWrapperBuilder()
        {
            sortWrapper = new LinkedHashMap<>();
            eqWrapper = new HashMap<>();
            neqWrapper = new HashMap<>();
            inWrapper = new HashMap<>();
            ninWrapper = new HashMap<>();
            keyWrapper = new HashMap<>();
            setWrapper = new HashMap<>();
        }

        public synchronized QueryWrapperBuilder eq(Boolean condition, String column, Object value)
        {
            if (condition)
            { eqWrapper.put(column, value); }

            return this;
        }

        public synchronized QueryWrapperBuilder eq(String column, Object value)
        {
            return eq(true, column, value);
        }

        public synchronized QueryWrapperBuilder neq(Boolean condition, String column, Object value)
        {
            if (condition)
            { neqWrapper.put(column, value); }

            return this;
        }

        public synchronized QueryWrapperBuilder neq(String column, Object value)
        {
            return neq(true, column, value);
        }

        public synchronized QueryWrapperBuilder in(Boolean condition, String column, List<?> value)
        {
            if (condition)
            { inWrapper.put(column, value); }

            return this;
        }

        public synchronized QueryWrapperBuilder in(String column, List<?> value)
        {
            return in(true, column, value);
        }

        public synchronized QueryWrapperBuilder notIn(Boolean condition, String column, List<?> value)
        {
            if (condition)
            { ninWrapper.put(column, value); }

            return this;
        }

        public synchronized QueryWrapperBuilder notIn(String column, List<?> value)
        {
            return notIn(true, column, value);
        }

        public synchronized QueryWrapperBuilder like(Boolean condition, String key, List<String> columns)
        {
            if (condition)
            { keyWrapper.put(key, columns); }

            return this;
        }

        public synchronized QueryWrapperBuilder like(String key, List<String> columns)
        {
            return like(true, key, columns);
        }

        public synchronized QueryWrapperBuilder page(Boolean condition, Integer offset, Integer limit)
        {
            if (condition)
            {
                this.offset = offset;
                this.limit = limit;
            }

            return this;
        }

        public synchronized QueryWrapperBuilder page(Integer offset, Integer limit)
        {
            return page(true, offset, limit);
        }

        public synchronized QueryWrapperBuilder asc(String column)
        {
            return asc(true, column);
        }

        public synchronized QueryWrapperBuilder asc(Boolean condition, String column)
        {
            if (condition)
            { sortWrapper.put(column, "asc"); }

            return this;
        }

        public synchronized QueryWrapperBuilder desc(String column)
        {
            return desc(true, column);
        }

        public synchronized QueryWrapperBuilder desc(Boolean condition, String column)
        {
            if (condition)
            { sortWrapper.put(column, "desc"); }

            return this;
        }

        public synchronized QueryWrapperBuilder sort(String column, String order)
        {
            return sort(true, column, order);
        }

        public synchronized QueryWrapperBuilder sort(Boolean condition, String column, String order)
        {
            if (condition)
            { sortWrapper.put(column, order); }

            return this;
        }

        public synchronized QueryWrapperBuilder set(Boolean condition, String column, Object value)
        {
            if (condition)
            { setWrapper.put(column, value); }

            return this;
        }

        public synchronized QueryWrapperBuilder set(String column, Object value)
        {
            return set(true, column, value);
        }

        public QueryWrapper build()
        {
            return new QueryWrapper(limit, offset, sortWrapper, eqWrapper, neqWrapper, inWrapper, ninWrapper, keyWrapper, setWrapper);
        }
    }
}