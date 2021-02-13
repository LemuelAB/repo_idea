package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    /**
     * 查询课程信息&条件查询 接口
     * */
    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByConditioin(@RequestBody CourseVO courseVO)
    {
        List<Course> courseList =
                courseService.findCourseByCondition(courseVO);
        ResponseResult result = new ResponseResult(true,200,"响应成 功",courseList);
        return result;
    }

    /**
     * 图片上传接口
     * */
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file,
                                     HttpServletRequest request){
        try {
            //1.判断文件是否为空
            if(file.isEmpty()){
                throw new RuntimeException();
            }
            //2.获取项目部署路径
            // D:\apache-tomcat-8.5.56\webapps\ssm_web\
            String realPath = request.getServletContext().getRealPath("/");
            // D:\apache-tomcat-8.5.56\webapps\
            String webappsPath =
                    realPath.substring(0,realPath.indexOf("ssm-web"));
            //3.获取原文件名
            String fileName = file.getOriginalFilename();
            //4.新文件名
            String newFileName = System.currentTimeMillis() +
                    fileName.substring(fileName.lastIndexOf("."));
            //5.上传文件
            String uploadPath = webappsPath+"upload\\";
            File filePath = new File(uploadPath,newFileName);
            //如果目录不存在就创建目录
            if(!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
                System.out.println("创建目录: " + filePath);
            }
            file.transferTo(filePath);
            //6.将文件名和文件路径返回
            Map<String,String> map = new HashMap<>();
            map.put("fileName",newFileName);
            map.put("filePath","http://localhost:8080/upload/"+newFileName);
            ResponseResult result = new ResponseResult(true,200,"响应成功",map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 保存&修改课程信息接口
     * */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO){
        try {
            if(courseVO.getId() == null){
                courseService.saveCourseOrTeacher(courseVO);
                ResponseResult responseResult = new ResponseResult(true,200,"新增成功",null);
                return responseResult;
            }else{
                courseService.updateCourseOrTeacher(courseVO);
                ResponseResult responseResult = new ResponseResult(true,200,"修改成功",null);
                return responseResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 回显章节对应的课程信息
     * */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(@RequestParam Integer id){
        try {
            //调用service
            CourseVO courseVo = courseService.findCourseById(id);
            ResponseResult responseResult = new ResponseResult(true,200,"根据ID查询课程信息成功",courseVo);
            return responseResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改课程状态
     * */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(@RequestParam int id,@RequestParam int status){
        try {
            //执行修改操作
            courseService.updateCourseStatus(id, status);
            //保存修改后的状态,并返回
            Map<String,Integer> map = new HashMap<>();
            map.put("status",status);
            ResponseResult result = new ResponseResult(true,200,"课程状态变更成功",map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
