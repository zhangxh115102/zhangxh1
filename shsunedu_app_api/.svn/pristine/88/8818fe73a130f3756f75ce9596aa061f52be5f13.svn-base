// doT模板获取数据
function getData(data, num) {
    var itemTText = $('#itemT' + num).html();
    var fnItemT = doT.template(itemTText);
    var html = fnItemT(data);
    $('.getDataBox' + num).append(html);

    var tabsSwiper = new Swiper('#tabs-container', {
        speed: 500,
        autoHeight: true,
        onSlideChangeStart: function() {
            $(".tabs .active").removeClass('active');
            $(".tabs a").eq(tabsSwiper.activeIndex).addClass('active');
        }
    });
    $(".tabs a").on('touchstart mousedown', function(e) {
        e.preventDefault();
        $(".tabs .active").removeClass('active');
        $(this).addClass('active');
        tabsSwiper.slideTo($(this).index());
    });
    $(".tabs a").click(function(e) {
        e.preventDefault();
    });

}

// 获取老师信息
$.ajax({
    url: 'http://10.1.88.50:8080/kuakao_api_app/course/courseDetail/',
    type: 'GET',
    dataType: 'json',
    data: {
        courseId: 36020,
        teacherId: 137
    },
    success: function(data) {
        if (data.success == 1000) {
            getData(data, 1);
            var type = data.selectCourseModel[0].courseType;

            if (type == 1) {
                var typeName = data.selectCourseModel[0].courseName;
                console.log(typeName);

                function isContains(str, substr) {
                    return new RegExp(substr).test(str);
                }
                if (isContains(typeName, '英语一')) {
                    $(".lesson_img").attr("src", "img/gongkai/0.jpg");
                }else if(isContains(typeName, '英语二')){
                    $(".lesson_img").attr("src", "img/gongkai/1.jpg");
                }else if(isContains(typeName, '数学一')){
                    $(".lesson_img").attr("src", "img/gongkai/2.jpg");
                }else if(isContains(typeName, '数学二')){
                    $(".lesson_img").attr("src", "img/gongkai/3.jpg");
                }else if(isContains(typeName, '数学三')){
                    $(".lesson_img").attr("src", "img/gongkai/4.jpg");
                }else if(isContains(typeName, '俄语')){
                    $(".lesson_img").attr("src", "img/gongkai/5.jpg");
                }else if(isContains(typeName, '日语')){
                    $(".lesson_img").attr("src", "img/gongkai/6.jpg");
                }else if(isContains(typeName, '政治')){
                    $(".lesson_img").attr("src", "img/gongkai/7.jpg");
                }else if(isContains(typeName, '管综')){
                    $(".lesson_img").attr("src", "img/gongkai/8.jpg");
                }else if(isContains(typeName, '经综')){
                    $(".lesson_img").attr("src", "img/gongkai/9.jpg");
                }



            } else if (type == 2) {
                var n = parseInt(5 * Math.random());
                $(".lesson_img").attr("src", "img/zhuanye/" + n + ".jpg");
            } else if (type == 4) {
                $(".lesson_img").attr("src", "img/biaozhun/0.jpg");
            }
        }
    }
});


// 获取课程介绍数据
$.ajax({
    url: 'http://10.1.88.50:8080/kuakao_api_app/course/courseIntroduce',
    type: 'GET',
    dataType: 'json',
    data: {
        teacherId: 521
    },
    success: function(data) {
        if (data.success == 1000) {
            getData(data, 2);
        }
    }
});

// 获取老师介绍数据
$.ajax({
    url: 'http://10.1.88.50:8080/kuakao_api_app/teacherMainPage/introduction',
    type: 'GET',
    dataType: 'json',
    data: {
        teacherId: 521
    },
    success: function(data) {
        if (data.success == 1000) {
            getData(data, 3);
        }
    }
});



// 获取课程评价数据
var pageNum = 1;
var dataLength = 5;
$.ajax({
    url: 'http://10.1.88.50:8080/kuakao_api_app/teacherCourse/courseComment',
    type: 'GET',
    dataType: 'json',
    data: {
        "courseId": 2081,
        'teacherId': 312,
        'currentPage': pageNum,
        'pageCount': dataLength
    },
    success: function(data) {
        if (data.success == 1000) {
            getData(data, 4);

            // 修改时间戳
            function changeTime(time) {
                // 获取的是毫秒数
                var time = new Date(time);
                // 年
                var year = time.getFullYear();
                // 月
                var month = time.getMonth();
                // 日
                var day = time.getDay();
                // 星期
                var xingqu = time.getDate();
                return {
                    year: year,
                    month: checkTime(month),
                    day: checkTime(day)
                };
            }

            function checkTime(n) {
                return n < 10 ? '0' + n : n;
            }
            for (var i = 0; i < data.comments.length; i++) {
                $('.plDate').each(function(i) {
                    $('.plDate').eq(i).html(changeTime(data.comments[i].time).year + '-' + changeTime(data.comments[i].time).month + '-' + changeTime(data.comments[i].time).day);
                });

            }
        }
    }
});
