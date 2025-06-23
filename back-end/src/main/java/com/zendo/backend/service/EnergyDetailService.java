package com.zendo.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import com.zendo.backend.entity.EnergyDetail;

public interface EnergyDetailService {

    List<EnergyDetail> updateLast24HoursData(OffsetDateTime collectingAt);

    List<EnergyDetail> getLast24HoursData(OffsetDateTime now);

    List<EnergyDetail> getAll();
}
