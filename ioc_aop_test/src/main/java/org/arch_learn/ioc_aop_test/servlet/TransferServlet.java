package org.arch_learn.ioc_aop_test.servlet;


import org.arch_learn.ioc_aop.annos.Autowired;
import org.arch_learn.ioc_aop.annos.Controller;
import org.arch_learn.ioc_aop.aop.ProxyFactory;
import org.arch_learn.ioc_aop.ioc.BeanFactory;
import org.arch_learn.ioc_aop_test.po.Result;
import org.arch_learn.ioc_aop_test.service.TransferService;
import org.arch_learn.ioc_aop_test.service.impl.TransferServiceImpl;
import org.arch_learn.ioc_aop_test.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应癫
 */
@Controller(value = "transferController")
@WebServlet(name = "transferServlet", urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {
    static {
        BeanFactory.start();
    }
//
//    @Autowired
//    private TransferService transferService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        System.out.println("------------fromCardNo:" + fromCardNo);
        String toCardNo = req.getParameter("toCardNo");
        System.out.println("------------toCardNo:" + toCardNo);
        String moneyStr = req.getParameter("money");
        System.out.println("------------money:" + moneyStr);
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {
            Object transferService = BeanFactory.getBean("transferService");
            TransferService transferServiceProxy = (TransferService) ProxyFactory.getJDKProxyObject(transferService);

            // 2. 调用service层方法
            transferServiceProxy.transfer(fromCardNo, toCardNo, money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }

        // 响应
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));
    }
}
