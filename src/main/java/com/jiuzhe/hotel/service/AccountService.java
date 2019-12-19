package com.jiuzhe.hotel.service;

import java.util.Map;

public interface AccountService {
    public Map getSettleAccount(String id);

    public Map getAccountInfo(String id);

    public Map saveSettleAccount(Map param);

    public Map delSettleAccount(Map param);

    public Map updatePasswd(Map param);

    public Map getbackPasswd(Map param);

    public Map checkPasswd(Map param);


}

