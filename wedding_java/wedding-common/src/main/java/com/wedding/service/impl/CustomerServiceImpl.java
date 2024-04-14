package com.wedding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.entity.Customer;
import com.wedding.mapper.CustomerMapper;
import com.wedding.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * 客服表(Customer)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-31 13:17:33
 */
@Service("customerService")
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}

