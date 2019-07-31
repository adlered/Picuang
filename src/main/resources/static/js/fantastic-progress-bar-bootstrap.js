/**
 利用Bootstrap的进度条叠加效果，实现三个进度条叠在一起比赛前进的效果，nice!
 GitHub: AdlerED
 需要先插入HTML如下：

 <div class="progress">
    <div id="bar1" class="progress-bar progress-bar-success" style="width: 0%">
    </div>
    <div id="bar2" class="progress-bar progress-bar-warning" style="width: 0%">
    </div>
    <div id="bar3" class="progress-bar progress-bar-danger" style="width: 0%">
    </div>
 </div>

 然后调用change(progress);方法即可，例如10%就是change(10);
 */
function change(progress) {
    switch(progress) {
        case 0: setProgress(0,0,0); break;
        case 1: setProgress(1,0,0); break;
        case 2: setProgress(1,1,0); break;
        case 3: setProgress(1,1,1); break;
        case 4: setProgress(2,1,1); break;
        case 5: setProgress(2,2,1); break;
        case 6: setProgress(2,2,2); break;
        case 7: setProgress(3,2,2); break;
        case 8: setProgress(3,3,2); break;
        case 9: setProgress(3,3,3); break;
        case 10: setProgress(4,3,3); break;
        case 11: setProgress(4,4,3); break;
        case 12: setProgress(4,4,4); break;
        case 13: setProgress(5,4,4); break;
        case 14: setProgress(5,5,4); break;
        case 15: setProgress(5,5,5); break;
        case 16: setProgress(6,5,5); break;
        case 17: setProgress(6,6,5); break;
        case 18: setProgress(6,6,6); break;
        case 19: setProgress(7,6,6); break;
        case 20: setProgress(7,7,6); break;
        case 21: setProgress(7,7,7); break;
        case 22: setProgress(8,7,7); break;
        case 23: setProgress(8,8,7); break;
        case 24: setProgress(8,8,8); break;
        case 25: setProgress(9,8,8); break;
        case 26: setProgress(9,9,8); break;
        case 27: setProgress(9,9,9); break;
        case 28: setProgress(10,9,9); break;
        case 29: setProgress(10,10,9); break;
        case 30: setProgress(10,10,10); break;
        case 31: setProgress(11,10,10); break;
        case 32: setProgress(11,11,10); break;
        case 33: setProgress(11,11,11); break;
        case 34: setProgress(12,11,11); break;
        case 35: setProgress(12,12,11); break;
        case 36: setProgress(12,12,12); break;
        case 37: setProgress(13,12,12); break;
        case 38: setProgress(13,13,12); break;
        case 39: setProgress(13,13,13); break;
        case 40: setProgress(14,13,13); break;
        case 41: setProgress(14,14,13); break;
        case 42: setProgress(14,14,14); break;
        case 43: setProgress(15,14,14); break;
        case 44: setProgress(15,15,14); break;
        case 45: setProgress(15,15,15); break;
        case 46: setProgress(16,15,15); break;
        case 47: setProgress(16,16,15); break;
        case 48: setProgress(16,16,16); break;
        case 49: setProgress(17,16,16); break;
        case 50: setProgress(17,17,16); break;
        case 51: setProgress(17,17,17); break;
        case 52: setProgress(18,17,17); break;
        case 53: setProgress(18,18,17); break;
        case 54: setProgress(18,18,18); break;
        case 55: setProgress(19,18,18); break;
        case 56: setProgress(19,19,18); break;
        case 57: setProgress(19,19,19); break;
        case 58: setProgress(20,19,19); break;
        case 59: setProgress(20,20,19); break;
        case 60: setProgress(20,20,20); break;
        case 61: setProgress(31,15,15); break;
        case 62: setProgress(33,14,15); break;
        case 63: setProgress(35,14,14); break;
        case 64: setProgress(37,13,14); break;
        case 65: setProgress(39,13,13); break;
        case 66: setProgress(41,12,13); break;
        case 67: setProgress(43,12,12); break;
        case 68: setProgress(45,11,12); break;
        case 69: setProgress(47,11,11); break;
        case 70: setProgress(49,10,11); break;
        case 71: setProgress(51,10,10); break;
        case 72: setProgress(53,9,10); break;
        case 73: setProgress(55,9,9); break;
        case 74: setProgress(57,8,9); break;
        case 75: setProgress(59,8,8); break;
        case 76: setProgress(61,7,8); break;
        case 77: setProgress(63,7,7); break;
        case 78: setProgress(65,6,7); break;
        case 79: setProgress(67,6,6); break;
        case 80: setProgress(69,5,6); break;
        case 81: setProgress(71,5,5); break;
        case 82: setProgress(73,4,5); break;
        case 83: setProgress(75,4,4); break;
        case 84: setProgress(77,3,4); break;
        case 85: setProgress(79,3,3); break;
        case 86: setProgress(81,2,3); break;
        case 87: setProgress(83,2,2); break;
        case 88: setProgress(85,1,2); break;
        case 89: setProgress(87,1,1); break;
        case 90: setProgress(89,0,1); break;
        case 91: setProgress(91,0,0); break;
        case 92: setProgress(92,0,0); break;
        case 93: setProgress(92,1,0); break;
        case 94: setProgress(92,1,1); break;
        case 95: setProgress(92,2,1); break;
        case 96: setProgress(92,2,2); break;
        case 97: setProgress(93,2,2); break;
        case 98: setProgress(94,2,2); break;
        case 99: setProgress(95,3,1); break;
        case 100: setProgress(100,0,0); break;
    }
}

function setProgress(bar1, bar2, bar3) {
    $("#bar1").css("width", bar1 + "%");
    $("#bar2").css("width", bar2 + "%");
    $("#bar3").css("width", bar3 + "%");
}