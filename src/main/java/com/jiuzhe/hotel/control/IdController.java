package com.jiuzhe.hotel.control;


import com.jiuzhe.hotel.constants.RtCodeConstant;
import com.jiuzhe.hotel.service.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class IdController {
    private static final Logger logger = LoggerFactory.getLogger(IdController.class);

    @Autowired
    IdService idService;

    @GetMapping("/generateId")
    public Map deposit() {
        try {
            return RtCodeConstant.getResult("0", idService.uid());
        } catch (Exception e) {
            return RtCodeConstant.getResult("-1");
        }
    }
}
