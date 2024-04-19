package com.ohgiraffers.test.user.Service;

import com.ohgiraffers.test.user.dto.UserDTO;
import com.ohgiraffers.test.user.entity.User;
import com.ohgiraffers.test.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드 생성자 생성
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;



    /* 1. findById*/
    public UserDTO findMenuByMenuCode(int userNo) {
        User foundUser = userRepository.findById(userNo).orElseThrow(IllegalAccessError::new);
        return modelMapper.map(foundUser, UserDTO.class);
    }

    /* 2. finaAll : All*/
    public List<UserDTO> findUserList() {
        List<User> userList = userRepository.findAll(Sort.by("userNo").descending());

        return userList.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    /* 3. findAll : pageable */
    public Page<UserDTO> findUserList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("userNo").descending()
        );

        Page<User> userList = userRepository.findAll(pageable);
        return userList.map(user -> modelMapper.map(user, UserDTO.class));
    }

    /* 4. Query Method */

    public List<UserDTO> findByUserGender(String gender) {
        List<User> userList = userRepository.findByGender(gender); // 성별을 문자열로 비교

        return userList.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    /* 5. JPQL or Native Query  등록 */



    public List<String> findUsersWithNonNullGender() {
        List<String> usersWithNonNullGender = userRepository.findDistinctUsersWithNonNullGender();
        return usersWithNonNullGender;
    }
    /* 6. save 등록 */
    @Transactional
    public void registUser(UserDTO userDTO) {
        userRepository.save(modelMapper.map(userDTO, User.class));
    }

    /* 7. modify 수정 */
    @Transactional
    public void modifyUser(UserDTO userDTO){
        User foundUser = userRepository.findById(userDTO.getUserNo()).orElseThrow(IllegalAccessError::new);

        foundUser.modifyUserName(userDTO.getUserName());
    }
    /* delete 삭제 */
    @Transactional
    public  void deleteUser(Integer userNo) {
        userRepository.deleteById(userNo);
    }













}
