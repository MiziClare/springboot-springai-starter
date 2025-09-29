package com.itheima.ai.tools;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.itheima.ai.entity.po.Course;
import com.itheima.ai.entity.po.CourseReservation;
import com.itheima.ai.entity.po.School;
import com.itheima.ai.entity.query.CourseQuery;
import com.itheima.ai.service.ICourseReservationService;
import com.itheima.ai.service.ICourseService;
import com.itheima.ai.service.ISchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CourseTools {

    // 准备使用 Mybatis Plus 提供的 ServiceImpl 来操作数据库
    private final ICourseService courseService;
    private final ISchoolService schoolService;
    private final ICourseReservationService courseReservationService;

    @Tool(description = "根据条件查询课程信息")
    public List<Course> queryCourses(@ToolParam(description = "查询的条件", required = false) CourseQuery query) {

        // 如果查询条件为null，直接返回所有课程。list() 方法是 Mybatis Plus 提供的，用于获取所有记录。
        if (query == null) {
            return courseService.list();
        }

        // 使用 Mybatis Plus 的链式查询构建器 QueryChainWrapper 来构建查询条件；courseService 是获取课程服务的查询构建器。
        QueryChainWrapper<Course> wrapper = courseService.query();
        wrapper
                // 如果查询条件中的type不为空，就添加类似 type = '编程' 这样的条件
                .eq(query.getType() != null, "type", query.getType()) // e.g., type = '编程'
                // 如果查询条件中的edu不为空，就添加类似 edu <= 2 这样的条件
                .le(query.getEdu() != null, "edu", query.getEdu()); // e.g., edu <= 2

        // 检查排序条件是否为null防止抛出空指针异常，且检查排序条件列表有元素
        if (query.getSorts() != null && !query.getSorts().isEmpty()) {
            // 遍历排序条件列表。query.getSorts() 返回一个包含排序规则的列表，每个规则由 CourseQuery.Sort 对象表示
            for (CourseQuery.Sort sort : query.getSorts()) {
                // 只允许按价格(price)或时长(duration)字段排序，如果是则添加排序条件
                if ("price".equals(sort.getField()) || "duration".equals(sort.getField())) {
                    // 三元运算符，如果排序方向不为空就用指定的，否则默认升序(true)
                    wrapper.orderBy(true, sort.getAsc() != null ? sort.getAsc() : true, sort.getField());
                }
            }
        }
        // 最后调用 list() 方法执行查询并返回结果列表
        return wrapper.list();
    }

    @Tool(description = "查询所有的校区信息")
    public List<School> querySchools() {
        return schoolService.list();
    }

    @Tool(description = "生成课程预约单,并返回生成的预约单号")
    public Integer createCourseReservation(@ToolParam(description = "想预定的课程") String course,
                                           @ToolParam(description = "学生姓名") String studentName,
                                           @ToolParam(description = "联系方式") String contactInfo,
                                           @ToolParam(description = "预约校区") String school,
                                           @ToolParam(description = "备注", required = false) String remark) {

        CourseReservation courseReservation = new CourseReservation();

        courseReservation.setCourse(course);
        courseReservation.setStudentName(studentName);
        courseReservation.setContactInfo(contactInfo);
        courseReservation.setSchool(school);
        courseReservation.setRemark(remark);

        courseReservationService.save(courseReservation);

        Integer id = courseReservation.getId();
        if (id == null) {
            throw new RuntimeException("预约单创建失败");
        }
        return id;
    }
}
