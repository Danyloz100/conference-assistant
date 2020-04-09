package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import com.advcourse.conferenceassistant.service.impl.QuestionServiceImpl;
import com.advcourse.conferenceassistant.service.impl.VisitorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Controller
public class ConferenceController {

    @Autowired
    private VisitorServiceImpl visitorService;
    @Autowired
    private ConferenceServiceImpl conferenceService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private QuestionServiceImpl questionService;

    @GetMapping("/liveconference")
    public void getError() {
        throw new NoSuchConferenceException();
    }

    @GetMapping("/liveconference/now/{confId}")
    public String getLive(
            @CookieValue(value = "testCookie", defaultValue = "defaultCookieValue")
                    String cookieValue, @PathVariable Long confId,
            Model model) {
        log.info("Welcome this is redirect to live conference page with id " + confId);
//        TODO: currently hardcoded, add time check for the current topic which is going live right now
        TopicDto currentTopic = topicService.findById(22);
        log.info("Active topic id: " + currentTopic.getId());
//        !! this topic id is for debugging only, use conferenceId 2;
        log.info("Before getting list of questions");
        VisitorDto visitorDto = visitorService.findByEmailAndVisit(cookieValue, confId);
        log.info("visitorDto: " + visitorDto);
        List<QuestionDto> questions = questionService.getQuestionsByTopicId(currentTopic.getId(), visitorDto.getEmail());
        log.info("Got the list of questions: " + questions);
        model.addAttribute("visitor", visitorService.findByEmailAndVisit(cookieValue, confId));
        model.addAttribute("topic", currentTopic);
        model.addAttribute("questions", questions);
        model.addAttribute("topquestions", questionService.getTopQuestionsByTopicId(questions));
        model.addAttribute("conference", conferenceService.findById(confId));
        return "topicquestions";
    }


    /**
     * delete email from cookie
     * and return to page "/"
     */
    @GetMapping("/logout/{confId}")
    String signOut(@PathVariable Long confId,
                   HttpServletResponse response) {


        Cookie newCookie = new Cookie("testCookie", "no_cookie");
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);

        return "forward:/liveconference/" + confId;
    }

}

