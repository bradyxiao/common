package com.tencent.qcloud.sampilenotepad.module;

import com.tencent.qcloud.commonutils.Utils;

/**
 * Created by bradyxiao on 2018/7/25.
 *
 * @author bradyxiao
 * @Time 2018/7/25
 *
 * A note item:
 * title(size) author time
 */

public class NoteItem {
    public String title;
    public int size;
    public String author;
    public String timestamp;

    public String getKey(){
        String source = toString();
        return Utils.md5(source);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(title).append(",").append(size)
                .append(",").append(author).append(",").append(timestamp);
        return stringBuffer.toString();
    }
}
