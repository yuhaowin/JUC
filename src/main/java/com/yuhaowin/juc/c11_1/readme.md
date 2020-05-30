synchronized的底层实现
JDK早期的 重量级 - OS
后来的改进
锁升级的概念：
    [我就是厕所所长一](https://www.jianshu.com/p/b43b7bf5e052)
    [我就是厕所所长二](https://www.jianshu.com/p/16c8b3707436)

sync (Object)
markword 记录这个线程ID （偏向锁）
如果线程争用：升级为 自旋锁
10次以后，
升级为重量级锁 - OS

执行时间短（加锁代码），线程数少，用自旋
执行时间长，线程数多，用系统锁
