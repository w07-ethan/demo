package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.converter.UserConverter;
import com.example.demo.dto.request.user.CreateUserRequest;
import com.example.demo.dto.request.user.UserPageRequest;
import com.example.demo.dto.response.PageVo;
import com.example.demo.dto.response.UserVo;
import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserPo;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ethan
 * @since 2025-10-23
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPo> implements IUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    public PageVo<UserVo> getUsers(UserPageRequest request) {
        // Build query wrapper
        QueryWrapper<UserPo> queryWrapper = new QueryWrapper<>();

        // Apply search
        if (StringUtils.hasText(request.getQ())) {
            queryWrapper.and(w -> w
                    .like("email", request.getQ())
            );
        }

        // Apply sorting
        boolean isAsc = "asc".equalsIgnoreCase(request.getSortDirection());
        queryWrapper.orderBy(true, isAsc, request.getSortBy());

        // Execute pagination query
        Page<UserPo> page = new Page<>(request.getPage() + 1, request.getSize());
        Page<UserPo> userPage = baseMapper.selectPage(page, queryWrapper);

        // Convert to VO
        List<UserVo> userVos = userPage.getRecords().stream()
                .map(userConverter::toUserVo)
                .toList();

        return PageVo.<UserVo>builder()
                .content(userVos)
                .page(request.getPage())
                .size(request.getSize())
                .totalElements(userPage.getTotal())
                .totalPages(userPage.getPages())
                .first(userPage.getCurrent() == 1)
                .last(userPage.getCurrent() == userPage.getPages())
                .build();
    }


    @Override
    public UserVo createUser(CreateUserRequest request) {
        // Check email exists
        if (baseMapper.findByEmail(request.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        // Check username exists
        if (baseMapper.findByUserName(request.username()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        UserPo userPO = new UserPo()
                .setEmail(request.email())
                .setUsername(request.username())
                .setFirstName(request.firstName())
                .setPasswordHash(encodedPassword)
                .setLastName(request.lastName());
        baseMapper.insert(userPO);

        return userConverter.toUserVo(userPO);
    }
}
