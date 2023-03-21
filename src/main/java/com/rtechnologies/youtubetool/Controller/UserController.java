package com.rtechnologies.youtubetool.Controller;

import com.rtechnologies.youtubetool.Model.Channel;
import com.rtechnologies.youtubetool.Model.User;
import com.rtechnologies.youtubetool.Model.Video;
import com.rtechnologies.youtubetool.Model.VideoStats;
import com.rtechnologies.youtubetool.Service.UserService;
import com.rtechnologies.youtubetool.Service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private YoutubeService youTubeService;

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        try {
            String channelId = user.getYoutubeChannelLink().substring(
                        user.getYoutubeChannelLink().lastIndexOf("/") + 1);
            Channel tempChannel = getChannelDetails(channelId);

            if(!tempChannel.getChannelName().isEmpty()){
                user.setYoutubeChannelLink(channelId);
                User registeredUser = userService.registerUser(user);
                return registeredUser;
            }
            return  new User();
        } catch (Exception e) {
            return new User();
        }
    }

    @GetMapping("/login/{email}/{password}")
    public User loginUser(@PathVariable String email, @PathVariable String password) {
        try {
            User user = userService.loginUser(email, password);
            return user;
        } catch (Exception e) {
            System.out.println(e.toString());
            return new User();
        }
    }

    @GetMapping("/get-users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/channels/videos/{channelId}")
    public List<Video> getChannelVideos(@PathVariable String channelId) {
        try {
            return youTubeService.getChannelVideos(channelId, 10);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @GetMapping("/channels/channel-details/{channelId}")
    public Channel getChannelDetails(@PathVariable String channelId) {
        try {
            return youTubeService.getChannelDetails(channelId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Channel();
        }
    }

    @GetMapping("/channel/average-stats")
    public VideoStats getChannelStats(@RequestBody List<Video> videos) {
        try {
            return youTubeService.averageStats(videos);
        } catch (Exception e) {
            e.printStackTrace();
            return new VideoStats();
        }
    }
}

