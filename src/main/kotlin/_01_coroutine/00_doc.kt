package _01_coroutine

/**
 * 携程文档
 * [https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md#using-in-your-projects]
 *
 * ================================================================================================
 * IDE 支持
 *
 * 1. 添加依赖
 *     # Maven (略)
 *     # Gradle (当前版本1.7.3, 可根据自身需求替换)
 *     dependencies {
 *         implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
 *     }
 *
 * 2. 确保 kotlin 版本支持
 *     # Groovy DSL (略)
 *     # kotlin DSL
 *     plugins {
 *         kotlin("jvm") version "1.9.21"
 *     }
 *
 * 3. 确保代码仓库支持
 *     repositories {
 *         mavenCentral()
 *     }
 *
 * ================================================================================================
 * 重要对象
 *
 * 1. Coroutine Builders
 *     每一个 Coroutine Builders 是一个函数, 它接受一个 suspend 的 lambda 表达式并创建一个协程来运行它
 *     常见的有 launch, async, runBlocking, produce, actor 等等
 *
 * 2. 挂起函数
 *
 * 3. 上下文 & 调度
 *
 * 4. 协程的作用域
 *    CoroutineScope: 可以通过创建一个 CoroutineScope 对象来创建协程;
 *    launch, async 都是 CoroutineScope 的扩展函数;
 *    GlobalScope 是 CoroutineScope 的实现类, 用于构建最顶层的协程, 这些协程的生命周期会伴随整个应用
 *
 */