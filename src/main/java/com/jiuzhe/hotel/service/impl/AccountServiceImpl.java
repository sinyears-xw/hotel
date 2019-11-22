package com.jiuzhe.hotel.service.impl;

import com.jiuzhe.hotel.constants.RtCodeConstant;
import com.jiuzhe.hotel.service.AccountService;
import com.jiuzhe.hotel.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private StringRedisTemplate rt;

	private  Log logger = LogFactory.getLog(this.getClass());

	private boolean checkAccount(String id) {
		Map num = jdbcTemplate.queryForMap("select count(1) num from account where user_id = '" + id + "'");
		if (Integer.parseInt(num.get("num").toString()) > 0)
			return true;
		else
			return false;
	}

	private boolean checkChannel(String c) {
		String[] channels = new String[]{"alipay","wx","wx_pub","wx_lite","bank_account"};
		for (int i = 0; i < channels.length; i++) {
			if (c.equals(channels[i]))
				return true;
		}
		return false;
	}

	public Map checkPasswd(Map param) {
		String userId = param.get("user_id").toString();
		if (StringUtil.isEmpty(userId))
			return RtCodeConstant.getResult("40001");

		Map accountPasswd = null;
		try {
			accountPasswd = jdbcTemplate.queryForMap("select passwd from account where user_id = '" + userId + "' for update");
		} catch (EmptyResultDataAccessException e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}

		String oldPasswd = param.get("old_passwd").toString();
		if (StringUtil.isEmpty(oldPasswd))
			return RtCodeConstant.getResult("40001");

		String storedPasswd = accountPasswd.get("passwd").toString();
		if (!storedPasswd.equals(oldPasswd)) {
			return RtCodeConstant.getResult("10007");
		}

		return RtCodeConstant.getResult("0");
	}

	@Transactional
	public Map delSettleAccount(Map param) {
		String userId = param.get("user_id").toString();
		if (StringUtil.isEmpty(userId))
			return RtCodeConstant.getResult("40001");

		jdbcTemplate.update(String.format("UPDATE account SET aliAccount = null, aliName = null where user_id='%s' ",userId));

		return RtCodeConstant.getResult("0");
	}		

	@Transactional
	public Map saveSettleAccount(Map param) {
		String userId = param.get("user_id").toString();
		if (StringUtil.isEmpty(userId))
			return RtCodeConstant.getResult("40001");
		if (!checkAccount(userId))
			return RtCodeConstant.getResult("30006");

		String channel = param.get("channel").toString();
		if (StringUtil.isEmpty(channel) || !checkChannel(channel))
			return RtCodeConstant.getResult("40001");

		String recipient_account = param.get("recipient_account").toString();
		if (StringUtil.isEmpty(recipient_account))
			return RtCodeConstant.getResult("40001");

		String recipient_name = param.get("recipient_name").toString();
		if (StringUtil.isEmpty(recipient_name))
			return RtCodeConstant.getResult("40001");

		switch (channel) {
			case "alipay":
				jdbcTemplate.update(String.format("update account set aliAccount='%s',aliName='%s' where user_id='%s'", recipient_account, recipient_name, userId, channel));
				break;
		}
		return RtCodeConstant.getResult("0");
	}		


	@Transactional
	public Map updatePasswd(Map param) {
		String userId = param.get("user_id").toString();
		if (StringUtil.isEmpty(userId))
			return RtCodeConstant.getResult("40001");

		Map accountPasswd = null;
		try {
			accountPasswd = jdbcTemplate.queryForMap("select passwd from account where user_id = '" + userId + "' for update");
		} catch (EmptyResultDataAccessException e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}

		String oldPasswd = param.get("old_passwd").toString();
		if (StringUtil.isEmpty(oldPasswd))
			return RtCodeConstant.getResult("40001");

		String newPasswd = param.get("new_passwd").toString();
		if (StringUtil.isEmpty(newPasswd))
			return RtCodeConstant.getResult("40001");

		String storedPasswd = accountPasswd.get("passwd").toString();
		if (storedPasswd.equals(oldPasswd)) {
			jdbcTemplate.update(String.format("update account set passwd = '%s', updt = now() where user_id = '%s'",newPasswd,userId));
			return RtCodeConstant.getResult("0");
		}
		
		return RtCodeConstant.getResult("-1");
	}

	public Map getAccountInfo(String id) {
		try {
			Map account = jdbcTemplate.queryForMap("select type,total_balance,disable,available_balance from account where user_id = '" + id + "'");
			return RtCodeConstant.getResult("0", account);
		} catch (EmptyResultDataAccessException e) {
			return RtCodeConstant.getResult("-1");
		} 
	}


	public Map getSettleAccount(String id) {
		String sql = String.format("select aliAccount, aliName from account where user_id = '%s' ",id);
		List<Map<String, Object>> products = jdbcTemplate.queryForList(sql);
		return RtCodeConstant.getResult("0", products);
	}
}

