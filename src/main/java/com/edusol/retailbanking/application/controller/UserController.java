package com.edusol.retailbanking.application.controller;

import com.edusol.retailbanking.application.dto.UserDto;
import com.edusol.retailbanking.application.exception.UserServiceException;
import com.edusol.retailbanking.application.request.UserDetailsRequestModel;
import com.edusol.retailbanking.application.responce.ErrorMessages;
import com.edusol.retailbanking.application.responce.OperationStatusModel;
import com.edusol.retailbanking.application.responce.RequestOperationStatus;
import com.edusol.retailbanking.application.responce.UserRest;
import com.edusol.retailbanking.application.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(value = "/getUser/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})

    public UserRest getUser(@PathVariable String id)
    {
        UserRest returnValue=new UserRest();
        UserDto userDto=userService.getUserById(id);
        BeanUtils.copyProperties(userDto,returnValue);

        return returnValue  ;
    }



    @GetMapping(value = "/getLimitedUser",
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})

    public List<UserRest> getLimitedUser(@RequestParam(value = "page",defaultValue = "0") int page,
                                        @RequestParam(value = "limit",defaultValue = "25") int limit)
    {
       List<UserRest>  returnValue=new ArrayList<>();
      List<UserDto> users=userService.getLimitedUser(page,limit);
      for (UserDto userDto:users) {
          UserRest userModel=new UserRest();
          BeanUtils.copyProperties(userDto, userModel);
          returnValue.add(userModel);
      }

        return returnValue  ;
    }





    @PostMapping(value = "/createUser",
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception
    {
        UserRest returnValue=new UserRest();
       if(userDetails.getFirstName().isEmpty())
           throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto createdUser=userService.createdUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);
        return  returnValue;
    }

    @PutMapping(value = "/updateUserDetails/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails,@PathVariable String id)
    {
        UserRest returnValue=new UserRest();
        UserDto userDto=new UserDto()   ;
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto updatedUser=userService.updateUser(userDto,id);
        BeanUtils.copyProperties(updatedUser,returnValue);
        return  returnValue;
    }


    @DeleteMapping(path = "/deleteUser/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id)
    {
        OperationStatusModel statusModel=new OperationStatusModel();
        statusModel.setOperationName(RequestOperationName.DELETE.name() );
        userService.deleteUser(id);
        statusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return statusModel;
    }
}
