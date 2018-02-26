<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>主页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="/fly/res/layui/css/layui.css">
    <link rel="stylesheet" href="/fly/res/css/global.css">
    <link rel="stylesheet" href="/css/index.css">
</head>
<body>

<div class="fly-header layui-bg-black">
    <div class="layui-container">
        <a class="fly-logo" href="/">
            <img src="/image/logo6.png" alt="layui">
        </a>
        <ul class="layui-nav fly-nav layui-hide-xs">
            <li class="layui-nav-item layui-this">
                <a href="/"><i class="layui-icon">&#xe68e;</i>主页</a>
            </li>
            <li class="layui-nav-item">
                <a href="case/case.html"><i class="layui-icon">&#xe61d;</i>归档</a>
            </li>
            <li class="layui-nav-item">
                <a href="http://www.layui.com/" target="_blank"><i class="layui-icon">&#xe650;</i>关于</a>
            </li>
        </ul>

        <ul class="layui-nav fly-nav-user">

            <!-- 未登入的状态 -->
            <li class="layui-nav-item">
                <a class="iconfont icon-touxiang layui-hide-xs" href="user/login.html"></a>
            </li>
            <li class="layui-nav-item">
                <a href="user/login.html">登录</a>
            </li>
            <li class="layui-nav-item">
                <a href="user/reg.html">注册</a>
            </li>

            <!-- 登入后的状态 -->
            <!--
            <li class="layui-nav-item">
              <a class="fly-nav-avatar" href="javascript:;">
                <cite class="layui-hide-xs">贤心</cite>
                <i class="iconfont icon-renzheng layui-hide-xs" title="认证信息：layui 作者"></i>
                <i class="layui-badge fly-badge-vip layui-hide-xs">VIP3</i>
                <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
              </a>
              <dl class="layui-nav-child">
                <dd><a href="user/set.html"><i class="layui-icon">&#xe620;</i>基本设置</a></dd>
                <dd><a href="user/message.html"><i class="iconfont icon-tongzhi" style="top: 4px;"></i>我的消息</a></dd>
                <dd><a href="user/home.html"><i class="layui-icon" style="margin-left: 2px; font-size: 22px;">&#xe68e;</i>我的主页</a></dd>
                <hr style="margin: 5px 0;">
                <dd><a href="/user/logout/" style="text-align: center;">退出</a></dd>
              </dl>
            </li>
            -->
        </ul>
    </div>
</div>

<div class="fly-panel fly-column">
    <div class="layui-container">
        <span class="layui-breadcrumb">
          <a href="">首页</a>
          <a><cite>文章列表</cite></a>
        </span>
    </div>
</div>

<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="fly-panel">
                <blockquote class="layui-elem-quote" style="background-color: #FFFFFF">
                    <p>
                        <span style="color: #009688">
                            <i class="iconfont icon-tongzhi" title="公告"></i><b>公告：</b>
                        </span>
                        <em>
                            Welcome to my blog, QAQ```
                        </em>
                    </p>
                </blockquote>
            </div>

            <#if blogs?size == 0>
                <div style="width: 100%;margin-top: 40px;margin-bottom: 50px">
                    <hr>
                    <h3 align="center">该页面暂无博客信息</h3>
                    <hr>
                </div>
            <#else >
                <#list blogs as blog>
                    <div class="fly-panel">
                        <div class="fly-panel-title">
                            <h3>
                                <a href="${blog.url}">${blog.title}</a>
                                <span class="layui-badge layui-bg-black" style="float: right;margin: 15px">置顶</span>
                            </h3>
                        </div>

                        <div class="fly-panel-main blog_info">
                    <span style="float: right;margin-left: 10px">
                        <img style="width: 100px;height: 100px"
                             src="http://www.myxinge.cn/${blog.mainImgUrl}"
                             alt="-">
                    </span>
                            ${blog.subject}
                        </div>
                        <div class="fly-list-info" style="padding: 15px;">
                            <a href="user/home.html" link>
                                <cite>星尘</cite>
                                <i class="iconfont icon-renzheng" title=""></i>
                                <i class="layui-badge fly-badge-vip">站长</i>
                            </a>
                            <span>${blog.createtime ?string('yyyy-MM-dd HH:mm:ss')}</span>

                            <!--<span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> 60</span>-->
                            <a href="#" link><span class="layui-badge fly-badge-accept layui-hide-xs">设计模式</span></a>
                            <span style="float: right;">
                        <i class="iconfont icon-pinglun1" title="讨论"></i> 66
                    </span>
                        </div>
                    </div>
                </#list>
            </#if>
            <div class="fly-panel">
                <div style="text-align: center">
                    <div class="laypage-main"><span class="laypage-curr">1</span><a href="/blog/pe/2/">2</a><a
                            href="/blog/pe/3/">3</a><a href="/blog/pe/4/">4</a><a
                            href="/blog/pe/5/">5</a><span>…</span><a href="/blog/pe/148/" class="laypage-last"
                                                                      title="尾页">尾页</a><a
                            href="/blog/pe/2/" class="laypage-next">下一页</a></div>
                </div>
            </div>
        </div>
        <div class="layui-col-md4">


            <div class="fly-panel">
                <div class="fly-panel-title">
                    我的代码库
                </div>
                <ul class="fly-list">
                    <li style="height: auto">
                        <a href="user/home.html" class="fly-avatar">
                            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                                 alt="贤心">
                        </a>
                        <h2>
                            <a class="layui-badge">分享</a>
                            <a href="jie/detail.html">MyBlog</a>
                        </h2>
                        我的博客项目版本1.0代码
                        <div class="fly-list-info">
                            <a href="user/home.html" link>
                                <cite>贤心</cite>
                                <!--
                                <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                <i class="layui-badge fly-badge-vip">VIP3</i>
                                -->
                            </a>
                            <span>刚刚</span>

                            <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> 60</span>
                            <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
                            <span class="fly-list-nums">
                <i class="iconfont icon-pinglun1" title="回答"></i> 66
              </span>
                        </div>
                        <div class="fly-list-badge">
                            <!--<span class="layui-badge layui-bg-red">精帖</span>-->
                        </div>
                    </li>
                    <li style="height: auto">
                        <a href="user/home.html" class="fly-avatar">
                            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                                 alt="贤心">
                        </a>
                        <h2>
                            <a class="layui-badge">分享</a>
                            <a href="jie/detail.html">MyBlog</a>
                        </h2>
                        我的博客项目版本1.0代码
                        <div class="fly-list-info">
                            <a href="user/home.html" link>
                                <cite>贤心</cite>
                                <!--
                                <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                <i class="layui-badge fly-badge-vip">VIP3</i>
                                -->
                            </a>
                            <span>刚刚</span>

                            <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> 60</span>
                            <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
                            <span class="fly-list-nums">
                <i class="iconfont icon-pinglun1" title="回答"></i> 66
              </span>
                        </div>
                        <div class="fly-list-badge">
                            <!--<span class="layui-badge layui-bg-red">精帖</span>-->
                        </div>
                    </li>
                    <li style="height: auto">
                        <a href="user/home.html" class="fly-avatar">
                            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                                 alt="贤心">
                        </a>
                        <h2>
                            <a class="layui-badge">分享</a>
                            <a href="jie/detail.html">MyBlog</a>
                        </h2>
                        我的博客项目版本1.0代码
                        <div class="fly-list-info">
                            <a href="user/home.html" link>
                                <cite>贤心</cite>
                                <!--
                                <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                <i class="layui-badge fly-badge-vip">VIP3</i>
                                -->
                            </a>
                            <span>刚刚</span>

                            <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> 60</span>
                            <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
                            <span class="fly-list-nums">
                <i class="iconfont icon-pinglun1" title="回答"></i> 66
              </span>
                        </div>
                        <div class="fly-list-badge">
                            <!--<span class="layui-badge layui-bg-red">精帖</span>-->
                        </div>
                    </li>
                    <li style="height: auto">
                        <a href="user/home.html" class="fly-avatar">
                            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                                 alt="贤心">
                        </a>
                        <h2>
                            <a class="layui-badge">分享</a>
                            <a href="jie/detail.html">MyBlog</a>
                        </h2>
                        我的博客项目版本1.0代码
                        <div class="fly-list-info">
                            <a href="user/home.html" link>
                                <cite>贤心</cite>
                                <!--
                                <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                <i class="layui-badge fly-badge-vip">VIP3</i>
                                -->
                            </a>
                            <span>刚刚</span>

                            <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> 60</span>
                            <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
                            <span class="fly-list-nums">
                <i class="iconfont icon-pinglun1" title="回答"></i> 66
              </span>
                        </div>
                        <div class="fly-list-badge">
                            <!--<span class="layui-badge layui-bg-red">精帖</span>-->
                        </div>
                    </li>
                    <li style="height: auto">
                        <a href="user/home.html" class="fly-avatar">
                            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                                 alt="贤心">
                        </a>
                        <h2>
                            <a class="layui-badge">分享</a>
                            <a href="jie/detail.html">MyBlog</a>
                        </h2>
                        我的博客项目版本1.0代码
                        <div class="fly-list-info">
                            <a href="user/home.html" link>
                                <cite>贤心</cite>
                                <!--
                                <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                <i class="layui-badge fly-badge-vip">VIP3</i>
                                -->
                            </a>
                            <span>刚刚</span>

                            <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> 60</span>
                            <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
                            <span class="fly-list-nums">
                <i class="iconfont icon-pinglun1" title="回答"></i> 66
              </span>
                        </div>
                        <div class="fly-list-badge">
                            <!--<span class="layui-badge layui-bg-red">精帖</span>-->
                        </div>
                    </li>
                </ul>
            </div>

            <div class="fly-panel" style="margin-top: 0px">
                <h3 class="fly-panel-title">工具链接</h3>
                <div class="layui-tab layui-tab-card" style="margin: 0">
                    <ul class="layui-tab-title">
                        <li class="layui-this">常用</li>
                        <li>资源</li>
                        <li>工具</li>
                        <li><a href=""><span class="layui-badge layui-bg-green">更多</span></a></li>
                    </ul>
                    <div class="layui-tab-content">
                        <div class="layui-tab-item layui-show">
                            <div class="layui-breadcrumb" lay-separator="|">
                                <a href="">娱乐</a>
                                <a href="">八卦</a>
                                <a href="">体育</a>
                                <a href="">搞笑</a>
                                <a href="">视频</a>
                                <a href="">游戏</a>
                                <a href="">综艺</a>
                            </div>

                            <div class="layui-breadcrumb" lay-separator="|">
                                <a href="">娱乐</a>
                                <a href="">八卦</a>
                                <a href="">体育</a>
                                <a href="">搞笑</a>
                                <a href="">视频</a>
                                <a href="">游戏</a>
                                <a href="">综艺</a>
                            </div>

                            <div class="layui-breadcrumb" lay-separator="|">
                                <a href="">娱乐</a>
                                <a href="">八卦</a>
                                <a href="">体育</a>
                                <a href="">搞笑</a>
                                <a href="">视频</a>
                                <a href="">游戏</a>
                                <a href="">综艺</a>
                            </div>

                            <div class="layui-breadcrumb" lay-separator="|">
                                <a href="">娱乐</a>
                                <a href="">八卦</a>
                                <a href="">体育</a>
                                <a href="">搞笑</a>
                                <a href="">视频</a>
                                <a href="">游戏</a>
                                <a href="">综艺</a>
                            </div>
                        </div>
                        <div class="layui-tab-item">
                            <dl>
                                <dd>
                                    <a href="">游戏</a>
                                </dd>
                                <dd>
                                    <a href="">游戏</a>
                                </dd>
                                <dd>
                                    <a href="">游戏</a>
                                </dd>
                            </dl>
                        </div>
                        <div class="layui-tab-item">内容3</div>
                        <div class="layui-tab-item">内容4</div>
                    </div>
                </div>
            </div>

            <div class="fly-panel">
                <div class="fly-panel-title">
                    文章分类查阅
                </div>

                <div class="fly-panel-main">
                    <div class="layui-breadcrumb" lay-separator="|">
                        <a href="">设计模式</a>
                        <a href="">工具使用</a>
                        <a href="">大数据</a>
                        <a href="">底层基础</a>
                        <a href="">其他</a>
                    </div>
                    <div class="layui-breadcrumb" lay-separator="|">
                        <a href="">设计模式</a>
                        <a href="">工具使用</a>
                        <a href="">大数据</a>
                        <a href="">底层基础</a>
                        <a href="">其他</a>
                    </div>
                    <div class="layui-breadcrumb" lay-separator="|">
                        <a href="">设计模式</a>
                        <a href="">工具使用</a>
                        <a href="">大数据</a>
                        <a href="">底层基础</a>
                        <a href="">其他</a>
                    </div>
                </div>
            </div>

            <div class="fly-panel fly-link">
                <h3 class="fly-panel-title">友情链接</h3>
                <dl class="fly-panel-main">
                    <dd><a href="http://www.layui.com/" target="_blank">layui</a>
                    <dd>
                    <dd><a href="http://layim.layui.com/" target="_blank">WebIM</a>
                    <dd>
                    <dd><a href="http://layer.layui.com/" target="_blank">layer</a>
                    <dd>
                    <dd><a href="http://www.layui.com/laydate/" target="_blank">layDate</a>
                    <dd>
                    <dd>
                        <a href="mailto:xianxin@layui-inc.com?subject=%E7%94%B3%E8%AF%B7Fly%E7%A4%BE%E5%8C%BA%E5%8F%8B%E9%93%BE"
                           class="fly-link">申请友链</a>
                    <dd>
                </dl>
            </div>

        </div>
    </div>
</div>

<div class="fly-footer">
    <p><a href="http://fly.layui.com/" target="_blank">Fly社区</a>|
        <a href="http://www.layui.com/template/fly/" target="_blank">获取Fly社区模版</a>
    </p>
    <p>
    <p>
        <a href="#" target="_blank">2018© 星尘</a>|<a href="http://www.miitbeian.gov.cn/">粤ICP备17139781号</a>
    </p>
</div>

<script src="/fly/res/layui/layui.js"></script>
<script>
    layui.cache.page = '';
    layui.cache.user = {
        username: '游客'
        , uid: -1
        , avatar: '/fly/res/images/avatar/00.jpg'
        , experience: 83
        , sex: '男'
    };
    layui.config({
        version: "3.0.0"
        , base: '/fly/res/mods/' //这里实际使用时，建议改成绝对路径
    }).extend({
        fly: 'index'
    }).use('fly');
</script>

<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cspan id='cnzz_stat_icon_30088308'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "w.cnzz.com/c.php%3Fid%3D30088308' type='text/javascript'%3E%3C/script%3E"));</script>

</body>
</html>