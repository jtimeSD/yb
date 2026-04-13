package com.bjpowernode.finance.service.impl;

import com.bjpowernode.finance.entity.Admin;
import com.bjpowernode.finance.mapper.AdminMapper;
import com.bjpowernode.finance.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired(required = false)
    AdminMapper adminMapper;

    @Override
    public Admin selectAdminByTerms(String username, String password) {
        // 只根据用户名查询，避免使用有问题的 selectByExample
        if (username == null) {
            return null;
        }
        Admin admin = adminMapper.selectByUsername(username);
        if (admin == null) {
            return null;
        }
        // 如果提供了密码，则校验密码（注意密码已加密，直接比较密文）
        if (password != null && !password.equals(admin.getPassword())) {
            return null;
        }
        return admin;
    }

    @Override
    @Transactional
    public Integer updateAdmin(Admin admin) {
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public Admin selectAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}