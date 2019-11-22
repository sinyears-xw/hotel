package com.jiuzhe.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.jiuzhe.hotel.constants.CommonConstant;
import com.jiuzhe.hotel.constants.OrderStatusEnum;
import com.jiuzhe.hotel.constants.RoomStatusEnum;
import com.jiuzhe.hotel.dao.HotelOrderDao;
import com.jiuzhe.hotel.dao.SkuSearchQueryDao;
import com.jiuzhe.hotel.dto.OrderSuccessDto;
import com.jiuzhe.hotel.dto.SkuDetailDto;
import com.jiuzhe.hotel.dto.UserDto;
import com.jiuzhe.hotel.entity.*;
import com.jiuzhe.hotel.module.EvaluateQuery;
import com.jiuzhe.hotel.module.HotelOrderQuery;
import com.jiuzhe.hotel.module.OrderCancelQuery;
import com.jiuzhe.hotel.module.SkuDetailQuery;
import com.jiuzhe.hotel.service.HotelorderService;
import com.jiuzhe.hotel.service.RoomReservationService;
import com.jiuzhe.hotel.service.SkuSearchService;
import com.jiuzhe.hotel.service.SqlService;
import com.jiuzhe.hotel.utils.CheckUtil;
import com.jiuzhe.hotel.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:订单实现层
 * @author:郑鹏宇
 * @date:2018/4/23
 */
@Service
public class HotelorderServiceImpl implements HotelorderService {
    private static final Logger logger = LoggerFactory.getLogger(HotelorderServiceImpl.class);
    @Autowired
    private HotelOrderDao hotelOrderDao;
    private
    @Autowired
    HotelorderService hotelorderService;
    @Autowired
    SkuSearchService skuSearchService;
    @Autowired
    RoomReservationService roomReservationService;

    @Value("${hotel.order.paied.expired-time}")
    private int paidExpriedtime;

    @Value("${hotel.order.paied.cancel-time}")
    private int paidCanceltime;

    /**
     * @Description:创建订单
     * @author:郑鹏宇
     * @date:2018/4/23
     */
    @Override
    public OrderSuccessDto creatHotelOrder(HotelOrderQuery query) {
        OrderSuccessDto orderSuccessDto = new OrderSuccessDto();

        HotelOrder order = HotelOrder.make(query);
        // 订单信息
//        order.setMerchantId(skuSearchQueryDao.getMerchantById(query.getSkuId()));
        order.setPaiedExpired(LocalDateTime.now().plusMinutes(paidExpriedtime));
        //给返回前台的对象赋值
        orderSuccessDto.setMerchantId(order.getMerchantId());
        orderSuccessDto.setOrderId(order.getId());
        orderSuccessDto.setSkuId(order.getSkuId());
        //如果是开房，不需要校验
        //这里需要校验前台给的价格和数据库价格是否一致
        Boolean checkPrice = this.checkPrices(order);
        //如果是商户开房则默认为true
//        if (0 == order.getOnLine()) {
//            checkPrice = true;
//        }
        if (checkPrice) {
            this.creatHotelOrder(order, this.hotelOrderDao);
        } else {
            throw new RuntimeException("price is erro");
        }
        return orderSuccessDto;
    }

    /**
     * @Description:查询当天某一个状态的订单数
     * @author: 郑鹏宇
     * @date 2018/8/14/014
     */
    @Override
    public Integer getOrderNumByStatus(String userId, Integer status) {
        return hotelOrderDao.getOrderNumByStatus(userId, status);
    }

    /**
     * @Description:查房态的转化接口
     * @author: 郑鹏宇
     * @date 2018/8/15/015
     */
    @Override
    public Map getReservation(String skuId, String beginDate, String endDate) {
        String endYestDay = TimeUtil.getNextDay(endDate, -1);
        Map map = new HashMap();
        if (TimeUtil.daysBetween(beginDate, endDate) == 1) {
            map = roomReservationService.getReservation(skuId, beginDate);
        } else {
            map = roomReservationService.getReservation(skuId, beginDate, endYestDay);
        }
        return map;
    }

    @Override
    public void setReservation(String skuId, String beginDate, String endDate) {
        String endYestDay = TimeUtil.getNextDay(endDate, -1);
        if (TimeUtil.daysBetween(beginDate, endDate) == 1) {
            roomReservationService.setReservation(skuId, beginDate);
        } else {
            roomReservationService.setReservation(skuId, beginDate, endYestDay);
        }
    }

    @Override
    public void unsetReservation(String skuId, String beginDate, String endDate) {
        String endYestDay = TimeUtil.getNextDay(endDate, -1);
        if (TimeUtil.daysBetween(beginDate, endDate) == 1) {
            roomReservationService.unsetReservation(skuId, beginDate);
        } else {
            roomReservationService.unsetReservation(skuId, beginDate, endYestDay);
        }
    }

    @Override
    public Map sendDelay15Min(String orderId, String skuId, String beginDt, String endDt) {
//        String endYestDay = TimeUtil.getNextDay(endDt, -1);
//        if (TimeUtil.daysBetween(beginDt, endDt) == 1) {
//            return rabbitMQService.sendDelay15Min(orderId, skuId, beginDt);
//        } else {
//            return rabbitMQService.sendDelay15Min(orderId, skuId, beginDt, endDt);
//        }
//        TODO mq
        return null;
    }

    /**
     * @Description:创建订单的方法
     * @author:郑鹏宇
     * @date:2018/4/23
     */
    @Transactional(rollbackFor = Exception.class)
    public void creatHotelOrder(HotelOrder order, HotelOrderDao hotelOrderDao) {
        order.setMerchantId("jiuzheceshi");
        hotelOrderDao.createHotelOrder(order);
        //创建成功发送消息队列
//        if (0 != order.getOnLine()) {
//            Map map = hotelorderService.sendDelay15Min(order.getId(), order.getSkuId(), order.getStartDate().toString(), order.getEndDate().toString());
//            if (!"0".equals(map.get("status"))) {
//                logger.error("消息队列发送失败");
//                throw new RuntimeException();
//            }
//        }
        setReservation(order.getSkuId(), order.getStartDate().toString(), order.getEndDate().toString());
    }

    /**
     * @Description:根据id获取订单详情
     * @author:郑鹏宇
     * @date:2018/4/14
     */
    @Override
    public HotelOrder getOrderById(String id) {
        HotelOrder hotelOrder = hotelOrderDao.getOrderById(id);
//        if (null != hotelOrder) {
//            //TODO 获取照片
//            Map<String, String> imgMap = hotelOrderDao.getImgsBySkuId(hotelOrder.getSkuId());
//            List<String> imgs = StringUtil.stringToList(imgMap.get("imgurls"));
//            hotelOrder.setSkuImg(imgs.get(0));
//        }
        return hotelOrder;
    }

    /**
     * @Description:修改订单状态(订单过期为单独方法)（已支付，发起退款）
     * @author:郑鹏宇
     * @date:2018/4/16
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeStatus(String id, Integer status) {
        //根据订单id获取订单信息，主要是获取房间信息skuId
        HotelOrder order = hotelOrderDao.getOrderById(id);
        //主动申请退押金（5>>6）(将订单状态5改为6)（退房，同时将房间状态变成脏房）
        if (status == OrderStatusEnum.APPLY.getIndex()) {
            hotelOrderDao.upRoomStatus(order.getSkuId(), RoomStatusEnum.DIRTY.getIndex());
        }
        //押金审核成功（6>>7）(将订单状态6改为7)(评论完成将7改成8)
        //支付完毕（1>>3）(需要加入退款时限paidCancelTime)
        LocalDateTime paidCancelTime = null;
        if (status == OrderStatusEnum.PAID.getIndex()) {
            paidCancelTime = LocalDateTime.now().plusMinutes(paidCanceltime);
        }
        //评论完成，退押金
        if (status == OrderStatusEnum.END.getIndex()) {
            //修改状态（不共用，利于事务回滚）
            int backBond = hotelOrderDao.upOrderStatusById(id, status, paidCancelTime);
            int skuPrice = order.getSkuPrice() * 100;
            int skuBond = order.getSkuBond() * 100;
            String userId = order.getUserId();
            String amount = String.valueOf(skuPrice + skuBond);
            //发送退押金的请求
            //退钱退到账户余bond额上去
            upUserAmount(userId, amount);
            return backBond;
        }
        return hotelOrderDao.upOrderStatusById(id, status, paidCancelTime);
    }

    /**
     * @Description:取消订单状态
     * @author:郑鹏宇 】
     * ‘
     * @date:2018/4/25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelOrder(OrderCancelQuery query) {
        //判定该订单是否已经付款（是否需要退款）
        HotelOrder order = hotelOrderDao.haveOrserPaid(query.getId(), OrderStatusEnum.PAID.getIndex());
        if (null != order) {
            //如果订单开始日期是当天，则不许退款
            if (LocalDate.now().equals(order.getStartDate())) {
                return CommonConstant.PAID_CANCEL_TIME;
            }
            //如果订单规定的退款时间已经结束，则不许退款
            if (!CheckUtil.checkWithNow(order.getPaidCancelTime())) {
                return CommonConstant.PAID_CANCEL_TIME;
            }
            //如果可以取消订单，则发送退钱的请求(首先需要释放房间)
            hotelOrderDao.upRoomStatus(query.getSkuId(), RoomStatusEnum.ON_SALE.getIndex());
            Integer bond = order.getSkuBond() * 100;
            String userId = order.getUserId();
            Integer skuPrice = order.getSkuPrice() * 100;
            String amount = String.valueOf(skuPrice + bond);
            //退钱退到账户余bond额上去
            upUserAmount(userId, amount);
            //发送请求钱需要修改数据库
            int back = hotelOrderDao.upOrderStatusById(query.getId(), OrderStatusEnum.CANCEL.getIndex(), null);
            return back;
        }
        //修改订单状态（为订单取消)
        return hotelOrderDao.upOrderStatusById(query.getId(), OrderStatusEnum.CANCEL.getIndex(), null);
    }

    /**
     * @Description:获取倒计时
     * @author:郑鹏宇
     * @date:2018/4/26
     */
    @Override
    public Integer getCountDown(String id) {
        LocalDateTime localDateTime = hotelOrderDao.getCountDown(id);
        Duration duration = Duration.between(LocalDateTime.now(), localDateTime);
        int a = (int) duration.toMillis();
        int b = a / 1000;
        if (b < 0) {
            b = 0;
        }
        return b;
    }

    /**
     * @Description:根据用户id获取订单信息
     * @author:郑鹏宇
     * @date:2018/4/23
     */
    @Override
    public List<HotelOrder> getOrderbyUserId(String userId) {
        PaidToLived paidToLived = new PaidToLived();
        paidToLived.setUserId(userId);
        //先刷新用户已入住状态
        this.changStatusToLived(paidToLived);
        List<HotelOrder> hotelOrders = hotelOrderDao.getOrdersByUserId(userId);
        return hotelOrders;
    }

    /**
     * @Description:根据商户ID来获取订单（都需要将没有入住的订单入住）
     * @author: 郑鹏宇
     * @date 2018/6/21/021
     */
    @Override
    public List<HotelOrder> getOrderbyMerchantId(String merchantId) {
        //查询之前需要刷新一次入住状态（3>>5）
        PaidToLived paidToLived = new PaidToLived();
        paidToLived.setMerchantId(merchantId);
        this.changStatusToLived(paidToLived);
        List<HotelOrder> hotelOrders = hotelOrderDao.getOrderbyMerchantId(merchantId);
        return hotelOrders;
    }

    @Override
    public List<HotelOrder> getBondOrderbyMerchantId(String merchantId) {
        List<HotelOrder> hotelOrders = hotelOrderDao.getBondOrderbyMerchantId(merchantId);
        return hotelOrders;
    }

    /**
     * @Description:查询用户所有的退押金审核订单
     * @author: 郑鹏宇
     * @date 2018/7/3/003
     */
    @Override
    public List<HotelOrder> getApplyOrderbyMerchantId(String merchantId, Integer currentPage, Integer number) {
        PageHelper.startPage(currentPage, number);
        List<HotelOrder> hotelOrders = hotelOrderDao.getApplyOrderbyMerchantId(merchantId);
        return hotelOrders;
    }


    /**
     * @Description:获取
     * @author:郑鹏宇
     * @date:2018/5/4
     */
    private Boolean checkPrices(HotelOrder order) {
        SkuDetailQuery skuDetailQuery = new SkuDetailQuery();
        skuDetailQuery.setId(order.getSkuId());
        skuDetailQuery.setStartDate(order.getStartDate().toString());
        skuDetailQuery.setEndDate(order.getEndDate().toString());
        SkuDetailDto dto = skuSearchService.getSkuFacilitys(skuDetailQuery);
        int skuPrices = 0;
        for (Integer price : dto.getPrices()) {
            skuPrices += price;
        }
        System.out.println();
        int bond1 = order.getSkuBond();
        int bond2 = dto.getRoomBond();
        if (bond1 == bond2 && order.getSkuPrice() == skuPrices) {
            return true;
        }
        return false;
    }

    /**
     * @Description:根据酒店id获取经纬度
     * @author:郑鹏宇
     * @date:2018/5/15
     */
    @Override
    public HotelSku getLngLatBySkuId(String skuId) {
        return hotelOrderDao.getLngLatBySkuId(skuId);
    }

    /**
     * @Description:将房间状态已支付变为已经入住(每天12点以后才可以触发)
     * @author:郑鹏宇
     * @date:2018/6/11
     */
    //返回值订单
    public void changStatusToLived(PaidToLived dto) {
        //每天12点以后修改当天入住状态
        LocalDateTime twelveOclock = LocalDateTime.parse(LocalDate.now().toString() + "T12:00:00.000");
        Duration duration = Duration.between(LocalDateTime.now(), twelveOclock);
        if (duration.toMillis() < 0) {
            hotelOrderDao.changePaidTolived(dto);
        }
    }

    /**
     * @Description:用户评分
     * @author: 郑鹏宇
     * @date 2018/6/26/026
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderScore(String skuId, String orderId, Integer score) {
        //将分写入订单
        hotelOrderDao.saveOrderScore(orderId, score);
        //需要计算酒店分输，同时去平均值写入酒店
        Evaluate eva = hotelOrderDao.getSkuScore(skuId);
        Integer skuScore = eva.getSkuScore();
        Integer grade = Integer.parseInt(String.valueOf(Math.round((score + skuScore) * 1.0 / 2)));
        hotelOrderDao.saveSkuScore(skuId, grade);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void evaluate(EvaluateQuery query) {
        //需要计算酒店分输，同时去平均值写入酒店
        Evaluate eva = hotelOrderDao.getSkuScore(query.getSkuId());
        Integer skuScore = eva.getSkuScore();
        Integer cleanScore = eva.getCleanScore();
        Integer grade = Integer.parseInt(String.valueOf(Math.round((query.getSkuScore() + skuScore) * 1.0 / 2)));
        Integer skuCleanGrade = Integer.parseInt(String.valueOf(Math.round((query.getCleanScore() + cleanScore) * 1.0 / 2)));
        //房间整体评分
        hotelOrderDao.saveSkuScore(query.getSkuId(), grade);
        //卫生整体评分
        hotelOrderDao.saveSkuCleanScore(query.getSkuId(), skuCleanGrade);
        hotelOrderDao.evaluate(query);
    }

    @Autowired
    SqlService sqlService;

    //修改用户金额（提现为负，充值为正）
    private void upUserAmount(String userId, String amount) {
        sqlService.init().update()
                .table("account")
                .column("total_balance")
                .valueI("total_balance" + amount)
                .column("available_balance")
                .valueI("available_balance" + amount)
                .condition("userId = ", userId)
                .modify();
    }

}
