var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
function fiveTwo(data) {
    return five(five(five(data)));
}
function five(input) {
    var output = "";
    var chr1, chr2, chr3 = "";
    var enc1, enc2, enc3, enc4 = "";
    var i = 0;
    do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);
        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;
        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
            + keyStr.charAt(enc3) + keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
    } while (i < input.length);
    return output;
};

function PowerUtil(username, password, index) {
    $.ajax({
        url: "PowerLoginUser",
        type: "post",
        datatype: "json",
        data: {
            username: fiveTwo(username),
            password: fiveTwo(password),
        },
        success: function (data) {
            console.log(data);
            layer.close(index);
            let code = data.code;
            let mes = data.mes;
            if (code == 200) {
                localStorage.setItem("PowerToken", data.PowerToken);
                location.href = 'User';
                // window.location.href="www.baidu.com";
            } else {
                parent.layer.msg(mes);
            }
        }, error: function (data) {
            layer.msg('网络发送请求出错！请尝试刷新！', {icon: 5});
        }
    });
};