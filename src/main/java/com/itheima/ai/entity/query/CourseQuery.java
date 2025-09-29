package com.itheima.ai.entity.query;

import org.springframework.ai.tool.annotation.ToolParam;
import lombok.Data;
import java.util.List;

@Data
public class CourseQuery {

    @ToolParam(required = false, description = "课程类型：编程、设计、自媒体、其它")
    private String type;
    @ToolParam(required = false, description = "学历要求：0-无、1-初中、2-高中、3-大专、4-本科及本科以上")
    private Integer edu;
    @ToolParam(required = false, description = "排序方式")
    private List<Sort> sorts;

    @Data
    // 内部静态类，表示排序条件
    public static class Sort {
        @ToolParam(required = false, description = "排序字段: price 或 duration")
        private String field;
        @ToolParam(required = false, description = "是否是升序: true/false")
        private Boolean asc;
    }
}
