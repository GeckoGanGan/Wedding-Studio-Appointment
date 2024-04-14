package com.wedding.domain.vo;

import com.wedding.domain.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/31/15:37
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioOnlineUserVo {
    private String studioId;
    private List<Users> onlineUsers;

}
