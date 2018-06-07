package java8.grammar;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestTime {
    public static void main(String[] args) {
        System.out.println("----------------------------基本概念展示区-------------------------------");

        //协调世界时-UTC
        System.out.println("世界协调时：");
        Instant instant = Instant.now();
        System.out.println(instant);
        System.out.println(instant.getEpochSecond());//epoch:时期、纪元，从计算机元年到现在的秒
        System.out.println(Instant.EPOCH);
        System.out.println(Instant.MAX);
        System.out.println(Instant.MIN);

        //本地日期
        System.out.println("本地日期：");
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        //本地时间
        System.out.println("本地时间：");
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        //完整的时间
        System.out.println("本地完整的时间：");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        //新api与date的转换
        Date date = Date.from(instant);
        Instant instant1 = date.toInstant();

        //新api与Calendar的转换貌似是单向的。。。
        Calendar calendar = Calendar.getInstance();
        instant = calendar.toInstant();

        System.out.println("----------------------------常用时间生成方法区-------------------------------");
        System.out.println("以localDateTime为例");
        Clock clock = Clock.systemUTC();//clock , 时间类的底层时钟
        LocalDateTime now = LocalDateTime.now(clock);
        //of 方式生成
        now = LocalDateTime.of(2018,1,1,1,1,1,1);
        //with 方式指定
        now = now.withYear(2020);
        //parse 方式传入
        now = LocalDateTime.parse("2020-01-01T01:01:01");
        // now:当前时间
        now = LocalDateTime.now();
        //to 方式
        localDate = now.toLocalDate();
        localTime = now.toLocalTime();
        //get 各种时间
        System.out.println("当前时间："+now);
        System.out.println("当前年："+now.getYear());
        System.out.println("当前月："+now.getMonth());
        System.out.println("当前月："+now.getMonthValue());
        System.out.println("当前日："+now.getDayOfMonth());
        System.out.println("当前星期："+now.getDayOfWeek());
        System.out.println("当前是一年的第几天："+now.getDayOfYear());
        System.out.println("当前时："+now.getHour());
        System.out.println("当前分："+now.getMinute());
        System.out.println("当前秒："+now.getSecond());
        System.out.println("当前毫秒："+now.get(ChronoField.MILLI_OF_SECOND));//chrono:长期的
        System.out.println("当前纳秒："+now.getNano());
        System.out.println("当前时间戳1："+System.currentTimeMillis());
        System.out.println("当前时间戳2："+clock.millis());
        //判断时间
        System.out.println("是否是闰年："+localDate.isLeapYear());//时间类没这个方法
        //工具类
        localDate =localDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("当前月的最后一天："+localDate);


        System.out.println("----------------------------常用时间计算方法区-------------------------------");
        now = now.minusDays(1).plusYears(1);//减一天,加一年
        System.out.println("减一天，加一年后结果："+now);
        //period and duration
        //period 表示以年、月、日衡量的时长。例如，“3年2个月零6天”。仅能处理日期
        ///duration 表示以秒和纳秒为基准的时长。例如，“23.6秒”.仅能处理时间
        now = LocalDateTime.now();
        LocalDateTime time2020 = now.withYear(2020);//与now整整差两年
        Period period =Period.between(now.toLocalDate(),time2020.toLocalDate());
        System.out.println("2020年与现在相差："+period.getYears()+"年"+period.getMonths()+"月"+period.getDays()+"日");
        Duration duration = Duration.between(now,time2020);
        System.out.println("2020年与现在相差："+duration.getSeconds()+"秒"+period.getMonths()+"纳秒");


        System.out.println("----------------------------常用时间格式化方法区-------------------------------");
        System.out.println(now.format(DateTimeFormatter.BASIC_ISO_DATE));   //20180603
        System.out.println(now.format(DateTimeFormatter.ISO_DATE));         //2018-06-03
        System.out.println(now.format(DateTimeFormatter.ISO_DATE_TIME));    //2018-06-03T22:20:48.625
        System.out.println(now.format(DateTimeFormatter.ISO_TIME));         //22:20:48.625
        System.out.println(now.format(DateTimeFormatter.ISO_LOCAL_DATE));   //2018-06-03
        System.out.println(now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));//2018-06-03T22:20:48.625
        System.out.println(now.format(DateTimeFormatter.ISO_LOCAL_TIME));   // 22:20:48.625
        System.out.println(now.format(DateTimeFormatter.ISO_ORDINAL_DATE)); //2018-154
        System.out.println(now.format(DateTimeFormatter.ISO_WEEK_DATE));    //2018-W22-7
        //自定义
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//线程安全的
        System.out.println("自定义的本地时间："+now.format(dtf));



        System.out.println("----------------------------简单的时区处理方法区-------------------------------");
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        ZoneOffset zone0 = ZoneOffset.ofHours(0);//0时区，英国
        System.out.println("当前时区："+zoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zone0);
        System.out.println("现在是英国时间："+zonedDateTime.format(dtf));


        //总之，新api, string to time, time to string 都很方便。时间的加减处理也很简单
    }
}
