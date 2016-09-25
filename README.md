# Animation
### 1、前言
在我们体验一款APP时，炫酷的动画往往能让用户体验大幅度提升。想当年我刚学`Android`的时候，无意中看到蘑菇街购物车的动画效果，把我给激动得，非要在自己的APP中加入那动画，记得当时用费了好大的劲...不提了，说多了都是泪...

先了解下，目前可以实现动画的方式有：
- **帧动画**（`Frame`） ：将一个完整的动画拆分成一张张单独的图片，然后再将它们连贯起来进行播放
**特点**：帧动画 由于是一帧一帧的，所以需要图片多。会增大apk的大小，但是这种动画可以实现一些比较难的效果
比如说等待的环形进度
- **补间动画**（`Tween`）  慢慢过渡，设置初值和末值，并用插值器来控制过渡
**特点**：相对也比较简单，页面切换的动画多用这个来做。缺点，视觉上
上变化，并不是真正的位置上的变化。
- **属性动画**（`Property`）   控制属性来实现动画。
**特点**：最为强大的动画，弥补了补间动画的缺点，实现位置+视觉的变化。并且可以自定义插值器，实现各种效果

### 2、实现（Java）
我想，与其看文字介绍还不如直接看效果和代码。先来个组合的动画效果。
![组合动画](http://upload-images.jianshu.io/upload_images/1638147-f92847f326bcc3e1.gif?imageMogr2/auto-orient/strip)
虽然不是很炫酷，不过这个效果包含多个基本动画。老规矩，从基础的开始一点一点来。
这里主要介绍`ObjectAnimator`的用法。
#### 2.1、透明度
刚刚演示的效果中，一开始有个变透明的过程，来看看单纯的变透明怎么写。
> 透明度由0～1表示。0表示完全透明，1表示不透明

- **例**：在1s内，将`imageView`的透明度从`1`变成`0`。
```java
//透明度起始为1，结束时为0
ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f);
animator.setDuration(1000);//时间1s
animator.start();
```
`ofFloat`中的参数：
`imageView`：执行动画的`View`;
`"alpha"`：表示透明动画；
`1f`：起始透明度；
`0f`：动画结束后的透明度；
还可以省略`1f`，写成下面这样
（注：只有执行一次动画的时候才可以这么写。这么说还可以执行多次？不急，往下看～～）
```java
ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 0f);
```
效果：
![alpha](http://upload-images.jianshu.io/upload_images/1638147-037cfd6ddce95fda.gif?imageMogr2/auto-orient/strip)
从效果可以看出，动画完成后，`imageView`就直接变透明了，回都回不来。从这也能猜出，属性动画直接改变了视图的属性。
除了把透明度从`1`变成`0`，`ObjectAnimator`还支持多个动画。

- **例**：在2s内，将`imageView`的透明度从`1`变成`0`然后再变成`1`。
```java
ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f, 1f);
animator.setDuration(2000);
animator.start();
```
这里只要多加个参数，表示下一次动画。
效果：
![alpha](http://upload-images.jianshu.io/upload_images/1638147-093a31890db03d41.gif?imageMogr2/auto-orient/strip)
这里执行了两次动画，可以看出，2s的时间平均分配给了这两次动画。
**如果你想执行三次、四次...动画，只要在后面多加几个参数就可以了。这也适用于其他的几个动画效果：旋转、移动、缩放**
>如果你想让它一直**重复**的话，可以使用`ObjectAnimator`提供的`setRepeatCount(int count)`。`count`为重复次数，`-1`表示一直重复。
```java
animator.setRepeatCount(-1);
```

#### 2.2、旋转
- **例**：在2s内，顺时针旋转360度，然后再逆时针旋转360度。
```java
ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f, 0f);
animator.setDuration(2000);
animator.start();
```
效果：
![rotation](http://upload-images.jianshu.io/upload_images/1638147-b0f21709b7746804.gif?imageMogr2/auto-orient/strip)
**注：
下个度数大于上个度数，顺时针旋转；下个度数小于上个度数，逆时针旋转。**
如：`0f` -> `360f` ，顺时针； `360f` -> `0f`，逆时针。

#### 2.3、移动
- **例**：在2s内，沿x轴左移300个像素，然后再右移300个像素
```java
ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, -300f, 0f);
animator.setDuration(2000);
animator.start();
```
效果：
![translationX](http://upload-images.jianshu.io/upload_images/1638147-c6333bc5e5e5f215.gif?imageMogr2/auto-orient/strip)
这里的移动分为沿`x、y`轴移动，沿x轴时使用`translationX`，沿`y`轴移动使用`translationY`。
**注：
translationX：下个位置大于上个上个位置时，向右移动，反之向左移动；
translationY：下个位置大于上个上个位置时，向下移动，反之向上移动。**
如：
`translationX`：`0f`-> `-300f`，向左；`-300f`-> `0f`，向右。

#### 2.4、缩放
**例**：在2s内，沿x轴放大成原来的两倍，然后缩小会原样。
```java
ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 2f, 1f);
animator.setDuration(2000);
animator.start();
```
效果：
![scaleX](http://upload-images.jianshu.io/upload_images/1638147-8a3cf0aacce2bece.gif?imageMogr2/auto-orient/strip)
缩放和移动相似，也分为沿`x、y`轴来放缩。沿x轴缩放使用`scaleX`，沿`y`轴缩放使用`scaleY`。
**注：
后面的参数表示倍数，1f表示原来的大小，以此推类：2f表示两倍、3f表示三倍**
如：`1f`-> `2f`，放大成原来的两倍；`2f`-> `1f`，从两倍变为原样。

#### 2.5组合动画
如果只有这些基本动画是无法满足我们实际的应用的，所以还有个类`AnimatorSet`，专门来组合这些动画。
`AnimatorSet`：这个类提供了一个play()方法，调用后将会返回一个`AnimatorSet.Builder`的实例，`AnimatorSet.Builder`中包括以下四个方法：
-  `after(Animator anim)` ：将现有动画插入到传入的动画之后执行
-  `after(long delay)` ：将现有动画延迟指定毫秒后执行
-  `before(Animator anim)`： 将现有动画插入到传入的动画之前执行
-  `with(Animator anim)` ：将现有动画和传入的动画同时执行

接下来看看应该怎么使用。
**例**：在3s内，沿`x、y`轴同时放大，然后缩小，在缩放的同时还要改变透明度。然后再完成3s的左右移动。
```java
//沿x轴放大
ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 2f, 1f);
//沿y轴放大
ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 2f, 1f);
//移动
ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f, 0f);
//透明动画
ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f, 1f);
AnimatorSet set = new AnimatorSet();
//同时沿X,Y轴放大，且改变透明度，然后移动
set.play(scaleXAnimator).with(scaleYAnimator).with(animator).before(translationXAnimator);
//都设置3s，也可以为每个单独设置
set.setDuration(3000);
set.start();
```
效果：
![组合动画](http://upload-images.jianshu.io/upload_images/1638147-e3abfb0dab4d44f6.gif?imageMogr2/auto-orient/strip)
也可以设置延迟执行
```java
animator.setStartDelay(1000);//延迟1000ms后执行,需要在start()前调用
```
#### 2.6、监听事件
在动画执行前，还可以为动画添加监听事件。
```java
        //添加监听事件
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //动画开始的时候调用
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //画结束的时候调用
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //动画被取消的时候调用
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //动画重复执行的时候调用

            }
        });
```
这样是不是太费事了？有时候，我们只想监听其中的某个事件，其他的我们并不关心。官方还是很人性化得为我们提供了另一个类：`AnimatorListenerAdapter`，在这个类中，只要重写我们想要的监听事件就可以了。
```java
//另一种设置监听的方式，里面的监听方法可以选择性重写
set.addListener(new AnimatorListenerAdapter() {
    @Override
    public void onAnimationStart(Animator animation) {

    }
});
```
### 3、实现（XML）
不仅可以在Java代码中实现属性动画，属性动画和过去的补间动画一样，都可以在XML中编写。在XML中编写好，在某些情况下还能重用。
首先要在`res`下创建一个`animator`文件夹
> 注意：是`animator`，不是`anim`

在`XML`中：
`ValueAnimator`   ——> `<animator>`
`ObjectAnimator`  ——> `<objectAnimator>`
`AnimatorSet `      ——> `<set>`

#### 3.1、透明度
**例**：2s内，透明度从`0.5f`变为`1f`，且一直重复动画，重复模式为`reverse`
 - 在`animator`文件夹下，创建`alpha.xml`文件：
```xml
<?xml version="1.0" encoding="utf-8"?>
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="2000"
    android:propertyName="alpha"
    android:repeatCount="-1"
    android:repeatMode="reverse"
    android:valueFrom="0.5"
    android:valueTo="1"
    android:valueType="floatType" />
```
`android:duration`：动画持续时间；
`android:propertyName`：动画类型；
`android:repeatCount`：重复次数，-1为一直重复；
`android:repeatMode`：重复模式：`reverse`（从结束的位置继续）， `restart`（从新开始）；
`android:valueFrom`：起始值；
`android:valueTo`：结束值。
`android:valueType`：值类型
 - 在Java中调用：
```java
Animator animator = AnimatorInflater.loadAnimator(this, R.animator.alpha);
animator.setTarget(imageView);
animator.start();
```
这样就能实现之前的透明效果，图片我就不贴了


#### 3.2、旋转
**例**：在2s内，顺时针旋转360度
- 在`animator`文件夹下，创建`rotation.xml`文件：
```xml
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"
    android:valueFrom="0"
    android:valueTo="360"
    android:duration="2000"
    android:propertyName="rotation"
    android:valueType="floatType"/>
```
这里就将`android:propertyName`的值改为`rotation`
- 在Java中的调用跟上面的相似。

#### 3.3、移动
**例**：在2s内，沿x轴向右移动200px
- 在`animator`文件夹下，创建`translation_x.xml`文件：
```xml
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="2000"
    android:propertyName="translationX"
    android:repeatCount="-1"
    android:repeatMode="reverse"
    android:valueFrom="0"
    android:valueTo="200" 
    android:valueType="floatType"/>
```
这里就将`android:propertyName`的值改为`translationX`，若沿`y`轴，使用`translationY`即可。
- 在Java中的调用跟上面的相似。

#### 3.4、缩放
**例**：在2s内，沿x轴放大成1.5倍
- 在`animator`文件夹下，创建`scale_x.xml`文件：
```xml
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="2000"
    android:propertyName="scaleX"
    android:valueFrom="1"
    android:valueTo="1.5"
    android:valueType="floatType" />
```
这里就将`android:propertyName`的值改为`scaleX`，若沿`y`轴，使用`scaleY`即可。
- 在Java中的调用跟上面的相似。

#### 3.5、组合动画
**例**：完成这样的移动动画效果：向左移动并旋转，然后回到原来的位置，接着向右移动并旋转，然后回到原来的位置。效果如下：
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:ordering="sequentially">
    <set>
        <objectAnimator
            android:duration="4000"
            android:propertyName="rotation"
            android:repeatMode="reverse"
            android:valueFrom="0"
            android:valueTo="360" />
        <set android:ordering="sequentially">
            <objectAnimator
                android:duration="2000"
                android:propertyName="translationX"
                android:repeatMode="reverse"
                android:valueFrom="0"
                android:valueTo="200" />
            <objectAnimator
                android:duration="2000"
                android:propertyName="translationX"
                android:repeatMode="reverse"
                android:valueFrom="200"
                android:valueTo="0" />
        </set>
    </set>
    <set >
        <objectAnimator
            android:duration="4000"
            android:propertyName="rotation"
            android:repeatMode="reverse"
            android:valueFrom="0"
            android:valueTo="360" />
        <set android:ordering="sequentially">
            <objectAnimator
                android:duration="2000"
                android:propertyName="translationX"
                android:repeatMode="reverse"
                android:valueFrom="0"
                android:valueTo="-200" />
            <objectAnimator
                android:duration="2000"
                android:propertyName="translationX"
                android:repeatMode="reverse"
                android:valueFrom="-200"
                android:valueTo="0" />
        </set>
    </set>
</set>
```
这里需要说下`set`中的属性`android:ordering`：规定了这个`set`中的动画的执行顺序，包括：
`together`（默认）：`set`中的动画同时执行 
`sequentially`：`set`中的动画按顺序执行
