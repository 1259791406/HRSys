/**
 * TODO 本功能需要layer和jquery插件的支持； 本功能为二次开发。
 * @see 1、修复最大最小年份的有效控制；
 * @see 2、修复一个月42天6行的显示问题，并加了显示判断；
 * @see 3、修复农历全部都显示初undefined；
 * @see 源文件地址：http://sc.chinaz.com/jiaobendemo.aspx?downloadid=0201785541739
 */
var layer;
var resultObj;
layui.use('layer', function () {
    layer = layui.layer;
});

function main() {


    if (typeof (layer) != "object" || !layer) {
        setTimeout("main()", 400);
        return;
    }
    var myCalendar = new SimpleCalendar('#calendar', {
        width: '100%',
        height: '100%',
        language: 'CH', //语言
        changAble: true,
        showLunarCalendar: true, //阴历
        showHoliday: false, //休假-需要改js，这里没用到先禁用
        showFestival: true, //节日
        showLunarFestival: true, //农历节日
        showSolarTerm: true, //节气
        showMark: true, //标记
        realTime: true, //具体时间
        timeRange: {
            startYear: 2020,
            endYear: 2099
        },
        mark: {},
        markColor: ['#DDDF00', '#1d38b4', '#ff0000', '#00dfdc', '#24CBE5'],
        getSelectedDay: {},
        main: function (year, month) {

            $('title').html('新南港 ' + year + ' 年 ' + month + ' 月排班');
            var resultObj = {};

            return resultObj;
        },
        theme: {
            changeAble: false,
            weeks: {
                backgroundColor: '#FBEC9C',
                fontColor: 'red',
                fontSize: '20px',
            },
            days: {
                backgroundColor: '#ffffff',
                fontColor: '#565555',
                fontSize: '24px'
            },
            todaycolor: 'orange',
            activeSelectColor: 'orange',
        }
    });

}

main();