package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.converter.UserConverter;
import com.example.demo.dto.request.user.CreateUserRequestVo;
import com.example.demo.dto.request.user.UserPageRequestVo;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

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

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("created_at", "updated_at", "email");

    public PageVo<UserVo> getUsers(UserPageRequestVo request) {
        // Validate sort field
        if (!ALLOWED_SORT_FIELDS.contains(request.getSortBy())) {
            throw new IllegalArgumentException("Invalid sort field: " + request.getSortBy());
        }

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
    @Transactional
    public UserVo createUser(CreateUserRequestVo request) {
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
