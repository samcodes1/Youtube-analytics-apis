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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private YoutubeService youTubeService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            String channelId = user.getYoutubeChannelLink().substring(
                    user.getYoutubeChannelLink().lastIndexOf("/") + 1);
            Channel tempChannel = getChannelDetails(channelId);

            if (!tempChannel.getChannelName().isEmpty()) {
                user.setYoutubeChannelLink(channelId);

                // Hash password before storing in the database
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                user.setPassword(hashedPassword);

                User registeredUser = userService.registerUser(user);

                return ResponseEntity.ok().body(registeredUser);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("email") String email,
                                       @RequestParam("password") String password) {
        try {
            User user = userService.loginUser(email, password);
            if (user != null) {
                // Compare hashed passwords
                if (BCrypt.checkpw(password, user.getPassword())) {
                    return ResponseEntity.ok().body(user);
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e) {
            // unexpected error occurred, log the error and return HTTP 500 status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
        }
    }

//    @GetMapping("/get-users")
//    public List<User> getUsers(){
//        return userService.getUsers();
//    }

    @GetMapping("/channels/videos/{channelId}")
    public List<Video> getChannelVideos(@PathVariable String channelId) {
        try {
            return youTubeService.getChannelVideos(channelId, 20);
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

    @PostMapping("/channel/average-stats")
    public VideoStats getChannelStats(@RequestBody List<Video> videos) {
        try {
            return youTubeService.averageStats(videos);
        } catch (Exception e) {
            e.printStackTrace();
            return new VideoStats();
        }
    }

    //New apis
    @GetMapping("/channel/video/most-viewed/{query}")
    public ResponseEntity<Video> getMostViewedVideo(@PathVariable String query) {
        System.out.println(query);
        try {
            Video video = youTubeService.getMostViewedVideo(query);
            if (video != null) {
                return ResponseEntity.ok(video);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/channel/video/search-results/{query}")
    public ResponseEntity<List<Video>> getVideoListWithKeyword(@PathVariable String query) {
        try {
            List<Video> videos = youTubeService.videoSearchResults(query);
            if (videos != null && !videos.isEmpty()) {
                return ResponseEntity.ok(videos);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            // log the exception instead of printing the stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

