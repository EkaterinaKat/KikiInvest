package org.katyshevtseva.invest.core.service;

import com.katyshevtseva.date.DateUtils;

import java.util.Date;

public interface Operation {

    String getComment();

    Date getDate();

    Float getAmount();

    String getFromString();

    String getToString();

    default String getDateString() {
        return DateUtils.READABLE_DATE_FORMAT.format(getDate());
    }
}
