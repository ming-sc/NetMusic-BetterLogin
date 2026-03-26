# 网络音乐机: 更好的登录

为网络音乐机模组提供更好的登录和 VIP 歌曲收听体验.

### 与网络音乐机: 登登你的相比?

|      | 登登你的            | 更好的登录                            |
|------|-----------------|----------------------------------|
| 登录方式 | 手动输入 Cookie     | 手动输入 Cookie, 扫码登录, 邮箱登录, 手机验证码登录 |
| 作用范围 | 仅登录者自身可听 VIP 歌曲 | 服务端配置 Cookie 后全服可听 VIP 歌曲        |
| 模组兼容 | 兼容网络音乐机: 播放列表模组 | 兼容车万女仆和精妙背包模组                    |

> 显然本模组不兼容登登你的和播放列表

### 使用方法

1. 输入 `/nmbl login` 打开登录界面.
2. 使用扫码登录 (推荐) , 邮箱登录或手机验证码登录方式登录.
3. 如果需要使用 Cookie 登录, 在配置文件 `config/netmusicbetterlogin-common.toml` 中设置 `Cookie` 字段,
   然后输入 `/nmbl server cookie reload` 命令 (该命令需要管理员权限) 重载 Cookie.

> **在多人游戏中使用**
>
> 如果是局域网联机, 只需房主登录即可.
>
> 如果是专用服务端 (如面板服等), 可以在客户端登录后, 用客户端的配置文件覆盖服务端的配置文件 (也可以手动复制粘贴 Cookie 到服务端配置文件),
> 然后输入 `/nmbl server cookie reload` 命令重载 Cookie.

### 命令

#### 仅客户端命令

- `/nmbl login` 打开登录界面.

#### 通用命令 (需要管理员权限)

- `/nmbl server cookie reload` 重载 Cookie.
- `/nmbl server cookie clear` 清除 Cookie.
- `/nmbl server level` 显示当前最高音质.
- `/nmbl server level set <level>` 设置最高音质.

### 安全提示

本模组的配置文件中存储了你的网易云 Cookie, 请勿将 Cookie 泄露给他人, 否则可能导致账号被盗. 如账号被盗用或被封禁,
本模组不承担任何责任. 请务必保护好你的账号安全.

### Q&A

**Q: 为什么邮箱登录/手机验证码登录提示存在风险?**

A: 网易云风控问题, 建议优先使用扫码登录

**Q: 为什么登录成功了还是听不了 VIP 歌曲 (无法找到链接为...的歌曲)?**

A: 查看使用方法部分中的 **在多人游戏中使用** , 确保正确登录.

### 许可证

本模组采用 MIT 许可证, 详情请参阅 [LICENSE](LICENSE) 文件.

Icons from [Pixel Icon Library](https://pixeliconlibrary.com) by [HackerNoon](https://hackernoon.com/)