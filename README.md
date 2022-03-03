# custom_ioc_aop
# hello

# 项目结构：ioc_aop是一个类似于spring的粗糙框架，依赖于上个模块学习的自定义orm框架（上个模块的代码我拿到这里来使用）。
# 测试工程是ioc_aop_test，依赖于ioc_aop。使用postman调用接口转账，在付账账户和收账账户之间添加异常代码(int i = i /0)，
# 分别在接口方法transfer上添加自定义注解@Transactional和去除自定义注解@Transactional,观察数据库是否回滚。