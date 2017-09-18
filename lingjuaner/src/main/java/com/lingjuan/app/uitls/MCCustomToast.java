package com.lingjuan.app.uitls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lingjuan.app.R;


/**
 * Created by Administrator on 2017-06-01.
 */

public class MCCustomToast extends Toast{


        public TextView tipInfo;
        public ImageView tipImage;

        public MCCustomToast(Context context) {
            super(context);

            View v1 = LayoutInflater.from(context).inflate(R.layout.jd_common_toast_style_center, null);
            this.setView(v1);
            this.tipInfo = (TextView) v1.findViewById(R.id.jd_toast_txt);
            this.tipImage = (ImageView) v1.findViewById(R.id.jd_toast_image);
            this.setGravity(17, 0, 0);
        }

        public final void setType(int type) {
            if (this.tipImage != null) {
                switch (type) {
                    case 1: {
                        this.tipImage.setBackgroundResource(R.drawable.jd_toast_exclamation);
                        return;
                    }
                    case 2: {
                        this.tipImage.setBackgroundResource(R.drawable.jd_toast_tick);
                        return;
                    }
                }
            }
        }

        public final void setText(CharSequence arg2) {
            if (this.tipInfo != null) {
                this.tipInfo.setText(arg2);
            }
        }

}
