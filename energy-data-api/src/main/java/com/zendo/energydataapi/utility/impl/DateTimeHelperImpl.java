package com.zendo.energydataapi.utility.impl;

import java.time.LocalDateTime;

import com.zendo.energydataapi.utility.DateTimeHelper;
import org.springframework.stereotype.Component;

@Component
public class DateTimeHelperImpl implements DateTimeHelper {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
