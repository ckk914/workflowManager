package com.workflowManager.controller.schedule;

import com.workflowManager.service.schedule.ColorIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/colors")
public class ColorIndexController {

    @Autowired
    private ColorIndexService colorIndexService;

    @GetMapping("/colorList")
//    public String getAllColorIndices(Model model) {
//        ColorIndexDto colorIndices = colorIndexService.getAllColorIndices();
//        model.addAttribute("colorIndices", colorIndices);
//        return "schedule/colorIndex/colorList";
//    }
//
//    @GetMapping("/{id}")
//    public String getColorIndexById(@PathVariable int id, Model model) {
//        ColorIndexDto colorIndex = colorIndexService.getColorIndexById(id);
//        model.addAttribute("colorIndex", colorIndex);
//        return "schedule/colorIndex/colorDetail";
//    }

//    @PostMapping
//    public String insertColorIndex(ColorIndexDto colorIndex) {
//        colorIndexService.insertColorIndex(colorIndex);
//        return "redirect:/colors";
//    }
//
//    @PutMapping("/{id}")
//    public String updateColorIndex(@PathVariable int id, ColorIndexDto colorIndex) {
//        colorIndex.setColor_index_id(id);
//        colorIndexService.updateColorIndex(colorIndex);
//        return "redirect:/colors";
//    }

    @DeleteMapping("/{id}")
    public String deleteColorIndex(@PathVariable int id) {
        colorIndexService.deleteColorIndex(id);
        return "redirect:/colors";
    }

}
