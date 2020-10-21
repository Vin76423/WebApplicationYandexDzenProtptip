package by.tms.controller;

import by.tms.dto.UserAuthDto;
import by.tms.entity.User;
import by.tms.service.post.PostService;
import by.tms.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(path ="/home")
public class HomeController {

    private UserService userService;
    private PostService postService;

    public HomeController(UserService userServiceImpl, PostService postServiceImpl) {
        this.userService = userServiceImpl;
        this.postService = postServiceImpl;
    }

    @GetMapping
    public ModelAndView getHomePage(ModelAndView modelAndView) {
        modelAndView.addObject("posts", postService.getDemoVersionAllPosts());
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping(path = "/reg")
    public ModelAndView getRegForm(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("reg");
        return modelAndView;
    }

    @PostMapping(path = "/reg")
    public ModelAndView getRegistration(@Valid @ModelAttribute("user") User user,
                                        BindingResult bindingResult,
                                        ModelAndView modelAndView) {
        if (!bindingResult.hasErrors()) {
            userService.createUser(user);
            modelAndView.setViewName("redirect:/home");
        }else {
            modelAndView.setViewName("reg");
        }
        return modelAndView;
    }

    @GetMapping(path = "/auth")
    public ModelAndView getAuthForm(ModelAndView modelAndView) {
        modelAndView.addObject("userAuthDto", new UserAuthDto());
        modelAndView.setViewName("auth");
        return modelAndView;
    }

    @PostMapping(path = "/auth")
    public ModelAndView getAuthorisation(@Valid @ModelAttribute("userAuthDto") UserAuthDto userAuthDto,
                                         BindingResult bindingResult,
                                         ModelAndView modelAndView,
                                         HttpSession session) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("auth");
            return modelAndView;
        }
        User user = userService.getUserByLogin(userAuthDto.getLogin());
        if (user.getPassword().equals(userAuthDto.getPassword())) {
            session.setAttribute("user", user);
            modelAndView.setViewName("redirect:/home");
            System.out.println(session.getId());
        }else {
            modelAndView.addObject("massage", "User with such password does not exist!");
            modelAndView.setViewName("auth");
        }
        return modelAndView;
    }

    @GetMapping(path = "/log-out")
    public String brakeSession(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/home";
    }
}
