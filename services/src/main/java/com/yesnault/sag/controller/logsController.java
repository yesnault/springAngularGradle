package com.yesnault.sag.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.yesnault.sag.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LogsController {

    org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogsController.class);

    class JsonLogger {
        public String name, level;

        JsonLogger(Logger logger) {
            this.name = logger.getName();
            this.level = logger.getEffectiveLevel().toString();
        }
    }

    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<JsonLogger> getList() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        List<JsonLogger> loggers = new ArrayList<>();
        for (Logger logger : context.getLoggerList()) {
            LOGGER.debug("Logger {} with level {}", logger.getName(), logger.getEffectiveLevel());
            loggers.add(new JsonLogger(logger));
        }
        return loggers;
    }

    @RequestMapping(value = "/logs/change/{loggerName}/{newLevel}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeLevel(@PathVariable String loggerName, @PathVariable String newLevel) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(loggerName).setLevel(Level.valueOf(newLevel));
    }

}