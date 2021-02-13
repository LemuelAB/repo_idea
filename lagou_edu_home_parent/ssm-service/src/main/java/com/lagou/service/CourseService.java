package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;

import java.util.List;

public interface CourseService {
    /**
     * 多条件查询课程列表
     * */
    public List<Course> findCourseByCondition(CourseVO courseVO);

    /**
     * 保存课程信息
     * */
    public void saveCourseOrTeacher(CourseVO courseVO);
    /**
     * 根据id查询课程信息
     * */
    public CourseVO findCourseById(Integer id);

    /**
     * 修改课程信息
     * */
    public void updateCourseOrTeacher(CourseVO courseVO);

    /**
     * 修改课程状态
     * */
    public void updateCourseStatus(int id,int status);
}
