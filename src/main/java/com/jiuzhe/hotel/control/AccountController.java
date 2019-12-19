package com.jiuzhe.hotel.control;

import com.jiuzhe.hotel.constants.RtCodeConstant;
import com.jiuzhe.hotel.service.AccountService;
import com.jiuzhe.hotel.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/faccount")
public class AccountController {
	private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
	AccountService accountService;

    @RequestMapping(value = "/delsettleaccount", method = RequestMethod.POST)
	@ResponseBody
	public Map delsettleaccount(@RequestBody Map param) {
		try {
			if (param == null)
				return RtCodeConstant.getResult("40001");

			return accountService.delSettleAccount(param);
		} catch (Exception e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}	
	}

	@RequestMapping(value = "/savesettleaccount", method = RequestMethod.POST)
	@ResponseBody
	public Map savesettleaccount(@RequestBody Map param) {
		try {
			if (param == null)
				return RtCodeConstant.getResult("40001");

			return accountService.saveSettleAccount(param);

		} catch (Exception e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}	
	}


	@RequestMapping(value = "/updatepasswd", method = RequestMethod.POST)
	@ResponseBody
	public Map updatePasswd(@RequestBody Map param) {
		try {
			if (param == null)
				return RtCodeConstant.getResult("40001");

			return accountService.updatePasswd(param);

		} catch (Exception e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}	
	}

	@RequestMapping(value = "/getbackpasswd", method = RequestMethod.POST)
	@ResponseBody
	public Map getbackPasswd(@RequestBody Map param) {
		try {
			if (param == null)
				return RtCodeConstant.getResult("40001");

			return accountService.getbackPasswd(param);

		} catch (Exception e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}
	}

	@RequestMapping(value = "/checkpasswd", method = RequestMethod.POST)
	@ResponseBody
	public Map checkpasswd(@RequestBody Map param) {
		try {
			if (param == null)
				return RtCodeConstant.getResult("40001");

			return accountService.checkPasswd(param);

		} catch (Exception e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}	
	}

	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map getAccountInfo(@PathVariable String id) {
		try {
			if (StringUtil.isEmpty(id))
				return RtCodeConstant.getResult("40001");
			
			return accountService.getAccountInfo(id);

		} catch (Exception e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}	
	}

	@RequestMapping(value = "/getsettleaccount/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map getsettleaccount(@PathVariable String id) {
		try {
			if (StringUtil.isEmpty(id))
				return RtCodeConstant.getResult("40001");
			return accountService.getSettleAccount(id);

		} catch (Exception e) {
			logger.error(e);
			return RtCodeConstant.getResult("-1");
		}	
	}
}
