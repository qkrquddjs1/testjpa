package com.ohgiraffers.test.user.controller;

import com.ohgiraffers.test.common.Pagenation;
import com.ohgiraffers.test.common.PagingButton;
import com.ohgiraffers.test.user.Service.UserService;
import com.ohgiraffers.test.user.dto.UserDTO;
import com.ohgiraffers.test.user.entity.User;
import com.ohgiraffers.test.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")  // HTML 경로 user
@RequiredArgsConstructor
public class UserController {

    private final UserService userService; // 의존성 주입
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/{userNo}")
    public String findMenuByCode(@PathVariable int userNo, Model model) {
        UserDTO resultUser = userService.findMenuByMenuCode(userNo);

        model.addAttribute("user", resultUser);

        return "user/detail";
    }

    @GetMapping("/list") // 페이징처리 @  @PageableDefault Pageable pageable
    public String listPage(Model model, @PageableDefault Pageable pageable) {

//        List<UserDTO> userList = userService.findUserList();
//
//        model.addAttribute("userList", userList);

        log.info("pageable: {}" , pageable);

        Page<UserDTO> userList = userService.findUserList(pageable);
        log.info("{}", userList.getContent());
        log.info("{}", userList.getTotalPages());
        log.info("{}", userList.getTotalElements());
        log.info("{}", userList.getSize());
        log.info("{}", userList.getNumberOfElements());
        log.info("{}", userList.isFirst());
        log.info("{}", userList.isLast());
        log.info("{}", userList.getSort());
        log.info("{}", userList.getNumber());

        PagingButton paging = Pagenation.getPagingButtonInfo(userList);

        model.addAttribute("userList", userList);
        model.addAttribute("paging", paging);

        return "user/list";
    }
    @GetMapping("/gender")
    @ResponseBody
    public List<String> findUsersWithNonNullGender() {

        return userService.findUsersWithNonNullGender();
    }

    @GetMapping("/regist")
    public void registPage() {}

    @PostMapping("/regist")
    public String registNewUser(@ModelAttribute UserDTO userDTO)  {
        userService.registUser(userDTO);

        return "redirect:/user/list";
    }
    @GetMapping("/modify")
    public void modifyPage() {}


    @PostMapping("/modify")
    public String modifyUser(@ModelAttribute UserDTO userDTO) {
        userService.modifyUser(userDTO);
        return "redirect:/user/list";
    }

    @GetMapping("/delete")
    public void deletePage() {}

    @PostMapping("/delete")
    public String deleteUser (@RequestParam Integer userNo) {
        userService.deleteUser(userNo);

        return "redirect:/user/list";
    }
    @GetMapping("/querymethod")
    public void querymethodPage() {}

    @GetMapping("/search")
    public String findByUserGender(@RequestParam String gender, Model model) {
        List<UserDTO> userList = userService.findByUserGender(gender);

        model.addAttribute("userList", userList);

        return "user/searchResult";
    }

}
