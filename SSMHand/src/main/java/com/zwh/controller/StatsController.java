package com.zwh.controller;

import com.github.pagehelper.util.StringUtil;
import com.zwh.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/stats")
public class StatsController {

    @Autowired
    private CheckinService checkinService;
    /**
     * 跳转到统计图表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/stats",method = RequestMethod.GET)
    public ModelAndView toStats (ModelAndView model) {
        model.setViewName("stats/stats");
        return model;
    }

    /**
     * 得到按日或按月统计
     * @param type
     * @return
     */
    @RequestMapping(value = "/get_stats",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getStats (String type) {
        Map<String,Object> result = new HashMap<>(0);
        if(StringUtil.isEmpty(type)) {
            result.put("type","error");
            result.put("msg","请选择类型统计！");
            return result;
        }
        //对类型进行分情况(按月或按日)
        switch (type) {
            case "day":{
                List<Map> statsByDay = checkinService.getStatsByDay();
                result.put("type","success");
                result.put("content",getStatsValue(statsByDay));
                return result;
            }
            case "month":{
                List<Map> statsByMonth = checkinService.getStatsByMonth();
                result.put("type","success");
                result.put("content",getStatsValue(statsByMonth));
                return result;
            }
            default:{
                result.put("type","error");
                result.put("msg","请选择正确的类型统计！");
                return result;
            }
        }
    }

    /**
     *
     * 对查询出的日期和营业额进行处理
     * @param statsValue
     * @return
     */

    public Map<String,Object> getStatsValue(List<Map> statsValue) {
        Map<String,Object> result = new HashMap<>(0);
        //获取key-->月份
        List<String> keyList = new ArrayList<>(0);
        //获取value-->营业额
        List<Float> valueList = new ArrayList<>(0);
        //对传入的数据进行遍历
        for(Map m : statsValue) {
            //从查询出的数据获取日期
            keyList.add(m.get("stat_date").toString());
            //获取营业额
            valueList.add(Float.valueOf(m.get("money").toString()));
        }
        //把查询出的数据存入Map集合
        result.put("stat_date",keyList);
        result.put("money",valueList);
        return result;

    }

}
