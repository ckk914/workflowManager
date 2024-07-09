package com.workflowManager.service.schedule;

import com.workflowManager.entity.schedule.ColorIndex;
import com.workflowManager.mapper.scheduleMapper.ColorIndexMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorIndexService {
    private final ColorIndexMapper colorIndexMapper;

    public List<ColorIndex> getAllColorIndexes() {
        return colorIndexMapper.getAllColorIndexes();
    }
//
//    public ColorIndexDto getColorIndexById(int color_index_id) {
//        return colorIndexMapper.getColorIndexById(color_index_id);
//    }
//
//    public void insertColorIndex(ColorIndexDto colorIndex) {
//        colorIndexMapper.insertColorIndex(colorIndex);
//    }
//
//    public void updateColorIndex(ColorIndexDto colorIndex) {
//        colorIndexMapper.updateColorIndex(colorIndex);
//    }

    public void deleteColorIndex(int color_index_id) {
        colorIndexMapper.deleteColorIndex(color_index_id);
    }
}
